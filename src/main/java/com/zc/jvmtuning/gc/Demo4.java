package com.zc.jvmtuning.gc;

/**
 * 描述:
 * Young GC后,由于survivor区放不下,直接进入老年区
 *
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * @Author: zhangchao
 **/
public class Demo4 {
    public static void main(String[] args) {
        byte[] array1 = new byte[2 * 1024 * 1024];
        array1 = new  byte[2 * 1024 * 1024];
        array1 = new  byte[2 * 1024 * 1024];


        byte[] array2 = new byte[128 * 1024];
        array2 = null;

        //young gc 后 由于survivor区内存不够,array1会晋升到老年代
        byte[] array3 = new byte[2 * 1024 * 1024];

    }
}
