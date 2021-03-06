package com.adatafun.recommendation.controller;

import com.adatafun.recommendation.model.User;
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

import java.util.*;

/**
 * HybridRecommendationController.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
@Component
public class HybridRecommendationController {

    private final RecommendationBaseRuleService recommendationBaseRuleService;
    private final RecommendationBaseContentService recommendationBaseContentService;
    private final RestaurantRecommendationController restaurantRecommendationController;
    private final LoungeRecommendationController loungeRecommendationController;
    private final ShopRecommendationController shopRecommendationController;
    private final CarRecommendationController carRecommendationController;
    private final CIPRecommendationController cipRecommendationController;
    private final VVIPRecommendationController vvipRecommendationController;
    private final ParkingRecommendationController parkingRecommendationController;
    private final static Logger logger = LoggerFactory.getLogger(HybridRecommendationController.class);

    @Autowired
    public HybridRecommendationController(RecommendationBaseRuleService recommendationBaseRuleService,
                                          RecommendationBaseContentService recommendationBaseContentService,
                                          RestaurantRecommendationController restaurantRecommendationController,
                                          LoungeRecommendationController loungeRecommendationController,
                                          ShopRecommendationController shopRecommendationController,
                                          CarRecommendationController carRecommendationController,
                                          CIPRecommendationController cipRecommendationController,
                                          VVIPRecommendationController vvipRecommendationController,
                                          ParkingRecommendationController parkingRecommendationController) {
        this.recommendationBaseRuleService = recommendationBaseRuleService;
        this.recommendationBaseContentService = recommendationBaseContentService;
        this.restaurantRecommendationController = restaurantRecommendationController;
        this.loungeRecommendationController = loungeRecommendationController;
        this.shopRecommendationController = shopRecommendationController;
        this.carRecommendationController = carRecommendationController;
        this.cipRecommendationController = cipRecommendationController;
        this.vvipRecommendationController = vvipRecommendationController;
        this.parkingRecommendationController = parkingRecommendationController;
    }

    public String getProduct(final JSONObject queryTypeProductJson){
        try {
            if (queryTypeProductJson.containsKey("userId")
                    && queryTypeProductJson.containsKey("productTypeInfo")
                    && queryTypeProductJson.containsKey("flightNo")
                    && queryTypeProductJson.containsKey("flightDate")
                    && queryTypeProductJson.containsKey("airportCode")) {

                String userId = queryTypeProductJson.getString("userId");
                String flightNo = queryTypeProductJson.getString("flightNo");
                String flightDate = queryTypeProductJson.getString("flightDate");
                String airportCode = queryTypeProductJson.getString("airportCode");
                List<Map> list;
                int flightInfoFlag = 1;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(flightInfoFlag, queryTypeProductJson, detectionResult);

                //验证航班信息的有效性
                detectionResult = parameterDetection.verifyFlightValidity();
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("productTypeInfo", "typeWeight", "typeId");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    logger.error("产品类型列表信息不全");
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //根据运营策略确定可用产品及其权重
                Map<String, Integer> resultProductIdentify = recommendationBaseRuleService.identifyAvailableProduct(list);

                //根据航班信息检验产品可用性
                Map<String,Object> paramFlightInfo = new HashMap<>();
                paramFlightInfo.put("flightInfoFlag", flightInfoFlag);
                paramFlightInfo.put("flightNo", flightNo);
                paramFlightInfo.put("flightDate", flightDate);
                Map<String, Integer> resultProductVerify = recommendationBaseRuleService.verifyProductAvailability(paramFlightInfo, resultProductIdentify);

                //获取餐厅、休息室、商铺、礼宾车、安检通道、代客泊车和要客通的打分列表
                List<Map<String, Object>> hybridProductList = calculateAvailableProductWeight(resultProductVerify,
                        userId, airportCode, flightNo, flightDate);

                //去除无用信息并排序产品
                DataProcessing dataProcessing = new DataProcessing();
                List<Map<String, Object>> productList = dataProcessing.eraseUselessInfo(hybridProductList);
                //List<Map<String, Object>> result = dataProcessing.hybridProductSort(productList);

                return JSON.toJSONString(new LZResult<>(productList));
            } else {
                logger.error("缺失用户或航班机场信息");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getType(final JSONObject queryTypeJson){
        try {
            if (queryTypeJson.containsKey("userId")
                    && queryTypeJson.containsKey("productTypeInfo")
                    && queryTypeJson.containsKey("position")
                    && queryTypeJson.containsKey("flightNo")
                    && queryTypeJson.containsKey("flightDate")) {

                Long userId = Long.parseLong(queryTypeJson.getString("userId"));
                String position = queryTypeJson.getString("position");
                String flightNo = queryTypeJson.getString("flightNo");
                String flightDate = queryTypeJson.getString("flightDate");
                int positionFlag = 1, flightInfoFlag = 1, userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(positionFlag, flightInfoFlag, queryTypeJson, detectionResult);

                //验证用户位置的有效性
                detectionResult = parameterDetection.verifyPositionValidity();
                positionFlag = Integer.parseInt(detectionResult.get("positionFlag").toString());

                //验证航班信息的有效性
                detectionResult = parameterDetection.verifyFlightValidity();
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("productTypeInfo", "typeWeight", "typeId");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    logger.error("产品类型列表信息不全");
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //根据地理位置和航班信息确定推荐规则
                Map<String,Object> paramHybridInfo = new HashMap<>();
                paramHybridInfo.put("positionFlag", positionFlag);
                paramHybridInfo.put("flightInfoFlag", flightInfoFlag);
                paramHybridInfo.put("position", position);
                paramHybridInfo.put("flightNo", flightNo);
                paramHybridInfo.put("flightDate", flightDate);
                Map<String,Object> resultHybridInfo = recommendationBaseRuleService.getHybridInfoRule(paramHybridInfo);

                //获取用户行为
                Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "模块行为");

                //获取用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
                paramUserBehavior.put("userId", userId);
                List<User> resultUserBehavior = recommendationBaseContentService.getUserModuleBehavior(paramUserBehavior);
                resultUserBehaviorRule.put("userList", resultUserBehavior);

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehaviorRule);

                //给模块打分
                List<Map> productListBasedByHybrid = dataProcessing.typeWeightCalculation(resultHybridInfo, list);
                List<Map> productList = dataProcessing.typeBehaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByHybrid);

                //排序
                productList = dataProcessing.productTypeSort(productList);

                return JSON.toJSONString(new LZResult<>(productList));
            } else {
                logger.error("缺失用户或航班信息");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getSetMeal(final JSONObject querySetMealJson){
        try {
            JSONArray setMealInfo = querySetMealJson.getJSONArray("setMealInfo");
            LZResult<JSONArray> result = new LZResult<>(setMealInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getCuisine(final JSONObject queryCuisineJson){
        try {
            JSONArray cuisineInfo = queryCuisineJson.getJSONArray("cuisineInfo");
            LZResult<JSONArray> result = new LZResult<>(cuisineInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    private List<Map<String, Object>> calculateAvailableProductWeight(Map<String, Integer> resultProductVerify
            , String userId, String airportCode, String flightNo, String flightDate) throws Exception {

        Integer flightInfoFlag = resultProductVerify.get("flightInfoFlag");
        Integer cipFlag = resultProductVerify.get("cipFlag");
        List<Map<String, Object>> restaurantList = new ArrayList<>();
        List<Map<String, Object>> loungeList = new ArrayList<>();
        List<Map<String, Object>> shopList = new ArrayList<>();
        List<Map<String, Object>> carList = new ArrayList<>();
        List<Map<String, Object>> cipList = new ArrayList<>();
        List<Map<String, Object>> parkingList = new ArrayList<>();
        List<Map<String, Object>> vvipList = new ArrayList<>();
        if (resultProductVerify.get("restaurantFlag") == 1) {
            restaurantList = restaurantRecommendationController.getRestaurantList(userId,
                    airportCode, flightNo, flightDate, flightInfoFlag, resultProductVerify.get("restaurantWeight"));
        }
        if (resultProductVerify.get("loungeFlag") == 1) {
            loungeList = loungeRecommendationController.getLoungeList(userId,
                    airportCode, flightNo, flightDate, flightInfoFlag, resultProductVerify.get("loungeWeight"));
        }
        if (resultProductVerify.get("shopFlag") == 1) {
            shopList = shopRecommendationController.getShopList(userId,
                    airportCode, flightNo, flightDate, flightInfoFlag, resultProductVerify.get("shopWeight"));
        }
        if (resultProductVerify.get("carFlag") == 1) {
            carList = carRecommendationController.getCarList(userId, airportCode,
                    resultProductVerify.get("carWeight"));
        }
        if (cipFlag == 1 || cipFlag == 2) {
            cipList = cipRecommendationController.getCIPList(userId, airportCode, cipFlag,
                    resultProductVerify.get("cipWeight"));
        }
        if (resultProductVerify.get("parkingFlag") == 1) {
            parkingList = parkingRecommendationController.getParkingList(userId, airportCode,
                    resultProductVerify.get("parkingWeight"));
        }
        if (resultProductVerify.get("vvipFlag") == 1) {
            vvipList = vvipRecommendationController.getVVIPList(userId, airportCode,
                    resultProductVerify.get("vvipWeight"));
        }

        //按类计算产品数量，并取最大值
        DataProcessing dataProcessing = new DataProcessing();
        Map<String, Integer> productNum = dataProcessing.getMaxProductNum(restaurantList, loungeList, shopList, carList,
                cipList, parkingList, vvipList);
        return dataProcessing.productSortBasedOperationStrategy(restaurantList, loungeList, shopList, carList, cipList,
                parkingList, vvipList, productNum);

    }

}
