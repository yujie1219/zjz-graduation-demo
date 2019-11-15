package com.zjz.graduationdemo.rateLimit;

import java.util.concurrent.LinkedBlockingQueue;

public class TokenQueue {
    private static TokenQueue instance = null;

    private int maxSize = 100;

    private LinkedBlockingQueue tokens;

    private TokenQueue() {
        tokens = new LinkedBlockingQueue<String>(maxSize);
    }

    public static TokenQueue getInstance() {
        if (instance == null) {
            synchronized (Bucket.class) {
                if (instance == null) {
                    instance = new TokenQueue();
                }
            }
        }
        return instance;
    }

    // using a Timed task to add token every ? seconds
    public boolean offer(String token) {
        return tokens.offer(token);
    }

    // if there is no token, block
    public String take() throws InterruptedException {
        return (String) tokens.take();
    }

    public boolean isEmpty() {
        return tokens.isEmpty();
    }
}
