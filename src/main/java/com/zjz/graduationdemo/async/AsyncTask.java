package com.zjz.graduationdemo.async;

import com.zjz.graduationdemo.pojo.Result;
import com.zjz.graduationdemo.rateLimit.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Slf4j
@Service
public class AsyncTask {

    @Async
    public Future<ResponseEntity<Result>> executeAsync(Bucket bucket, String key) {
        log.info("key {} comes", key);
        while (bucket.isRequestPending(key)) {
            // if the request is still pending, do nothing
        }

        log.info("key {} finish", key);
        return new AsyncResult<>(new ResponseEntity(new Result(), HttpStatus.OK));
    }
}
