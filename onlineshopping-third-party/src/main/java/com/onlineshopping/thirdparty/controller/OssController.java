package com.onlineshopping.thirdparty.controller;


import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.thirdparty.service.impl.OssServerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

@RestController("api/thirdparty")
public class OssController {

    @Autowired
    private OssServerServiceImpl ossServerUploadService;

    //服务端签名直传
    @RequestMapping("/oss/policy")
    public Result policy(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> ossMap = ossServerUploadService.doGet(request, response);
        return  Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg()).put("data",ossMap);

    }

    //批量删除文件
    @RequestMapping("/ossBatchDelete")
    public Result delete(@RequestBody List<String> filePaths){
        ossServerUploadService.batchDelete(filePaths);

        return Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg());

    }



}
