package com.yu.init.xunfei;


import com.yu.init.manager.SparkManager;
import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.exception.SparkException;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import io.github.briqt.spark4j.model.response.SparkTextUsage;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class XunFeiAiTest {

    @Resource
    private SparkManager sparkManager;

    @Resource
    private PlatformTransactionManager transactionManager;

    public static final String PRECONDITION ="你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容:\n" +
            "分析需求：分析网站趋势，请使用折线图\n" +
            "原始数据：\n" +
            "1月,13\n" +
            "2月,12\n" +
            "3月,5\n" +
            "4月,7\n" +
            "5月,8\n" +
            "6月,13\n" +
            "7月,45\n" +
            "8月,26\n" +
            "9月,14\n" +
            "10月,15\n" +
            "11月,11\n" +
            "12月,13\n" +
            "请根据分析需求和原始数据这两部分内容,生成前端 Echarts V5 的 option对象所对应的的json字符串格式的代码 和 分200字结论 ,不要生成其他无相关的内容,比如:注释 }";
    @Test
    public void sync(){
        SparkClient sparkClient=new SparkClient();
        sparkClient.appid="6d07b086";
        sparkClient.apiKey="1509ca9333dc32b28c0162763fc92222";
        sparkClient.apiSecret="ZWZkMzNhMjE5YWZmM2RlZjk3ZmViMDA2";
        List<SparkMessage> messages=new ArrayList<>();
        messages.add(SparkMessage.userContent(PRECONDITION));
        SparkRequest sparkRequest=SparkRequest.builder()
                .messages(messages)
                .maxTokens(2048)
                .temperature(0.2)
                .apiVersion(SparkApiVersion.V3_5)
                .build();
        try {
            // 同步调用
            SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
            SparkTextUsage textUsage = chatResponse.getTextUsage();
            sparkClient.chatStream(sparkRequest,new SparkConsoleListener());
            System.out.println(chatResponse.getContent());
        } catch (SparkException e) {
            System.out.println("发生异常了：" + e.getMessage());
        }
    }


    @Test
    public void test() {
    }
  
    @Test
    public void name() {
        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages=new ArrayList<>();
        messages.add(SparkMessage.systemContent("请你扮演我的语文老师李老师，问我讲解问题问题，希望你可以保证知识准确，逻辑严谨。"));
        messages.add(SparkMessage.userContent("鲁迅和周树人小时候打过架吗？"));
// 构造请求
        SparkRequest sparkRequest=SparkRequest.builder()
                .messages(messages)

                .maxTokens(2048)
// 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.2)
// 指定请求版本，默认使用最新3.0版本
                .apiVersion(SparkApiVersion.V3_5)
                .build();

        try {
            // 同步调用
            SparkClient sparkClient=new SparkClient();

            sparkClient.appid="6d07b086";
            sparkClient.apiKey="1509ca9333dc32b28c0162763fc92222";
            sparkClient.apiSecret="ZWZkMzNhMjE5YWZmM2RlZjk3ZmViMDA2";
            SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
            SparkTextUsage textUsage = chatResponse.getTextUsage();

            System.out.println("\n回答：" + chatResponse.getContent());
            System.out.println("\n提问tokens：" + textUsage.getPromptTokens()
                    + "，回答tokens：" + textUsage.getCompletionTokens()
                    + "，总消耗tokens：" + textUsage.getTotalTokens());
        } catch (SparkException e) {
            System.out.println("发生异常了：" + e.getMessage());
        }

    }
}
