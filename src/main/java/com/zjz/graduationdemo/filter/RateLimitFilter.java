package com.zjz.graduationdemo.filter;

import com.google.gson.Gson;
import com.zjz.graduationdemo.pojo.Result;
import com.zjz.graduationdemo.rateLimit.Bucket;
import com.zjz.graduationdemo.rateLimit.BucketListener;
import com.zjz.graduationdemo.rateLimit.ClientRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class RateLimitFilter implements Filter {
    @Autowired
    private Bucket bucket;

    @Autowired
    private BucketListener listener;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        int count = Bucket.count.addAndGet(1);
        if (bucket.offerBucket(new ClientRequest(servletRequest, servletResponse, filterChain))) {
            log.info("bucket is not full, put request {} into bucket", count);
            listener.handle();
        } else {
            log.info("bucket is full,request {} return an fail response to client", count);
            returnFailResponse((HttpServletResponse) servletResponse);
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
