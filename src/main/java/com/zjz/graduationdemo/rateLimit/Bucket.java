package com.zjz.graduationdemo.rateLimit;

import com.zjz.graduationdemo.pojo.ClientRequest;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Bucket {
    public static AtomicInteger count = new AtomicInteger(0);
    private static Bucket instance = null;

    private int maxBucketSize = 100;
    private int maxTokenSize = 100;

    private Queue bucket;
    private LinkedBlockingQueue tokens;

    private Bucket() {
        bucket = new LinkedBlockingQueue<ClientRequest>(maxBucketSize);
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

    public boolean offerBucket(ClientRequest request) {
        return bucket.offer(request);
    }

    public ClientRequest pollBucket() {
        Object object = bucket.poll();
        return object == null ? null : (ClientRequest) object;
    }

    public boolean offerToken(String token) {
        return tokens.offer(token);
    }

    /**
     * If there is no any token in tokens, block the thread
     *
     * @return the token at the head of the queue
     * @throws InterruptedException
     */
    public String takeToken() throws InterruptedException {
        Object object = tokens.take();
        return object == null ? null : (String) object;
    }
}
