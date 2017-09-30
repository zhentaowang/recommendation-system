package com.adatafun.recommendation.utils;

import com.adatafun.recommendation.model.User;
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

    public Map<String, Object> normalizationBaseAtan(Map<String, Object> param) {

        Map<String, Object> result = new HashMap<>();
        try {
            int userBehaviorFlag = Integer.parseInt(param.get("userBehaviorFlag").toString());
            List<User> userList = JSONArray.parseArray(JSONObject.toJSONString(param.get("userList")), User.class);
            if (userBehaviorFlag == 1) {
                for (User user : userList) {
                    if (user.getConsumptionNum() != null) {
                        user.setConsumptionNum(Math.atan(user.getConsumptionNum()) * 2 / PI);
                    } else {
                        user.setConsumptionNum(0.0);
                    }
                    if (user.getCollectionNum() != null) {
                        user.setCollectionNum(Math.atan(user.getCollectionNum()) * 2 / PI);
                    } else {
                        user.setCollectionNum(0.0);
                    }
                    if (user.getCommentNum() != null) {
                        user.setCommentNum(Math.atan(user.getCommentNum()) * 2 / PI);
                    } else {
                        user.setCommentNum(0.0);
                    }
                    if (user.getUsageCounter() != null) {
                        user.setUsageCounter(Math.atan(user.getUsageCounter()) * 2 / PI);
                    } else {
                        user.setUsageCounter(0.0);
                    }
                    if (user.getBrowseNum() != null) {
                        user.setBrowseNum(Math.atan(user.getBrowseNum()) * 2 / PI);
                    } else {
                        user.setBrowseNum(0.0);
                    }
                    if (user.getBrowseHours() != null) {
                        user.setBrowseHours(Math.atan(user.getBrowseHours()) * 2 / PI);
                    } else {
                        user.setBrowseHours(0.0);
                    }
                    if (user.getAverageOrderAmount() != null) {
                        user.setAverageOrderAmount(Math.atan(user.getAverageOrderAmount()) * 2 / PI);
                    } else {
                        user.setAverageOrderAmount(0.0);
                    }
                    if (user.getPeopleConsumption() != null) {
                        user.setPeopleConsumption(Math.atan(user.getPeopleConsumption()) * 2 / PI);
                    } else {
                        user.setPeopleConsumption(0.0);
                    }
                }
            }
            result.put("userList", userList);
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

    public Map<String, Object> weightCalculation(Map<String, Object> param) {

        Map<String, Object> result = new HashMap<>();
        try {
            Double score = 0.0;
            int positionFlag = Integer.parseInt(param.get("positionFlag").toString());
            int flightInfoFlag = Integer.parseInt(param.get("flightInfoFlag").toString());
            int userBehaviorFlag = Integer.parseInt(param.get("userBehaviorFlag").toString());
            List<User> userList = JSONArray.parseArray(JSONObject.toJSONString(param.get("userList")), User.class);
            List<User> userTagsList = JSONArray.parseArray(JSONObject.toJSONString(param.get("userTagsList")), User.class);
            List<Map> productList = JSONArray.parseArray(JSONObject.toJSONString(param.get("productList")), Map.class);
            for (Map<String, Object> product : productList) {

                //计算地理位置得分
                if (positionFlag == 1 && !product.get("fdInspection").equals("")) {
                    Integer positionRuleWeight = Integer.parseInt(param.get("positionRuleWeight").toString());
                    JSONObject positionRuleContent = JSONObject.parseObject(param.get("positionRuleContent").toString());
                    score += positionRuleWeight * positionRuleContent.getDouble(product.get("fdInspection").toString());
                }

                //计算航班信息得分
                if (flightInfoFlag == 1 && !product.get("fdInspection").equals("")) {
                    Integer flightInfoRuleWeight = Integer.parseInt(param.get("flightInfoRuleWeight").toString());
                    JSONObject flightInfoRuleContent = JSONObject.parseObject(param.get("flightInfoRuleContent").toString());
                    score += flightInfoRuleWeight * flightInfoRuleContent.getDouble(product.get("fdInspection").toString());
                }

                //计算用户行为得分
                if (userBehaviorFlag == 1) { //用户行为推荐有效
                    Double behaviorScore = 0.0;
                    Integer userBehaviorRuleWeight = Integer.parseInt(param.get("userBehaviorRuleWeight").toString());
                    JSONObject userBehaviorRuleContent = JSONObject.parseObject(param.get("userBehaviorRuleContent").toString());
                    for (User user : userList) {
                        if (user.getRestaurantCode().equals(product.get("fdCode"))) {
                            behaviorScore += user.getConsumptionNum() * userBehaviorRuleContent.getDouble("consumptionNum");
                            behaviorScore += user.getCollectionNum() * userBehaviorRuleContent.getDouble("collectionNum");
                            behaviorScore += user.getCommentNum() * userBehaviorRuleContent.getDouble("commentNum");
                            behaviorScore += user.getAverageOrderAmount() * userBehaviorRuleContent.getDouble("averageOrderAmount");
                            behaviorScore += user.getUsageCounter() * userBehaviorRuleContent.getDouble("usageCounter");
                            behaviorScore += user.getBrowseNum() * userBehaviorRuleContent.getDouble("browseNum");
                            behaviorScore += user.getBrowseHours() * userBehaviorRuleContent.getDouble("browseHours");
                            if (user.getMultiTimeConsumption()) {
                                behaviorScore += userBehaviorRuleContent.getDouble("isMultitimeConsumption");
                            }
                            assert userTagsList != null;
                            if (userTagsList.size() != 0) { //用户偏好推荐有效
                                if (product.get("fdCls").equals("1")) {
                                    product.put("fdCls", "中餐");
                                } else if (product.get("fdCls").equals("0")) {
                                    product.put("fdCls", "西餐");
                                }
                                if (userTagsList.get(0).getRestaurantPreferences().equals(product.get("fdCls"))) {
                                    behaviorScore += userBehaviorRuleContent.getDouble("restaurantPreferences");
                                }
                            }
                        }
                    }
                    score += userBehaviorRuleWeight * behaviorScore;
                }
                product.put("score", score);
            }

            result.put("productList", productList);
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

    public Map<String, Object> productSort(Map<String, Object> param) {

        Map<String, Object> result = new HashMap<>();
        try {
            String orderByKey = param.get("orderByKey").toString();
            String productCode = param.get("productCode").toString();
            ComparatorListSort comparatorListSort = new ComparatorListSort(orderByKey);
            List<Map> productList = JSONArray.parseArray(JSONObject.toJSONString(param.get("productList")), Map.class);
            List<Map> list = JSONArray.parseArray(JSONObject.toJSONString(param.get("list")), Map.class);
            //排序
            for (Map product : productList) {
                for (Map attribute : list) {
                    if (product.get("fdCode").equals(attribute.get(productCode).toString())) {
                        Double tempScore = Integer.parseInt(attribute.get(orderByKey).toString())
                                * Double.parseDouble(product.get("score").toString());
                        Integer resultScore = tempScore.intValue();
                        attribute.put(orderByKey, resultScore);
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
