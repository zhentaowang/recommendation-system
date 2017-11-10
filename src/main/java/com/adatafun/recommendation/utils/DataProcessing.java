package com.adatafun.recommendation.utils;

import com.adatafun.recommendation.model.RecommendationRule;
import com.adatafun.recommendation.model.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
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

    public List<Map<String, Object>> positionWeightCalculation(Map resultPosition, List<Map<String, Object>> productList) throws Exception {

        int positionFlag = Integer.parseInt(resultPosition.get("positionFlag").toString());
        RecommendationRule positionRule = (RecommendationRule)resultPosition.get("positionRule");

        //计算地理位置得分
        for (Map<String, Object> product : productList) {
            Double score = 0.0;
            if (positionFlag == 1 && !product.get("fd_inspection").equals("")) {
                Integer positionRuleWeight = positionRule.getTypeWeight();
                JSONObject positionRuleContent = JSONObject.parseObject(positionRule.getRuleContent());
                score += positionRuleWeight * positionRuleContent.getDouble(product.get("fd_inspection").toString());
            }
            product.put("score", score);
        }

        return productList;

    }

    public List<Map<String, Object>> flightWeightCalculation(Map resultFlightInfo, List<Map<String, Object>> productList) throws Exception {

        int flightInfoFlag = Integer.parseInt(resultFlightInfo.get("flightInfoFlag").toString());
        RecommendationRule flightInfoRule = (RecommendationRule)resultFlightInfo.get("flightInfoRule");

        //计算航班信息得分
        for (Map<String, Object> product : productList) {
            Double score = 0.0;
            if(!product.containsKey("score")) {
                product.put("score", score);
            }
            if (flightInfoFlag == 1 && product.containsKey("fd_inspection") && !product.get("fd_inspection").equals("")) {
                Integer flightInfoRuleWeight = flightInfoRule.getTypeWeight();
                JSONObject flightInfoRuleContent = JSONObject.parseObject(flightInfoRule.getRuleContent());
                score += flightInfoRuleWeight * flightInfoRuleContent.getDouble(product.get("fd_inspection").toString());
            }
            product.put("score", score + Double.parseDouble(product.get("score").toString()));
        }

        return productList;

    }

    public List<Map> typeWeightCalculation(Map resultHybridInfo, List<Map> typeList) throws Exception {

        int hybridInfoFlag = Integer.parseInt(resultHybridInfo.get("hybridInfoFlag").toString());
        RecommendationRule hybridInfoRule = (RecommendationRule)resultHybridInfo.get("hybridInfoRule");

        //计算位置和航班信息得分
        for (Map type : typeList) {
            Double score = 0.0;
            type.put("score", score);
            if (hybridInfoFlag == 2) {
                Integer hybridInfoRuleWeight = hybridInfoRule.getTypeWeight();
                JSONObject hybridInfoRuleContent = JSONObject.parseObject(hybridInfoRule.getRuleContent());
                if (type.get("typeId").equals("2")) {
                    score += hybridInfoRuleWeight * hybridInfoRuleContent.getDouble("restaurant");
                }
                if (type.get("typeId").equals("1")) {
                    score += hybridInfoRuleWeight * hybridInfoRuleContent.getDouble("lounge");
                }
                if (type.get("typeId").equals("10")) {
                    score += hybridInfoRuleWeight * hybridInfoRuleContent.getDouble("shop");
                }
                if (type.get("typeId").equals("4")) {
                    score += hybridInfoRuleWeight * hybridInfoRuleContent.getDouble("article");
                }
                if (type.get("typeId").equals("9")) {
                    score += hybridInfoRuleWeight * hybridInfoRuleContent.getDouble("car");
                }
                if (type.get("typeId").equals("5")) {
                    score += hybridInfoRuleWeight * hybridInfoRuleContent.getDouble("CIP");
                }
                if (type.get("typeId").equals("7")) {
                    score += hybridInfoRuleWeight * hybridInfoRuleContent.getDouble("parking");
                }
                if (type.get("typeId").equals("8")) {
                    score += hybridInfoRuleWeight * hybridInfoRuleContent.getDouble("VVIP");
                }
                type.put("score", score);
            }
        }

        return typeList;

    }

    public List<Map<String, Object>> privilegeWeightCalculation(Map resultPrivilegeInfo, List<Map<String, Object>> productList) throws Exception {

        int privilegeInfoFlag = Integer.parseInt(resultPrivilegeInfo.get("privilegeInfoFlag").toString());
        List<User> userList = JSONArray.parseArray(JSONObject.toJSONString(resultPrivilegeInfo.get("userList")), User.class);
        RecommendationRule privilegeInfoRule = (RecommendationRule)resultPrivilegeInfo.get("privilegeInfoRule");

        for (Map<String, Object> product : productList) {

            //计算餐厅优惠信息得分
            Double score = 0.0;
            if (privilegeInfoFlag == 1) { //餐厅优惠推荐有效
                Double privilegeScore = 0.0;
                Integer privilegeInfoRuleWeight = privilegeInfoRule.getTypeWeight();
                JSONObject privilegeInfoRuleContent = JSONObject.parseObject(privilegeInfoRule.getRuleContent());
                for (User user : userList) {
                    if (user.getRestaurantCode().equals(product.get("fd_code"))) {
                        int priority = user.getPriority().intValue();
                        switch (priority) {
                            case 3:
                                privilegeScore += user.getPriority() * privilegeInfoRuleContent.getDouble("flashSale");
                                break;
                            case 2:
                                privilegeScore += user.getPriority() * privilegeInfoRuleContent.getDouble("coupon");
                                break;
                            case 1:
                                privilegeScore += user.getPriority() * privilegeInfoRuleContent.getDouble("settlementDiscount");
                                break;
                        }
                    }
                }
                score += privilegeInfoRuleWeight * privilegeScore;
            }
            product.put("score", score + Double.parseDouble(product.get("score").toString()));
        }

        return productList;

    }

    public List<Map<String, Object>> behaviorWeightCalculation(Map resultUserBehavior, List<Map<String, Object>> productList) throws Exception {

        int userBehaviorFlag = Integer.parseInt(resultUserBehavior.get("userBehaviorFlag").toString());
        List<User> userList = JSONArray.parseArray(JSONObject.toJSONString(resultUserBehavior.get("userList")), User.class);
        RecommendationRule userBehaviorRule = (RecommendationRule)resultUserBehavior.get("userBehaviorRule");
        for (Map<String, Object> product : productList) {

            //计算用户行为得分
            Double score = 0.0;
            if (userBehaviorFlag == 1) { //用户行为推荐有效
                Double behaviorScore = 0.0;
                Integer userBehaviorRuleWeight = userBehaviorRule.getTypeWeight();
                JSONObject userBehaviorRuleContent = JSONObject.parseObject(userBehaviorRule.getRuleContent());
                for (User user : userList) {
                    if (user.getRestaurantCode().equals(product.get("fd_code"))) {
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
                    }
                }
                score += userBehaviorRuleWeight * behaviorScore;
            }
            product.put("score", score + Double.parseDouble(product.get("score").toString()));
        }
        return productList;

    }

    public List<Map> typeBehaviorWeightCalculation(Map resultUserBehavior, List<Map> productList) throws Exception {

        int userBehaviorFlag = Integer.parseInt(resultUserBehavior.get("userBehaviorFlag").toString());
        List<User> userList = JSONArray.parseArray(JSONObject.toJSONString(resultUserBehavior.get("userList")), User.class);
        RecommendationRule userBehaviorRule = (RecommendationRule)resultUserBehavior.get("userBehaviorRule");
        for (Map product : productList) {

            //计算用户行为得分
            Double score = 0.0;
            if (userBehaviorFlag == 1) { //用户行为推荐有效
                Double behaviorScore = 0.0;
                Integer userBehaviorRuleWeight = userBehaviorRule.getTypeWeight();
                JSONObject userBehaviorRuleContent = JSONObject.parseObject(userBehaviorRule.getRuleContent());
                for (User user : userList) {
                    if (user.getProductType().equals(product.get("typeId"))) {
                        behaviorScore += user.getConsumptionNum() * userBehaviorRuleContent.getDouble("consumptionNum");
                        behaviorScore += user.getCollectionNum() * userBehaviorRuleContent.getDouble("collectionNum");
                        behaviorScore += user.getUsageCounter() * userBehaviorRuleContent.getDouble("usageCounter");
                        behaviorScore += user.getBrowseNum() * userBehaviorRuleContent.getDouble("browseNum");
                        behaviorScore += user.getBrowseHours() * userBehaviorRuleContent.getDouble("browseHours");
                    }
                }
                score += userBehaviorRuleWeight * behaviorScore;
            }
            score = score + Double.parseDouble(product.get("score").toString());
            Double typeWeight = score * Double.parseDouble(product.get("typeWeight").toString());
            product.remove("score");
            product.put("typeWeight", typeWeight.intValue());
        }
        return productList;

    }

    public List<Map<String, Object>> preferenceWeightCalculation(Map resultUserPreference, List<Map<String, Object>> productList) throws Exception {

        int userPreferenceFlag = Integer.parseInt(resultUserPreference.get("userBehaviorFlag").toString());
        List<User> userTagsList = JSONArray.parseArray(JSONObject.toJSONString(resultUserPreference.get("userList")), User.class);
        RecommendationRule userPreferenceRule = (RecommendationRule)resultUserPreference.get("userPreferenceRule");
        for (Map<String, Object> product : productList) {

            //计算用户偏好得分
            Double score = 0.0;
            if (userPreferenceFlag == 1) { //用户偏好推荐有效
                Double behaviorScore = 0.0;
                Integer userPreferenceRuleWeight = userPreferenceRule.getTypeWeight();
                JSONObject userPreferenceRuleContent = JSONObject.parseObject(userPreferenceRule.getRuleContent());
                //目前只用于餐厅，对于商铺逻辑需要修改（商铺类型）
                if (product.containsKey("fd_cls")) {
                    if (product.get("fd_cls").equals("1")) {
                        product.put("fd_cls", "中餐");
                    } else {
                        product.put("fd_cls", "西餐");
                    }
                } else {
                    product.put("fd_cls", "中餐");
                }

                if (userTagsList.size() != 0) {
                    String restaurantPreferences = userTagsList.get(0).getRestaurantPreferences();
                    if (restaurantPreferences != null && restaurantPreferences.equals(product.get("fd_cls"))) {
                        behaviorScore += userPreferenceRuleContent.getDouble("restaurantPreferences");
                    }
                    score += userPreferenceRuleWeight * behaviorScore;
                }
            }
            product.put("score", score + Double.parseDouble(product.get("score").toString()));
        }
        return productList;

    }

    public List<Map<String,Object>> bannerWeightCalculation(Map resultUserBehavior, List<Map<String, Object>> productList) throws Exception {

        List<User> userList = JSONArray.parseArray(JSONObject.toJSONString(resultUserBehavior.get("userList")), User.class);
        int userBehaviorFlag = Integer.parseInt(resultUserBehavior.get("userBehaviorFlag").toString());
        RecommendationRule userBehaviorRule = (RecommendationRule)resultUserBehavior.get("userBehaviorRule");
        for (Map<String, Object> product : productList) {

            //计算用户行为得分
            Double score = 0.0;
            if (userBehaviorFlag == 1) { //用户行为推荐有效
                Double behaviorScore = 0.0;
                Integer userBehaviorRuleWeight = userBehaviorRule.getTypeWeight();
                JSONObject userBehaviorRuleContent = JSONObject.parseObject(userBehaviorRule.getRuleContent());
                for (User user : userList) {
                    if (user.getRestaurantCode().equals(product.get("id"))) {
                        behaviorScore += user.getCollectionNum() * userBehaviorRuleContent.getDouble("collectionNum");
                        behaviorScore += user.getBrowseNum() * userBehaviorRuleContent.getDouble("browseNum");
                        behaviorScore += user.getBrowseHours() * userBehaviorRuleContent.getDouble("browseHours");
                    }
                }
                score += userBehaviorRuleWeight * behaviorScore;
            }
            product.put("score", score);
        }
        return productList;

    }

    public List<Map<String, Object>> hybridWeightCalculation(Map resultUserBehavior, List<Map<String, Object>> productList) throws Exception {

        int userBehaviorFlag = Integer.parseInt(resultUserBehavior.get("userBehaviorFlag").toString());
        List<User> userList = JSONArray.parseArray(JSONObject.toJSONString(resultUserBehavior.get("userList")), User.class);
        RecommendationRule userBehaviorRule = (RecommendationRule)resultUserBehavior.get("userBehaviorRule");
        for (Map<String, Object> product : productList) {

            //计算用户行为得分
            Double score = 0.0;

            if (product.containsKey("flag") && product.get("flag").equals(1)) { //满足该条件时该产品权重最高
                product.put("score", 10);
            }

            if (userBehaviorFlag == 1) { //用户行为推荐有效
                Double behaviorScore = 0.0;
                Integer userBehaviorRuleWeight = userBehaviorRule.getTypeWeight();
                JSONObject userBehaviorRuleContent = JSONObject.parseObject(userBehaviorRule.getRuleContent());
                for (User user : userList) {
                    if (user.getRestaurantCode().equals(product.get("fd_code"))) {
                        behaviorScore += user.getConsumptionNum() * userBehaviorRuleContent.getDouble("consumptionNum");
                        behaviorScore += user.getCollectionNum() * userBehaviorRuleContent.getDouble("collectionNum");
                        behaviorScore += user.getCommentNum() * userBehaviorRuleContent.getDouble("commentNum");
                        behaviorScore += user.getAverageOrderAmount() * userBehaviorRuleContent.getDouble("averageOrderAmount");
                        behaviorScore += user.getUsageCounter() * userBehaviorRuleContent.getDouble("usageCounter");
                        behaviorScore += user.getBrowseNum() * userBehaviorRuleContent.getDouble("browseNum");
                        behaviorScore += user.getBrowseHours() * userBehaviorRuleContent.getDouble("browseHours");
                    }
                }
                score += userBehaviorRuleWeight * behaviorScore;
            }
            product.put("score", score * 10);
        }
        return productList;

    }

    public List<Map> productSort(Map<String, Object> param, List<Map> list, List<Map<String, Object>> productList) throws Exception {

        String orderByKey = param.get("orderByKey").toString();
        String productCode = param.get("productCode").toString();
        ComparatorListSort comparatorListSort = new ComparatorListSort(orderByKey);
        //排序
        for (Map<String, Object> product : productList) {
            for (Map attribute : list) {
                Boolean flag1 = product.containsKey("fd_code") && product.get("fd_code").equals(attribute.get(productCode).toString());
                Boolean flag2 = product.containsKey("id") && product.get("id").equals(attribute.get(productCode).toString());
                if (flag1 || flag2) {
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

    public List<Map<String, Object>> eraseUselessInfo(List<Map<String, Object>> hybridProductList) throws Exception {

        //去除无用信息
        List<Map<String, Object>> productList = new ArrayList<>();
        for (Map<String, Object> product : hybridProductList) {
            Map<String, Object> map = new HashMap<>();
            map.put("productCode", product.get("fd_code"));
            Double tempScore = Double.parseDouble(product.get("score").toString());
            map.put("productWeight", tempScore.intValue());
            productList.add(map);
        }

        return productList;

    }

    public List<Map<String, Object>> getFinalScore(Integer typeWeight, List<Map<String, Object>> productList) throws Exception {

        //根据运营策略决定最终分数
        for (Map<String, Object> product : productList) {
            product.put("score", Double.parseDouble(product.get("score").toString()) * typeWeight);
        }

        return productList;

    }

    public List<Map<String, Object>> hybridProductSort(List<Map<String, Object>> productList) throws Exception {

        //排序
        ComparatorListSort comparatorListSort = new ComparatorListSort("productWeight");
        productList.sort(comparatorListSort);
        return productList.subList(0, 20);

    }

    public List<Map> productTypeSort(List<Map> productList) throws Exception {

        //排序
        ComparatorListSort comparatorListSort = new ComparatorListSort("typeWeight");
        productList.sort(comparatorListSort);
        return productList;

    }

    public List<Map<String, Object>> soleProductSort(List<Map<String, Object>> productList) throws Exception {

        //排序
        ComparatorListSort comparatorListSort = new ComparatorListSort("score");
        productList.sort(comparatorListSort);
        return productList;

    }

    public Map<String, Integer> getMaxProductNum(List<Map<String, Object>> restaurantList,
                                                  List<Map<String, Object>> loungeList,
                                                  List<Map<String, Object>> shopList,
                                                  List<Map<String, Object>> carList,
                                                  List<Map<String, Object>> cipList,
                                                  List<Map<String, Object>> parkingList,
                                                  List<Map<String, Object>> vvipList) throws Exception {
        Map<String, Integer> result = new HashMap<>();

        //按类计算产品数量，并取最大值
        Integer maxProductNum = 0, restaurantNum = 0, loungeNum = 0, shopNum = 0, carNum = 0, cipNum = 0, parkingNum = 0, vvipNum = 0;
        if (restaurantList != null) {
            restaurantNum = restaurantList.size();
            maxProductNum = restaurantNum;
        }
        if (loungeList != null) {
            loungeNum = loungeList.size();
            if (maxProductNum < loungeNum) {
                maxProductNum = loungeNum;
            }
        }
        if (shopList != null) {
            shopNum = shopList.size();
            if (maxProductNum < shopNum) {
                maxProductNum = shopNum;
            }
        }
        if (carList != null) {
            carNum = carList.size();
            if (maxProductNum < carNum) {
                maxProductNum = carNum;
            }
        }
        if (cipList != null) {
            cipNum = cipList.size();
            if (maxProductNum < cipNum) {
                maxProductNum = cipNum;
            }
        }
        if (parkingList != null) {
            parkingNum = parkingList.size();
            if (maxProductNum < parkingNum) {
                maxProductNum = parkingNum;
            }
        }
        if (vvipList != null) {
            vvipNum = vvipList.size();
            if (maxProductNum < vvipNum) {
                maxProductNum = vvipNum;
            }
        }

        result.put("maxProductNum", maxProductNum);
        result.put("restaurantNum", restaurantNum);
        result.put("loungeNum", loungeNum);
        result.put("shopNum", shopNum);
        result.put("carNum", carNum);
        result.put("cipNum", cipNum);
        result.put("parkingNum", parkingNum);
        result.put("vvipNum", vvipNum);

        return result;
    }

    public List<Map<String, Object>> productSortBasedOperationStrategy(List<Map<String, Object>> restaurantList,
                                                                        List<Map<String, Object>> loungeList,
                                                                        List<Map<String, Object>> shopList,
                                                                        List<Map<String, Object>> carList,
                                                                        List<Map<String, Object>> cipList,
                                                                        List<Map<String, Object>> parkingList,
                                                                        List<Map<String, Object>> vvipList,
                                                                        Map<String, Integer> productNum) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Integer productIndex = 0; productIndex < productNum.get("maxProductNum"); productIndex++) {
            //满足该条件时快速安检权重最高
            Boolean priorityFlag = cipList.size() != 0 && cipList.get(0).containsKey("flag") && cipList.get(0).get("flag").equals(1);
            if (priorityFlag) {
                if (productIndex < productNum.get("cipNum")) {
                    result.add(cipList.get(productIndex));
                }
            }
            if (productIndex < productNum.get("restaurantNum")) {
                result.add(restaurantList.get(productIndex));
            }
            if (productIndex < productNum.get("loungeNum")) {
                result.add(loungeList.get(productIndex));
            }
            if (productIndex < productNum.get("shopNum")) {
                result.add(shopList.get(productIndex));
            }
            if (productIndex < productNum.get("carNum")) {
                result.add(carList.get(productIndex));
            }
            if (!priorityFlag) {
                if (productIndex < productNum.get("cipNum")) {
                    result.add(cipList.get(productIndex));
                }
            }
            if (productIndex < productNum.get("vvipNum")) {
                result.add(vvipList.get(productIndex));
            }
            if (productIndex < productNum.get("parkingNum")) {
                result.add(parkingList.get(productIndex));
            }
        }
        return result;
    }

}
