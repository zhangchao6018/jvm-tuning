package com.zc.jvmtuning.gc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述:
 * -Xms20M -Xmx20M -Xmn10M -Xss1M -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=92 -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:+CMSParallelInitialMarkEnabled -XX:+CMSScavengeBeforeRemark -XX:+DisableExplicitGC -XX:+PrintGCDetails -Xloggc:gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/usr/local/app/oom
 * @Author: zhangchao
 **/
@RequestMapping("/oom")
@RestController
public class TomcatRequestOOMController {
    private static AtomicInteger COUNTER = new AtomicInteger(0);
    @GetMapping("get")
    public String get(){
        try { TimeUnit.SECONDS.sleep(4);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println(COUNTER.getAndIncrement()+"_success");
        return "success";
    }
}
