package com.zjz.graduationdemo.rateLimit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledTasks {
    @Autowired
    private Bucket bucket;

    /**
     * Add 100 tokens every 1 seconds
     */
    @Scheduled(fixedRate = 1000)
    public void addToken() {
        for (int i = 0; i < 100; i++) {
            // not sure If token need be generated buy some tools and if token need have some meanings
            if (!bucket.offerToken(i + "")) {
                break;
            }
        }
    }

    /**
     * Consume 100 bucket every 1 seconds
     */
    @Scheduled(fixedRate = 1000)
    public void consumeBucket() {
        for (int i = 0; i < 100; i++) {
            bucket.consumeBucket();
        }
    }
}
