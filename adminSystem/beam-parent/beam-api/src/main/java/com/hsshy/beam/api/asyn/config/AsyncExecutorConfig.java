package com.hsshy.beam.api.asyn.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
@Slf4j
@Configuration
public class AsyncExecutorConfig {

//    corePoolSize（核心线程数）
//
//            （1）核心线程会一直存在，即使没有任务执行； （2）当线程数小于核心线程数的时候，即使有空闲线程，也会一直创建线程直到达到核心线程数；
//            （3）设置allowCoreThreadTimeout=true（默认false）时，核心线程会超时关闭。

//    queueCapacity（任务队列容量）
//
//                也叫阻塞队列，当核心线程都在运行，此时再有任务进来，会进入任务队列，排队等待线程执行。

//    maxPoolSize（最大线程数）
//
//            (1）线程池里允许存在的最大线程数量；
//            (2）当任务队列已满，且线程数量大于等于核心线程数时，会创建新的线程执行任务；
//            (3）线程池里允许存在的最大线程数量。当任务队列已满，且线程数量大于等于核心线程数时，会创建新的线程执行任务。

//    keepAliveTime（线程空闲时间）
//
//            (1）当线程空闲时间达到keepAliveTime时，线程会退出（关闭），直到线程数等于核心线程数；
//            (2）如果设置了allowCoreThreadTimeout=true，则线程会退出直到线程数等于零。

//    allowCoreThreadTimeout（允许核心线程超时）
//
//    rejectedExecutionHandler（任务拒绝处理器）
//
//            (1）当线程数量达到最大线程数，且任务队列已满时，会拒绝任务；
//            (2）调用线程池shutdown()方法后，会等待执行完线程池的任务之后，再shutdown()。如果在调用了shutdown()方法和线程池真正shutdown()之间提交任务，会拒绝新任务。

    @Bean
    public Executor asyncServiceExecutor() {
        log.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        //如果是IO密集型应用，则线程池大小设置为2N+1；
        //如果是CPU密集型应用，则线程池大小设置为N+1；
        executor.setCorePoolSize(6);
        //配置最大线程数
        executor.setMaxPoolSize(10);
        //配置队列大小
        executor.setQueueCapacity(10000);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-service-");
        // 设置拒绝策略：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}