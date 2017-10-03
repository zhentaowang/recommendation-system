package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.RecommendationRuleMapper;
import com.adatafun.recommendation.model.RecommendationRule;
import com.adatafun.recommendation.model.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.adatafun.recommendation.service.ElasticSearchService.getUserBehaviorLabel;

/**
 * RecommendationBaseContentService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
@Service
public class RecommendationBaseContentService {

    private final RecommendationRuleMapper recommendationRuleMapper;

    @Autowired
    public RecommendationBaseContentService(RecommendationRuleMapper recommendationRuleMapper) {
        this.recommendationRuleMapper = recommendationRuleMapper;
    }

    public Map<String, Object> getUserBehavior(Map<String, Object> param) throws Exception {

        Map<String,Object> result = new HashMap<>();
        int userBehaviorFlag = Integer.parseInt(param.get("userBehaviorFlag").toString());
        Map<String,Object> paramUserBehaviorRule = new HashMap<>();
        paramUserBehaviorRule.put("ruleName", param.get("ruleName"));
        RecommendationRule userBehaviorRule = recommendationRuleMapper.getRecommendationRule(paramUserBehaviorRule);
        if (userBehaviorRule.equals(new RecommendationRule())) {
            userBehaviorFlag = 0;
        }

        //获取用户行为标签
        Map<String,Object> paramUserBehaviorLabel = new HashMap<>();
        paramUserBehaviorLabel.put("indexName", param.get("indexName"));
        paramUserBehaviorLabel.put("typeName", param.get("typeName"));
        paramUserBehaviorLabel.put("userId", param.get("userId"));
        List<User> userList = getUserBehaviorLabel(paramUserBehaviorLabel);
        assert userList != null;
        if (userList.size() == 0) {
            userBehaviorFlag = 0;
        }
        paramUserBehaviorLabel.put("typeName", "userTags");
        List<User> userTagsList = getUserBehaviorLabel(paramUserBehaviorLabel);
        result.put("userBehaviorRule", userBehaviorRule);
        result.put("userList", userList);
        result.put("userTagsList", userTagsList);
        result.put("userBehaviorFlag", userBehaviorFlag);
        return result;

    }

}
