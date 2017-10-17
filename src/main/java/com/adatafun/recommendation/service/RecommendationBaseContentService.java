package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.RecommendationRuleMapper;
import com.adatafun.recommendation.model.RecommendationRule;
import com.adatafun.recommendation.model.User;
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

    public Map<String, Object> getUserBehavior(Map<String, Object> param) throws Exception {

        Map<String,Object> result = new HashMap<>();
        int userBehaviorFlag = Integer.parseInt(param.get("userBehaviorFlag").toString());
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

        result.put("userList", userList);
        result.put("userBehaviorFlag", userBehaviorFlag);
        return result;

    }

}
