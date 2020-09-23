package com.zc.jvmtuning.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 堆内存溢出
 *
 * -Xms10m -Xmx10m
 *
 *
 *
 * JVM模板
 * -Xms4096M -Xmx4096M -Xmn3072M -Xss1M -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=92 -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:+CMSParallelInitialMarkEnabled -XX:+CMSScavengeBeforeRemark -XX:+DisableExplicitGC -XX:+PrintGCDetails -Xloggc:gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/usr/local/app/oom
 * @Author: zhangchao
 **/
public class Demo12 {
    public static void main(String[] args) {
        int counter = 0;
        List<UserVo> list = new ArrayList<>();
        while (true){
            list.add(new UserVo());
            System.out.println("第"+ (++counter) +"个对象");

        }
//         for (int i=1; i<=200; i++){
//              new  Thread(() -> {
//                  byte[] arr = new byte[10 * 1024 * 1024];
//                  byte[] arr1 = new byte[10 * 1024 * 1024];
//                  byte[] arr3 = new byte[10 * 1024 * 1024];
//                  byte[] arr4 = new byte[10 * 1024 * 1024];
//                  try { TimeUnit.SECONDS.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
//
//              }, valueOf(i)).start();
//         }
    }

    static class UserVo{

    }
}
