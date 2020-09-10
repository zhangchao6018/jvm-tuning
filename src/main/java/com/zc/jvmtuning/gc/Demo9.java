package com.zc.jvmtuning.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 由于代码问题造成频繁Full GC
 *
 * 用MAT工具分析
 *
 * @Author: zhangchao
 **/
public class Demo9 {
    public static void main(String[] args) throws InterruptedException {
        List<Data> datas = new ArrayList<>();
        for (int j=1;j<=10000 ;j++) {
            datas.add(new Data());
        }
        Thread.sleep( 60 * 60 * 1000);
    }

    static class Data{

    }
}
