package com.zjz.graduationdemo.rateLimit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

@Slf4j
@Component
public class BucketListener {

    @Autowired
    private Bucket bucket;

    public void handle() throws IOException, ServletException {
        try {
            bucket.takeToken();

            ClientRequest clientRequest = bucket.pollBucket();
            clientRequest.getFilterChain().doFilter(clientRequest.getRequest(), clientRequest.getResponse());
        } catch (InterruptedException e) {
            log.info("handle request error:" + e.getLocalizedMessage());
        }
    }
}
