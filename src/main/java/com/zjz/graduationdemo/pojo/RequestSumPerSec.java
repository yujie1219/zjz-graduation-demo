package com.zjz.graduationdemo.pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class RequestSumPerSec {
    // Request Number per second
    private static Long requestNum = 0L;
    private static Long successNum = 0L;
    private static Long failNum = 0L;
    private static Long invalidNum = 0L;

    // Request Number Summary Cache
    private static AtomicLong requestNumSum = new AtomicLong(0);
    private static AtomicLong successNumSum = new AtomicLong(0);
    private static AtomicLong failNumSum = new AtomicLong(0);
    private static AtomicLong invalidNumSum = new AtomicLong(0);

    public static synchronized void requestSuccess(){
        requestNum++;
        successNum++;
    }

    public static synchronized void requestFail(){
        requestNum++;
        failNum++;
    }

    public static synchronized  RequestSummary getRequestSummary(){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        RequestSumPerSec.SyncCache();
        return new RequestSummary(currentTime, requestNumSum.get(), successNumSum.get(), failNumSum.get(), invalidNumSum.get());
    }

    private static void SyncCache(){
        requestNumSum.addAndGet(requestNum);
        successNumSum.addAndGet(successNum);
        failNumSum.addAndGet(failNum);
        invalidNumSum.addAndGet(invalidNum);

        requestNum = 0L;
        successNum = 0L;
        failNum = 0L;
        invalidNum = 0L;
    }
}
