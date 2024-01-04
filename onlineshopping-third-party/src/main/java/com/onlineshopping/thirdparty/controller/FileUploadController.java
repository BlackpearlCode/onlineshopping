package com.onlineshopping.thirdparty.controller;

import com.onlineshopping.thirdparty.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    @Autowired
    private FileService fileService;


    @RequestMapping("/upload")
    public String fileUpload(MultipartFile file){
        return fileService.upload(file);
    }

    @RequestMapping("/test")
    public String test(){
        return fileService.test();
    }




}
