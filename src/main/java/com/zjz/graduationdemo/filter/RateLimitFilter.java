package com.zjz.graduationdemo.filter;

import com.zjz.graduationdemo.rateLimit.Bucket;
import com.zjz.graduationdemo.rateLimit.BucketListener;
import com.zjz.graduationdemo.rateLimit.ClientRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
@Component
public class RateLimitFilter implements Filter {
    @Autowired
    private Bucket bucket;

    @Autowired
    private BucketListener listener;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (bucket.offerBucket(new ClientRequest(servletRequest, servletResponse, filterChain))) {
            log.info("bucket is not full, put request into bucket");
            listener.handle();
        } else {
            log.info("bucket is full, return an fail response to client");
            // return an fail response to client
        }
    }
}
