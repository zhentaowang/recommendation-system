package com.adatafun.recommendation.mapper;

import com.adatafun.recommendation.model.ItdLounge;

import java.util.List;
import java.util.Map;

/**
 * ItdLoungeMapper.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/9/29.
 */
public interface ItdLoungeMapper {
    List<ItdLounge> getLoungeListByCode(Map<String, Object> map);
}
