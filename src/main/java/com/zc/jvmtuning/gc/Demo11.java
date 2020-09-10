package com.zc.jvmtuning.gc;

/**
 * 描述:
 * JVM栈内存溢出
 * @Author: zhangchao
 **/
public class Demo11 {
    public static void main(String[] args) {
        loop();
    }
    public static void loop(){
        System.out.println("do loop...");
        loop();
    }
}
