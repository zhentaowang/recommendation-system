package com.adatafun.recommendation.controller;

import com.adatafun.recommendation.service.ItdCarService;
import com.adatafun.recommendation.service.RecommendationBaseContentService;
import com.adatafun.recommendation.service.RecommendationBaseRuleService;
import com.adatafun.recommendation.utils.DataProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CarRecommendationController.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/23.
 */
@Component
public class CarRecommendationController {

    private final ItdCarService itdCarService;
    private final RecommendationBaseRuleService recommendationBaseRuleService;
    private final RecommendationBaseContentService recommendationBaseContentService;

    @Autowired
    public CarRecommendationController(ItdCarService itdCarService,
                                        RecommendationBaseRuleService recommendationBaseRuleService,
                                        RecommendationBaseContentService recommendationBaseContentService) {
        this.itdCarService = itdCarService;
        this.recommendationBaseRuleService = recommendationBaseRuleService;
        this.recommendationBaseContentService = recommendationBaseContentService;
    }

    List<Map<String, Object>> getCarList(String userId, String airportCode, Integer carWeight) throws Exception {
        int userBehaviorFlag = 1;

        //过滤得到礼宾车
        List<Map<String,Object>> carList = itdCarService.getCarList(airportCode);

        //获取用户行为规则
        Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "礼宾车行为");

        //获取用户行为标签
        Map<String,Object> paramUserBehavior = new HashMap<>();
        paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
        paramUserBehavior.put("userId", userId);
        paramUserBehavior.put("indexName", "user");
        paramUserBehavior.put("typeName", "userCar");
        Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
        resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

        //用户行为数据归一化处理
        DataProcessing dataProcessing = new DataProcessing();
        Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

        //给礼宾车打分
        List<Map<String, Object>> productList = dataProcessing.hybridWeightCalculation(userBehaviorAfterNorm, carList);

        //根据运营策略决定最终分数
        return dataProcessing.getFinalScore(carWeight, productList);
    }

}
