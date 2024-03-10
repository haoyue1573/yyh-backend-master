package com.yu.init.aop;


import com.alibaba.excel.support.cglib.proxy.MethodInterceptor;
import com.alibaba.excel.support.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
public class Solver implements MethodInterceptor,Isolver {
     @Override
    public void solve() {
        System.out.println("疯狂掉头发解决问题……");

         List<String> list = new ArrayList<>();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }
}
