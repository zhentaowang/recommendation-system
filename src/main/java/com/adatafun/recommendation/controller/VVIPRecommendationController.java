package com.adatafun.recommendation.controller;

import com.adatafun.recommendation.service.ItdVVIPService;
import com.adatafun.recommendation.service.RecommendationBaseContentService;
import com.adatafun.recommendation.service.RecommendationBaseRuleService;
import com.adatafun.recommendation.utils.DataProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VVIPRecommendationController.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/23.
 */
@Component
public class VVIPRecommendationController {
    private final ItdVVIPService itdVVIPService;
    private final RecommendationBaseRuleService recommendationBaseRuleService;
    private final RecommendationBaseContentService recommendationBaseContentService;

    @Autowired
    public VVIPRecommendationController(ItdVVIPService itdVVIPService,
                                       RecommendationBaseRuleService recommendationBaseRuleService,
                                       RecommendationBaseContentService recommendationBaseContentService) {
        this.itdVVIPService = itdVVIPService;
        this.recommendationBaseRuleService = recommendationBaseRuleService;
        this.recommendationBaseContentService = recommendationBaseContentService;
    }

    List<Map<String, Object>> getVVIPList(String userId, String airportCode, Integer vvipWeight) throws Exception {
        int userBehaviorFlag = 1;

        //过滤得到要客通
        List<Map<String,Object>> vvipList = itdVVIPService.getVVIPList(airportCode);

        //获取用户行为规则
        Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "要客通行为");

        //获取用户行为标签
        Map<String,Object> paramUserBehavior = new HashMap<>();
        paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
        paramUserBehavior.put("userId", userId);
        paramUserBehavior.put("indexName", "user");
        paramUserBehavior.put("typeName", "userVvip");
        Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
        resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

        //用户行为数据归一化处理
        DataProcessing dataProcessing = new DataProcessing();
        Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

        //给要客通打分
        List<Map<String, Object>> productList = dataProcessing.hybridWeightCalculation(userBehaviorAfterNorm, vvipList);

        //根据运营策略决定最终分数
        return dataProcessing.getFinalScore(vvipWeight, productList);
    }
}
