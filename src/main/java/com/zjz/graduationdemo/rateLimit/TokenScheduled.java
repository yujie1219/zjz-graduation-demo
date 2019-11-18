package com.zjz.graduationdemo.rateLimit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenScheduled {
    @Autowired
    private Bucket bucket;

    @Scheduled(fixedRate = 3000)
    public void addToken() {
        for (int i = 0; i < 100; i++) {
            if (!bucket.offerToken(i + "")) {
                // log.error("add {}st token fail, token list is full", i);
                break;
            }
        }
    }
}
