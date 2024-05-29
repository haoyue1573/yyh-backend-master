package com.yu.init.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.init.common.ErrorCode;
import com.yu.init.enums.Enums;
import com.yu.init.exception.BusinessException;
import com.yu.init.exception.ThrowUtils;
import com.yu.init.manager.SparkManager;
import com.yu.init.mapper.ChartMapper;
import com.yu.init.model.dto.chart.GenChartByAiRequest;
import com.yu.init.model.entity.Chart;
import com.yu.init.model.vo.BiResponse;
import com.yu.init.service.ChartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService {

    @Resource
    private SparkManager sparkManager;

    @Override
    public BiResponse genChartData(GenChartByAiRequest genChartByAiRequest,String csvData,Long userId) {
        String name = genChartByAiRequest.getName();
        String goal = genChartByAiRequest.getGoal();
        String chartType = genChartByAiRequest.getChartType();
        // 构造用户输入
        StringBuilder userInput = new StringBuilder();
        userInput.append("你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容:").append("\n");
        userInput.append("分析需求：");
        // 拼接分析目标
        String userGoal = goal;
        if (StringUtils.isNotBlank(chartType)) {
            userGoal += "，请使用" + chartType;
        }
        userInput.append(userGoal).append("\n");
        userInput.append("原始数据：").append("\n");

        userInput.append(csvData).append("\n");

        System.out.println(csvData);
        userInput.append( "请根据分析需求和原始数据这两部分内容，按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
                "{生成前端 Echarts V5 的 option对象 所对应的的json字符串格式的代码 代码使用 ```json 和```进行包裹  , 用于合理地将数据进行可视化，不要生成任何多余的内容。比如: 注释}\n" +
                "{还有数据分析结论，越详细越好,不要生成多余的注释}");
        System.out.println(userInput.toString());
        String result = sparkManager.sendMesToXunFeiAndSync(userInput.toString());
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(result);
        System.out.println("-------------------------------------------------------------------------");
        String[] split = result.split("```json");
        String genChart = "";
        String genResult = "";
        if (split.length==3){
            String[] split1 = split[2].split("```");
            genChart = split1[0];
            genResult = split1[1];
        }else if (split.length==2){
            String[] split2 = split[1].split("```");
            genChart = split2[0];
            genResult = split2[1];
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"生成失败");
        }
        // 插入到数据库
        Chart chart = new Chart();
        chart.setName(name);
        chart.setGoal(goal);
        chart.setStatus(Enums.SUCCEED.getMsg());
        chart.setChartData(csvData);
        chart.setChartType(chartType);
        chart.setGenChart(genChart);
        chart.setGenResult(genResult);
        chart.setUserId(userId);
        boolean saveResult = this.save(chart);
        ThrowUtils.throwIf(!saveResult, ErrorCode.SYSTEM_ERROR, "图表保存失败");
        BiResponse biResponse = new BiResponse();
        biResponse.setGenChart(genChart);
        biResponse.setGenResult(genResult);
        biResponse.setChartId(chart.getId());
        return biResponse;
    }
}


