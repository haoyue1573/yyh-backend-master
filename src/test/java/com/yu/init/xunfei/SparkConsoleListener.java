package com.yu.init.xunfei;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.listener.SparkBaseListener;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.request.SparkRequest;
import io.github.briqt.spark4j.model.response.SparkResponse;
import io.github.briqt.spark4j.model.response.SparkResponseUsage;
import io.github.briqt.spark4j.model.response.SparkTextUsage;
import okhttp3.WebSocket;

import java.util.ArrayList;
import java.util.List;

public class SparkConsoleListener extends SparkBaseListener {

    private final StringBuilder stringBuilder = new StringBuilder();

    public ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public void onMessage(String content, SparkResponseUsage usage, Integer status, SparkRequest sparkRequest, SparkResponse sparkResponse, WebSocket webSocket) {
        if (0 == status) {
            List<SparkMessage> messages = sparkRequest.getPayload().getMessage().getText();
            try {
                System.out.println("提问：" + objectMapper.writeValueAsString(messages));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\n收到回答：\n");
        }

        try {
            System.out.println("--content：" + content + " --完整响应：" + objectMapper.writeValueAsString(sparkResponse));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (2 == status) {
            System.out.println("\n完整回答：" + stringBuilder.toString());
            SparkTextUsage textUsage = usage.getText();
            System.out.println("\n回答结束；提问tokens：" + textUsage.getPromptTokens()
                    + "，回答tokens：" + textUsage.getCompletionTokens()
                    + "，总消耗tokens：" + textUsage.getTotalTokens());
        }
    }

    /**
     * 调用代码
     * @param args
     */
    public static void main(String[] args) {
        SparkClient sparkClient=new SparkClient();
        sparkClient.appid="6d07b086";
        sparkClient.apiKey="1509ca9333dc32b28c0162763fc92222";
        sparkClient.apiSecret="ZWZkMzNhMjE5YWZmM2RlZjk3ZmViMDA2";
        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages=new ArrayList<>();
        messages.add(SparkMessage.userContent("请你扮演我的语文老师李老师，问我讲解问题问题，希望你可以保证知识准确，逻辑严谨。"));
        messages.add(SparkMessage.assistantContent("好的，这位同学，有什么问题需要李老师为你解答吗？"));
        messages.add(SparkMessage.userContent("鲁迅和周树人小时候打过架吗？"));
        SparkRequest sparkRequest=SparkRequest.builder()
                .messages(messages)
                .maxTokens(2048)
                .temperature(0.2)
                .apiVersion(SparkApiVersion.V3_5)
                .build();
        // 使用默认的控制台监听器，流式调用；
        sparkClient.chatStream(sparkRequest,new io.github.briqt.spark4j.listener.SparkConsoleListener());
        // 实际使用时请继承SparkBaseListener自定义监听器实现
     //   sparkClient.chatStream(sparkRequest,new SparkConsoleListener());

    }
}