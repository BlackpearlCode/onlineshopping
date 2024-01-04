package com.onlineshopping.product.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.product.entity.PmsCategory;
import com.onlineshopping.product.service.impl.PmsCategoryBrandRelationServiceImpl;
import com.onlineshopping.product.service.impl.PmsCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("product/category")
public class PmsCategoryController {

    @Autowired
    private PmsCategoryServiceImpl categoryService;

    @Autowired
    private PmsCategoryBrandRelationServiceImpl brandRelationService;

    @RequestMapping("/list")
    public List<PmsCategory> list(){
        return categoryService.selectlist();
    }

    @RequestMapping("/info/{catId}")
    public String findById(@PathVariable("catId") String id) throws JSONException {
        JSONObject json=new JSONObject();
        PmsCategory category = categoryService.selectByPrimaryKey(Long.valueOf(id));
        json.put("data",category);
        return json.toString();
    }

    @RequestMapping("/list/tree")
    public String listTree() throws JsonProcessingException, JSONException {
        JSONObject json=new JSONObject();
        List<PmsCategory> list = categoryService.seletcListTree();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString=mapper.writeValueAsString(list);
        List categoryList = mapper.readValue(jsonString, List.class);
        json.put("data",categoryList);
        return json.toString();
    }

    @RequestMapping("/delete")
    public String delete(@RequestBody Long[] catIds) throws JSONException {
        JSONObject json=new JSONObject();
        int num = categoryService.updateShowStatusList(Arrays.asList(catIds));
        if(num>0){
            brandRelationService.deleteByCatelogId(Arrays.asList(catIds));
            json.put("200","删除成功");
        }else{
            json.put("500","删除失败");
        }
        return json.toString();
    }

    @RequestMapping("/save")
    public String save(@RequestBody PmsCategory category) throws JSONException {
        int num = categoryService.insert(category);
        JSONObject json=new JSONObject();
        if(num>0){
            json.put("200","保存成功");
        }else{
            json.put("500","保存失败");
        }
        return json.toString();
    }

    @RequestMapping("/update")
    public Result update(@RequestBody PmsCategory category){
        PmsCategory categoryOld = categoryService.selectByPrimaryKey(category.getCatId());
        if(!categoryOld.getName().equals(category.getName())){
            brandRelationService.updateByCatelogId(category.getCatId(), category.getName());
        }
        int num = categoryService.updateByPrimaryKeySelective(category);
        if(num>0){
            return Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg());

        }else{
            return Result.r(BizCodeEnum.ERROR.getCode(),BizCodeEnum.ERROR.getMsg());
        }

    }
}
