package com.adatafun.recommendation.controller;

import com.adatafun.recommendation.service.*;
import com.adatafun.recommendation.utils.DataProcessing;
import com.adatafun.recommendation.utils.ParameterDetection;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LoungeRecommendationController.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/18.
 */
@Component
public class LoungeRecommendationController {

    private final ItdLoungeService itdLoungeService;
    private final TbdUserFlightService tbdUserFlightService;
    private final RecommendationBaseRuleService recommendationBaseRuleService;
    private final RecommendationBaseContentService recommendationBaseContentService;
    private final static Logger logger = LoggerFactory.getLogger(LoungeRecommendationController.class);

    @Autowired
    public LoungeRecommendationController(ItdLoungeService itdLoungeService,
                                          TbdUserFlightService tbdUserFlightService,
                                          RecommendationBaseRuleService recommendationBaseRuleService,
                                          RecommendationBaseContentService recommendationBaseContentService) {
        this.itdLoungeService = itdLoungeService;
        this.tbdUserFlightService = tbdUserFlightService;
        this.recommendationBaseRuleService = recommendationBaseRuleService;
        this.recommendationBaseContentService = recommendationBaseContentService;
    }

    public String getLounge(final JSONObject queryLoungeJson){
        try {
            if (queryLoungeJson.containsKey("userId")
                    && queryLoungeJson.containsKey("loungeInfo")
                    && queryLoungeJson.containsKey("position")
                    && queryLoungeJson.containsKey("flightNo")
                    && queryLoungeJson.containsKey("flightDate")) {

                Long userId = Long.parseLong(queryLoungeJson.getString("userId"));
                String position = queryLoungeJson.getString("position");
                String flightNo = queryLoungeJson.getString("flightNo");
                String flightDate = queryLoungeJson.getString("flightDate");
                int identityFlag = 0, positionFlag = 1, flightInfoFlag = 1, userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(positionFlag, flightInfoFlag, queryLoungeJson, detectionResult);

                //验证用户位置的有效性
                detectionResult = parameterDetection.verifyPositionValidity();
                positionFlag = Integer.parseInt(detectionResult.get("positionFlag").toString());

                //验证航班信息的有效性
                detectionResult = parameterDetection.verifyFlightValidity();
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //识别用户身份，无法确认身份时，默认为乘机人
                Map<String,Object> paramIdentity = new HashMap<>();
                paramIdentity.put("userId", userId);
                paramIdentity.put("flightNo", flightNo);
                paramIdentity.put("flightDate", flightDate);
                Map<String, Object> userIdentity = tbdUserFlightService.getUserIdentityById(paramIdentity);
                if (userIdentity != null) {//1：乘机人，2：送机人，3：接机人
                    positionFlag = 0;
                    identityFlag = 1; //识别用户身份为非乘机人
                    position = userIdentity.get("identity").toString();
                }

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("loungeInfo", "loungeWeight", "loungeCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    logger.error("休息室列表信息不全");
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的休息室
                List<Map<String, Object>> itdLoungeList = itdLoungeService.getLoungeListByCode(list);
                if (itdLoungeList.size() == 0) {
                    return JSON.toJSONString(new LZResult<>(list));
                }

                //根据用户身份或地理位置确定位置推荐规则
                Map<String,Object> paramPosition = new HashMap<>();
                positionFlag = (positionFlag == 0 ? identityFlag : positionFlag);
                paramPosition.put("positionFlag", positionFlag);
                paramPosition.put("position", position);
                Map<String,Object> resultPosition = recommendationBaseRuleService.getPositionRule(paramPosition);

                //根据航班信息确定推荐规则
                Map<String,Object> paramFlightInfo = new HashMap<>();
                paramFlightInfo.put("flightInfoFlag", flightInfoFlag);
                paramFlightInfo.put("flightNo", queryLoungeJson.getString("flightNo"));
                paramFlightInfo.put("flightDate", queryLoungeJson.getString("flightDate"));
                Map resultFlightInfo = recommendationBaseRuleService.getFlightInfoRule(paramFlightInfo);

                //获取用户行为和偏好规则
                Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "休息室行为");

                //获取用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
                paramUserBehavior.put("userId", userId);
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userLounge");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给休息室打分
                List<Map<String, Object>> productListBasedPosition = dataProcessing.positionWeightCalculation(resultPosition, itdLoungeList);
                List<Map<String, Object>> productListBasedByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, productListBasedPosition);
                List<Map<String,Object>> productList = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByFlight);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("orderByKey", "loungeWeight");
                paramProductSort.put("productCode", "loungeCode");
                list = dataProcessing.productSort(paramProductSort, list, productList);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                logger.error("缺失用户、休息室或航班机场信息");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getCustomizationLounge(final JSONObject queryLoungeJson){
        try {
            if (queryLoungeJson.containsKey("userId")
                    && queryLoungeJson.containsKey("loungeInfo")) {

                int flightInfoFlag = 1, userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(flightInfoFlag, queryLoungeJson, detectionResult);

                //验证航班信息的有效性
                detectionResult = parameterDetection.verifyFlightValidity();
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("loungeInfo", "loungeWeight", "loungeCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    logger.error("休息室列表信息不全");
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的休息室
                List<Map<String, Object>> itdLoungeList = itdLoungeService.getLoungeListByCode(list);
                if (itdLoungeList.size() == 0) {
                    return JSON.toJSONString(new LZResult<>(list));
                }

                //根据航班信息确定推荐规则
                Map<String,Object> paramFlightInfo = new HashMap<>();
                paramFlightInfo.put("flightInfoFlag", flightInfoFlag);
                paramFlightInfo.put("flightNo", queryLoungeJson.getString("flightNo"));
                paramFlightInfo.put("flightDate", queryLoungeJson.getString("flightDate"));
                Map resultFlightInfo = recommendationBaseRuleService.getFlightInfoRule(paramFlightInfo);

                //获取用户行为和偏好规则
                Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "休息室行为");

                //获取用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
                paramUserBehavior.put("userId", queryLoungeJson.getString("userId"));
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userLounge");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给休息室打分
                List<Map<String, Object>> productListBasedByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, itdLoungeList);
                List<Map<String,Object>> productList = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByFlight);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("orderByKey", "loungeWeight");
                paramProductSort.put("productCode", "loungeCode");
                list = dataProcessing.productSort(paramProductSort, list, productList);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                logger.error("缺失用户或休息室信息");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    List<Map<String, Object>> getLoungeList(String userId, String airportCode, String flightNo, String flightDate,
                                            Integer flightInfoFlag, Integer loungeWeight) throws Exception {

        int userBehaviorFlag = 1;

        //过滤得到有合作的休息室
        List<Map<String, Object>> itdLoungeList = itdLoungeService.getLoungeList(airportCode);

        //根据航班信息确定推荐规则
        Map<String,Object> paramFlightInfo = new HashMap<>();
        paramFlightInfo.put("flightInfoFlag", flightInfoFlag);
        paramFlightInfo.put("flightNo", flightNo);
        paramFlightInfo.put("flightDate", flightDate);
        Map resultFlightInfo = recommendationBaseRuleService.getFlightInfoRule(paramFlightInfo);

        //获取用户行为和偏好规则
        Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "休息室行为");

        //获取用户行为标签
        Map<String,Object> paramUserBehavior = new HashMap<>();
        paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
        paramUserBehavior.put("userId", userId);
        paramUserBehavior.put("indexName", "user");
        paramUserBehavior.put("typeName", "userLounge");
        Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
        resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

        //用户行为数据归一化处理
        DataProcessing dataProcessing = new DataProcessing();
        Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

        //给休息室打分
        List<Map<String, Object>> productListBasedByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, itdLoungeList);
        List<Map<String, Object>> productListBasedBehavior = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByFlight);
        List<Map<String, Object>> productList = dataProcessing.getFinalScore(loungeWeight, productListBasedBehavior);

        return dataProcessing.soleProductSort(productList);
    }

}
