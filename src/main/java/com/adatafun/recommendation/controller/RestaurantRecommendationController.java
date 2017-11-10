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
 * RestaurantRecommendationController.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/18.
 */
@Component
public class RestaurantRecommendationController {

    private final ItdRestaurantService itdRestaurantService;
    private final TbdUserFlightService tbdUserFlightService;
    private final RecommendationBaseRuleService recommendationBaseRuleService;
    private final RecommendationBaseContentService recommendationBaseContentService;
    private final static Logger logger = LoggerFactory.getLogger(RestaurantRecommendationController.class);

    @Autowired
    public RestaurantRecommendationController(ItdRestaurantService itdRestaurantService,
                                              TbdUserFlightService tbdUserFlightService,
                                          RecommendationBaseRuleService recommendationBaseRuleService,
                                          RecommendationBaseContentService recommendationBaseContentService) {
        this.itdRestaurantService = itdRestaurantService;
        this.tbdUserFlightService = tbdUserFlightService;
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
    public String getRestaurant(final JSONObject queryRestaurantJson){

        try {
            if (queryRestaurantJson.containsKey("userId")
                    && queryRestaurantJson.containsKey("restaurantInfo")
                    && queryRestaurantJson.containsKey("position")
                    && queryRestaurantJson.containsKey("flightNo")
                    && queryRestaurantJson.containsKey("flightDate")) {

                Long userId = Long.parseLong(queryRestaurantJson.getString("userId"));
                String position = queryRestaurantJson.getString("position");
                String flightNo = queryRestaurantJson.getString("flightNo");
                String flightDate = queryRestaurantJson.getString("flightDate");
                int identityFlag = 0, positionFlag = 1, flightInfoFlag = 1, userBehaviorFlag = 1, privilegeInfoFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(positionFlag, flightInfoFlag, queryRestaurantJson, detectionResult);

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
                detectionResult = parameterDetection.verifyIntegrity("restaurantInfo", "restaurantWeight", "restaurantCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    logger.error("餐厅列表信息不全");
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到有合作的餐厅
                List<Map<String, Object>> itdRestaurantList = itdRestaurantService.getRestaurantListByCode(list);
                if (itdRestaurantList == null) {
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
                Map<String,Object> resultFlightInfo = recommendationBaseRuleService.getFlightInfoRule(paramFlightInfo);

                //获取餐厅优惠规则
                Map<String,Object> resultPrivilegeInfoRule = recommendationBaseRuleService.getPrivilegeInfoRule(privilegeInfoFlag);

                //获取餐厅优惠标签
                Map<String,Object> paramPrivilegeInfo = new HashMap<>();
                paramPrivilegeInfo.put("privilegeInfoFlag", resultPrivilegeInfoRule.get("privilegeInfoFlag"));
                paramPrivilegeInfo.put("indexName", "user");
                paramPrivilegeInfo.put("typeName", "restaurant");
                Map<String, Object> resultPrivilegeInfo = recommendationBaseContentService.getPrivilegeInfo(paramPrivilegeInfo, itdRestaurantList);
                resultPrivilegeInfo.put("privilegeInfoRule", resultPrivilegeInfoRule.get("privilegeInfoRule"));

                //获取用户行为和偏好规则
                Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "餐厅行为");

                //获取用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
                paramUserBehavior.put("userId", userId);
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userRest");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //获取用户偏好标签
                paramUserBehavior.put("typeName", "userTags");
                Map<String, Object> resultUserPreference = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserPreference.put("userPreferenceRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给餐厅打分
                List<Map<String, Object>> productListBasedPosition = dataProcessing.positionWeightCalculation(resultPosition, itdRestaurantList);
                List<Map<String, Object>> productListBasedByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, productListBasedPosition);
                List<Map<String, Object>> productListBasedByPrivilege = dataProcessing.privilegeWeightCalculation(resultPrivilegeInfo, productListBasedByFlight);
                List<Map<String,Object>> productListBasedBehavior = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByPrivilege);
                List<Map<String,Object>> productList = dataProcessing.preferenceWeightCalculation(resultUserPreference, productListBasedBehavior);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("orderByKey", "restaurantWeight");
                paramProductSort.put("productCode", "restaurantCode");
                list = dataProcessing.productSort(paramProductSort, list, productList);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                logger.error("缺失用户、餐厅或航班机场信息");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getCustomizationRestaurant(final JSONObject queryRestaurantJson){

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
                    logger.error("餐厅列表信息不全");
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
                resultUserPreference.put("userPreferenceRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给餐厅打分
                List<Map<String, Object>> productListBasedByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, itdRestaurantList);
                List<Map<String,Object>> productListBasedBehavior = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByFlight);
                List<Map<String,Object>> productList = dataProcessing.preferenceWeightCalculation(resultUserPreference, productListBasedBehavior);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("orderByKey", "restaurantWeight");
                paramProductSort.put("productCode", "restaurantCode");
                list = dataProcessing.productSort(paramProductSort, list, productList);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                logger.error("缺失用户或餐厅信息");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getBrandRestaurant(final JSONObject queryBrandRestaurantJson){
        try {
            if (queryBrandRestaurantJson.containsKey("userId")
                    && queryBrandRestaurantJson.containsKey("brandRestaurantInfo")) {

                int positionFlag = 1, flightInfoFlag = 1, userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(positionFlag, flightInfoFlag, queryBrandRestaurantJson, detectionResult);

                //验证用户位置的有效性
                detectionResult = parameterDetection.verifyPositionValidity();
                positionFlag = Integer.parseInt(detectionResult.get("positionFlag").toString());

                //验证航班信息的有效性
                detectionResult = parameterDetection.verifyFlightValidity();
                flightInfoFlag = Integer.parseInt(detectionResult.get("flightInfoFlag").toString());

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("brandRestaurantInfo", "brandRestaurantWeight", "brandRestaurantCode");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    logger.error("品牌餐厅列表信息不全");
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
                resultUserPreference.put("userPreferenceRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给餐厅打分
                List<Map<String, Object>> productListBasedPosition = dataProcessing.positionWeightCalculation(resultPosition, itdRestaurantList);
                List<Map<String, Object>> productListBasedByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, productListBasedPosition);
                List<Map<String,Object>> productListBasedBehavior = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByFlight);
                List<Map<String,Object>> productList = dataProcessing.preferenceWeightCalculation(resultUserPreference, productListBasedBehavior);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("orderByKey", "brandRestaurantWeight");
                paramProductSort.put("productCode", "brandRestaurantCode");
                list = dataProcessing.productSort(paramProductSort, list, productList);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                logger.error("缺失用户或餐厅信息");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    List<Map<String, Object>> getRestaurantList(String userId, String airportCode, String flightNo, String flightDate,
                                                Integer flightInfoFlag, Integer restaurantWeight) throws Exception {

        int userBehaviorFlag = 1, privilegeInfoFlag = 1;

        //过滤得到有合作的餐厅
        List<Map<String, Object>> itdRestaurantList = itdRestaurantService.getRestaurantList(airportCode);

        //根据航班信息确定推荐规则
        Map<String,Object> paramFlightInfo = new HashMap<>();
        paramFlightInfo.put("flightInfoFlag", flightInfoFlag);
        paramFlightInfo.put("flightNo", flightNo);
        paramFlightInfo.put("flightDate", flightDate);
        Map<String,Object> resultFlightInfo = recommendationBaseRuleService.getFlightInfoRule(paramFlightInfo);

        //获取餐厅优惠规则
        Map<String,Object> resultPrivilegeInfoRule = recommendationBaseRuleService.getPrivilegeInfoRule(privilegeInfoFlag);

        //获取餐厅优惠标签
        Map<String,Object> paramPrivilegeInfo = new HashMap<>();
        paramPrivilegeInfo.put("privilegeInfoFlag", resultPrivilegeInfoRule.get("privilegeInfoFlag"));
        paramPrivilegeInfo.put("indexName", "user");
        paramPrivilegeInfo.put("typeName", "restaurant");
        Map<String, Object> resultPrivilegeInfo = recommendationBaseContentService.getPrivilegeInfo(paramPrivilegeInfo, itdRestaurantList);
        resultPrivilegeInfo.put("privilegeInfoRule", resultPrivilegeInfoRule.get("privilegeInfoRule"));

        //获取用户行为和偏好规则
        Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "餐厅行为");

        //获取用户行为标签
        Map<String,Object> paramUserBehavior = new HashMap<>();
        paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
        paramUserBehavior.put("userId", userId);
        paramUserBehavior.put("indexName", "user");
        paramUserBehavior.put("typeName", "userRest");
        Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
        resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

        //获取用户偏好标签
        paramUserBehavior.put("typeName", "userTags");
        Map<String, Object> resultUserPreference = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
        resultUserPreference.put("userPreferenceRule", resultUserBehaviorRule.get("userBehaviorRule"));

        //用户行为数据归一化处理
        DataProcessing dataProcessing = new DataProcessing();
        Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

        //给餐厅打分
        List<Map<String, Object>> productListBasedByFlight = dataProcessing.flightWeightCalculation(resultFlightInfo, itdRestaurantList);
        List<Map<String, Object>> productListBasedByPrivilege = dataProcessing.privilegeWeightCalculation(resultPrivilegeInfo, productListBasedByFlight);
        List<Map<String,Object>> productListBasedBehavior = dataProcessing.behaviorWeightCalculation(userBehaviorAfterNorm, productListBasedByPrivilege);
        List<Map<String, Object>> productListBasedPreference = dataProcessing.preferenceWeightCalculation(resultUserPreference, productListBasedBehavior);
        List<Map<String, Object>> productList = dataProcessing.getFinalScore(restaurantWeight, productListBasedPreference);

        return dataProcessing.soleProductSort(productList);
    }

}
