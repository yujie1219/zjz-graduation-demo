package com.zjz.graduationdemo.filter;

import com.google.gson.Gson;
import com.zjz.graduationdemo.GraduationDemoConfig;
import com.zjz.graduationdemo.pojo.Result;
import com.zjz.graduationdemo.rateLimit.Bucket;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
@Order(2)
public class RateLimitFilter implements Filter {
    @Autowired
    private Bucket bucket;

    @Autowired
    private GraduationDemoConfig config;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        int count = Bucket.count.addAndGet(1);
        boolean bucketOpen = config.isBucketOpen();
        boolean tokenOpen = config.isTokenOpen();

        if(!bucketOpen && !tokenOpen){
            filterChain.doFilter(servletRequest, servletResponse);
        }

        // maybe we can set the attribute name as token and generate the value via JWT
        String uuid = UUID.randomUUID().toString();
        servletRequest.setAttribute(config.getKeyName(), uuid);

        log.info("Request {} comes,uuid {}", count, uuid);
        if (bucketOpen && bucket.offerBucket((HttpServletRequest) servletRequest)) {
            log.info("bucket is not full, put request {} into bucket,uuid {}", count, uuid);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            log.info("bucket is full,try to take token for request {}", count);
            if (tokenOpen && bucket.takeToken(ObjectSizeCalculator.getObjectSize(servletRequest))) {
                log.info("request {} get token successfully,uuid {}", count, uuid);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                log.info("Token queue is empty, request {} will return an error response for client,uuid {}", count, uuid);
                returnFailResponse((HttpServletResponse) servletResponse);
            }
        }
    }

    private void returnFailResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json; charset=utf-8");
        Result result = new Result(false, "bucket is full");

        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(result));
        out.close();
    }
}
