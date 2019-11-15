package com.zjz.graduationdemo.rateLimit;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Data
@AllArgsConstructor
public class ClientRequest {
    private ServletRequest request;

    private ServletResponse response;

    private FilterChain filterChain;
}
