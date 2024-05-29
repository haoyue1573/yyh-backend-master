package com.yu.init.websocket;

import io.github.briqt.spark4j.listener.SparkBaseListener;
import io.github.briqt.spark4j.model.request.SparkRequest;
import io.github.briqt.spark4j.model.response.SparkResponse;
import io.github.briqt.spark4j.model.response.SparkResponseUsage;
import io.github.briqt.spark4j.model.response.SparkTextUsage;

import javax.websocket.Session;
import java.io.IOException;


public class SparkStreamListener extends SparkBaseListener {

    private final StringBuilder stringBuilder = new StringBuilder();

    private final Session  session ;

    public SparkStreamListener(Session session) {
        this.session = session;
    }

    @Override
    public void onMessage(String content, SparkResponseUsage usage, Integer status, SparkRequest sparkRequest, SparkResponse sparkResponse, okhttp3.WebSocket webSocket) {
        System.out.println("session的值："+session.getClass());
        System.out.println("status:"+status);
        System.out.println("当前线程是："+Thread.currentThread().getName());
        String responseContent  = sparkResponse.getPayload().getChoices().getText().get(0).getContent();
        try {
            session.getBasicRemote().sendText(responseContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringBuilder.append(responseContent);
        if (2 == status) {
            System.out.println("\n完整回答：" + stringBuilder);
            SparkTextUsage textUsage = usage.getText();
            System.out.println("\n回答结束；提问tokens：" + textUsage.getPromptTokens()
                    + "，回答tokens：" + textUsage.getCompletionTokens()
                    + "，总消耗tokens：" + textUsage.getTotalTokens());
        }
    }
}
