package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.RecommendationRuleMapper;
import com.adatafun.recommendation.mapper.TbdFlightInfoMapper;
import com.adatafun.recommendation.model.RecommendationRule;
import com.adatafun.recommendation.model.TbdFlightInfo;
import com.adatafun.recommendation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.adatafun.recommendation.service.ElasticSearchService.getUserBehaviorLabel;

/**
 * RecommendationBaseRuleService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
@Service
public class RecommendationBaseRuleService {

    private final RecommendationRuleMapper recommendationRuleMapper;
    private final TbdFlightInfoMapper tbdFlightInfoMapper;

    @Autowired
    public RecommendationBaseRuleService(RecommendationRuleMapper recommendationRuleMapper,
                                         TbdFlightInfoMapper tbdFlightInfoMapper) {
        this.recommendationRuleMapper = recommendationRuleMapper;
        this.tbdFlightInfoMapper = tbdFlightInfoMapper;
    }

    public Map getPositionRule(Map<String, Object> param) throws Exception {

        Map<String,Object> result = new HashMap<>();
        RecommendationRule positionRule = new RecommendationRule();
        int positionFlag = Integer.parseInt(param.get("positionFlag").toString());
        if (positionFlag == 1) {
            String position = param.get("position").toString();
            Map<String,Object> paramPositionRule = new HashMap<>();
            switch (position) {
                case "机场内":
                    paramPositionRule.put("ruleName", "用户定位在安检后");
                    break;
                case "机场外":
                    paramPositionRule.put("ruleName", "用户定位在安检前");
                    break;
                case "到达区":
                    paramPositionRule.put("ruleName", "用户定位在到达区");
                    break;
            }
            if (paramPositionRule.isEmpty()) {
                positionFlag = 0;
            } else {
                positionRule = recommendationRuleMapper.getRecommendationRule(paramPositionRule);
                if (positionRule.equals(new RecommendationRule())) {
                    positionFlag = 0;
                }
            }
        }
        result.put("positionRule", positionRule);
        result.put("positionFlag", positionFlag);
        return result;

    }

    public Map getFlightInfoRule(Map<String, Object> param) throws Exception {

        Map<String,Object> result = new HashMap<>();
        RecommendationRule flightInfoRule = new RecommendationRule();
        int flightInfoFlag = Integer.parseInt(param.get("flightInfoFlag").toString());
        if (flightInfoFlag == 1) {

            //获取航班信息
            Map<String,Object> paramFlightInfo = new HashMap<>();
            paramFlightInfo.put("flightNo", param.get("flightNo"));
            paramFlightInfo.put("flightDate", param.get("flightDate"));
            TbdFlightInfo tbdFlightInfo = tbdFlightInfoMapper.getFlightInfoByFlightNo(paramFlightInfo);

            //确定推荐规则
            if (!tbdFlightInfo.equals(new TbdFlightInfo())) {
                Date currentDate = new Date();
                Map<String,Object> paramFlightInfoRule = new HashMap<>();
                if (tbdFlightInfo.getFlightStatus().equals("到达")) {
                    Date flightArriveDate = tbdFlightInfo.getArriveTimeActual();
                    int timeInterval = (int) (currentDate.getTime() - flightArriveDate.getTime())/(1000 * 60 * 60);
                    if (timeInterval < 3) {
                        paramFlightInfoRule.put("ruleName", "航班到达后3小时内");
                    } else {
                        flightInfoFlag = 0;
                    }
                } else {
                    Date flightDepartDate = tbdFlightInfo.getDepartTimePlan();
                    int timeInterval = (int) (flightDepartDate.getTime() - currentDate.getTime())/(1000 * 60 * 60);
                    if (timeInterval < 1) {
                        paramFlightInfoRule.put("ruleName", "航班起飞1小时内");
                    } else {
                        paramFlightInfoRule.put("ruleName", "航班起飞1小时前");
                    }
                }
                flightInfoRule = recommendationRuleMapper.getRecommendationRule(paramFlightInfoRule);
                if (flightInfoRule.equals(new RecommendationRule())) {
                    flightInfoFlag = 0;
                }
            } else {
                flightInfoFlag = 0;
            }
        }
        result.put("flightInfoRule", flightInfoRule);
        result.put("flightInfoFlag", flightInfoFlag);
        return result;

    }

    public Map<String, Object> getUserBehaviorRule(int userBehaviorFlag, String ruleName) throws Exception {

        Map<String,Object> result = new HashMap<>();
        Map<String,Object> paramUserBehaviorRule = new HashMap<>();
        paramUserBehaviorRule.put("ruleName", ruleName);
        RecommendationRule userBehaviorRule = recommendationRuleMapper.getRecommendationRule(paramUserBehaviorRule);
        if (userBehaviorRule.equals(new RecommendationRule())) {
            userBehaviorFlag = 0;
        }

        result.put("userBehaviorRule", userBehaviorRule);
        result.put("userBehaviorFlag", userBehaviorFlag);
        return result;

    }

}
