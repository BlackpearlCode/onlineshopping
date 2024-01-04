package com.onlineshopping.thirdparty.service;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public interface OssServerService {
     //服务端签名直传
     Map<String,String> doGet(HttpServletRequest request, HttpServletResponse response);

     //批量删除文件
     void batchDelete(List<String> filePaths);




}
