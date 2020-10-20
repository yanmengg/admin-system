package com.hsshy.beam.api.modular.test.controller;
import com.hsshy.beam.api.asyn.AsyncService;
import com.hsshy.beam.api.modular.test.dto.LoginForm;
import com.hsshy.beam.common.annotion.IgnoreSignAuth;
import com.hsshy.beam.common.annotion.IgnoreUTokenAuth;
import com.hsshy.beam.common.utils.JwtTokenUtil;
import com.hsshy.beam.common.utils.R;
import com.hsshy.beam.common.utils.redis.RedisUtil;
import com.hsshy.beam.web.modular.base.controller.BaseBeanController;
import com.hsshy.beam.web.modular.common.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @description: 测试
 * @author: hs
 * @create: 2019-04-01 22:16:07
 **/
@Api(value = "测试", tags = "TestController")
@RequestMapping(value = "/test")
@RestController
public class TestController extends BaseBeanController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private AsyncService asyncService;

    /*
    *
    *  请求参数  可以再sign/util/signTest中进行模拟
        {
            "object":"ewogICJwYXNzd29yZCI6ICJzdHJpbmciLAogICJ1c2VybmFtZSI6ICJzdHJpbmciCn0=",
            "sign":"15dedfdf8c0d9d81378a22903ff6ab98"
        }
    *
    *
    */
    //签名方式：
    // 请求对象为 object: 整个对象进行base64编码后的值  sign: 将整个对象进行base64编码得到的值拼接上密钥做md5加密作为sign
    @ApiOperation(value = "登陆")
    @IgnoreUTokenAuth
    @IgnoreSignAuth
    @PostMapping(value = "/login")
    public R login(@RequestBody LoginForm loginForm) {
        String token = JwtTokenUtil.generateToken("1");
        return R.ok(token);
    }

    @ApiOperation(value = "免授权")
    @IgnoreUTokenAuth
    @GetMapping(value = "/ignore")
    public R ignore() {
        return R.ok();
    }

    @ApiOperation(value = "授权")
    @GetMapping(value = "/auth")
    public R auth() {
        logger.info("用户ID:{}", getUserId());
        return R.ok();
    }

//    @IgnoreUTokenAuth
//    @ApiOperation(value = "测试爆满CPU")
//    @GetMapping(value = "/fuck/cpu")
//    public void fuckCpu() {
//        while (true) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                }
//            }).start();
//        }
//    }


    @ApiOperation(value = "测试异步线程")
    @IgnoreUTokenAuth
    @GetMapping(value = "/async1")
    public R testAsync1() throws Exception {
        long startTime = System.currentTimeMillis();   //获取开始时间
        Future<String> asyncResult1 = asyncService.getAsyncResult1();
        Future<String> asyncResult2 = asyncService.getAsyncResult2();
        Map map = new HashMap();
        map.put("asyncResult1", asyncResult1.get());
        map.put("asyncResult2", asyncResult2.get());
        long endTime = System.currentTimeMillis(); //获取结束时间
        logger.info("程序运行时间： " + (endTime - startTime) + "ms");
        return R.ok(map);
    }

    //线程池
    @Autowired
    public Executor asyncServiceExecutor;

    @ApiOperation(value = "测试异步线程，并返回结果")
    //提高tomcat吞吐能力
    @IgnoreUTokenAuth
    @PostMapping(value = "/async2")
    public DeferredResult<R> testAsync2() throws ExecutionException, InterruptedException {
        DeferredResult deferredResult = new DeferredResult(10000L);
        CompletableFuture<R> result = CompletableFuture.supplyAsync(() -> {
            //耗时操作
            //.........
            return R.ok();
        }, asyncServiceExecutor);
        deferredResult.setResult(result.get());


        return deferredResult;
    }


    @ApiOperation(value = "动态数据源测试-默认数据源")
    @IgnoreUTokenAuth
    @GetMapping(value = "/dynamic/default")
    public R dynamicDefault() {
        //dev 测试提交 ---
        return R.ok(sysConfigService.getValue("test", ""));

    }


    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    //调整队列数 拒绝服务
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(10000));

    @ApiOperation(value = "producer")
    @IgnoreUTokenAuth
    @GetMapping(value = "/producer")
    public R producer() {
//        for (int i = 0; i < 100; i++) {
//            springProducer.syncSendOrderly("newTopic", "fuck" + i,i);
//            springProducer.syncSendOrderly("newTopic", "pick" + i,i);
////            springProducer.syncSendOrderly("newTopic", "fuck" + i, i);
//        }
//        springProducer.syncSend("newTopic","fuck");
//        for(int i=0;i<20;i++){
//            int userId = i;
//            Runnable task = new Runnable() {
//                @Override
//                public void run() {
////                    springProducer.sendMsg("newTopic","fuck"+userId);
//                    if(){
//
//                    }
//                    springProducer.syncSendOrderly("newTopic","fuck"+userId,userId);
//
//                    latch.countDown();
//                }
//            };
//            executor.execute(task);
//        }
//        CompletableFuture<R> result1 = CompletableFuture.supplyAsync(() -> {
//            for(int i=0;i<100;i++){
//                springProducer.syncSendOrderly("newTopic","fuck"+i,i);
//            }
//            return R.ok();
//        }, asyncServiceExecutor);
//        CompletableFuture<R> result2 = CompletableFuture.supplyAsync(() -> {
//            for(int i=0;i<10;i++){
////                springProducer.syncSendOrderly("newTopic","pick"+i,i);
//                springProducer.sendMsg("newTopic","pick"+i);
//            }
//            return R.ok();
//        }, asyncServiceExecutor);

        return R.ok();
    }


    @ApiOperation(value = "producer2")
    @IgnoreUTokenAuth
    @GetMapping(value = "/producer2")
    public R producer2() {
        for (int i = 0; i < 10; i++) {
        }
        return R.ok();
    }


//    @ApiOperation(value = "动态数据源测试-test数据源")
//    @IgnoreUTokenAuth
//    @GetMapping(value = "/dynamic/test")
//    @DataSource(name = "test")
//    public R  dynamicTest(){
//
//        return R.ok(sysConfigService.getValue("test",""));
//    }
//
//    @ApiOperation(value = "动态数据源测试-tool数据源")
//    @IgnoreUTokenAuth
//    @GetMapping(value = "/dynamic/tool")
//    @DataSource(name = "tool")
//    public R  dynamicTool(){
//        return R.ok(sysConfigService.getValue("test",""));
//    }

}
