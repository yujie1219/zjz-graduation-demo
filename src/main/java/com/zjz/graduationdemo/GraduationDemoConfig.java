package com.zjz.graduationdemo;

import com.zjz.graduationdemo.rateLimit.Bucket;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "config.properties")
@Data
public class GraduationDemoConfig {
    @Value("${key-name}")
    private String keyName;

    @Value("${bucket-max-size}")
    private int bucketMaxSize;

    @Value("${tokens-max-size}")
    private int tokensMaxSize;

    @Value("${bucket-consume-rate}")
    private int bucketConsumeRate;

    @Value("${token-add-rate}")
    private int tokenAddRate;

    @Bean
    public Bucket bucket() {
        return Bucket.getInstance(bucketMaxSize, tokensMaxSize);
    }
}
