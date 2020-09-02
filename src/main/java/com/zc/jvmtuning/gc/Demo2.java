package com.zc.jvmtuning.gc;

/**
 * 描述:
 * 对象进入老年代场景1
 * <p></>动态年龄判定规划</p>
 *
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 *
 * -XX:SurvivorRatio=8  新生代内存  Eden去和Survivor去的内存大小比例  即8:1:1
 * -XX:PretenureSizeThreshold=10485760   多大内存的大对象直接分配到老年代
 * @Author: zhangchao
 **/
public class Demo2 {
    public static void main(String[] args) {
        byte[] array1 = new byte[2 * 1024 * 1024];
        array1 = new  byte[2 * 1024 * 1024];
        array1 = new  byte[2 * 1024 * 1024];
        array1 = null;

        byte[] array2 = new byte[128 * 1024];

        //尝试给array3分配内存时 Eden内存已经不足 -->触发young gc  gc完之后没有晋升到老年代的内存,array2年龄为1
        byte[] array3 = new byte[2 * 1024 * 1024];

        array3 = new byte[2 * 1024 * 1024];
        array3 = new byte[2 * 1024 * 1024];
        array3 = new byte[128 * 1024];
        array3 = null;

        //第二次young gc
        //此时发现array2+未知对象占用内存年龄为1，且内存占比大于survivor的50%，因此将年龄为1以及以后的晋升到老年区
        byte[] array4 = new byte[2 * 1024 * 1024];

    }
}
