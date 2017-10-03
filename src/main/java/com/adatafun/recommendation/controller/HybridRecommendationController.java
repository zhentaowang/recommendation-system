package com.adatafun.recommendation.controller;

import com.adatafun.recommendation.model.*;
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
    private final RecommendationBaseRuleService recommendationBaseRuleService;
    private final RecommendationBaseContentService recommendationBaseContentService;

    @Autowired
    public HybridRecommendationController(ItdRestaurantService itdRestaurantService, ItdLoungeService itdLoungeService,
                                          ItdShopService itdShopService, RecommendationBaseRuleService recommendationBaseRuleService,
                                          RecommendationBaseContentService recommendationBaseContentService) {
        this.itdRestaurantService = itdRestaurantService;
        this.itdLoungeService = itdLoungeService;
        this.itdShopService = itdShopService;
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
                detectionResult = parameterDetection.verifyValidity();
                positionFlag = Integer.parseInt(detectionResult.get("positionFlag").toString());
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("restaurantInfo", "restaurantWeight", "restaurantCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的优惠餐厅
                List<ItdRestaurant> itdRestaurantList = itdRestaurantService.getRestaurantListByCode(list);
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

                //获取用户行为规则和用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", userBehaviorFlag);
                paramUserBehavior.put("userId", queryRestaurantJson.getString("userId"));
                paramUserBehavior.put("ruleName", "餐厅行为");
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userRest");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给餐厅打分
                Map<String,Object> paramWeightCalculation = new HashMap<>();
                paramWeightCalculation.put("productList", itdRestaurantList);
                paramWeightCalculation.put("resultPosition", resultPosition);
                paramWeightCalculation.put("resultFlightInfo", resultFlightInfo);
                paramWeightCalculation.put("resultUserBehavior", userBehaviorAfterNorm);
                List<Map> productList = dataProcessing.weightCalculation(paramWeightCalculation);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("list", list);
                paramProductSort.put("productList", productList);
                paramProductSort.put("orderByKey", "restaurantWeight");
                paramProductSort.put("productCode", "restaurantCode");
                list = dataProcessing.productSort(paramProductSort);

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

                //验证用户位置和航班信息的有效性
                detectionResult = parameterDetection.verifyValidity();
                positionFlag = Integer.parseInt(detectionResult.get("positionFlag").toString());
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("loungeInfo", "loungeWeight", "loungeCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的休息室
                List<ItdLounge> itdLoungeList = itdLoungeService.getLoungeListByCode(list);
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

                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", userBehaviorFlag);
                paramUserBehavior.put("userId", queryLoungeJson.getString("userId"));
                paramUserBehavior.put("ruleName", "休息室行为");
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userLounge");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给餐厅打分
                Map<String,Object> paramWeightCalculation = new HashMap<>();
                paramWeightCalculation.put("productList", itdLoungeList);
                paramWeightCalculation.put("resultPosition", resultPosition);
                paramWeightCalculation.put("resultFlightInfo", resultFlightInfo);
                paramWeightCalculation.put("resultUserBehavior", userBehaviorAfterNorm);
                List<Map> productList = dataProcessing.weightCalculation(paramWeightCalculation);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("list", list);
                paramProductSort.put("productList", productList);
                paramProductSort.put("orderByKey", "loungeWeight");
                paramProductSort.put("productCode", "loungeCode");
                list = dataProcessing.productSort(paramProductSort);

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
                detectionResult = parameterDetection.verifyValidity();
                positionFlag = Integer.parseInt(detectionResult.get("positionFlag").toString());
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("shopInfo", "shopWeight", "shopCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的休息室
                List<ItdShop> itdShopList = itdShopService.getShopListByCode(list);
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

                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", userBehaviorFlag);
                paramUserBehavior.put("userId", queryShopJson.getString("userId"));
                paramUserBehavior.put("ruleName", "商铺行为");
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userLounge");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给餐厅打分
                Map<String,Object> paramWeightCalculation = new HashMap<>();
                paramWeightCalculation.put("productList", itdShopList);
                paramWeightCalculation.put("resultPosition", resultPosition);
                paramWeightCalculation.put("resultFlightInfo", resultFlightInfo);
                paramWeightCalculation.put("resultUserBehavior", userBehaviorAfterNorm);
                List<Map> productList = dataProcessing.weightCalculation(paramWeightCalculation);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("list", list);
                paramProductSort.put("productList", productList);
                paramProductSort.put("orderByKey", "shopWeight");
                paramProductSort.put("productCode", "shopCode");
                list = dataProcessing.productSort(paramProductSort);

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
            JSONArray brandRestaurantInfo = queryBrandRestaurantJson.getJSONArray("brandRestaurantInfo");
            LZResult<JSONArray> result = new LZResult<>(brandRestaurantInfo);
            return JSON.toJSONString(result);
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
            JSONArray bannerArticleInfo = queryBannerArticleJson.getJSONArray("bannerArticleInfo");
            LZResult<JSONArray> result = new LZResult<>(bannerArticleInfo);
            return JSON.toJSONString(result);
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
