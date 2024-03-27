package com.yu.init.utils.service;

import com.yu.init.model.dto.chart.GenChartByAiRequest;
import com.yu.init.model.entity.Chart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yu.init.model.vo.BiResponse;

/**
 *
 */
public interface ChartService extends IService<Chart> {

    BiResponse genChartData(GenChartByAiRequest genChartByAiRequest,String csvData,Long id);

}
