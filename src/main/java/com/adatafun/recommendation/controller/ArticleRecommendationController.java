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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ArticleRecommendationController.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/18.
 */
@Component
public class ArticleRecommendationController {

    private final TbdBannerService tbdBannerService;
    private final TbdBellesLettresService tbdBellesLettresService;
    private final RecommendationBaseRuleService recommendationBaseRuleService;
    private final RecommendationBaseContentService recommendationBaseContentService;
    private final static Logger logger = LoggerFactory.getLogger(ArticleRecommendationController.class);

    @Autowired
    public ArticleRecommendationController(TbdBannerService tbdBannerService,
                                           TbdBellesLettresService tbdBellesLettresService,
                                          RecommendationBaseRuleService recommendationBaseRuleService,
                                          RecommendationBaseContentService recommendationBaseContentService) {
        this.tbdBannerService = tbdBannerService;
        this.tbdBellesLettresService = tbdBellesLettresService;
        this.recommendationBaseRuleService = recommendationBaseRuleService;
        this.recommendationBaseContentService = recommendationBaseContentService;
    }

    public String getBannerArticle(final JSONObject queryBannerArticleJson){
        try {
            if (queryBannerArticleJson.containsKey("userId")
                    && queryBannerArticleJson.containsKey("bannerArticleInfo")) {

                int userBehaviorFlag = 1;
                List<Map> list;
                Map<String, Object> detectionResult = new HashMap<>();
                ParameterDetection parameterDetection = new ParameterDetection(queryBannerArticleJson, detectionResult);

                //验证产品信息的完整性
                detectionResult = parameterDetection.verifyIntegrity("bannerArticleInfo", "bannerArticleWeight", "bannerArticleId");
                if (detectionResult.get("msg").equals(LZStatus.SUCCESS.display())) {
                    list = JSONArray.parseArray(JSONObject.toJSONString(detectionResult.get("list")), Map.class);
                } else {
                    logger.error("专题文章列表信息不全");
                    return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
                }

                //过滤得到banner文章
                List<Map<String,Object>> tbdBannerList = tbdBannerService.getBannerListById(list);
                if (tbdBannerList.size() == 0) {
                    return JSON.toJSONString(new LZResult<>(list));
                }

                //获取用户行为规则
                Map<String, Object> resultUserBehaviorRule = recommendationBaseRuleService.getUserBehaviorRule(userBehaviorFlag, "banner行为");

                //获取用户行为标签
                Map<String,Object> paramUserBehavior = new HashMap<>();
                paramUserBehavior.put("userBehaviorFlag", resultUserBehaviorRule.get("userBehaviorFlag"));
                paramUserBehavior.put("userId", queryBannerArticleJson.getString("userId"));
                paramUserBehavior.put("indexName", "user");
                paramUserBehavior.put("typeName", "userBanner");
                Map<String, Object> resultUserBehavior = recommendationBaseContentService.getUserBehavior(paramUserBehavior);
                resultUserBehavior.put("userBehaviorRule", resultUserBehaviorRule.get("userBehaviorRule"));

                //用户行为数据归一化处理
                DataProcessing dataProcessing = new DataProcessing();
                Map<String, Object> userBehaviorAfterNorm = dataProcessing.normalizationBaseAtan(resultUserBehavior);

                //给文章打分
                List<Map<String,Object>> productList = dataProcessing.bannerWeightCalculation(userBehaviorAfterNorm, tbdBannerList);

                //排序
                Map<String,Object> paramProductSort = new HashMap<>();
                paramProductSort.put("orderByKey", "bannerArticleWeight");
                paramProductSort.put("productCode", "bannerArticleId");
                list = dataProcessing.productSort(paramProductSort, list, productList);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                logger.error("缺用户id或者专题文章列表");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getNewSubjectArticle(final JSONObject querySubjectArticleJson){
        try {
            if (querySubjectArticleJson.containsKey("userId")
                    && querySubjectArticleJson.containsKey("label")) {

                //根据标签给用户推荐文章
                String label = querySubjectArticleJson.getString("label");
                List<Map<String, Object>> list = tbdBellesLettresService.getSubjectArticleListExpectSoleLabel(label);

                //验证用户是否浏览过该文章
                Map<String,Object> paramArticleQuery = new HashMap<>();
                paramArticleQuery.put("userId", querySubjectArticleJson.getString("userId"));
                paramArticleQuery.put("indexName", "user");
                paramArticleQuery.put("typeName", "userArticle");
                list = recommendationBaseContentService.getNoLabelArticleByUser(paramArticleQuery, list);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                logger.error("缺用户id或者文章标签列表");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getPreferenceSubjectArticle(final JSONObject querySubjectArticleJson){
        try {
            if (querySubjectArticleJson.containsKey("userId")) {

                //获取用户偏好标签
                Map<String,Object> paramUserPreference = new HashMap<>();
                paramUserPreference.put("userId", querySubjectArticleJson.getString("userId"));
                paramUserPreference.put("indexName", "user");
                paramUserPreference.put("typeName", "userTags");
                User resultUserPreference = recommendationBaseContentService.getPreferenceSubjectArticle(paramUserPreference);

                //根据偏好给用户推荐文章
                List<Map<String, Object>> list = tbdBellesLettresService.getSubjectArticleListByLabel(resultUserPreference);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                logger.error("缺用户id");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    public String getSubjectArticleByLabel(final JSONObject querySubjectArticleJson){
        try {
            if (querySubjectArticleJson.containsKey("userId")
                    && querySubjectArticleJson.containsKey("label")) {

                //根据标签给用户推荐文章
                String label = querySubjectArticleJson.getString("label");
                List<Map<String, Object>> list = tbdBellesLettresService.getSubjectArticleListBySoleLabel(label);

                return JSON.toJSONString(new LZResult<>(list));
            } else {
                logger.error("缺用户id或者文章标签列表");
                return JSON.toJSONString(LXResult.build(LZStatus.DATA_TRANSFER_ERROR.value(), LZStatus.DATA_TRANSFER_ERROR.display()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throws Exception ", e);
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

}
