package com.example.bigtalkscript.Thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
/**
 * @author jim
 */
@Configuration
@EnableAsync
public class TaskPoolConfig {
    @Bean("taskExecutor")
    public Executor taskExecutro(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //corePoolSize 线程池维护线程的最少数量
        taskExecutor.setCorePoolSize(10);
        //MaxPoolSize 线程池维护线程的最大数量
        taskExecutor.setMaxPoolSize(50);
        //queueCapacity 缓存队列
        taskExecutor.setQueueCapacity(200);
        //keepAliveSeconds 允许的空闲时间
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("taskExecutor--");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        return taskExecutor;
    }
}
