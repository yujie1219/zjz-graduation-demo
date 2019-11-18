package com.zjz.graduationdemo.controller;

import com.zjz.graduationdemo.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/graduation")
public class GraController {

    @RequestMapping(path = "/call",method = RequestMethod.GET)
    public ResponseEntity<Result> callSuccess() {
        log.info("Request at callSuccess method");
        return new ResponseEntity<Result>(new Result(), HttpStatus.OK);
    }

}
