package com.adatafun.recommendation.mapper;

import com.adatafun.recommendation.model.TbdFlightInfo;

import java.util.Map;

/**
 * TbdFlightInfoMapper.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
public interface TbdFlightInfoMapper {
    TbdFlightInfo getFlightInfoByFlightNo(Map<String, Object> map);
}
