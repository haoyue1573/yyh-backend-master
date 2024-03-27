package com.yu.init.utils.service;

import com.yu.init.manager.SparkManager;
import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.listener.SparkBaseListener;
import io.github.briqt.spark4j.model.request.SparkRequest;
import io.github.briqt.spark4j.model.response.SparkResponse;
import io.github.briqt.spark4j.model.response.SparkResponseUsage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@Slf4j
@ServerEndpoint("/websocket/ai") //暴露的ws应用的路径
public class WebSocket  extends SparkBaseListener {



        @Resource
        private SparkManager sparkManager;

        @Resource
        private SparkClient sparkClient;

        /**
         * 客户端与服务端连接成功
         * @param session
         * @param username
         */
        @OnOpen
        public void onOpen(Session session,@PathParam("identification") String username){

                System.out.println("建立链接");
        }

        /**
         * 客户端与服务端连接关闭
         * @param session
         * @param username
         */
        @OnClose
        public void onClose(Session session, @PathParam("identification") String username){

                System.out.println("客户端与服务端连接关闭");

        }

        /**
         * 客户端与服务端连接异常
         * @param error
         * @param session
         * @param username
         */
        @OnError
        public void onError(Throwable error,Session session,@PathParam("identification") String username) {
                System.out.println("后台链接异常");
        }

        @OnMessage
        public void onMsg(Session session,String message,@PathParam("identification") String username) throws IOException {
                System.out.println("接收到前端的消息为"+message);
  /*              // 消息列表，可以在此列表添加历史对话记录
                List<SparkMessage> messages = new ArrayList<>();
                messages.add(SparkMessage.userContent(message));
                // 构造请求
                SparkRequest sparkRequest = SparkRequest.builder()
                        // 消息列表
                        .messages(messages)
                        // 模型回答的tokens的最大长度，非必传，默认为2048
                        .maxTokens(2048)
                        // 结果随机性，取值越高随机性越强，即相同的问题得到的不同答案的可能性越高，非必传，取值为[0,1]，默认为0.5
                        .temperature(0.2)
                        // 指定请求版本
                        .apiVersion(SparkApiVersion.V3_5)
                        .build();
                // 流式调用
                sparkClient.chatStream(sparkRequest,new SparkConsoleListener());
               // session.getAsyncRemote().sendText("大傻叉");*/
        }

        @Override
        public void onMessage(String content, SparkResponseUsage usage, Integer status, SparkRequest sparkRequest, SparkResponse sparkResponse, okhttp3.WebSocket webSocket) {

                webSocket.send("");
        }
}
