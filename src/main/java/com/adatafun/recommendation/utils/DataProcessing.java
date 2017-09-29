package com.adatafun.recommendation.utils;

import com.adatafun.recommendation.model.ItdRestaurant;
import com.adatafun.recommendation.model.UserRest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LZStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.PI;

/**
 * DataProcessing.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
public class DataProcessing {

    public Map normalizationBaseAtan(Map<String, Object> param){

        Map<String,Object> result = new HashMap<>();
        try {
            int userBehaviorFlag = Integer.parseInt(param.get("userBehaviorFlag").toString());
            List<UserRest> userRestList = JSONArray.parseArray(JSONObject.toJSONString(param.get("userRestList")), UserRest.class);
            if (userBehaviorFlag == 1) {
                for (UserRest userRest : userRestList) {
                    if (userRest.getConsumptionNum() != null) {
                        userRest.setConsumptionNum(Math.atan(userRest.getConsumptionNum()) * 2/PI);
                    } else {
                        userRest.setConsumptionNum(0.0);
                    }
                    if (userRest.getCollectionNum() != null) {
                        userRest.setCollectionNum(Math.atan(userRest.getCollectionNum()) * 2/PI);
                    } else {
                        userRest.setCollectionNum(0.0);
                    }
                    if (userRest.getCommentNum() != null) {
                        userRest.setCommentNum(Math.atan(userRest.getCommentNum()) * 2/PI);
                    } else {
                        userRest.setCommentNum(0.0);
                    }
                    if (userRest.getUsageCounter() != null) {
                        userRest.setUsageCounter(Math.atan(userRest.getUsageCounter()) * 2/PI);
                    } else {
                        userRest.setUsageCounter(0.0);
                    }
                    if (userRest.getBrowseNum() != null) {
                        userRest.setBrowseNum(Math.atan(userRest.getBrowseNum()) * 2/PI);
                    } else {
                        userRest.setBrowseNum(0.0);
                    }
                    if (userRest.getBrowseHours() != null) {
                        userRest.setBrowseHours(Math.atan(userRest.getBrowseHours()) * 2/PI);
                    } else {
                        userRest.setBrowseHours(0.0);
                    }
                    if (userRest.getAverageOrderAmount() != null) {
                        userRest.setAverageOrderAmount(Math.atan(userRest.getAverageOrderAmount()) * 2/PI);
                    } else {
                        userRest.setAverageOrderAmount(0.0);
                    }
                }
            }
            result.put("userRestList", userRestList);
            result.put("status", LZStatus.SUCCESS.value());
            result.put("msg", LZStatus.SUCCESS.display());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", LZStatus.ERROR.value());
            result.put("msg", LZStatus.ERROR.display());
            return result;
        }

    }

    public Map weightCalculation(Map<String, Object> param){

        Map<String,Object> result = new HashMap<>();
        try {
            Double score = 0.0;
            int positionFlag = Integer.parseInt(param.get("positionFlag").toString());
            int flightInfoFlag = Integer.parseInt(param.get("flightInfoFlag").toString());
            int userBehaviorFlag = Integer.parseInt(param.get("userBehaviorFlag").toString());
            ComparatorListSort comparatorListSort = new ComparatorListSort("restaurantWeight");
            List<UserRest> userRestList = JSONArray.parseArray(JSONObject.toJSONString(param.get("userRestList")), UserRest.class);
            List<UserRest> userTagsList = JSONArray.parseArray(JSONObject.toJSONString(param.get("userTagsList")), UserRest.class);
            List<ItdRestaurant> itdRestaurantList = JSONArray.parseArray(JSONObject.toJSONString(param.get("itdRestaurantList")), ItdRestaurant.class);
            List<Map> list = JSONArray.parseArray(JSONObject.toJSONString(param.get("list")), Map.class);
            for(ItdRestaurant itdRestaurant : itdRestaurantList) {
                //计算地理位置得分
                if (positionFlag == 1 && !itdRestaurant.getFdInspection().equals("")) {
                    Integer positionRuleWeight = Integer.parseInt(param.get("positionRuleWeight").toString());
                    JSONObject positionRuleContent = JSONObject.parseObject(param.get("positionRuleContent").toString());
                    score += positionRuleWeight * positionRuleContent.getDouble(itdRestaurant.getFdInspection());
                }

                //计算航班信息得分
                if (flightInfoFlag == 1 && !itdRestaurant.getFdInspection().equals("")) {
                    Integer flightInfoRuleWeight = Integer.parseInt(param.get("flightInfoRuleWeight").toString());
                    JSONObject flightInfoRuleContent = JSONObject.parseObject(param.get("flightInfoRuleContent").toString());
                    score += flightInfoRuleWeight * flightInfoRuleContent.getDouble(itdRestaurant.getFdInspection());
                }

                //计算用户行为得分
                if (userBehaviorFlag == 1) { //用户行为推荐有效
                    Double behaviorScore = 0.0;
                    Integer userBehaviorRuleWeight = Integer.parseInt(param.get("userBehaviorRuleWeight").toString());
                    JSONObject userBehaviorRuleContent = JSONObject.parseObject(param.get("userBehaviorRuleContent").toString());
                    for(UserRest userRest : userRestList) {
                        if (userRest.getRestaurantCode().equals(itdRestaurant.getFdCode())) {
                            behaviorScore += userRest.getConsumptionNum() * userBehaviorRuleContent.getDouble("consumptionNum");
                            behaviorScore += userRest.getCollectionNum() * userBehaviorRuleContent.getDouble("collectionNum");
                            behaviorScore += userRest.getCommentNum() * userBehaviorRuleContent.getDouble("commentNum");
                            behaviorScore += userRest.getAverageOrderAmount() * userBehaviorRuleContent.getDouble("averageOrderAmount");
                            behaviorScore += userRest.getUsageCounter() * userBehaviorRuleContent.getDouble("usageCounter");
                            behaviorScore += userRest.getBrowseNum() * userBehaviorRuleContent.getDouble("browseNum");
                            behaviorScore += userRest.getBrowseHours() * userBehaviorRuleContent.getDouble("browseHours");
                            if (userRest.getMultiTimeConsumption()) {
                                behaviorScore += userBehaviorRuleContent.getDouble("isMultitimeConsumption");
                            }
                            assert userTagsList != null;
                            if (userTagsList.size() != 0) { //用户偏好推荐有效
                                if (itdRestaurant.getFdCls().equals("1")) {
                                    itdRestaurant.setFdCls("中餐");
                                } else if (itdRestaurant.getFdCls().equals("0")) {
                                    itdRestaurant.setFdCls("西餐");
                                }
                                if (userTagsList.get(0).getRestaurantPreferences().equals(itdRestaurant.getFdCls())) {
                                    behaviorScore += userBehaviorRuleContent.getDouble("restaurantPreferences");
                                }
                            }
                        }
                    }
                    score += userBehaviorRuleWeight * behaviorScore;
                }
                itdRestaurant.setScore(score);
            }

            //排序
            for(ItdRestaurant itdRestaurant : itdRestaurantList) {
                for(Map attribute : list) {
                    if (itdRestaurant.getFdCode().equals(attribute.get("restaurantCode").toString())) {
                        Double tempScore = Integer.parseInt(attribute.get("restaurantWeight").toString()) * itdRestaurant.getScore();
                        Integer resultScore = tempScore.intValue();
                        attribute.put("restaurantWeight", resultScore);
                    }
                }
            }
            list.sort(comparatorListSort);

            result.put("list", list);
            result.put("status", LZStatus.SUCCESS.value());
            result.put("msg", LZStatus.SUCCESS.display());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", LZStatus.ERROR.value());
            result.put("msg", LZStatus.ERROR.display());
            return result;
        }

    }

}
