package com.onlineshopping.product.web;

import com.onlineshopping.product.entity.PmsCategory;
import com.onlineshopping.product.service.PmsCategoryService;
import com.onlineshopping.product.vo.Catelog2Vo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private PmsCategoryService categoryService;
    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){
        //查询所有一级分类
        List<PmsCategory> categoryList = categoryService.getLevelCategorys(0l);
        model.addAttribute("categorys",categoryList);
        log.info("categorys的值：{}",categoryList);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson(){
        Map<String, List<Catelog2Vo>> map=categoryService.getCatalogJson();
        return map;
    }
}
