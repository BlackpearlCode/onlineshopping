package com.onlineshopping.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(value = "third-party")
public interface OssFileFeignService {

    @RequestMapping(value = "/upload",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(MultipartFile file);
    @RequestMapping("/test")
    String test();

    //批量删除oss对象
    @RequestMapping("/ossBatchDelete")
    void batchDelete(List<String> filePaths );
}
