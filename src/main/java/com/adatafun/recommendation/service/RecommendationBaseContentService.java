package com.adatafun.recommendation.service;

import com.adatafun.recommendation.model.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.adatafun.recommendation.service.ElasticSearchService.*;

/**
 * RecommendationBaseContentService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
@Service
public class RecommendationBaseContentService {

    public Map<String, Object> getPrivilegeInfo(Map<String, Object> param, List<Map<String, Object>> productList) throws Exception {

        Map<String,Object> result = new HashMap<>();
        int privilegeInfoFlag = Integer.parseInt(param.get("privilegeInfoFlag").toString());
        //获取餐厅优惠标签
        Map<String,Object> paramRestaurantPrivilegeLabel = new HashMap<>();
        paramRestaurantPrivilegeLabel.put("indexName", param.get("indexName"));
        paramRestaurantPrivilegeLabel.put("typeName", param.get("typeName"));
        List<User> userList = getRestaurantPrivilegeLabel(paramRestaurantPrivilegeLabel, productList);
        if (userList == null) {
            privilegeInfoFlag = 0;
        }

        result.put("userList", userList);
        result.put("privilegeInfoFlag", privilegeInfoFlag);
        return result;

    }

    public Map<String, Object> getUserBehavior(Map<String, Object> param) throws Exception {

        Map<String,Object> result = new HashMap<>();
        int userBehaviorFlag = Integer.parseInt(param.get("userBehaviorFlag").toString());
        //获取用户行为标签
        Map<String,Object> paramUserBehaviorLabel = new HashMap<>();
        paramUserBehaviorLabel.put("indexName", param.get("indexName"));
        paramUserBehaviorLabel.put("typeName", param.get("typeName"));
        paramUserBehaviorLabel.put("userId", param.get("userId"));
        List<User> userList = getUserBehaviorLabel(paramUserBehaviorLabel);
        if (userList == null) {
            userBehaviorFlag = 0;
        }

        result.put("userList", userList);
        result.put("userBehaviorFlag", userBehaviorFlag);
        return result;

    }

    private User countUserBehavior(Map<String, Object> userBehavior) throws Exception {

        User result = new User();
        int userBehaviorFlag = Integer.parseInt(userBehavior.get("userBehaviorFlag").toString());
        List<User> userList = JSONArray.parseArray(JSONObject.toJSONString(userBehavior.get("userList")), User.class);

        //获取用户行为标签
        Double usageCounter = 0.0, consumptionNum = 0.0, browseNum = 0.0, browseHours = 0.0, collectionNum = 0.0;
        if (userBehaviorFlag == 1) {
            for (User user : userList) {
                if (user.getUsageCounter() != null) {
                    usageCounter += user.getUsageCounter();
                }
                if (user.getConsumptionNum() != null) {
                    consumptionNum += user.getConsumptionNum();
                }
                if (user.getBrowseNum() != null) {
                    browseNum += user.getBrowseNum();
                }
                if (user.getBrowseHours() != null) {
                    browseHours += user.getBrowseHours();
                }
                if (user.getCollectionNum() != null) {
                    collectionNum += user.getCollectionNum();
                }
            }
        }
        result.setUsageCounter(usageCounter);
        result.setConsumptionNum(consumptionNum);
        result.setBrowseNum(browseNum);
        result.setBrowseHours(browseHours);
        result.setCollectionNum(collectionNum);

        return result;

    }

    public List<User> getUserModuleBehavior(Map<String, Object> param) throws Exception {

        List<User> result = new ArrayList<>();
        int userBehaviorFlag = Integer.parseInt(param.get("userBehaviorFlag").toString());
        //获取用户行为标签
        Map<String,Object> paramUserBehavior = new HashMap<>();
        paramUserBehavior.put("userBehaviorFlag", userBehaviorFlag);
        paramUserBehavior.put("userId", param.get("userId"));
        paramUserBehavior.put("indexName", "user");
        paramUserBehavior.put("typeName", "userRest");
        Map<String, Object> resultUserRestBehavior = getUserBehavior(paramUserBehavior);
        paramUserBehavior.put("typeName", "userLounge");
        Map<String, Object> resultUserLoungeBehavior = getUserBehavior(paramUserBehavior);
        paramUserBehavior.put("typeName", "userShop");
        Map<String, Object> resultUserShopBehavior = getUserBehavior(paramUserBehavior);
        paramUserBehavior.put("typeName", "userBanner");
        Map<String, Object> resultUserBannerBehavior = getUserBehavior(paramUserBehavior);
        paramUserBehavior.put("typeName", "userCar");
        Map<String, Object> resultUserCarBehavior = getUserBehavior(paramUserBehavior);
        paramUserBehavior.put("typeName", "userCIP");
        Map<String, Object> resultUserCIPBehavior = getUserBehavior(paramUserBehavior);
        paramUserBehavior.put("typeName", "userParking");
        Map<String, Object> resultUserParkingBehavior = getUserBehavior(paramUserBehavior);
        paramUserBehavior.put("typeName", "userVvip");
        Map<String, Object> resultUserVVIPBehavior = getUserBehavior(paramUserBehavior);

        User userRest = countUserBehavior(resultUserRestBehavior);
        userRest.setProductType("2");
        User userLounge = countUserBehavior(resultUserLoungeBehavior);
        userLounge.setProductType("1");
        User userShop = countUserBehavior(resultUserShopBehavior);
        userShop.setProductType("10");
        User userArticle = countUserBehavior(resultUserBannerBehavior);
        userArticle.setProductType("4");
        User userCar = countUserBehavior(resultUserCarBehavior);
        userCar.setProductType("9");
        User userCIP = countUserBehavior(resultUserCIPBehavior);
        userCIP.setProductType("5");
        User userParking = countUserBehavior(resultUserParkingBehavior);
        userParking.setProductType("7");
        User userVVIP = countUserBehavior(resultUserVVIPBehavior);
        userVVIP.setProductType("8");
        result.add(userRest);
        result.add(userLounge);
        result.add(userShop);
        result.add(userArticle);
        result.add(userCar);
        result.add(userCIP);
        result.add(userParking);
        result.add(userVVIP);

        return result;

    }

    public List<Map<String, Object>> getNoLabelArticleByUser(Map<String, Object> param, List<Map<String, Object>> list) throws Exception {

        //获取用户没有浏览过的文章
        Map<String,Object> paramArticleQuery = new HashMap<>();
        List<Map<String, Object>> subList = new ArrayList<>();
        paramArticleQuery.put("indexName", param.get("indexName"));
        paramArticleQuery.put("typeName", param.get("typeName"));
        paramArticleQuery.put("userId", param.get("userId"));
        for (Map<String, Object> article : list) {
            paramArticleQuery.put("subjectArticleId", article.get("id"));
            Boolean isBrowse = articleQuery(paramArticleQuery);
            if (isBrowse != null && isBrowse) {
                subList.add(article);
            }
        }
        list.removeAll(subList);

        return list.subList(0, 3);

    }

    public User getPreferenceSubjectArticle(Map<String, Object> param) throws Exception {

        //获取用户偏好的专题文章
        Map<String,Object> paramUserPreferenceLabel = new HashMap<>();
        paramUserPreferenceLabel.put("indexName", param.get("indexName"));
        paramUserPreferenceLabel.put("typeName", param.get("typeName"));
        paramUserPreferenceLabel.put("userId", param.get("userId"));

        return getUserPreference(paramUserPreferenceLabel);

    }

}
