package com.zc.jvmtuning.gc;

/**
 * 描述:
 * 对象进入老年代场景2
 * <p></>15次young gc 仍然留在新生代的survivor区</p>
 *
 *为了避免动态年龄规划 将新生代内存设置为100M
 * -XX:NewSize=104857600 -XX:MaxNewSize=104857600 -XX:InitialHeapSize=209715200 -XX:MaxHeapSize=209715200 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=104857600 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 *
 * -XX:SurvivorRatio=8  新生代内存  Eden去和Survivor去的内存大小比例  即8:1:1
 * -XX:PretenureSizeThreshold=10485760   多大内存的大对象直接分配到老年代
 * @Author: zhangchao
 **/
public class Demo3 {
    public static void main(String[] args) {
        byte[] array1 = new byte[  2 * 1024 * 1024];
        byte[] array2;
        //每三次触发一次young gc  4,7,10,......... 第15次gc是循环到49时,对比小于49的循环池次数,发现第49次   concurrent mark-sweep generation total 102400K, used 2536K
        for (int i=1; i<=49; i++){
            array2 = new byte[20 * 1024 * 1024];
            array2 = null;
        }
    }
}
