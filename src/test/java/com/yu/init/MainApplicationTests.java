package com.yu.init;

import com.yu.init.enums.Enums;

import java.util.ArrayList;

/**
 * 主类测试
 *

 */
class MainApplicationTests {

    public static void main(String[] args) {
     String string = "【【【【\n" +
             "{\n" +
             "  \"title\": {\n" +
             "    \"text\": \"用户增长情况分析\"\n" +
             "  },\n" +
             "  \"tooltip\": {},\n" +
             "  \"legend\": {\n" +
             "    \"data\": [\"用户数量\"]\n" +
             "  },\n" +
             "  \"xAxis\": {\n" +
             "    \"data\": [\"1月\", \"2月\", \"3月\"]\n" +
             "  },\n" +
             "  \"yAxis\": {},\n" +
             "  \"series\": [\n" +
             "    {\n" +
             "      \"name\": \"用户数量\",\n" +
             "      \"type\": \"bar\",\n" +
             "      \"data\": [21, 15, 13]\n" +
             "    }\n" +
             "  ]\n" +
             "}\n" +
             "\n" +
             "【【【【\n" +
             "根据给定的数据，我们可以看到在1月份用户数量达到了最高的21人，而在2月份和3月份，用户数量有所下降，分别为15人和13人。从这个趋势来看，用户增长情况并不理想，可能需要进一步分";
        String[] split = string.split("【【【【");
        System.out.println(split.length);
        System.out.println(split[0].trim());
        System.out.println("================================");
        System.out.println(split[1].trim());
        System.out.println("================================");
        System.out.println(split[2].trim());

        System.out.println();
        Enums[] values = Enums.values();
        System.out.println(values[0]);
        System.out.println(Enums.WAITING.getMsg());

        ArrayList<String> list = new ArrayList<>();
        list.add("1");



    }


}
