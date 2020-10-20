package com.hsshy.beam.api.asyn;
import com.hsshy.beam.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class AsyncService {

    @Async("asyncServiceExecutor")
    public Future<String> getAsyncResult1() {
        String result = "asyncResultTest1";
        try {
            Thread.sleep(3000);
            log.info("线程名称：{}，睡眠:{}秒", Thread.currentThread().getName(), "3");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<String>(result);
    }

    @Async("asyncServiceExecutor")
    public Future<String> getAsyncResult2() {
        String result = "asyncResultTest2";
        try {
            Thread.sleep(4000);
            log.info("线程名称：{}，睡眠:{}秒", Thread.currentThread().getName(), "4");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<String>(result);
    }

    @Async("asyncServiceExecutor")
    public CompletableFuture<R> getAsyncResult3() throws Exception {

        CompletableFuture<R> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            System.out.println("run end ...");
            return R.ok();
        });
        return future;
    }
}