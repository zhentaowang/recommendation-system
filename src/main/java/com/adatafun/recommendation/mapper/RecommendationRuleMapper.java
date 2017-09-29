package com.adatafun.recommendation.mapper;

import com.adatafun.recommendation.model.RecommendationRule;

import java.util.Map;

/**
 * RecommendationRuleMapper.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
public interface RecommendationRuleMapper {
    RecommendationRule getRecommendationRule(Map<String, Object> map);
}
