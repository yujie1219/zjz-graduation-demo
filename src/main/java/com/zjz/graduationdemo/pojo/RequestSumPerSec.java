package com.zjz.graduationdemo.pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class RequestSumPerSec {
    // Request Number per second
    private static Long requestNum;
    private static Long successNum;
    private static Long failNum;
    private static Long invalidNum;

    // Request Number Summary Cache
    private static AtomicLong requestNumSum;
    private static AtomicLong successNumSum;
    private static AtomicLong failNumSum;
    private static AtomicLong invalidNumSum;

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
