package com.onlineshopping.thirdparty.service.impl;


import com.aliyun.oss.OSSException;
import com.onlineshopping.thirdparty.service.FileService;
import com.onlineshopping.thirdparty.tools.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileUpload fileUpload;


    @Override
    public String upload(MultipartFile file) {
        try {
            return fileUpload.fileUpload(file);
        } catch (OSSException | IOException e) {

            logger.error(e.getMessage());
        }
        return "文件上传失败";
    }

    @Override
    public String test() {
        return "调用成功";
    }
}
