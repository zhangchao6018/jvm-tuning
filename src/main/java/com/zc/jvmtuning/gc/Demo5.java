package com.zc.jvmtuning.gc;

/**
 * 描述:
 * 15次 young gc 后晋升到老年代
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=4 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * @Author: zhangchao
 **/
public class Demo5 {
    public static void main(String[] args) {
        int age = 15;
        byte[] array2= new byte[128 * 1024];

        while (age >= 0){
            byte[] array1 = new byte[2 * 1024 * 1024];
            array1 = new byte[1024 * 1024];
            array1 = new byte[1024 * 1024];
            array1 = null;
            byte[] array3 = new byte[2 * 1024 * 1024];
            age--;
        }
    }
}
