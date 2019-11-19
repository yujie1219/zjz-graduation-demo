package com.zjz.graduationdemo.rateLimit;

import com.zjz.graduationdemo.pojo.ClientRequest;
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

    /**
     * Every time a request is added to bucket, try to take an token and then poll the bucket, continue send request/response on the filter chain
     *
     * @throws IOException
     * @throws ServletException
     */
    public void handle() throws IOException, ServletException {
        try {
            bucket.takeToken();

            ClientRequest clientRequest = bucket.pollBucket();
            if (clientRequest != null && clientRequest.getFilterChain() != null) {
                clientRequest.getFilterChain().doFilter(clientRequest.getRequest(), clientRequest.getResponse());
            } else {
                // I'm not sure what should do here, maybe if response is not null we can return an error response, otherwise write it to log
                log.error("Request filterChain is null");
            }
        } catch (InterruptedException e) {
            log.error("handle request error:" + e.getLocalizedMessage());
        }
    }
}
