package com.onlineshopping.thirdparty.tools;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FileUpload {
    Logger logger=  LoggerFactory.getLogger(FileUpload.class);

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    String endpoint;
    @Value("${spring.cloud.alicloud.access-key}")
    String accessKeyId;
    @Value("${spring.cloud.alicloud.secret-key}")
    String accessKeySecret;
    @Value("${spring.cloud.alicloud.bucktName}")
    String bucketName ;
    public String fileUpload(MultipartFile file) throws IOException {

        String objectName = file.getOriginalFilename();

        InputStream inputStream = file.getInputStream();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build("https"+endpoint,accessKeyId,accessKeySecret);

        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);

            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            return "文件上传成功";
        } catch (OSSException oe) {
            logger.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            logger.error("Error Message:" + oe.getErrorMessage());
            logger.error("Error Code:" + oe.getErrorCode());
            logger.error("Request ID:" + oe.getRequestId());
            logger.error("Host ID:" + oe.getHostId());
            return "文件上传失败";
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
