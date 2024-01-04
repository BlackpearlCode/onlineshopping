package com.onlineshopping.es.config;

import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

@Configuration
public class ElasticSearchConfig {
    static Logger logger=  LoggerFactory.getLogger(ElasticSearchConfig.class);
    @Value("${spring.elasticsearch.uris}")
    private String hosts;
    @Value("${spring.elasticsearch.port}")
    private int port;
    @Value("${spring.elasticsearch.username}")
    private String userName;
    @Value("${spring.elasticsearch.password}")
    private String password;





    @Bean
    public RestHighLevelClient restHighLevelClient() {
        HttpHost[] httpHosts = toHttpHost();

        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts)
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    @SneakyThrows
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(userName,password));
                        httpClientBuilder.setSSLContext(buildSSLContext());
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        return httpClientBuilder;
                    }
                });
        return new RestHighLevelClient(restClientBuilder);
    }
    private static SSLContext buildSSLContext() {
        ClassPathResource resource = new ClassPathResource("java-ca.crt");
        SSLContext sslContext = null;
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            Certificate trustedCa;
            try (InputStream is = resource.getInputStream()) {
                trustedCa = factory.generateCertificate(is);
            }
            KeyStore trustStore = KeyStore.getInstance("pkcs12");
            trustStore.load(null, null);
            trustStore.setCertificateEntry("ca", trustedCa);
            SSLContextBuilder sslContextBuilder = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, null);
            sslContext = sslContextBuilder.build();
        } catch (CertificateException | IOException | KeyStoreException | NoSuchAlgorithmException |
                 KeyManagementException e) {
            logger.error("ES连接认证失败", e);
        }

        return sslContext;
    }




    //将多个连接地址存放进数组中
    private  HttpHost[] toHttpHost(){

        if(!StringUtils.hasLength(hosts)){
            logger.error("elasticsearch 连接地址不能为空");
        }
        //将多个用逗号隔开的ip存放进数组中
        String[] hostArray = hosts.split(",");
        HttpHost[] httpHosts=new HttpHost[hostArray.length];
        HttpHost httpHost;

        for(int i=0;i<hostArray.length;i++){
            httpHost=new HttpHost(hostArray[i],port,"https");
            httpHosts[i]=httpHost;
        }
        return httpHosts;
    }
}
