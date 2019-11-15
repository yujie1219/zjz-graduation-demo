package com.zjz.graduationdemo.rateLimit;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Bucket {
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
        return (ClientRequest) bucket.poll();
    }

    // using a Timed task to add token every ? seconds
    public boolean offerToken(String token) {
        return tokens.offer(token);
    }

    // if there is no token, block
    public String takeToken() throws InterruptedException {
        return (String) tokens.take();
    }
}
