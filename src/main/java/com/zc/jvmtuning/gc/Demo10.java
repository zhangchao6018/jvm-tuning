package com.zc.jvmtuning.gc;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 描述:模拟Metaspace内存溢出
 *
 * -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
 *
 *
 *
 * -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -XX:+PrintGCDetails -Xloggc:./jvm-tuning/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./jvm-tuning
 *
 * 查看gclog,以及内存快照
 * 发现Enhancer频繁生成动态Class对象
 *
 * 解决:
 * Enhancer单例化
 * @Author: zhangchao
 **/
public class Demo10 {
    public static void main(String[] args) {
        final int[] count = {0};
        while (true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Car.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    if (method.getName().equals("run")){
                        System.out.println(count[0]++ +"启动汽车时,先进行自动安全检查...");
                        return methodProxy.invokeSuper(o,objects);
                    }else {
                        return methodProxy.invokeSuper(o,objects);
                    }
                }
            });
            Car car = (Car) enhancer.create();
            car.run();
        }
    }

    static class Car{
        public void run(){
            System.out.println("汽车自动启动,开始行使...");
        }
    }
}
