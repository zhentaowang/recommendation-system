package com.adatafun.recommendation.mapper;

import com.adatafun.recommendation.model.RecommendationRule;

import java.util.Map;

/**
 * Created by wzt on 2017/9/22.
 */
public interface RecommendationRuleMapper {
    RecommendationRule getRecommendationRule(Map<String, Object> map);
}
