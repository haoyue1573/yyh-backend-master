package com.yu.init.jwt;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.commons.lang3.StringUtils;

public class Demo {

    public static void main(String[] args) {

        LogFactory logFactory = LogFactory.create();
        StringBuilder userInput = new StringBuilder();
        userInput.append("你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容:").append("\n");
        userInput.append("分析需求：");
        // 拼接分析目标
        String userGoal = "分析人口趋势";
        if (StringUtils.isNotBlank("折线图")) {
            userGoal += "，请使用" + "折线图";
        }
        userInput.append(userGoal).append("\n");
        userInput.append("原始数据：");

        userInput.append("csv数据...").append("\n");
        System.out.println(userInput.toString());

        Log log = logFactory.createLog(Demo.class);
        log.info("你是谁");

    }

    void  test(){
        System.out.println("test..");
    }
}
