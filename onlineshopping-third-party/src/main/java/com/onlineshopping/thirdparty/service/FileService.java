package com.onlineshopping.thirdparty.service;


import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String upload(MultipartFile file);

    String test();
}
