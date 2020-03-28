package com.zjz.graduationdemo.rateLimit;

import com.zjz.graduationdemo.GraduationDemoConfig;
import com.zjz.graduationdemo.dao.RSDao;
import com.zjz.graduationdemo.pojo.RequestSumPerSec;
import com.zjz.graduationdemo.pojo.RequestSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledTasks {
    @Autowired
    private Bucket bucket;

    @Autowired
    private GraduationDemoConfig config;

    @Autowired
    private RSDao rsDao;

    /**
     * Add 100 tokens every 1 seconds
     */
    @Scheduled(fixedRate = 1000)
    public void addToken() {
        for (int i = 0; i < config.getTokenAddRate(); i++) {
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
        for (int i = 0; i < config.getBucketConsumeRate(); i++) {
            bucket.consumeBucket();
        }
    }

    /**
     * Restore coming request every 1 seconds
     */
    @Scheduled(fixedRate = 1000)
    public void restoreRequestSum(){
        RequestSummary currentRS = RequestSumPerSec.getRequestSummary();
        rsDao.save(currentRS);
    }
}
