package com.adatafun.recommendation.mapper;

import com.adatafun.recommendation.model.TbdFlightInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/9/21.
 */
public interface TbdFlightInfoMapper {
    TbdFlightInfo getFlightInfoByFlightNo(Map<String, Object> map);
}
