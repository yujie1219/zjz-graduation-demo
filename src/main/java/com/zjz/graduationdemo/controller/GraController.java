package com.zjz.graduationdemo.controller;

import com.zjz.graduationdemo.pojo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graduation")
public class GraController {

    @RequestMapping(path = "/call",method = RequestMethod.GET)
    public ResponseEntity<Result> callSuccess() {
        return new ResponseEntity<Result>(new Result(), HttpStatus.OK);
    }

}
