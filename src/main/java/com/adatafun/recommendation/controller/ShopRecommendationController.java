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
 * ShopRecommendationController.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/18.
 */
@Component
public class ShopRecommendationController {

    private final ItdShopService itdShopService;
    private final TbdUserFlightService tbdUserFlightService;
    private final RecommendationBaseRuleService recommendationBaseRuleService;
    private final RecommendationBaseContentService recommendationBaseContentService;
    private final static Logger logger = LoggerFactory.getLogger(ShopRecommendationController.class);

    @Autowired
    public ShopRecommendationController(ItdShopService itdShopService,
                                        TbdUserFlightService tbdUserFlightService,
                                        RecommendationBaseRuleService recommendationBaseRuleService,
                                        RecommendationBaseContentService recommendationBaseContentService) {
        this.itdShopService = itdShopService;
        this.tbdUserFlightService = tbdUserFlightService;
        this.recommendationBaseRuleService = recommendationBaseRuleService;
        this.recommendationBaseContentService = recommendationBaseContentService;
    }

    public String getShop(final JSONObject queryShopJson){
        try {
            if (queryShopJson.containsKey("userId")
                    && queryShopJson.containsKey("shopInfo")
                    && queryShopJson.containsKey("position")
                    && queryShopJson.containsKey("flightNo")
                    && queryShopJson.containsKey("flightDate")) {

                Long userId = Long.parseLong(queryShopJson.getString("userId"));
                String position = queryShopJson.getString("position");
                String flightNo = queryShopJson.getString("flightNo");
                String flightDate = queryShopJson.getString("flightDate");
                int identityFlag = 0, positionFlag = 1, flightInfoFlag = 1, userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(positionFlag, flightInfoFlag, queryShopJson, detectionResult);

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
                detectionResult = parameterDetection.verifyIntegrity("shopInfo", "shopWeight", "shopCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    logger.error("商铺列表信息不全");
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的商铺
                List<Map<String, Object>> itdShopList = itdShopService.getShopListByCode(list);
                if (itdShopList.size() == 0) {
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
                paramFlightInfo.put("flightNo", flightNo);
                paramFlightInfo.put("flightDate", flightDate);
                Map resultFlightInfo = recommendationBaseRuleService.getFlightInfoRule(paramFlightInfo);

                //获取用户行为和偏好规则
                Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "商铺行为");

                //获取用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
                paramUserBehavior.put("userId", userId);
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userShop");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给商铺打分
                List<Map<String, Object>> productListBasedPosition = dataProcessing.positionWeightCalculation(resultPosition, itdShopList);
                List<Map<String, Object>> productListBasedByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, productListBasedPosition);
                List<Map<String,Object>> productList = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByFlight);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("orderByKey", "shopWeight");
                paramProductSort.put("productCode", "shopCode");
                list = dataProcessing.productSort(paramProductSort, list, productList);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                logger.error("缺失用户、商铺或航班机场信息");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getCustomizationShop(final JSONObject queryShopJson){
        try {
            if (queryShopJson.containsKey("userId")
                    && queryShopJson.containsKey("shopInfo")) {

                int flightInfoFlag = 1, userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(flightInfoFlag, queryShopJson, detectionResult);

                //验证航班信息的有效性
                detectionResult = parameterDetection.verifyFlightValidity();
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("shopInfo", "shopWeight", "shopCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    logger.error("商铺列表信息不全");
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的休息室
                List<Map<String,Object>> itdShopList = itdShopService.getShopListByCode(list);
                if (itdShopList.size() == 0) {
                    return JSON.toJSONString(new LZResult<>(list));
                }

                //根据航班信息确定推荐规则
                Map<String,Object> paramFlightInfo = new HashMap<>();
                paramFlightInfo.put("flightInfoFlag", flightInfoFlag);
                paramFlightInfo.put("flightNo", queryShopJson.getString("flightNo"));
                paramFlightInfo.put("flightDate", queryShopJson.getString("flightDate"));
                Map resultFlightInfo = recommendationBaseRuleService.getFlightInfoRule(paramFlightInfo);

                //获取用户行为和偏好规则
                Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "商铺行为");

                //获取用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
                paramUserBehavior.put("userId", queryShopJson.getString("userId"));
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userShop");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给商铺打分
                List<Map<String, Object>> productListOrderByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, itdShopList);
                List<Map<String,Object>> productList = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListOrderByFlight);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("orderByKey", "shopWeight");
                paramProductSort.put("productCode", "shopCode");
                list = dataProcessing.productSort(paramProductSort, list, productList);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                logger.error("缺失用户或商铺信息");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    List<Map<String, Object>> getShopList(String userId, String airportCode, String flightNo, String flightDate,
                                          Integer flightInfoFlag, Integer shopWeight) throws Exception {

        int userBehaviorFlag = 1;

        //过滤得到有合作的商铺
        List<Map<String, Object>> itdShopList = itdShopService.getShopList(airportCode);

        //根据航班信息确定推荐规则
        Map<String,Object> paramFlightInfo = new HashMap<>();
        paramFlightInfo.put("flightInfoFlag", flightInfoFlag);
        paramFlightInfo.put("flightNo", flightNo);
        paramFlightInfo.put("flightDate", flightDate);
        Map resultFlightInfo = recommendationBaseRuleService.getFlightInfoRule(paramFlightInfo);

        //获取用户行为和偏好规则
        Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "商铺行为");

        //获取用户行为标签
        Map<String,Object> paramUserBehavior = new HashMap<>();
        paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
        paramUserBehavior.put("userId", userId);
        paramUserBehavior.put("indexName", "user");
        paramUserBehavior.put("typeName", "userShop");
        Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
        resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

        //用户行为数据归一化处理
        DataProcessing dataProcessing = new DataProcessing();
        Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

        //给商铺打分
        List<Map<String, Object>> productListBasedByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, itdShopList);
        List<Map<String, Object>> productList = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByFlight);

        //根据运营策略决定最终分数
        return dataProcessing.getFinalScore(shopWeight, productList);
    }

}
