package com.adatafun.recommendation.controller;

import com.adatafun.recommendation.service.ItdCIPService;
import com.adatafun.recommendation.service.RecommendationBaseContentService;
import com.adatafun.recommendation.service.RecommendationBaseRuleService;
import com.adatafun.recommendation.utils.DataProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CIPRecommendationController.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/23.
 */
@Component
public class CIPRecommendationController {
    private final ItdCIPService itdCIPService;
    private final RecommendationBaseRuleService recommendationBaseRuleService;
    private final RecommendationBaseContentService recommendationBaseContentService;

    @Autowired
    public CIPRecommendationController(ItdCIPService itdCIPService,
                                       RecommendationBaseRuleService recommendationBaseRuleService,
                                       RecommendationBaseContentService recommendationBaseContentService) {
        this.itdCIPService = itdCIPService;
        this.recommendationBaseRuleService = recommendationBaseRuleService;
        this.recommendationBaseContentService = recommendationBaseContentService;
    }

    List<Map<String, Object>> getCIPList(String userId, String airportCode, Integer cipFlag, Integer cipWeight) throws Exception {
        int userBehaviorFlag = 1;

        //过滤得到安检通道
        List<Map<String,Object>> cipList = itdCIPService.getCIPList(airportCode);

        //航班起飞前60-120分钟，权重最高
        for (Map<String, Object> cip : cipList) {
            if (cipFlag == 2) { //flag==2,说明此时用户急需快速安检通道服务
                cip.put("flag", 1);
            }
        }

        //获取用户行为规则
        Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "快速安检通道行为");

        //获取用户行为标签
        Map<String,Object> paramUserBehavior = new HashMap<>();
        paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
        paramUserBehavior.put("userId", userId);
        paramUserBehavior.put("indexName", "user");
        paramUserBehavior.put("typeName", "userCIP");
        Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
        resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

        //用户行为数据归一化处理
        DataProcessing dataProcessing = new DataProcessing();
        Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

        //给安检通道打分
        List<Map<String, Object>> productList = dataProcessing.hybridWeightCalculation(userBehaviorAfterNorm, cipList);

        //根据运营策略决定最终分数
        return dataProcessing.getFinalScore(cipWeight, productList);
    }
}
