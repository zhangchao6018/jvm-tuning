package com.zc.jvmtuning.gc;

/**
 * 描述: 模拟生产环境频繁发生young gc
 * -XX:NewSize=104857600 -XX:MaxNewSize=104857600 -XX:InitialHeapSize=209715200 -XX:MaxHeapSize=209715200 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=3145728 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 *
 * 1.用 jstat -gc PID 1000 1000 观察EU 发现每秒增加5M左右对象
 *
 * 2.每次gc 约回收70M对象 耗费1毫秒
 *
 * 3.若将这个测试用例放大10倍  估算gc时间为10ms
 *
 * 4.每次young gc 后存活的对象呢?
 *  这台机器上是相邻young gc survivor区新增内存小于1M(随着后续运行s区基本不占用内存)
 *  说明目前系统运行良好,无需优化
 *
 * 5.总结思路
 * jstat分析:
 * 新生代对象增长速率
 * Young GC触发频率
 * Young GC耗时
 * 每次Young GC 多少对象存活
 * 每次Young GC多少对象进入老年代
 * 老年代对象增长速率
 * Full GC触发频率
 * Full GC的耗时
 *
 * @Author: zhangchao
 **/
public class Demo6 {
    public static void main(String[] args) throws Exception{
        Thread.sleep(30000);
        while (true){
            loadData();
        }
    }

    private static void loadData() throws InterruptedException {
        byte[] data = null;
        for (int j=1;j<=50 ;j++) {
            data =new byte[100 * 1024];
        }
        data = null;
        Thread.sleep(1000);
    }
}
