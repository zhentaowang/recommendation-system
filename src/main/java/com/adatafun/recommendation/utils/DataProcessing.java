package com.adatafun.recommendation.utils;

import com.adatafun.recommendation.model.RecommendationRule;
import com.adatafun.recommendation.model.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

import static java.lang.Math.PI;

/**
 * DataProcessing.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
public class DataProcessing {

    public Map<String, Object> normalizationBaseAtan(Map<String, Object> resultUserBehavior) throws Exception {

        int userBehaviorFlag = Integer.parseInt(resultUserBehavior.get("userBehaviorFlag").toString());
        List<User> userList = JSONArray.parseArray(JSONObject.toJSONString(resultUserBehavior.get("userList")), User.class);
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
            resultUserBehavior.put("userList", userList);
        }
        return resultUserBehavior;

    }

    public List<Map> weightCalculation(Map<String, Object> param) {

        Double score = 0.0;
        Map resultPosition = (Map)param.get("resultPosition");
        int positionFlag = Integer.parseInt(resultPosition.get("positionFlag").toString());
        RecommendationRule positionRule = (RecommendationRule)resultPosition.get("positionRule");
        Map resultFlightInfo = (Map)param.get("resultFlightInfo");
        int flightInfoFlag = Integer.parseInt(resultFlightInfo.get("flightInfoFlag").toString());
        RecommendationRule flightInfoRule = (RecommendationRule)resultFlightInfo.get("flightInfoRule");
        Map resultUserBehavior = (Map)param.get("resultUserBehavior");
        List<User> userList = JSONArray.parseArray(JSONObject.toJSONString(resultUserBehavior.get("userList")), User.class);
        List<User> userTagsList = JSONArray.parseArray(JSONObject.toJSONString(resultUserBehavior.get("userTagsList")), User.class);
        List<Map> productList = JSONArray.parseArray(JSONObject.toJSONString(param.get("productList")), Map.class);
        for (Map<String, Object> product : productList) {

            //计算地理位置得分
            if (positionFlag == 1 && !product.get("fdInspection").equals("")) {
                Integer positionRuleWeight = positionRule.getTypeWeight();
                JSONObject positionRuleContent = JSONObject.parseObject(positionRule.getRuleContent());
                score += positionRuleWeight * positionRuleContent.getDouble(product.get("fdInspection").toString());
            }

            //计算航班信息得分
            if (flightInfoFlag == 1 && !product.get("fdInspection").equals("")) {
                Integer flightInfoRuleWeight = flightInfoRule.getTypeWeight();
                JSONObject flightInfoRuleContent = JSONObject.parseObject(flightInfoRule.getRuleContent());
                score += flightInfoRuleWeight * flightInfoRuleContent.getDouble(product.get("fdInspection").toString());
            }
            product.put("score", score);
        }

        //计算用户行为得分
        productList = behaviorWeightCalculation(resultUserBehavior, userList, userTagsList, productList);
        return productList;

    }

    private List<Map> behaviorWeightCalculation(Map resultUserBehavior, List<User> userList, List<User> userTagsList, List<Map> productList) {


        Double score = 0.0;
        int userBehaviorFlag = Integer.parseInt(resultUserBehavior.get("userBehaviorFlag").toString());
        RecommendationRule userBehaviorRule = (RecommendationRule)resultUserBehavior.get("userBehaviorRule");
        for (Map<String, Object> product : productList) {

            //计算用户行为得分
            if (userBehaviorFlag == 1) { //用户行为推荐有效
                Double behaviorScore = 0.0;
                Integer userBehaviorRuleWeight = userBehaviorRule.getTypeWeight();
                JSONObject userBehaviorRuleContent = JSONObject.parseObject(userBehaviorRule.getRuleContent());
                for (User user : userList) {
                    if (user.getRestaurantCode().equals(product.get("fdCode"))) {
                        behaviorScore += user.getConsumptionNum() * userBehaviorRuleContent.getDouble("consumptionNum");
                        behaviorScore += user.getCollectionNum() * userBehaviorRuleContent.getDouble("collectionNum");
                        behaviorScore += user.getCommentNum() * userBehaviorRuleContent.getDouble("commentNum");
                        behaviorScore += user.getAverageOrderAmount() * userBehaviorRuleContent.getDouble("averageOrderAmount");
                        behaviorScore += user.getUsageCounter() * userBehaviorRuleContent.getDouble("usageCounter");
                        behaviorScore += user.getBrowseNum() * userBehaviorRuleContent.getDouble("browseNum");
                        behaviorScore += user.getBrowseHours() * userBehaviorRuleContent.getDouble("browseHours");
                        if (userBehaviorRuleContent.containsKey("peopleConsumption")) {
                            behaviorScore += user.getPeopleConsumption() * userBehaviorRuleContent.getDouble("peopleConsumption");
                        }
                        if (user.getMultiTimeConsumption()) {
                            behaviorScore += userBehaviorRuleContent.getDouble("isMultitimeConsumption");
                        }
                        assert userTagsList != null;
                        if (userTagsList.size() != 0) { //用户偏好推荐有效 对于商铺逻辑需要修改（商铺类型）
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
            product.put("score", score + Double.parseDouble(product.get("score").toString()));
        }
        return productList;

    }

    public List<Map> productSort(Map<String, Object> param) throws Exception {

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
        return list;
    }
}
