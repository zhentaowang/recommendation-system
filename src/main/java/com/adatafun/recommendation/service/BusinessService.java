/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.adatafun.recommendation.service;

import com.adatafun.recommendation.Main;
import com.adatafun.recommendation.mapper.ItdRestaurantMapper;
import com.adatafun.recommendation.mapper.RecommendationRuleMapper;
import com.adatafun.recommendation.mapper.TbdFlightInfoMapper;
import com.adatafun.recommendation.model.*;
import com.adatafun.recommendation.utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.server.business.IBusinessService;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Math.PI;

/**
 * PermissionMapper.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/05/2017.
 */
@Service
public class BusinessService implements IBusinessService {

    private final ItdRestaurantMapper itdRestaurantMapper;
    private final TbdFlightInfoMapper tbdFlightInfoMapper;
    private final RecommendationRuleMapper recommendationRuleMapper;
    private final ElasticSearchService elasticSearchService = new ElasticSearchService();

    @Autowired
    public BusinessService(ItdRestaurantMapper itdRestaurantMapper, TbdFlightInfoMapper tbdFlightInfoMapper,
                           RecommendationRuleMapper recommendationRuleMapper) {
        this.itdRestaurantMapper = itdRestaurantMapper;
        this.tbdFlightInfoMapper = tbdFlightInfoMapper;
        this.recommendationRuleMapper = recommendationRuleMapper;
    }

    @Override
    public JSONObject handle(String operation,JSONObject request) {
        String success = null;

        switch (operation) {
            case "queryRestaurant":
                success = getRestaurant(request);
                break;
            case "querySetMeal":
                success = RecommendationService.getSetMeal(request);
                break;
            case "queryBrandRestaurant":
                success = RecommendationService.getBrandRestaurant(request);
                break;
            case "queryCuisine":
                success = RecommendationService.getCuisine(request);
                break;
            case "queryLounge":
                success = RecommendationService.getLounge(request);
                break;
            case "queryShop":
                success = RecommendationService.getShop(request);
                break;
            case "queryBannerArticle":
                success = RecommendationService.getBannerArticle(request);
                break;
            case "queryHomepageArticle":
                success = RecommendationService.getHomepageArticle(request);
                break;
            case "queryPageArticle":
                success = RecommendationService.getPageArticle(request);
                break;
            case "queryType":
                success = RecommendationService.getType(request);
                break;
            case "queryProduct":
                success = RecommendationService.getProduct(request);
                break;
            case "queryTypeProduct":
                success = RecommendationService.getProduct(request);
                break;
            case "createIndex":
                success = createIndex(request);
                break;
            case "createIndexMapping":
                success = createIndexMapping(request);
                break;
            case "getIndexMapping":
                success = getIndexMapping(request);
                break;
            case "index":
                success = index(request);
                break;
            case "termQuery":
                success = termQuery(request);
                break;
            case "termsQuery":
                success = termsQuery(request);
                break;
            case "wildcardQuery":
                success = wildcardQuery(request);
                break;
            case "prefixQuery":
                success = prefixQuery(request);
                break;
            case "rangeQuery":
                success = rangeQuery(request);
                break;
            case "queryString":
                success = queryString(request);
                break;
            case "count":
                success = count(request);
                break;
            case "getById":
                success = getById(request);
                break;
            case "deleteIndexDocument":
                success = deleteIndexDocument(request);
                break;
            case "deleteIndex":
                success = deleteIndex(request);
                break;
            default:
                break;
        }
        return JSON.parseObject(success);
    }

