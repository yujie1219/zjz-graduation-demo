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

    /**
     * The Api is used to return a successful response
     *
     * @return Successful Response
     */
    @RequestMapping(path = "/call", method = RequestMethod.GET)
    public ResponseEntity<Result> callSuccess() {
        return new ResponseEntity(new Result(), HttpStatus.OK);
    }

}
