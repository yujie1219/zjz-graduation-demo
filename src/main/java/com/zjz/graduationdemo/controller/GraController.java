package com.zjz.graduationdemo.controller;

import com.zjz.graduationdemo.GraduationDemoConfig;
import com.zjz.graduationdemo.pojo.Result;
import com.zjz.graduationdemo.rateLimit.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * This Api is used to return a successful response
     *
     * @return Successful Response
     */
    @RequestMapping(path = "/call", method = RequestMethod.GET)
    public ResponseEntity<Result> callSuccess() {
        while (bucket.isRequestPending(request.getAttribute(config.getKeyName()).toString())) {
            // if the request is still pending, do nothing
        }

        return new ResponseEntity(new Result(), HttpStatus.OK);
    }

}
