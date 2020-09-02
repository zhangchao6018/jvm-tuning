package com.zc.jvmtuning.gc;

/**
 * 描述:
 * 老年代GC如何触发
 *
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=3145728 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * @Author: zhangchao
 **/
public class Demo5_2 {
    public static void main(String[] args) {
        //直接进入老年代
        byte[] array1 = new byte[4 * 1024 * 1024];
        array1 = null;

        byte[] array2 = new byte[2 * 1024 * 1024];
        byte[] array3 = new byte[2 * 1024 * 1024];
        byte[] array4 = new byte[2 * 1024 * 1024];
        byte[] array5 = new byte[128 * 1024];

        //触发young gc 发现内存不够用,于是将array2,array3放入老年代,尝试放array4,array5和时发现内存不够 4+2+2+0.128 >10
        // 触发full gc ,回收掉array1  此时老年代占用4M
        //接这将array4+array5+未知对象放入老年代(因为array6>survivor,之前的老对象肯定是要往老年代放的)-->放入老年代(6M多一点)
        //此时新生代放array6 成功  新生代可以放下 -->放入新生代
        // -->[CMS: 8194K->6634K(10240K)
        byte[] array6 = new byte[2 * 1024 * 1024];
    }
}
