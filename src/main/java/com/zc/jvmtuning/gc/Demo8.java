package com.zc.jvmtuning.gc;

/**
 *
 * -------------------------------------------------描述: 生产环境,对QPS高的系统进行gc优化-------------------------------------------------
 * 由于QPS高,年轻代内次发生y gc时,很多请求还没处理完,导致存活对象较多,然后survivor区存放不了,导致频繁f gc...
 *
 * 1.优化前参数:
 * -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=5
 *
 * 用了CMS默认的标记-清除算法5次Full GC后触发一次Compaction操作(压缩)--该操作使对象紧邻一起,避免大量内存锁片
 * 问题:由于压缩频率不高,大量内存碎片会提高Full GC频率
 *
 * 2. 如何进行优化
 * 2.1 jstat分析各个机器jvm情况,判断每次用y gc存活对象有多少,增加survivor区内存,避免对象过快进入老年代
 * 2.2 优化CMS内存碎片问题
 * -XX:CMSFullGCsBeforeCompaction=5  每次f gc 都整理内存碎片
 *
 *
 *
 *
 *  -------------------------------------------------问题描述: 如何对FullGC 进行深度优化-------------------------------------------------
 *  JVM默认参数使系统更早出现瓶颈
 *
 *  1. 4核8g机器JVM指导参数
 * -Xms4096M -Xmx4096M -Xmn3072M -Xss1M -XX:PermSize=256M -XX:MaxPermSize=256M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFaction=92 -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0
 *
 *  2.上述思路解释
 *  2.1让年轻代内存更大点,使survivor内存充足,这样使y gc存活对象不至于由于s区内存不足,直接晋升到老年代,导致f gc
 *  也不会因为s区内存不够,导致触发动态年龄判定规则,让新生代内存提前进入老年代
 *
 *  2.2 CMSFullGCsBeforeCompaction参数,让每次Full GC重新整理内存(虽然会多耗时一点,但是长远角度,减少了内存碎片带来的增加Full GC的风险)
 *
 *  3 如果发生FullGC,如何优化其性能?
 *  3.1新增 -XX:+CMSParallelInitialMarkEnabled
 *  作用:CMS垃圾回收器的"初始标记阶段"(会stop the world) 开启多线程执行,减少停顿时间
 *
 *  3.2新增 -XX:+CMSScavengeBeforeRemark 在CMS重新标记阶段之前,尽量先执行一次Young GC
 *  CMS 会以新生代异步非对象作为gc root,因此开启此开关会先回收掉一部分年轻代对象,减少重新标记的跟踪对象的基数
 *  作用: 回收掉年轻代一些没有引用的对象,让CMS重新标记阶段可以少扫描一些对象,提升CMS重新标记的性能,减少STW的时间
 *
 *  4.参数最终版本:
 *  -Xms4096M -Xmx4096M -Xmn3072M -Xss1M -XX:PermSize=256M -XX:MaxPermSize=256M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFaction=92 -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:+CMSParallelInitialMarkEnabled -XX:+CMSScavengeBeforeRemark
 *
 *
 *
 *
 *
 *
 *  打包命令：mvn clean package -Ptest -DskipTests
 *  执行命令：java -jar -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=1024m  -Xms512M -Xmx1024M -Xloggc:./gc.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintHeapAtGC -XX:+HeapDumpOnOutOfMemoryError /root/msa/mta-analysis-0.0.1-SNAPSHOT.jar
 * @Author: zhangchao
 **/
public class Demo8 {
    public static void main(String[] args) {

    }
}
