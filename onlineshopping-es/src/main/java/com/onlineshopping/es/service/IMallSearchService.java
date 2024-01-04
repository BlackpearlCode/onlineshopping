package com.onlineshopping.es.service;

import com.onlineshopping.es.vo.SearchParam;
import com.onlineshopping.es.vo.SearchResult;

import java.io.IOException;

public interface IMallSearchService {
    /**
     *
     * @param param：检索的所有参数
     * @return：返回检索结果，里面包含页面的所有信息
     */

    SearchResult search(SearchParam param) throws IOException;
}
