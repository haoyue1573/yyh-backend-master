package com.yu.init.config;

import io.github.briqt.spark4j.SparkClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Data
@Configuration
@SuppressWarnings("All")
@ConfigurationProperties(prefix = "xunfei.client")
public class XunFeiConfig  {
    private String appid;
    private String apiSecret;
    private String apiKey;
    @Bean
    public SparkClient sparkClient() {
        SparkClient sparkClient = new SparkClient();
        sparkClient.apiKey = this.apiKey;
        sparkClient.apiSecret = this.apiSecret;
        sparkClient.appid = this.appid;
        return sparkClient;
    }

}