package com.yu.init.utils.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.init.common.ErrorCode;
import com.yu.init.enums.Enums;
import com.yu.init.exception.ThrowUtils;
import com.yu.init.manager.RedisLimiterManager;
import com.yu.init.manager.SparkManager;
import com.yu.init.mapper.ChartMapper;
import com.yu.init.model.dto.chart.GenChartByAiRequest;
import com.yu.init.model.entity.Chart;
import com.yu.init.model.vo.BiResponse;
import com.yu.init.utils.service.ChartService;
import com.yu.init.utils.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 */
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService {

    @Resource
    private RedisLimiterManager redisLimiterManager;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private SparkManager sparkManager;

    @Resource
    private UserService userService;


    @Override
    public BiResponse genChartData(GenChartByAiRequest genChartByAiRequest,String csvData,Long UserId) {
        String name = genChartByAiRequest.getName();
        String goal = genChartByAiRequest.getGoal();
        String chartType = genChartByAiRequest.getChartType();

        // 构造用户输入
        StringBuilder userInput = new StringBuilder();
        userInput.append("你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容:").append("\n");
        userInput.append("分析需求：").append("\n");
        // 拼接分析目标
        String userGoal = goal;
        if (StringUtils.isNotBlank(chartType)) {
            userGoal += "，请使用" + chartType;
        }
        userInput.append(userGoal).append("\n");
        userInput.append("原始数据：").append("\n");

        userInput.append(csvData).append("\n");
        userInput.append("请根据这两部分内容，按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
                "{前端 Echarts V5 的 option 配置对象的json字符串格式的js代码，合理地将数据进行可视化，不要生成任何多余的内容，比如注释}\n" +
                "{明确的数据分析结论，越详细越好，不要生成多余的注释\n}" +
                "最终生成的前端代码和分析结论使用 #### 隔离开;");
        String result = sparkManager.sendMesToXunFeiAndSync(userInput.toString());

        System.out.println(result);
        String[] split = result.split("```javascript");
        String[] split1 = split[1].split("```");
        String genChart = split1[0];
        String genResult = split1[1];
        // 插入到数据库
        Chart chart = new Chart();
        chart.setName(name);
        chart.setGoal(goal);
        chart.setStatus(Enums.SUCCEED.getMsg());
        chart.setChartData(csvData);
        chart.setChartType(chartType);
        chart.setGenChart(genChart);
        chart.setGenResult(genResult);
        chart.setUserId(UserId);
        boolean saveResult = this.save(chart);
        ThrowUtils.throwIf(!saveResult, ErrorCode.SYSTEM_ERROR, "图表保存失败");
        BiResponse biResponse = new BiResponse();
        biResponse.setGenChart(genChart);
        biResponse.setGenResult(genResult);
        biResponse.setChartId(chart.getId());
        return biResponse;
    }
}


