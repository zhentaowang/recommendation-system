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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HybridRecommendationController.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
@Component
class HybridRecommendationController {

    private final ItdRestaurantService itdRestaurantService;
    private final ItdLoungeService itdLoungeService;
    private final ItdShopService itdShopService;
    private final TbdBannerService tbdBannerService;
    private final RecommendationBaseRuleService recommendationBaseRuleService;
    private final RecommendationBaseContentService recommendationBaseContentService;

    @Autowired
    public HybridRecommendationController(ItdRestaurantService itdRestaurantService, ItdLoungeService itdLoungeService,
                                          ItdShopService itdShopService, TbdBannerService tbdBannerService,
                                          RecommendationBaseRuleService recommendationBaseRuleService,
                                          RecommendationBaseContentService recommendationBaseContentService) {
        this.itdRestaurantService = itdRestaurantService;
        this.itdLoungeService = itdLoungeService;
        this.itdShopService = itdShopService;
        this.tbdBannerService = tbdBannerService;
        this.recommendationBaseRuleService = recommendationBaseRuleService;
        this.recommendationBaseContentService = recommendationBaseContentService;
    }

    /**
     * 推荐引擎 - 推荐餐厅
     * @param queryRestaurantJson
     * userId 用户id
     * flightNo 航班号
     * position 用户位置信息
     * restaurantInfo 排序前餐厅列表
     */
    String getRestaurant(final JSONObject queryRestaurantJson){

        try {
            if (queryRestaurantJson.containsKey("userId")
                    && queryRestaurantJson.containsKey("restaurantInfo")) {

                int positionFlag = 1, flightInfoFlag = 1, userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(positionFlag, flightInfoFlag, queryRestaurantJson, detectionResult);

                //验证用户位置和航班信息的有效性
                detectionResult = parameterDetection.verifyPositionValidity();
                positionFlag = Integer.parseInt(detectionResult.get("positionFlag").toString());
                detectionResult = parameterDetection.verifyFlightValidity();
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("restaurantInfo", "restaurantWeight", "restaurantCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的优惠餐厅
                List<Map<String, Object>> itdRestaurantList = itdRestaurantService.getRestaurantListByCode(list);
                if (itdRestaurantList.size() == 0) {
                    return JSON.toJSONString(new LZResult<>(list));
                }

                //根据地理位置确定推荐规则
                Map<String,Object> paramPosition = new HashMap<>();
                paramPosition.put("positionFlag", positionFlag);
                paramPosition.put("position", queryRestaurantJson.getString("position"));
                Map resultPosition = recommendationBaseRuleService.getPositionRule(paramPosition);

                //根据航班信息确定推荐规则
                Map<String,Object> paramFlightInfo = new HashMap<>();
                paramFlightInfo.put("flightInfoFlag", flightInfoFlag);
                paramFlightInfo.put("flightNo", queryRestaurantJson.getString("flightNo"));
                paramFlightInfo.put("flightDate", queryRestaurantJson.getString("flightDate"));
                Map resultFlightInfo = recommendationBaseRuleService.getFlightInfoRule(paramFlightInfo);

                //获取用户行为和偏好规则
                Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "餐厅行为");

                //获取用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
                paramUserBehavior.put("userId", queryRestaurantJson.getString("userId"));
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userRest");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //获取用户偏好标签
                paramUserBehavior.put("typeName", "userTags");
                Map<String, Object> resultUserPreference = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userTagsList", resultUserPreference.get("userList"));

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给餐厅打分
                List<Map<String, Object>> productListBasedPosition = dataProcessing.positionWeightCalculation(resultPosition, itdRestaurantList);
                List<Map<String, Object>> productListBasedByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, productListBasedPosition);
                List<Map<String,Object>> productListBasedBehavior = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByFlight);
                List<Map<String,Object>> productList = dataProcessing.preferenceWeightCalculation(userBehaviorAfterNorm, productListBasedBehavior);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("orderByKey", "restaurantWeight");
                paramProductSort.put("productCode", "restaurantCode");
                list = dataProcessing.productSort(paramProductSort, list, productList);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    String getLounge(final JSONObject queryLoungeJson){
        try {
            if (queryLoungeJson.containsKey("userId")
                    && queryLoungeJson.containsKey("loungeInfo")) {

                int positionFlag = 1, flightInfoFlag = 1, userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(positionFlag, flightInfoFlag, queryLoungeJson, detectionResult);

                //验证用户位置的有效性
                detectionResult = parameterDetection.verifyPositionValidity();
                positionFlag = Integer.parseInt(detectionResult.get("positionFlag").toString());

                //验证航班信息的有效性
                detectionResult = parameterDetection.verifyFlightValidity();
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("loungeInfo", "loungeWeight", "loungeCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的休息室
                List<Map<String, Object>> itdLoungeList = itdLoungeService.getLoungeListByCode(list);
                if (itdLoungeList.size() == 0) {
                    return JSON.toJSONString(new LZResult<>(list));
                }

                //根据地理位置确定推荐规则
                Map<String,Object> paramPosition = new HashMap<>();
                paramPosition.put("positionFlag", positionFlag);
                paramPosition.put("position", queryLoungeJson.getString("position"));
                Map resultPosition = recommendationBaseRuleService.getPositionRule(paramPosition);

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
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    String getShop(final JSONObject queryShopJson){
        try {
            if (queryShopJson.containsKey("userId")
                    && queryShopJson.containsKey("shopInfo")) {

                int positionFlag = 1, flightInfoFlag = 1, userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(positionFlag, flightInfoFlag, queryShopJson, detectionResult);

                //验证用户位置和航班信息的有效性
                detectionResult = parameterDetection.verifyPositionValidity();
                positionFlag = Integer.parseInt(detectionResult.get("positionFlag").toString());
                detectionResult = parameterDetection.verifyFlightValidity();
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("shopInfo", "shopWeight", "shopCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的商铺
                List<Map<String, Object>> itdShopList = itdShopService.getShopListByCode(list);
                if (itdShopList.size() == 0) {
                    return JSON.toJSONString(new LZResult<>(list));
                }

                //根据地理位置确定推荐规则
                Map<String,Object> paramPosition = new HashMap<>();
                paramPosition.put("positionFlag", positionFlag);
                paramPosition.put("position", queryShopJson.getString("position"));
                Map resultPosition = recommendationBaseRuleService.getPositionRule(paramPosition);

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

                //给餐厅打分
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
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    String getCustomizationRestaurant(final JSONObject queryRestaurantJson){

        try {
            if (queryRestaurantJson.containsKey("userId")
                    && queryRestaurantJson.containsKey("restaurantInfo")) {

                int flightInfoFlag = 1, userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(flightInfoFlag, queryRestaurantJson, detectionResult);

                //验证航班信息的有效性
                detectionResult = parameterDetection.verifyFlightValidity();
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("restaurantInfo", "restaurantWeight", "restaurantCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的优惠餐厅
                List<Map<String, Object>> itdRestaurantList = itdRestaurantService.getRestaurantListByCode(list);
                if (itdRestaurantList.size() == 0) {
                    return JSON.toJSONString(new LZResult<>(list));
                }

                //根据航班信息确定推荐规则
                Map<String,Object> paramFlightInfo = new HashMap<>();
                paramFlightInfo.put("flightInfoFlag", flightInfoFlag);
                paramFlightInfo.put("flightNo", queryRestaurantJson.getString("flightNo"));
                paramFlightInfo.put("flightDate", queryRestaurantJson.getString("flightDate"));
                Map resultFlightInfo = recommendationBaseRuleService.getFlightInfoRule(paramFlightInfo);

                //获取用户行为和偏好规则
                Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "餐厅行为");

                //获取用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
                paramUserBehavior.put("userId", queryRestaurantJson.getString("userId"));
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userRest");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //获取用户偏好标签
                paramUserBehavior.put("typeName", "userTags");
                Map<String, Object> resultUserPreference = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userTagsList", resultUserPreference.get("userList"));

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给餐厅打分
                List<Map<String, Object>> productListBasedByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, itdRestaurantList);
                List<Map<String,Object>> productListBasedBehavior = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByFlight);
                List<Map<String,Object>> productList = dataProcessing.preferenceWeightCalculation(userBehaviorAfterNorm, productListBasedBehavior);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("orderByKey", "restaurantWeight");
                paramProductSort.put("productCode", "restaurantCode");
                list = dataProcessing.productSort(paramProductSort, list, productList);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    String getCustomizationLounge(final JSONObject queryLoungeJson){
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
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    String getCustomizationShop(final JSONObject queryShopJson){
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
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    String getSetMeal(final JSONObject querySetMealJson){
        try {
            JSONArray setMealInfo = querySetMealJson.getJSONArray("setMealInfo");
            LZResult<JSONArray> result = new LZResult<>(setMealInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    String getBrandRestaurant(final JSONObject queryBrandRestaurantJson){
        try {
            if (queryBrandRestaurantJson.containsKey("userId")
                    && queryBrandRestaurantJson.containsKey("brandRestaurantInfo")) {

                int positionFlag = 1, flightInfoFlag = 1, userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(positionFlag, flightInfoFlag, queryBrandRestaurantJson, detectionResult);

                //验证用户位置和航班信息的有效性
                detectionResult = parameterDetection.verifyPositionValidity();
                positionFlag = Integer.parseInt(detectionResult.get("positionFlag").toString());
                detectionResult = parameterDetection.verifyFlightValidity();
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("brandRestaurantInfo", "brandRestaurantWeight", "brandRestaurantCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的品牌餐厅
                List<Map<String,Object>> itdRestaurantList = itdRestaurantService.getBrandRestaurantListByCode(list);
                if (itdRestaurantList.size() == 0) {
                    return JSON.toJSONString(new LZResult<>(list));
                }

                //根据地理位置确定推荐规则
                Map<String,Object> paramPosition = new HashMap<>();
                paramPosition.put("positionFlag", positionFlag);
                paramPosition.put("position", queryBrandRestaurantJson.getString("position"));
                Map resultPosition = recommendationBaseRuleService.getPositionRule(paramPosition);

                //根据航班信息确定推荐规则
                Map<String,Object> paramFlightInfo = new HashMap<>();
                paramFlightInfo.put("flightInfoFlag", flightInfoFlag);
                paramFlightInfo.put("flightNo", queryBrandRestaurantJson.getString("flightNo"));
                paramFlightInfo.put("flightDate", queryBrandRestaurantJson.getString("flightDate"));
                Map resultFlightInfo = recommendationBaseRuleService.getFlightInfoRule(paramFlightInfo);

                //获取用户行为和偏好规则
                Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "餐厅行为");

                //获取用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
                paramUserBehavior.put("userId", queryBrandRestaurantJson.getString("userId"));
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userRest");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //获取用户偏好标签
                paramUserBehavior.put("typeName", "userTags");
                Map<String, Object> resultUserPreference = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userTagsList", resultUserPreference.get("userList"));


                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给餐厅打分
                List<Map<String, Object>> productListBasedPosition = dataProcessing.positionWeightCalculation(resultPosition, itdRestaurantList);
                List<Map<String, Object>> productListBasedByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, productListBasedPosition);
                List<Map<String,Object>> productListBasedBehavior = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByFlight);
                List<Map<String,Object>> productList = dataProcessing.preferenceWeightCalculation(userBehaviorAfterNorm, productListBasedBehavior);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("orderByKey", "brandRestaurantWeight");
                paramProductSort.put("productCode", "brandRestaurantCode");
                list = dataProcessing.productSort(paramProductSort, list, productList);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    String getCuisine(final JSONObject queryCuisineJson){
        try {
            JSONArray cuisineInfo = queryCuisineJson.getJSONArray("cuisineInfo");
            LZResult<JSONArray> result = new LZResult<>(cuisineInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    String getBannerArticle(final JSONObject queryBannerArticleJson){
        try {
            if (queryBannerArticleJson.containsKey("userId")
                    && queryBannerArticleJson.containsKey("bannerArticleInfo")) {

                int userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(queryBannerArticleJson, detectionResult);

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("bannerArticleInfo", "bannerArticleWeight", "bannerArticleId");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到banner文章
                List<Map<String,Object>> tbdBannerList = tbdBannerService.getBannerListById(list);
                if (tbdBannerList.size() == 0) {
                    return JSON.toJSONString(new LZResult<>(list));
                }

                //获取用户行为和偏好规则
                Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "banner行为");

                //获取用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
                paramUserBehavior.put("userId", queryBannerArticleJson.getString("userId"));
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userBanner");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给文章打分
                List<Map<String,Object>> productList = dataProcessing.bannerWeightCalculation(userBehaviorAfterNorm, tbdBannerList);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("orderByKey", "bannerArticleWeight");
                paramProductSort.put("productCode", "bannerArticleId");
                list = dataProcessing.productSort(paramProductSort, list, productList);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }


    String getHomepageArticle(final JSONObject queryHomepageArticleJson){
        try {
            JSONArray homepageArticleInfo = queryHomepageArticleJson.getJSONArray("homepageArticleInfo");
            LZResult<JSONArray> result = new LZResult<>(homepageArticleInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }


    String getPageArticle(final JSONObject queryPageArticleJson){
        try {
            JSONArray pageArticleInfo = queryPageArticleJson.getJSONArray("pageArticleInfo");
            LZResult<JSONArray> result = new LZResult<>(pageArticleInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }


    String getType(final JSONObject queryTypeJson){
        try {
            JSONArray typeInfo = queryTypeJson.getJSONArray("typeInfo");
            LZResult<JSONArray> result = new LZResult<>(typeInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    String getProduct(final JSONObject queryTypeProductJson){
        try {
            JSONArray typeProductInfo = queryTypeProductJson.getJSONArray("typeProductInfo");
            LZResult<JSONArray> result = new LZResult<>(typeProductInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

}
