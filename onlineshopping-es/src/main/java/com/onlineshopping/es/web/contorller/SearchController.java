package com.onlineshopping.es.web.contorller;

import com.onlineshopping.es.service.IMallSearchService;
import com.onlineshopping.es.vo.SearchParam;
import com.onlineshopping.es.vo.SearchResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;

@Controller
public class SearchController {

    @Autowired
    IMallSearchService mallSearchService;
    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request) throws IOException {
        //查询字符串
        String queryString = request.getQueryString();
        param.set_queryString(queryString);
        //根据传递过来的页面参数，去es中查询商品
        SearchResult result=mallSearchService.search(param);
        model.addAttribute("result",result);
        return "list";
    }
}
