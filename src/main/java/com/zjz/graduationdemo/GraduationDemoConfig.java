package com.zjz.graduationdemo;

import com.zjz.graduationdemo.rateLimit.Bucket;
import com.zjz.graduationdemo.rateLimit.TokenQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraduationDemoConfig {
    @Bean
    public Bucket bucket() {
        return Bucket.getInstance();
    }

    @Bean
    public TokenQueue tokens() {
        return TokenQueue.getInstance();
    }
}
