package com.zc.jvmtuning.gc;

/**
 * 描述: 模拟生产频繁发生Full GC
 * -XX:NewSize=104857600 -XX:MaxNewSize=104857600 -XX:InitialHeapSize=209715200 -XX:MaxHeapSize=209715200 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=20971520 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * 原因;Young GC太频繁,而survivor区容不下存活的对象,直接进入老年代
 *
 * 1.观察 Young GC Full GC耗时
 * 发现young有时候耗时较长(比full gc长)
 * 原因:每次young gc  s区内存不够,都会进入到老年代,一旦某次Eden内存满了,发现老年代内存不够时,将触发Full GC,此时需要等待Full GC完成,才能执行y gc 将内存放入老年代
 *
 *  优化:调大年轻代内存,增加s区内存,让s区有足够空间存放没次y gc的存活内存
 *
 *  优化后的jvm参数:
 *
 *  step1:
 *  单纯加大新生代内存
 *  -XX:NewSize=209715200 -XX:MaxNewSize=209715200 -XX:InitialHeapSize=314572800 -XX:MaxHeapSize=314572800 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=20971520 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:./jvm-tuning/gc.log
 *  结果:发现老年代发生频率有所降低,但是仍然不够
 *  原因分析:
 *  仅升级新生代内存->s区内存20M 每次e区存活10M放入
 *  step2
 *  调整e区s区内存比例(注意:一定要权衡新生代内存生产速度,比例太低,会导致y gc频繁)
 *
 *  -XX:NewSize=209715200 -XX:MaxNewSize=209715200 -XX:InitialHeapSize=314572800 -XX:MaxHeapSize=314572800 -XX:SurvivorRatio=2 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=20971520 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 *
 *  结果:发现full gc 基本不发生
 *
 *  step3 :如果实际情况,服务器无法拓展内存呢?
 *  -XX:NewSize=104857600 -XX:MaxNewSize=104857600 -XX:InitialHeapSize=209715200 -XX:MaxHeapSize=209715200 -XX:SurvivorRatio=2 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=20971520 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 *
 *  事实上这里存在一个隐患 如果把loadData后面创建最后四个的代码提到循环前面,结果可能就不这么乐观了(见demo7_2.jpg)
 *
 *  原因分析:新生代s区内存(25M)小于一次方法执行可能存活的最大内存(40M)
 *
 *  实际生产中多线程可能更复杂,因此该情况首要是新增新生代内存大小,提升s区内存占比,使其尽可能总能存储每次y gc的存活对象
 *
 *
 *
 *
 *  ---------------------------------------------JVM调优一般步骤-----------------------------------------------
 *  1.分析机器情况（机器配置，堆内存大小，运行时长，FullGC次数、时间，YoungGC次数、时间、剩余内存、晋升内存）
 * 2.查看具体的jvm参数配置
 * 3.然后根据JVM参数配置梳理出JVM模型，每个区间的大小是多少，画出来JVM模型（考虑每个设置在申请情况下会执行GC）
 * 4.结合jstat查看的GC情况，在结合JVM模型进行二次分析
 * 5.jmap dump内存快照，通过jhat或者Visual VM之类的工具查看具体的对象分类情况
 * 6.根据分析的情况再具体到问题（Bug、或者参数设置等问题）
 * 7.修复Bug，优化JVM参数
 * @Author: zhangchao
 **/
public class Demo7 {
    public static void main(String[] args) throws Exception{
        Thread.sleep(30000);
        while (true){
            loadData();
        }
    }

    private static void loadData() throws InterruptedException {


        byte[] data = null;
        for (int j=1;j<=4 ;j++) {
            data = new byte[10 * 1024 * 1024];
        }
        data = null;

        byte[] data1 = new byte[10 * 1024 * 1024];
        byte[] data2 = new byte[10 * 1024 * 1024];
        byte[] data3 = new byte[10 * 1024 * 1024];
        data3 = new  byte[10 * 1024 * 1024];

        Thread.sleep(1000);
    }
}
