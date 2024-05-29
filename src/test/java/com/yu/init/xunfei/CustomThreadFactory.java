package com.yu.init.xunfei;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: yuhao
 * @Date: 2024/5/28 12:22
 */
public class CustomThreadFactory implements ThreadFactory {

    private   AtomicInteger threadId = new AtomicInteger(1);

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("线程"+threadId.get());
        threadId.incrementAndGet();
        return thread;
    }

    public static void main(String[] args) {
        AtomicInteger threadId = new AtomicInteger(100);
        System.out.println(threadId.get());
        System.out.println(threadId);
    }
}
