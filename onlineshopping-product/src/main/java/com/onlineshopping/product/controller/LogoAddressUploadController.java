package com.onlineshopping.product.controller;



import com.onlineshopping.product.feign.OssFileFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("thirdparty")
public class LogoAddressUploadController {

    @Autowired
    private OssFileFeignService upload;
    @RequestMapping("/oss/policy")
    public String upload(MultipartFile file){
        return upload.upload(file);
    }

    @RequestMapping("/test")
    public String test(){
        return upload.test();
    }
}
