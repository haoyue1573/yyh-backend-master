package com.yu.init.manager;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.util.Proxys;
import org.springframework.stereotype.Component;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

@Component
public class OpenAiManager {

    Proxy proxy = Proxys.http("127.0.0.1", 7890);

    ChatGPT chatGPT = ChatGPT.builder()
            .apiKey("xxx")
            .proxy(proxy)
            .timeout(900)
            .apiHost("https://api.openai.com/")
            .build()
            .init();

    public static final String PRESET_MESSAGE = "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
            "分析需求：\n" +
            "{数据分析的需求或者目标}\n" +
            "原始数据：\n" +
            "{csv格式的原始数据，用,作为分隔符}\n" +
            "请根据这两部分内容，按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
            "【【【【【\n" +
            "{前端 Echarts V5 的 option 配置对象js代码，合理地将数据进行可视化，不要生成任何多余的内容，比如注释}\n" +
            "【【【【【\n" +
            "{明确的数据分析结论，越详细越好，不要生成多余的注释\n}";

    public static final String Result_Example = "这是一个输出示例：\n" +
            "【【【【【\n" +
            "{\n" +
            "\"title\": {\n" +
            "\"text\": \"用户增长情况\"\n" +
            "},\n" +
            "\"tooltip\": {\n" +
            "\"trigger\": \"axis\"\n" +
            "},\n" +
            "\"legend\": {\n" +
            "\"data\": [\"用户数\"]\n" +
            "},\n" +
            "\"toolbox\": {\n" +
            "\"show\": true,\n" +
            "\"feature\": {\n" +
            "\"dataZoom\": {},\n" +
            "\"brush\": {\n" +
            "\"type\": [\"rect\", \"polygon\", \"clear\"]\n" +
            "}\n" +
            "}\n" +
            "},\n" +
            "\"xAxis\": {\n" +
            "\"type\": \"category\",\n" +
            "\"data\": [\"1号\", \"2号\", \"3号\"]\n" +
            "},\n" +
            "\"yAxis\": {\n" +
            "\"type\": \"value\"\n" +
            "},\n" +
            "\"series\": [\n" +
            "{\n" +
            "\"name\": \"用户数\",\n" +
            "\"type\": \"line\",\n" +
            "\"data\": [10, 20, 30]\n" +
            "}\n" +
            "]\n" +
            "}\n" +
            "【【【【【\n" +
            "根据数据分析，网站用户呈现逐日增长的趋势。从1号到3号，用户数从10增长到30，平均每天增长约10个用户。这表明网站在这段时间内有着稳定的用户增长。如果需要更详细的分析，比如用户增长的原因、用户留存率等，可能需要更多的数据来进行深入分析。";

    public String sendMesToChatGPT(final String content) {
        List<Message> messages = new ArrayList<>();
        messages.add(Message.of(content));
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages(messages)
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        return res.getContent();
    }
}
