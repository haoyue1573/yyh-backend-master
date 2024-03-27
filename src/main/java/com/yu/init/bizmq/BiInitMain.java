package com.yu.init.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 用于创建测试程序用到的交换机和队列（只用在程序启动前执行一次）
 */
public class BiInitMain {

    public static void main(String[] args) {
        try {
            //创建连接工厂
            ConnectionFactory connectionFactory=new ConnectionFactory();
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");
            connectionFactory.setHost("123.249.10.204");
            connectionFactory.setVirtualHost("my_vhost");
            connectionFactory.setPort(5672);
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            String EXCHANGE_NAME =  BiMqConstant.BI_EXCHANGE_NAME;
            channel.exchangeDeclare("dep", "direct");

            // 创建队列，随机分配一个队列名称
            String queueName = BiMqConstant.BI_QUEUE_NAME;
            channel.queueDeclare("dep_queue", true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME,"dep_routingkey");
            channel.close();
            connection.close();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