    /**
     * 推荐引擎 - 推荐餐厅
     * @para userId 用户id
     * @para flightNo 航班号
     * @para position 用户位置信息
     * @para restaurantInfo 排序前餐厅列表
     * @return 排序后餐厅列表
     */
    public String getRestaurant(final JSONObject queryRestaurantJson){

        try {
            if (queryRestaurantJson.containsKey("userId")
                    && queryRestaurantJson.containsKey("restaurantInfo")) {

                int positionFlag = 1, flightInfoFlag = 1, userBehaviorFlag = 1;
                if (!queryRestaurantJson.containsKey("position") ||
                        queryRestaurantJson.getString("position").equals(null) ||
                        queryRestaurantJson.getString("position").equals("")) {
                    positionFlag = 0; //没有位置信息，位置推荐无效
                }
                if (!queryRestaurantJson.containsKey("flightNo") ||
                        !queryRestaurantJson.containsKey("flightDate") ||
                        queryRestaurantJson.getString("flightNo").equals(null) ||
                        queryRestaurantJson.getString("flightNo").equals("") ||
                        queryRestaurantJson.getString("flightDate").equals("") ||
                        queryRestaurantJson.getString("flightDate").equals(null)) {
                    flightInfoFlag = 0; //没有航班信息或航班信息不全，航班推荐无效
                }

                List<Map> list = JSON.parseArray(queryRestaurantJson.getString("restaurantInfo"), Map.class);
                if (list.size() == 0) {
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display()));
                }
                for (Map<String, Object> attribute : list) {
                    if (!attribute.containsKey("restaurantCode") || !attribute.containsKey("restaurantWeight")
                            || attribute.get("restaurantCode").equals(null) || attribute.get("restaurantWeight").equals(null)
                            || attribute.get("restaurantCode").equals("") || attribute.get("restaurantWeight").equals("")) {
                        return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                    }
                }
                ComparatorListSort comparatorListSort = new ComparatorListSort("restaurantWeight");
                Collections.sort(list,comparatorListSort);

                //过滤得到有合作的优惠餐厅
                Map<String,Object> paramRestaurant = new HashMap<>();
                StringBuffer code = new StringBuffer();
                for(Map<String, Object> attribute : list) {
                    code.append("'").append(attribute.get("restaurantCode").toString()).append("'").append(",");
                }
                paramRestaurant.put("code", code.substring(0,code.length() - 1));
                List<ItdRestaurant> itdRestaurantList = itdRestaurantMapper.getRestaurantListByCode(paramRestaurant);
                if (itdRestaurantList.size() == 0) {
                    LZResult<List<Map>> result = new LZResult<>(list);
                    return JSON.toJSONString(result);
                }

                //根据地理位置确定推荐规则
                Integer positionRuleWeight = 0;
                JSONObject positionRuleContent = null;
                if (positionFlag == 1) {
                    String position = queryRestaurantJson.getString("position");
                    Map<String,Object> paramPositionRule = new HashMap<>();
                    if (position.equals("机场内")) {
                        paramPositionRule.put("ruleName", "用户定位在安检后");
                    } else if (position.equals("机场外")) {
                        paramPositionRule.put("ruleName", "用户定位在安检前");
                    } else if (position.equals("到达区")) {
                        paramPositionRule.put("ruleName", "用户定位在到达区");
                    }
                    if (paramPositionRule.isEmpty()) {
                        positionFlag = 0;
                    } else {
                        RecommendationRule positionRule = recommendationRuleMapper.getRecommendationRule(paramPositionRule);
                        if (positionRule.equals(null)) {
                            positionFlag = 0;
                        } else {
                            positionRuleContent = JSONObject.parseObject(positionRule.getRuleContent());
                            positionRuleWeight = positionRule.getTypeWeight();
                        }
                    }
                }


                //根据航班信息确定推荐规则
                Integer flightInfoRuleWeight = 0;
                JSONObject flightInfoRuleContent = null;
                if (flightInfoFlag == 1) {

                    //获取航班信息
                    Map<String,Object> paramFlightInfo = new HashMap<>();
                    paramFlightInfo.put("flightNo", queryRestaurantJson.getString("flightNo"));
                    paramFlightInfo.put("flightDate", queryRestaurantJson.getString("flightDate"));
                    TbdFlightInfo tbdFlightInfo = tbdFlightInfoMapper.getFlightInfoByFlightNo(paramFlightInfo);

                    //确定推荐规则
                    if (!tbdFlightInfo.equals(null)) {
                        Date currentDate = new Date();
                        Map<String,Object> paramFlightInfoRule = new HashMap<>();
                        if (tbdFlightInfo.getFlightStatus().equals("到达")) {
                            Date flightArriveDate = tbdFlightInfo.getArriveTimeActual();
                            int timeInterval = (int) (currentDate.getTime() - flightArriveDate.getTime())/(1000 * 60 * 60);
                            if (timeInterval < 3) {
                                paramFlightInfoRule.put("ruleName", "航班到达后3小时内");
                            } else {
                                LZResult<List<Map>> result = new LZResult<>(list);
                                return JSON.toJSONString(result);
                            }
                        } else {
                            Date flightDepartDate = tbdFlightInfo.getDepartTimePlan();
                            int timeInterval = (int) (flightDepartDate.getTime() - currentDate.getTime())/(1000 * 60 * 60);
                            if (timeInterval < 1) {
                                paramFlightInfoRule.put("ruleName", "航班起飞1小时内");
                            } else {
                                paramFlightInfoRule.put("ruleName", "航班起飞1小时前");
                            }
                        }
                        RecommendationRule flightInfoRule = recommendationRuleMapper.getRecommendationRule(paramFlightInfoRule);
                        if (!flightInfoRule.equals(null)) {
                            flightInfoRuleContent = JSONObject.parseObject(flightInfoRule.getRuleContent());
                            flightInfoRuleWeight = flightInfoRule.getTypeWeight();
                        } else {
                            flightInfoFlag = 0;
                        }

                    } else {
                        flightInfoFlag = 0;
                    }

                }

                //获取用户行为规则
                Integer userBehaviorRuleWeight = 0;
                JSONObject userBehaviorRuleContent = null;
                Map<String,Object> paramUserBehaviorRule = new HashMap<>();
                paramUserBehaviorRule.put("ruleName", "用户app行为");
                RecommendationRule userBehaviorRule = recommendationRuleMapper.getRecommendationRule(paramUserBehaviorRule);
                if (userBehaviorRule.equals(null)) {
                    userBehaviorFlag = 0;
                } else {
                    userBehaviorRuleContent = JSONObject.parseObject(userBehaviorRule.getRuleContent());
                    userBehaviorRuleWeight = userBehaviorRule.getTypeWeight();
                }

                //获取用户行为标签
                Map<String,Object> paramUserBehaviorLabel = new HashMap<>();
                paramUserBehaviorLabel.put("indexName", "user");
                paramUserBehaviorLabel.put("typeName", "userRest");
                paramUserBehaviorLabel.put("userId", queryRestaurantJson.getString("userId"));
                List<UserRest> userRestList = getUserBehaviorLabel(paramUserBehaviorLabel);
                if (userRestList.size() == 0) {
                    userBehaviorFlag = 0;
                }
                paramUserBehaviorLabel.put("typeName", "userTags");
                List<UserRest> userTagsList = getUserBehaviorLabel(paramUserBehaviorLabel);

                //用户行为数据归一化处理
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

                //给餐厅打分
                Double score = 0.0;
                for(ItdRestaurant itdRestaurant : itdRestaurantList) {
                    //计算地理位置得分
                    if (positionFlag == 1 && !itdRestaurant.getFdInspection().equals(null)) {
                        score += positionRuleWeight * positionRuleContent.getDouble(itdRestaurant.getFdInspection());
                    }

                    //计算航班信息得分
                    if (flightInfoFlag == 1 && !itdRestaurant.getFdInspection().equals(null)) {
                        score += flightInfoRuleWeight * flightInfoRuleContent.getDouble(itdRestaurant.getFdInspection());
                    }

                    //计算用户行为得分
                    if (userBehaviorFlag == 1) { //用户行为推荐有效
                        Double behaviorScore = 0.0;
                        for(UserRest userRest : userRestList) {
                            if (userRest.getRestaurantCode().equals(itdRestaurant.getFdCode())) {
                                behaviorScore += userRest.getConsumptionNum() * userBehaviorRuleContent.getDouble("consumptionNum");
                                behaviorScore += userRest.getCollectionNum() * userBehaviorRuleContent.getDouble("collectionNum");
                                behaviorScore += userRest.getCommentNum() * userBehaviorRuleContent.getDouble("commentNum");
                                behaviorScore += userRest.getAverageOrderAmount() * userBehaviorRuleContent.getDouble("averageOrderAmount");
                                behaviorScore += userRest.getUsageCounter() * userBehaviorRuleContent.getDouble("usageCounter");
                                behaviorScore += userRest.getBrowseNum() * userBehaviorRuleContent.getDouble("browseNum");
                                behaviorScore += userRest.getBrowseHours() * userBehaviorRuleContent.getDouble("browseHours");
                                if (userRest.getMultitimeConsumption()) {
                                    behaviorScore += userBehaviorRuleContent.getDouble("isMultitimeConsumption");
                                }
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
                    for(Map<String, Object> attribute : list) {
                        if (itdRestaurant.getFdCode().equals(attribute.get("restaurantCode").toString())) {
                            Double tempScore = Integer.parseInt(attribute.get("restaurantWeight").toString()) * itdRestaurant.getScore();
                            Integer resultScore = tempScore.intValue();
                            attribute.put("restaurantWeight", resultScore.toString());
                        }
                    }
                }
                Collections.sort(list,comparatorListSort);
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

    /**
     * 搜索文档 - 单值完全匹配查询
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @return
     */
    public List<UserRest> getUserBehaviorLabel(Map<String, Object> param) {

        try {
            elasticSearchService.setUp();
            List<UserRest> result = elasticSearchService.termQuery(param);
            elasticSearchService.tearDown();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 创建索引
     * @para indexName 索引名称
     * @return
     */
    public String createIndex(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            elasticSearchService.createIndex(param);
            elasticSearchService.tearDown();
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * Put映射
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @return
     */
    public String createIndexMapping(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            elasticSearchService.createIndexMapping(param);
            elasticSearchService.tearDown();
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * Get映射
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @return
     */
    public String getIndexMapping(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            LZResult<String> result = new LZResult<>(elasticSearchService.getIndexMapping(param));
            elasticSearchService.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 索引文档
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @return
     */
    public String index(JSONObject request) {

        try {
            elasticSearchService.setUp();
            List<Object> userList = new ArrayList<>();
            JSONArray jsonArray = JSON.parseArray(request.getString("data"));
            for (int i = 0; i < jsonArray.size(); i++) {
                UserRest userRest = JSON.toJavaObject(jsonArray.getJSONObject(i), UserRest.class);
                userList.add(userRest);
            }
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            elasticSearchService.index(param, userList);
            elasticSearchService.tearDown();
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 单值完全匹配查询
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @return
     */
    public String termQuery(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            param.put("userId", request.getString("userId"));
            LZResult<List<UserRest>> result = new LZResult<>(elasticSearchService.termQuery(param));
            elasticSearchService.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 多值完全匹配查询
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @return
     */
    public String termsQuery(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            param.put("name", request.getString("name"));
            LZResult<List<User>> result = new LZResult<>(elasticSearchService.termsQuery(param));
            elasticSearchService.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 通配符和正则表达式查询
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @return
     */
    public String wildcardQuery(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            LZResult<List<UserRest>> result = new LZResult<>(elasticSearchService.wildcardQuery(param));
            elasticSearchService.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 前缀查询
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @return
     */
    public String prefixQuery(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            LZResult<List<UserRest>> result = new LZResult<>(elasticSearchService.prefixQuery(param));
            elasticSearchService.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 区间查询
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @return
     */
    public String rangeQuery(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            LZResult<List<User>> result = new LZResult<>(elasticSearchService.rangeQuery(param));
            elasticSearchService.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 文本检索
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @return
     */
    public String queryString(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            LZResult<List<User>> result = new LZResult<>(elasticSearchService.queryString(param));
            elasticSearchService.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 统计总数
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @return
     */
    public String count(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            LZResult<Double> result = new LZResult<>(elasticSearchService.count(param));
            elasticSearchService.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 通过id查询
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @return
     */
    public String getById(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            param.put("id", request.getString("id"));
            LZResult<UserRest> result = new LZResult<>(elasticSearchService.get(param));
            elasticSearchService.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 删除索引文档 - 依据id
     * @para indexName 索引名称
     * @para typeName 索引类型
     * @para id 文档id
     * @return
     */
    public String deleteIndexDocument(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            param.put("id", request.getString("id"));
            elasticSearchService.deleteIndexDocument(param);
            elasticSearchService.tearDown();
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 删除索引 - 依据索引名称
     * @para indexName 索引名称
     * @return
     */
    public String deleteIndex(JSONObject request) {

        try {
            elasticSearchService.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            elasticSearchService.deleteIndex(param);
            elasticSearchService.tearDown();
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

}
