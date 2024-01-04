package com.onlineshopping.thirdparty.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.onlineshopping.thirdparty.service.OssServerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OssServerServiceImpl implements OssServerService {

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    String endpoint;
    @Value("${spring.cloud.alicloud.access-key}")
    String accessKeyId;
    @Value("${spring.cloud.alicloud.secret-key}")
    String accessKeySecret;
    @Value("${spring.cloud.alicloud.bucktName}")
    String bucketName ;
    // 设置上传回调URL，即回调服务器地址，用于处理应用服务器与OSS之间的通信。OSS会在文件上传完成后，把文件上传信息通过此回调URL发送给应用服务器。
//    @Value("${spring.cloud.alicloud.callbackUrl}")
//    String callbackUrl ;
    @Override
    public Map<String,String> doGet(HttpServletRequest request, HttpServletResponse response) {
        // 填写Host地址，格式为https://bucketname.endpoint。
        String host ="https://"+bucketName+"."+endpoint;
        // 设置上传到OSS文件的前缀，文件前缀为当前日期
        LocalDate date=LocalDate.now();
        String dir = date+"/";
        // 创建ossClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        Map<String, String> respMap = new LinkedHashMap<String, String>();
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);
            respMap.put("accessid", accessKeyId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return respMap;
    }

    @Override
    public void batchDelete(List<String> filePaths) {
        OSS ossClient = new OSSClientBuilder().build("https://"+endpoint, accessKeyId,accessKeySecret);
        ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(filePaths).withEncodingType("url"));
    }


}
