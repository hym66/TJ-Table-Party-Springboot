package com.backend.tjtablepartyspringboot.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tencent")
@AllArgsConstructor
@NoArgsConstructor
public class TencentCosConfig {
    public static final String COS_IMAGE = "tp";

    @Autowired
    TencentCosProperties4Picture tencentCosProperties4Picture;

    @Bean
    @Qualifier(COS_IMAGE)
    @Primary
    public COSClient getCoSClient4Picture() {
        //初始化用户身份信息
        COSCredentials cosCredentials = new BasicCOSCredentials(tencentCosProperties4Picture.getSecretId(),tencentCosProperties4Picture.getSecretKey());
        //设置bucket区域，
        //clientConfig中包含了设置region
        ClientConfig clientConfig = new ClientConfig(new Region(tencentCosProperties4Picture.getRegionId()));
        //生成cos客户端
        COSClient cosClient = new COSClient(cosCredentials,clientConfig);
        return  cosClient;

    }


}
