package com.adatafun.recommendation.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;

import javax.ws.rs.POST;

/**
 * Created by yanggf on 2017/9/4.
 */
public class RecommendationService {
    @POST
    public static String getSetMeal(final JSONObject querySetMealJson){
        try {
            JSONArray setMealInfo = querySetMealJson.getJSONArray("setMealInfo");
            LZResult<JSONArray> result = new LZResult<>(setMealInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    @POST
    public static String getBrandRestaurant(final JSONObject queryBrandRestaurantJson){
        try {
            JSONArray brandRestaurantInfo = queryBrandRestaurantJson.getJSONArray("brandRestaurantInfo");
            LZResult<JSONArray> result = new LZResult<>(brandRestaurantInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    @POST
    public static String getCuisine(final JSONObject queryCuisineJson){
        try {
            JSONArray cuisineInfo = queryCuisineJson.getJSONArray("cuisineInfo");
            LZResult<JSONArray> result = new LZResult<>(cuisineInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    @POST
    public static String getLounge(final JSONObject queryLoungeJson){
        try {
            JSONArray loungeInfo = queryLoungeJson.getJSONArray("loungeInfo");
            LZResult<JSONArray> result = new LZResult<>(loungeInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }


    @POST
    public static String getShop(final JSONObject queryShopJson){
        try {
            JSONArray shopInfo = queryShopJson.getJSONArray("shopInfo");
            LZResult<JSONArray> result = new LZResult<>(shopInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }

    @POST
    public static String getBannerArticle(final JSONObject queryBannerArticleJson){
        try {
            JSONArray bannerArticleInfo = queryBannerArticleJson.getJSONArray("bannerArticleInfo");
            LZResult<JSONArray> result = new LZResult<>(bannerArticleInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }


    @POST
    public static String getHomepageArticle(final JSONObject queryHomepageArticleJson){
        try {
            JSONArray homepageArticleInfo = queryHomepageArticleJson.getJSONArray("homepageArticleInfo");
            LZResult<JSONArray> result = new LZResult<>(homepageArticleInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }


    @POST
    public static String getPageArticle(final JSONObject queryPageArticleJson){
        try {
            JSONArray pageArticleInfo = queryPageArticleJson.getJSONArray("pageArticleInfo");
            LZResult<JSONArray> result = new LZResult<>(pageArticleInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }


    @POST
    public static String getType(final JSONObject queryTypeJson){
        try {
            JSONArray typeInfo = queryTypeJson.getJSONArray("typeInfo");
            LZResult<JSONArray> result = new LZResult<>(typeInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }
    }


    @POST
    public static String getProduct(final JSONObject queryTypeProductJson){
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
