package com.yu.init.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.init.model.entity.Chart;
import com.yu.init.service.ChartService;
import com.yu.init.mapper.ChartMapper;
import org.springframework.stereotype.Service;
/**
 *
 */
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

}


