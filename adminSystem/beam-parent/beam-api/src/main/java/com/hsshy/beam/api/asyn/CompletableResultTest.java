package com.hsshy.beam.api.asyn;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletableResultTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = CompletableFuture.completedFuture("result");

        //例1
//      创建一个带result的CompletableFuture
//        System.out.println(future.get());

//      默认创建的CompletableFuture是没有result的，这时调用future.get()会一直阻塞下去知道有result或者出现异常
//        CompletableFuture<String> future = new CompletableFuture<>();
//        try {
//            future.get(1, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            // no care
//        }
//        //例2
//        // 给future填充一个result
//        future.complete("result");
//        assert "result".equals(future.get());
//
//        //例3
//        // 给future填充一个异常
//        future = new CompletableFuture<>();
//        future.completeExceptionally(new RuntimeException("exception"));
//        try {
//            future.get();
//        } catch (Exception e) {
//            assert "exception".equals(e.getCause().getMessage());
//        }

        // 使用示例
//        CompletableFuture.runAsync(() -> {
//            System.out.println("hello world");
//        });
//        CompletableFuture.supplyAsync(() -> {
//            System.out.println("hello world");
//            return "result";
//        });

        // 使用示例
//        CompletableFuture.supplyAsync(() -> {
//            System.out.println("hello world");
//            return "result";
//        }).whenCompleteAsync((result, e) -> {
//            System.out.println(result + " " + e);
//        }).exceptionally((e) -> {
//            System.out.println("exception " + e);
//            return "exception";
//        });


        // handle方法示例：
//        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("hello world");
//            return "result";
//        });
//        CompletableFuture<Integer> f2 = f1.handle((r, e) -> {
//
//            System.out.println(r+"handle");
//            return 1;
//        });

        // thenAccept方法示例：
//        CompletableFuture.supplyAsync(() -> {
//            System.out.println("hello world");
//            return "result";
//        }).thenAccept(r -> {
//            System.out.println(r);
//        }).thenAccept(r -> {
//            // 这里的r为Void（null）了
//            System.out.println(r);
//        });

        // thenAcceptBoth方法示例：
//        CompletableFuture.supplyAsync(() -> {
//            System.out.println("hello world");
//            return "result";
//        }).thenAcceptBoth(CompletableFuture.completedFuture("result2"), (r1, r2) -> {
//            System.out.println(r1 + "-" + r2);
//        });

        // thenCombine方法示例
//        CompletableFuture.supplyAsync(() -> {
//            System.out.println("hello world");
//            return "result";
//        }).thenCombine(CompletableFuture.completedFuture("result2"), (r1, r2) -> {
//            System.out.println(r1 + "-" + r2);
//            return r1 + "-" + r2;
//        });

        // thenCompose方法示例：
        CompletableFuture.supplyAsync(() -> {
            System.out.println("hello world");
            return "result";
        }).thenApply(s->s+" test").thenCompose(r -> {
            System.out.println(r);
            return CompletableFuture.supplyAsync(() -> {
                System.out.println(r + " result2");
                return r + " result2";
            });
        });

        // 上面的代码和下面的代码效果是一样的
//        CompletableFuture.supplyAsync(() -> {
//            System.out.println("hello world");
//            return "result";
//        }).thenApply(r -> {
//            System.out.println(r);
//            return r;
//        }).thenApplyAsync(r -> {
//            System.out.println(r + " result2");
//            return r + " result2";
//        });

    }


}