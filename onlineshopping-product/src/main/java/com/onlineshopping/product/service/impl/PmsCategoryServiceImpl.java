package com.onlineshopping.product.service.impl;

import com.onlineshopping.product.entity.PmsCategory;
import com.onlineshopping.product.feign.RedisFeignService;
import com.onlineshopping.product.mapper.PmsCategoryMapper;
import com.onlineshopping.product.aop.GmallCache;
import com.onlineshopping.product.service.PmsCategoryService;
import com.onlineshopping.product.vo.Catelog2Vo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PmsCategoryServiceImpl implements PmsCategoryService {

    @Resource
    private PmsCategoryMapper pmsCategoryMapper;

    @Autowired
    private RedisFeignService redisUtil;


    @Override
    public int deleteByPrimaryKey(Long catId) {
        return pmsCategoryMapper.deleteByPrimaryKey(catId);
    }

    @Override
    public int insert(PmsCategory record) {
        return pmsCategoryMapper.insert(record);
    }

    @Override
    public int insertSelective(PmsCategory record) {
        return pmsCategoryMapper.insertSelective(record);
    }

    @Override
    public PmsCategory selectByPrimaryKey(Long catId) {
        return pmsCategoryMapper.selectByPrimaryKey(catId);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsCategory record) {
        return pmsCategoryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PmsCategory record) {
        return pmsCategoryMapper.updateByPrimaryKey(record);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public List<PmsCategory> selectlist() {
        return pmsCategoryMapper.list();
    }

    /**
     * 查询所有数据并组装成父子结构
     *
     * @return
     */
    @Override
    public List<PmsCategory> seletcListTree() {
        //查出所有数据
        List<PmsCategory> categoryList = pmsCategoryMapper.list();
        List<PmsCategory> productLevel1 = categoryList.stream().filter((PmsCategory) ->
                PmsCategory.getParentCid() == 0
        ).map((menu) -> {
            menu.setChildren(getChildrens(menu, categoryList));
            return menu;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
        return productLevel1;
    }

    @Override
    public int updateShowStatusList(List ids) {
        return pmsCategoryMapper.updateShowStatusList(ids);

    }

    @Override
    public List<PmsCategory> getLevelCategorys(Long parentId) {
        //查询parentId对应的分类信息
        return pmsCategoryMapper.selectByParentId(parentId);
    }

    @Override
    @GmallCache(prefix = "catalogJSON")
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        String key="catalogJSON";

         return getDataFromDB(key);
    }

    private Map<String, List<Catelog2Vo>> getDataFromDB(String key) {
        /**
         * 将数据库的多次查询变成一次查询
         */
        List<PmsCategory> pmsCategories = pmsCategoryMapper.selectAll();
        System.out.println("从数据库中查询数据.........");
        //查出所有一级分类
        List<PmsCategory> level1Categorys = getLevelCategorys(pmsCategories, 0L);
        Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), l1 -> {
            //查询每个一级分类下的二级分类
            List<PmsCategory> categoryList1 = getParentId(pmsCategories, l1.getCatId());
            List<Catelog2Vo> catelog2VoList = null;
            if (!categoryList1.isEmpty()) {
                catelog2VoList = categoryList1.stream().map(level2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(l1.getCatId().toString(), null, level2.getCatId().toString(), level2.getName());
                    //查询每个二级分类下的三级分类
                    List<PmsCategory> categoryList3 = getCategoryList3(pmsCategories, level2.getCatId());
                    if (!categoryList3.isEmpty()) {
                        List<Catelog2Vo.Catelog3Vo> catelog3Vos = categoryList3.stream().map(level3 -> {
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(level2.getCatId().toString(), level3.getCatId().toString(), level3.getName());
                            return catelog3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3list(catelog3Vos);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2VoList;
        }));
        //将查询出的数据放入缓存
        //缓存失效时间(解决缓存雪崩)
        Random random = new Random();
        long randomTime = random.nextInt(60 * 60 * 24);
        long time = TimeUnit.SECONDS.convert(1, TimeUnit.DAYS) + randomTime;
        //加入缓存
        if (parent_cid.isEmpty()) {
            redisUtil.hmset("catalogJSON", null, time);
        } else {
            redisUtil.hmset("catalogJSON",  parent_cid,time);
        }
        return parent_cid;
    }
    //查询一级分类
    private List<PmsCategory> getLevelCategorys(List<PmsCategory> pmsCategories, Long parentId) {
        return pmsCategories.stream().filter(item -> item.getParentCid() == parentId).collect(Collectors.toList());
    }

    //查询三级分类
    private List<PmsCategory> getCategoryList3(List<PmsCategory> pmsCategories, Long parentId) {
        return pmsCategories.stream().filter(item -> item.getParentCid() == parentId).collect(Collectors.toList());
    }

    //查询二级分类
    private List<PmsCategory> getParentId(List<PmsCategory> pmsCategories, Long parentId) {
        return pmsCategories.stream().filter(item -> item.getParentCid() == parentId).collect(Collectors.toList());
    }


    /**
     * 查找当前菜单的子菜单
     *
     * @param root 当前菜单
     * @param all  所有菜单
     * @return
     */
    private List<PmsCategory> getChildrens(PmsCategory root, List<PmsCategory> all) {
        List<PmsCategory> children = all.stream().filter(PmsCategory -> {
            //获取二级子菜单
            return PmsCategory.getParentCid() == root.getCatId();
        }).map(PmsCategory -> {
            //递归遍历，找到每一层的子菜单
            PmsCategory.setChildren(getChildrens(PmsCategory, all));
            return PmsCategory;
        }).sorted((menu1, menu2) -> {
            //菜单排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return children;
    }

}
