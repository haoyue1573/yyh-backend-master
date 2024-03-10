package com.yu.init.spring;

import com.yu.init.config.RedissonConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SpringDemo {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        RedissonConfig redissonConfig = (RedissonConfig) context.getBean(RedissonConfig.class);
        Assert.notNull(null,"数据不能为空");
        System.out.println(redissonConfig);
        Object object = new Object();

        Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                method.invoke(object, args);

                return null;
            }
        });
    }
}
