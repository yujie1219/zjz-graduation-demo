package com.zjz.graduationdemo.rateLimit;

import com.zjz.graduationdemo.GraduationDemoConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Bucket {
    public static AtomicInteger count = new AtomicInteger(0);
    private static Bucket instance = null;

    private Queue bucket;
    private Map<String, HttpServletRequest> pendingRequest;
    private LinkedBlockingQueue tokens;

    @Autowired
    private GraduationDemoConfig config;

    private Bucket(int bucketMaxSize, int tokensMaxSize) {
        bucket = new LinkedBlockingQueue<String>(bucketMaxSize);
        pendingRequest = new ConcurrentHashMap<>();
        tokens = new LinkedBlockingQueue<String>(tokensMaxSize);
    }

    public static Bucket getInstance(int bucketMaxSize, int tokensMaxSize) {
        if (instance == null) {
            synchronized (Bucket.class) {
                if (instance == null) {
                    instance = new Bucket(bucketMaxSize, tokensMaxSize);
                }
            }
        }
        return instance;
    }

    /**
     * Put key & request into pendingRequestMap and add the key to bucket
     *
     * @param request
     * @return
     */
    public boolean offerBucket(HttpServletRequest request) {
        String key = request.getAttribute(config.getKeyName()).toString();
        pendingRequest.put(key, request);
        return bucket.offer(key);
    }

    /**
     * consume key at the head of the bucket and remove the request related
     *
     * @return the request at the head of the bucket
     */
    public HttpServletRequest consumeBucket() {
        Object object = bucket.poll();
        String key = object == null ? null : (String) object;
        if (key == null) {
            return null;
        } else {
            HttpServletRequest servletRequest = pendingRequest.get(key);
            if (servletRequest != null) {
                pendingRequest.remove(key);
            }
            return servletRequest;
        }
    }

    /**
     * if pendingRequest Map contains the key ,the request is still pending
     *
     * @param key
     * @return if the request is sill pending
     */
    public boolean isRequestPending(String key) {
        return pendingRequest.containsKey(key);
    }

    public boolean offerToken(String token) {
        return tokens.offer(token);
    }

    /**
     * If there is no any token in tokens, return null and do not block the thread
     *
     * @return the token at the head of the queue
     */
    public String takeToken() {
        Object object = tokens.poll();
        return object == null ? null : (String) object;
    }
}
