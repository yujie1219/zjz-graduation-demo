package com.zjz.graduationdemo.rateLimit;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Bucket {
    public static AtomicInteger count = new AtomicInteger(0);
    private static Bucket instance = null;

    private int maxBucketSize = 100;
    private int maxTokenSize = 100;

    private Queue bucket;
    private Map<String, HttpServletRequest> pendingRequest;
    private LinkedBlockingQueue tokens;

    private Bucket() {
        bucket = new LinkedBlockingQueue<String>(maxBucketSize);
        pendingRequest = new HashMap<>();
        tokens = new LinkedBlockingQueue<String>(maxTokenSize);
    }

    public static Bucket getInstance() {
        if (instance == null) {
            synchronized (Bucket.class) {
                if (instance == null) {
                    instance = new Bucket();
                }
            }
        }
        return instance;
    }

    public boolean offerBucket(HttpServletRequest request) {
        String key = request.getAttribute("key").toString();
        pendingRequest.put(key, request);
        return bucket.offer(key);
    }

    /**
     * consume bucket at the head of the queue
     *
     * @return the bucket at the head of the queue
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
