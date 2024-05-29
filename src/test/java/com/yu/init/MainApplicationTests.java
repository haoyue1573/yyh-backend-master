package com.yu.init;

import com.yu.init.xunfei.CustomThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 主类测试
 */
public class MainApplicationTests {

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2), new CustomThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        for (int i = 1; i < 5; i++) {
            int temp = i;
            if (temp==4){
                Thread.sleep(5000);
            }
            threadPoolExecutor.execute(()->{
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String name = Thread.currentThread().getName();
                System.out.println("任务"+ temp+" "+name);
            });
        }
        threadPoolExecutor.shutdown();
    }
}
