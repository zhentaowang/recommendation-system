package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.RecommendationRuleMapper;
import com.adatafun.recommendation.mapper.TbdFlightInfoMapper;
import com.adatafun.recommendation.model.RecommendationRule;
import com.adatafun.recommendation.model.TbdFlightInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Map<String, Object> getPositionRule(Map<String, Object> param) throws Exception {

        Map<String,Object> result = new HashMap<>();
        RecommendationRule positionRule = new RecommendationRule();
        int positionFlag = Integer.parseInt(param.get("positionFlag").toString());
        if (positionFlag == 1) {
            String position = param.get("position").toString();
            Map<String,Object> paramPositionRule = new HashMap<>();
            switch (position) {
                case "2":
                    paramPositionRule.put("ruleName", "送机人");
                    break;
                case "3":
                    paramPositionRule.put("ruleName", "接机人");
                    break;
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
                if (positionRule == null) {
                    positionFlag = 0;
                }
            }
        }
        result.put("positionRule", positionRule);
        result.put("positionFlag", positionFlag);
        return result;

    }

    public Map<String, Object> getFlightInfoRule(Map<String, Object> param) throws Exception {

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
            if (tbdFlightInfo != null) {
                Date currentDate = new Date();
                Map<String,Object> paramFlightInfoRule = new HashMap<>();
                String flightStatus = tbdFlightInfo.getFlightStatus();
                Date flightArriveDate = tbdFlightInfo.getArriveTimeActual();
                Date flightDepartDate = tbdFlightInfo.getDepartTimePlan();
                if (tbdFlightInfo.getDepartTimePredict() != null) {
                    flightDepartDate = tbdFlightInfo.getDepartTimePredict();
                }
                if (flightStatus != null) {
                    if (flightStatus.equals("到达") && flightArriveDate != null) {
                        int timeInterval = (int) (currentDate.getTime() - flightArriveDate.getTime())/(1000 * 60 * 60);
                        if (timeInterval < 3) {
                            paramFlightInfoRule.put("ruleName", "航班到达后3小时内");
                        } else {
                            flightInfoFlag = 0;
                        }
                    } else if ((flightStatus.equals("计划") || flightStatus.equals("延误")) && flightDepartDate != null) {
                        int timeInterval = (int) (flightDepartDate.getTime() - currentDate.getTime())/(1000 * 60 * 60);
                        if (timeInterval < 1) {
                            paramFlightInfoRule.put("ruleName", "航班起飞1小时内");
                        } else {
                            paramFlightInfoRule.put("ruleName", "航班起飞1小时前");
                        }
                    }
                } else {
                    flightInfoFlag = 0;
                }
                if (flightInfoFlag == 1) {
                    flightInfoRule = recommendationRuleMapper.getRecommendationRule(paramFlightInfoRule);
                }
                if (flightInfoRule == null) {
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

    public Map<String, Object> getHybridInfoRule(Map<String, Object> param) throws Exception {

        Map<String,Object> result = new HashMap<>();
        RecommendationRule hybridInfoRule = new RecommendationRule();
        int flightInfoFlag = Integer.parseInt(param.get("flightInfoFlag").toString());
        int positionFlag = Integer.parseInt(param.get("positionFlag").toString());
        Integer hybridInfoFlag = flightInfoFlag + positionFlag;
        String position = param.get("position").toString();
        if (hybridInfoFlag == 2) {

            //获取航班信息
            Map<String,Object> paramFlightInfo = new HashMap<>();
            paramFlightInfo.put("flightNo", param.get("flightNo"));
            paramFlightInfo.put("flightDate", param.get("flightDate"));
            TbdFlightInfo tbdFlightInfo = tbdFlightInfoMapper.getFlightInfoByFlightNo(paramFlightInfo);

            //确定推荐规则
            if (tbdFlightInfo != null) {
                String flightStatus = tbdFlightInfo.getFlightStatus();
                Date currentDate = new Date();
                Map<String,Object> paramHybridInfoRule = new HashMap<>();
                if (flightStatus.equals("计划")) {
                    Date flightDepartDate = tbdFlightInfo.getDepartTimePlan();
                    int timeInterval = (int) (flightDepartDate.getTime() - currentDate.getTime())/(1000 * 60 * 60);
                    if (position.equals("机场外")) {
                        if (timeInterval < 1) {
                            paramHybridInfoRule.put("ruleName", "起飞前1小时以内+安检前/未知定位");
                        } else if (timeInterval < 3) {
                            paramHybridInfoRule.put("ruleName", "起飞前1-3小时+机场外");
                        } else {
                            paramHybridInfoRule.put("ruleName", "起飞前3小时以上+机场外");
                        }
                    } else {
                        if (timeInterval < 1) {
                            paramHybridInfoRule.put("ruleName", "起飞前1小时以内+安检后");
                        } else if (timeInterval < 3) {
                            paramHybridInfoRule.put("ruleName", "起飞前1-3小时+安检后");
                        } else {
                            paramHybridInfoRule.put("ruleName", "起飞前3小时以上+安检后");
                        }
                    }
                } else if (flightStatus.equals("延误")) {
                    if (position.equals("机场外")) {
                        paramHybridInfoRule.put("ruleName", "延误+安检前/未知定位");
                    } else {
                        paramHybridInfoRule.put("ruleName", "延误+安检后");
                    }
                }
                hybridInfoRule = recommendationRuleMapper.getRecommendationRule(paramHybridInfoRule);
                if (hybridInfoRule == null) {
                    hybridInfoFlag = 0;
                }
            } else {
                hybridInfoFlag = 0;
            }
        }
        result.put("hybridInfoRule", hybridInfoRule);
        result.put("hybridInfoFlag", hybridInfoFlag);
        return result;

    }

    public Map<String, Object> getPrivilegeInfoRule(int privilegeInfoFlag) throws Exception {

        Map<String,Object> result = new HashMap<>();
        RecommendationRule privilegeInfoRule = new RecommendationRule();
        if (privilegeInfoFlag == 1) {
            Map<String,Object> paramPrivilegeInfoRule = new HashMap<>();
            paramPrivilegeInfoRule.put("ruleName", "优惠行为");
            privilegeInfoRule = recommendationRuleMapper.getRecommendationRule(paramPrivilegeInfoRule);
            if (privilegeInfoRule == null) {
                privilegeInfoFlag = 0;
            }
        }

        result.put("privilegeInfoRule", privilegeInfoRule);
        result.put("privilegeInfoFlag", privilegeInfoFlag);
        return result;

    }

    public Map<String, Object> getUserBehaviorRule(int userBehaviorFlag, String ruleName) throws Exception {

        Map<String,Object> result = new HashMap<>();
        Map<String,Object> paramUserBehaviorRule = new HashMap<>();
        paramUserBehaviorRule.put("ruleName", ruleName);
        RecommendationRule userBehaviorRule = recommendationRuleMapper.getRecommendationRule(paramUserBehaviorRule);
        if (userBehaviorRule == null) {
            userBehaviorFlag = 0;
        }

        result.put("userBehaviorRule", userBehaviorRule);
        result.put("userBehaviorFlag", userBehaviorFlag);
        return result;

    }

    public Map<String, Integer> identifyAvailableProduct(List<Map> list) throws Exception {

        int restaurantFlag = 0, loungeFlag = 0, shopFlag = 0, carFlag = 0, cipFlag = 0, parkingFlag = 0, vvipFlag = 0;
        Map<String, Integer> result = new HashMap<>();
        Integer typeWeight;

        //龙腾提供的可用产品
        for (Map map : list) {
            switch (map.get("typeId").toString()) {
                case "2" :
                    restaurantFlag = 1;
                    typeWeight = Integer.parseInt(map.get("typeWeight").toString());
                    result.put("restaurantWeight", typeWeight);
                    break;
                case "1" :
                    loungeFlag = 1;
                    typeWeight = Integer.parseInt(map.get("typeWeight").toString());
                    result.put("loungeWeight", typeWeight);
                    break;
                case "10" :
                    shopFlag = 1;
                    typeWeight = Integer.parseInt(map.get("typeWeight").toString());
                    result.put("shopWeight", typeWeight);
                    break;
                case "9" :
                    carFlag = 1;
                    typeWeight = Integer.parseInt(map.get("typeWeight").toString());
                    result.put("carWeight", typeWeight);
                    break;
                case "5" :
                    cipFlag =1;
                    typeWeight = Integer.parseInt(map.get("typeWeight").toString());
                    result.put("cipWeight", typeWeight);
                    break;
                case "7" :
                    parkingFlag = 1;
                    typeWeight = Integer.parseInt(map.get("typeWeight").toString());
                    result.put("parkingWeight", typeWeight);
                    break;
                case "8" :
                    vvipFlag = 1;
                    typeWeight = Integer.parseInt(map.get("typeWeight").toString());
                    result.put("vvipWeight", typeWeight);
                    break;
            }
        }
        result.put("restaurantFlag", restaurantFlag);
        result.put("loungeFlag", loungeFlag);
        result.put("shopFlag", shopFlag);
        result.put("carFlag", carFlag);
        result.put("cipFlag", cipFlag);
        result.put("parkingFlag", parkingFlag);
        result.put("vvipFlag", vvipFlag);
        return result;

    }

    public Map<String, Integer> verifyProductAvailability(Map<String, Object> param, Map<String, Integer> resultProductIdentify) throws Exception {

        int restaurantFlag = resultProductIdentify.get("restaurantFlag"), loungeFlag = resultProductIdentify.get("loungeFlag"),
                shopFlag = resultProductIdentify.get("shopFlag"), carFlag = resultProductIdentify.get("carFlag"),
                cipFlag = resultProductIdentify.get("cipFlag"), parkingFlag = resultProductIdentify.get("parkingFlag"),
                vvipFlag = resultProductIdentify.get("vvipFlag");
        int flightInfoFlag = Integer.parseInt(param.get("flightInfoFlag").toString());

        //检验产品可用性
        if (flightInfoFlag == 1) {

            //获取航班信息
            Map<String,Object> paramFlightInfo = new HashMap<>();
            paramFlightInfo.put("flightNo", param.get("flightNo"));
            paramFlightInfo.put("flightDate", param.get("flightDate"));
            TbdFlightInfo tbdFlightInfo = tbdFlightInfoMapper.getFlightInfoByFlightNo(paramFlightInfo);

            //检验产品可用性
            if (tbdFlightInfo != null) {
                String flightStatus = tbdFlightInfo.getFlightStatus();
                String flightCategory = tbdFlightInfo.getFlightCategory();
                Date currentDate = new Date();
                long currentTime = currentDate.getTime();
                long flightDepartTime = tbdFlightInfo.getDepartTimePlan().getTime();
                if (tbdFlightInfo.getDepartTimePredict() != null) {
                    flightDepartTime = tbdFlightInfo.getDepartTimePredict().getTime();
                }

                //礼宾车和要客通
                if (flightStatus.equals("起飞")) {
                    long flightArriveTimePlan = tbdFlightInfo.getArriveTimePlan().getTime();
                    int timeInterval = (int) (flightArriveTimePlan - currentTime)/(1000 * 60 * 60);
                    if (flightCategory.matches("\"0\"|\"1\"|\"2\"")) { //境内起飞
                        if (timeInterval < 6) {
                            carFlag = 0;
                            vvipFlag = 0;
                        }
                    } else {
                        if (timeInterval < 24) {
                            carFlag = 0;
                            vvipFlag = 0;
                        }
                    }
                }
                if (flightStatus.equals("计划") || flightStatus.equals("延误")) {
                    int timeInterval = (int) (flightDepartTime - currentTime)/(1000 * 60 * 60);
                    if (flightCategory.matches("0")) { //境内降落
                        if (timeInterval < 6) {
                            carFlag = 0;
                            vvipFlag = 0;
                        }
                    } else {
                        if (timeInterval < 24) {
                            carFlag = 0;
                            vvipFlag = 0;
                        }
                    }
                }

                //快速安检通道
                if (flightStatus.equals("计划") || flightStatus.equals("延误")) {
                    int timeInterval = (int) (flightDepartTime - currentTime)/(1000 * 60 * 60);
                    if (timeInterval < 1) {
                        cipFlag = 0;
                    } else if (timeInterval < 2) {
                        cipFlag = 2; //航班起飞前60-120分钟,快速安检通道权重最高
                    } else {
                        cipFlag = 1;
                    }
                }

                //代客泊车
                if (flightStatus.equals("计划") || flightStatus.equals("延误")) {
                    int timeInterval = (int) (flightDepartTime - currentTime)/(1000 * 60 * 60);
                    if (timeInterval < 4) {
                        parkingFlag = 0;
                    }
                }
            } else {
                flightInfoFlag = 0;
            }
        }
        resultProductIdentify.put("flightInfoFlag", flightInfoFlag);
        resultProductIdentify.put("restaurantFlag", restaurantFlag);
        resultProductIdentify.put("loungeFlag", loungeFlag);
        resultProductIdentify.put("shopFlag", shopFlag);
        resultProductIdentify.put("carFlag", carFlag);
        resultProductIdentify.put("cipFlag", cipFlag);
        resultProductIdentify.put("parkingFlag", parkingFlag);
        resultProductIdentify.put("vvipFlag", vvipFlag);
        return resultProductIdentify;

    }

}
