package com.yu.init.xunfei;


import com.yu.init.MainApplication;
import com.yu.init.manager.SparkManager;
import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.exception.SparkException;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import io.github.briqt.spark4j.model.response.SparkTextUsage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = MainApplication.class)
@RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class XunFeiAiTest {

    @Resource
    private SparkManager sparkManager;

    @Resource
    private PlatformTransactionManager transactionManager;

    public static final String PRECONDITION = "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
            "分析需求：\n" +
            "{数据分析的需求或者目标}\n" +
            "原始数据：\n" +
            "1月 12 2月 3 3月 33\n" +
            "请根据这两部分内容，按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
            "【【【【【\n" +
            "{前端 Echarts V5 的 option 配置对象js代码，合理地将数据进行可视化，不要生成任何多余的内容，比如注释}\n" +
            "【【【【【\n" +
            "{明确的数据分析结论，越详细越好，不要生成多余的注释\n}" +
            "最终格式是：【【【【【前端代码【【【【【分析结论";

    @Test
    public void test() {
       // String s = sparkManager.sendMesToXunFeiAndSync(PRECONDITION);

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // ....  业务代码
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }
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
