package com.zc.jvmtuning.gc;

/**
 * 描述:
 * 老年代GC如何触发
 * 47
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=3145728 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:./jvm-tuning/gc.log
 *
 * 总结:
 * 1.full gc全部晋升
 * 当新生代内存不足,将要触发y gc ,判断老年代内存是否充足
 * 若不充足,则触发full gc ,接着会将此次新生代所有存活对象晋升到老年代
 *
 * 2.年轻代存活对象部分晋升到老年代
 *  前提是不会触发old gc (老年代内存判断充足)
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
        //接这将array4+array5+未知对象放入老年代(因为array6>survivor,由于发生full gc 年轻代所有之前的存活对象肯定是都往老年代放的)-->放入老年代(6M多一点)
        // -->[CMS: 8194K->6634K(10240K)

        //此时full gc结束,young gc 将700多K的未知对象+类信息对象放入s区
        //接着array6继续分配到新生代的Eden区
        byte[] array6 = new byte[2 * 1024 * 1024];
    }
}
