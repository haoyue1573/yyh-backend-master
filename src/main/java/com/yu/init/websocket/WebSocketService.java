package com.yu.init.websocket;

import com.yu.init.manager.SparkManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@Slf4j
@ServerEndpoint("/websocket/ai") //暴露的ws的路径
public  class WebSocketService  {


    private static SparkManager sparkManager;


    @Autowired
    public void SetSparkManager(SparkManager sparkManager){
         WebSocketService.sparkManager = sparkManager;
    }

    /**
     * 客户端与服务端连接成功
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("链接建立成功");
    }

    /**
     * 客户端与服务端连接关闭
     *
     * @param session
     * @param username
     */
    @OnClose
    public void onClose(Session session, @PathParam("identification") String username) {
        System.out.println("客户端与服务端连接关闭");
    }

    /**
     * 客户端与服务端连接异常
     * @param error
     * @param session
     * @param username
     */
    @OnError
    public void onError(Throwable error, Session session, @PathParam("identification") String username) {
        throw new RuntimeException(error);
    }

    /**
     * 可客户端和服务端通信方法
     *
     * @param session
     * @param message
     * @param username
     * @throws IOException
     */

    @OnMessage
    public void onMsg(Session session, String message, @PathParam("identification") String username) throws IOException {
        System.out.println("客户端的信息"+message);
        sparkManager.sendMesToXunFeiAndStream(message, session);
    }
}