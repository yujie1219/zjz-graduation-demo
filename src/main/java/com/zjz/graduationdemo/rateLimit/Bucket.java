package com.zjz.graduationdemo.rateLimit;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Bucket {
    private static Bucket instance = null;

    private int maxSize = 100;

    private Queue bucket;

    private Bucket() {
        bucket = new LinkedBlockingQueue<ClientRequest>(maxSize);
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

    public boolean offer(ClientRequest request) {
        return bucket.offer(request);
    }

    public ClientRequest poll() {
        return (ClientRequest) bucket.poll();
    }

    public ClientRequest peek() {
        return (ClientRequest) bucket.peek();
    }
}
