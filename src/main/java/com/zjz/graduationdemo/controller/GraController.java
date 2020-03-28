package com.zjz.graduationdemo.controller;

import com.zjz.graduationdemo.GraduationDemoConfig;
import com.zjz.graduationdemo.async.AsyncTask;
import com.zjz.graduationdemo.dao.RSDao;
import com.zjz.graduationdemo.pojo.RequestSummary;
import com.zjz.graduationdemo.pojo.Result;
import com.zjz.graduationdemo.rateLimit.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@RestController
@RequestMapping("/graduation")
public class GraController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private Bucket bucket;

    @Autowired
    private GraduationDemoConfig config;

    @Autowired
    private AsyncTask asyncTask;

    @Autowired
    private RSDao rsDao;

    /**
     * This Api is used to return a successful response
     *
     * @return Successful Response
     */
    @RequestMapping(path = "/call", method = RequestMethod.GET)
    public ResponseEntity<Result> call() throws ExecutionException, InterruptedException {
        Future<ResponseEntity<Result>> future = asyncTask.executeAsync(bucket, request.getAttribute(config.getKeyName()).toString());

        return future.get();
    }

    @RequestMapping(path = "/getRS", method = RequestMethod.GET)
    public ResponseEntity<List<RequestSummary>> getRequestSummary(@RequestParam Date start, @RequestParam Date end) {
        Optional<List<RequestSummary>> result = rsDao.findByCurrentTimeBetween(start, end);

        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
