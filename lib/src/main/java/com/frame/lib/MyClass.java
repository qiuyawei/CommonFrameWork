package com.frame.lib;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MyClass {
    public static void main(String[] args) {
//        testUndefineLengthArray(1,6,9,10);
//        getNewStr("zhangSan ","is a","goodBoy !");
        voliateTest();
    }


    /**
     * 不定长度数组测试
     */
    public static void testUndefineLengthArray(int... args) {
        System.out.println("lenth:" + args.length);
        int sum = 0;
        for (int i = 0; i < args.length; i++) {
            sum = sum + args[i];
        }
        System.out.println("sum:" + sum);
    }

    public static void getNewStr(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : params) {
            stringBuilder.append(str);
        }
        System.out.println("拼接的新字符串：" + stringBuilder.toString());
    }

    /**
     * 测试原子性，就是高并发情况下，只有一个线程可以访问这个数据
     */

    public static void voliateTest() {
        ExecutorService executors = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            final int finalI = i;
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(new Random().nextInt(finalI+1)*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int val=Constans.a.get();
                    int vall=Constans.max;
//                    System.out.println("tickets_int_" + vall);
//
//                    Constans.max=vall-1;
//
                    System.out.println("tickets_ato_" + val);
                    Constans.a.set(val-1);
                }
            });
        }
        executors.shutdown();
    }
}
