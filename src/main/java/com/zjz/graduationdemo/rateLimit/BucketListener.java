package com.zjz.graduationdemo.rateLimit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

@Component
public class BucketListener {

    @Autowired
    private Bucket bucket;

    @Autowired
    private TokenQueue tokens;

    public void handle() throws IOException, ServletException {
        try {
            tokens.take();

            ClientRequest clientRequest = bucket.poll();
            clientRequest.getFilterChain().doFilter(clientRequest.getRequest(), clientRequest.getResponse());
        } catch (InterruptedException e) {
            // write into log
        }
    }
}
