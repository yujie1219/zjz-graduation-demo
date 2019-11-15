package com.zjz.graduationdemo.filter;

import com.zjz.graduationdemo.rateLimit.Bucket;
import com.zjz.graduationdemo.rateLimit.BucketListener;
import com.zjz.graduationdemo.rateLimit.ClientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class RateLimitFilter implements Filter {
    @Autowired
    private Bucket bucket;

    @Autowired
    private BucketListener listener;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (bucket.offerBucket(new ClientRequest(servletRequest, servletResponse, filterChain))) {
            listener.handle();
        } else {
            // return an error response to client
        }
    }
}
