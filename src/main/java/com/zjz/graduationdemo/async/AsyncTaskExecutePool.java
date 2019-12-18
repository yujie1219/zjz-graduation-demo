package com.zjz.graduationdemo.async;

import com.zjz.graduationdemo.GraduationDemoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AsyncTaskExecutePool implements AsyncConfigurer {
    @Autowired
    private GraduationDemoConfig graduationDemoConfig;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(graduationDemoConfig.getCorePoolSize());
        executor.setMaxPoolSize(graduationDemoConfig.getMaxPoolSize());
        executor.setQueueCapacity(graduationDemoConfig.getQueueCapacity());
        executor.setKeepAliveSeconds(graduationDemoConfig.getKeepAliveSeconds());
        executor.setThreadNamePrefix("GraduationExecutor-");

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        return executor;
    }
}
