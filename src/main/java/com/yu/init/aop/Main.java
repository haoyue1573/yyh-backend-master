package com.yu.init.aop;

public class Main {
    public static void main(String[] args) {
            Isolver solver = new Solver();
            Isolver proxyInstance = (Isolver) new ProxyFactory(solver).getProxyInstance();
            proxyInstance.solve();
    }
}
