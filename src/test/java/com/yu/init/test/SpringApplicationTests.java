package com.yu.init.test;

import com.yu.init.MainApplication;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = MainApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(value = SpringJUnit4ClassRunner.class)
public class SpringApplicationTests {

    @Resource
    private YuCongMingClient yuCongMingClient;

    @Test
    public void test(){
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setMessage("你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
                "分析需求：分析网站趋势\n" +
                "原始数据:\n" +
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
                "请根据这两部分内容，按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
                "【【【【\n" +
                "{前端Echarts V5的option配置对象js代码，合理地将数据进行可视化，不要生成任何多余的内容，比如注释}\n" +
                "【【【【\n" +
                "{明确的数据分析结论、越详细越好，不要生成多余的注释}");
        devChatRequest.setModelId(1659171950288818178L);
        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        DevChatResponse data = response.getData();
        System.out.println(data.getContent());
    }
}
