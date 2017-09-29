package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.ItdRestaurantMapper;
import com.adatafun.recommendation.mapper.RecommendationRuleMapper;
import com.adatafun.recommendation.mapper.TbdFlightInfoMapper;
import com.adatafun.recommendation.model.ItdRestaurant;
import com.adatafun.recommendation.model.UserRest;
import com.adatafun.recommendation.utils.ComparatorListSort;
import com.adatafun.recommendation.utils.DataProcessing;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HybridRecommendationService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
@Service
public class HybridRecommendationService {


    private final ItdRestaurantMapper itdRestaurantMapper;
    private final TbdFlightInfoMapper tbdFlightInfoMapper;
    private final RecommendationRuleMapper recommendationRuleMapper;

    @Autowired
    public HybridRecommendationService(ItdRestaurantMapper itdRestaurantMapper, TbdFlightInfoMapper tbdFlightInfoMapper,
                                          RecommendationRuleMapper recommendationRuleMapper) {
        this.itdRestaurantMapper = itdRestaurantMapper;
        this.tbdFlightInfoMapper = tbdFlightInfoMapper;
        this.recommendationRuleMapper = recommendationRuleMapper;
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
                    && queryRestaurantJson.containsKey("restaurantInfo")) {

                int positionFlag = 1, flightInfoFlag = 1, userBehaviorFlag = 1;
                if (!queryRestaurantJson.containsKey("position") ||
                        queryRestaurantJson.getString("position").equals("")) {
                    positionFlag = 0; //没有位置信息，位置推荐无效
                }
                if (!queryRestaurantJson.containsKey("flightNo") ||
                        !queryRestaurantJson.containsKey("flightDate") ||
                        queryRestaurantJson.getString("flightNo").equals("") ||
                        queryRestaurantJson.getString("flightDate").equals("")) {
                    flightInfoFlag = 0; //没有航班信息或航班信息不全，航班推荐无效
                }

                List<Map> list = JSON.parseArray(queryRestaurantJson.getString("restaurantInfo"), Map.class);
                if (list.size() == 0) {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display()));
                }
                for (Map attribute : list) {
                    if (!attribute.containsKey("restaurantCode") || !attribute.containsKey("restaurantWeight")
                            || attribute.get("restaurantCode").equals("") || attribute.get("restaurantWeight").equals("")) {
                        return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                    }
                }
                ComparatorListSort comparatorListSort = new ComparatorListSort("restaurantWeight");
                list.sort(comparatorListSort);

                //过滤得到有合作的优惠餐厅
                Map<String,Object> paramRestaurant = new HashMap<>();
                StringBuilder code = new StringBuilder();
                for(Map attribute : list) {
                    code.append("'").append(attribute.get("restaurantCode").toString()).append("'").append(",");
                }
                paramRestaurant.put("code", code.substring(0,code.length() - 1));
                List<ItdRestaurant> itdRestaurantList = itdRestaurantMapper.getRestaurantListByCode(paramRestaurant);
                if (itdRestaurantList.size() == 0) {
                    LZResult<List<Map>> result = new LZResult<>(list);
                    return JSON.toJSONString(result);
                }

                RecommendationBaseRuleService recommendationBaseRuleService = new RecommendationBaseRuleService(recommendationRuleMapper, tbdFlightInfoMapper);
                String position = queryRestaurantJson.getString("position");
                //根据地理位置确定推荐规则
                Map<String,Object> paramPosition = new HashMap<>();
                Integer positionRuleWeight = 0;
                JSONObject positionRuleContent = null;
                paramPosition.put("positionFlag", positionFlag);
                paramPosition.put("position", position);
                Map resultPosition = recommendationBaseRuleService.getPositionRule(paramPosition);
                if (resultPosition.get("msg").equals("操作成功")) {
                    positionFlag = Integer.parseInt(resultPosition.get("positionFlag").toString());
                }
                if (positionFlag == 1) {
                    positionRuleWeight = Integer.parseInt(resultPosition.get("positionRuleWeight").toString());
                    positionRuleContent = JSONObject.parseObject(resultPosition.get("positionRuleContent").toString());
                }

                //根据航班信息确定推荐规则
                Map<String,Object> paramFlightInfo = new HashMap<>();
                Integer flightInfoRuleWeight = 0;
                JSONObject flightInfoRuleContent = null;
                paramFlightInfo.put("flightInfoFlag", flightInfoFlag);
                paramFlightInfo.put("flightNo", queryRestaurantJson.getString("flightNo"));
                paramFlightInfo.put("flightDate", queryRestaurantJson.getString("flightDate"));
                Map resultFlightInfo = recommendationBaseRuleService.getFlightInfoRule(paramFlightInfo);
                if (resultFlightInfo.get("msg").equals("操作成功")) {
                    flightInfoFlag = Integer.parseInt(resultFlightInfo.get("flightInfoFlag").toString());
                }
                if (flightInfoFlag == 1) {
                    flightInfoRuleWeight = Integer.parseInt(resultFlightInfo.get("flightInfoRuleWeight").toString());
                    flightInfoRuleContent = JSONObject.parseObject(resultFlightInfo.get("flightInfoRuleContent").toString());
                }

                RecommendationBaseContentService recommendationBaseContentService = new RecommendationBaseContentService(recommendationRuleMapper);
                List<UserRest> userRestList = new ArrayList<>();
                List<UserRest> userTagsList = new ArrayList<>();
                //获取用户行为规则和用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                Integer userBehaviorRuleWeight = 0;
                JSONObject userBehaviorRuleContent = null;
                paramUserBehavior.put("userBehaviorFlag", userBehaviorFlag);
                paramUserBehavior.put("userId", queryRestaurantJson.getString("userId"));
                Map resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                if (resultUserBehavior.get("msg").equals("操作成功")) {
                    userBehaviorFlag = Integer.parseInt(resultUserBehavior.get("userBehaviorFlag").toString());
                }
                if (userBehaviorFlag == 1) {
                    userBehaviorRuleWeight = Integer.parseInt(resultUserBehavior.get("userBehaviorRuleWeight").toString());
                    userBehaviorRuleContent = JSONObject.parseObject(resultUserBehavior.get("userBehaviorRuleContent").toString());
                    userRestList = JSONArray.parseArray(JSONObject.toJSONString(resultUserBehavior.get("userRestList")), UserRest.class);
                    userTagsList = JSONArray.parseArray(JSONObject.toJSONString(resultUserBehavior.get("userTagsList")), UserRest.class);
                }

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String,Object> paramDataNormalization = new HashMap<>();
                paramDataNormalization.put("userBehaviorFlag", userBehaviorFlag);
                paramDataNormalization.put("userRestList", userRestList);
                Map resultDataNormalization = dataProcessing.normalizationBaseAtan(paramDataNormalization);
                if (resultDataNormalization.get("msg").equals("操作成功")) {
                    userRestList = JSONArray.parseArray(JSONObject.toJSONString(resultDataNormalization.get("userRestList")), UserRest.class);
                } else {
                    return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
                }

                //给餐厅打分,并排序
                Map<String,Object> paramWeightCalculation = new HashMap<>();
                paramWeightCalculation.put("itdRestaurantList", itdRestaurantList);
                paramWeightCalculation.put("positionFlag", positionFlag);
                paramWeightCalculation.put("positionRuleWeight", positionRuleWeight);
                paramWeightCalculation.put("positionRuleContent", positionRuleContent);
                paramWeightCalculation.put("flightInfoFlag", flightInfoFlag);
                paramWeightCalculation.put("flightInfoRuleWeight", flightInfoRuleWeight);
                paramWeightCalculation.put("flightInfoRuleContent", flightInfoRuleContent);
                paramWeightCalculation.put("userBehaviorFlag", userBehaviorFlag);
                paramWeightCalculation.put("userRestList", userRestList);
                paramWeightCalculation.put("userBehaviorRuleContent", userBehaviorRuleContent);
                paramWeightCalculation.put("userTagsList", userTagsList);
                paramWeightCalculation.put("userBehaviorRuleWeight", userBehaviorRuleWeight);
                paramWeightCalculation.put("list", list);
                Map resultWeightCalculation = dataProcessing.weightCalculation(paramWeightCalculation);
                if (resultWeightCalculation.get("msg").equals("操作成功")) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(resultWeightCalculation.get("list")), Map.class);
                } else {
                    return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
                }

                LZResult<List<Map>> result = new LZResult<>(list);
                return JSON.toJSONString(result);
            } else {
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getLounge(final JSONObject queryLoungeJson){
        try {
            JSONArray loungeInfo = queryLoungeJson.getJSONArray("loungeInfo");
            LZResult<JSONArray> result = new LZResult<>(loungeInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
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

    public String getBrandRestaurant(final JSONObject queryBrandRestaurantJson){
        try {
            JSONArray brandRestaurantInfo = queryBrandRestaurantJson.getJSONArray("brandRestaurantInfo");
            LZResult<JSONArray> result = new LZResult<>(brandRestaurantInfo);
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

    public String getShop(final JSONObject queryShopJson){
        try {
            JSONArray shopInfo = queryShopJson.getJSONArray("shopInfo");
            LZResult<JSONArray> result = new LZResult<>(shopInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getBannerArticle(final JSONObject queryBannerArticleJson){
        try {
            JSONArray bannerArticleInfo = queryBannerArticleJson.getJSONArray("bannerArticleInfo");
            LZResult<JSONArray> result = new LZResult<>(bannerArticleInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }


    public String getHomepageArticle(final JSONObject queryHomepageArticleJson){
        try {
            JSONArray homepageArticleInfo = queryHomepageArticleJson.getJSONArray("homepageArticleInfo");
            LZResult<JSONArray> result = new LZResult<>(homepageArticleInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }


    public String getPageArticle(final JSONObject queryPageArticleJson){
        try {
            JSONArray pageArticleInfo = queryPageArticleJson.getJSONArray("pageArticleInfo");
            LZResult<JSONArray> result = new LZResult<>(pageArticleInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }


    public String getType(final JSONObject queryTypeJson){
        try {
            JSONArray typeInfo = queryTypeJson.getJSONArray("typeInfo");
            LZResult<JSONArray> result = new LZResult<>(typeInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getProduct(final JSONObject queryTypeProductJson){
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
