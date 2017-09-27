package com.adatafun.recommendation.service;

import com.adatafun.recommendation.model.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.ws.rs.POST;
import java.util.*;

/**
 * Created by yanggf on 2017/9/4.
 */
public class RecommendationService {
    @POST
    public static String getRestaurant(final JSONObject queryRestaurantJson){
        int restaurantLength = 0;
        String[] restaurantCode = new String[10];
        Integer[] restaurantWeight = new Integer[10];
        try {
            String userId = queryRestaurantJson.getString("userId");
            String flightNo = queryRestaurantJson.getString("flightNo");
            String airportCode;
            String position;
            if ( queryRestaurantJson.containsKey("airportCode") ) {
                airportCode = queryRestaurantJson.getString("airportCode");
            }
            if ( queryRestaurantJson.containsKey("position") ) {
                position = queryRestaurantJson.getString("position");
            }
            JSONArray restaurantInfo = queryRestaurantJson.getJSONArray("restaurantInfo");
            //System.out.println(restaurantInfo);
            restaurantLength = restaurantInfo.size();
            for (int i = 0; i < restaurantInfo.size(); i++){
                JSONObject oj = restaurantInfo.getJSONObject(i);
                restaurantCode[i] = restaurantInfo.getJSONObject(i).getString("restaurantCode");
                restaurantWeight[i] = restaurantInfo.getJSONObject(i).getInteger("restaurantWeight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"internal server error\",\"status\":\"500\"}";
        }
        List<String> resultRestaurant = new ArrayList<String>();
        if ( restaurantLength > 0) {
            for (int i = 0; i < restaurantLength; i++){
                resultRestaurant.add(restaurantCode[i]);
            }
        }
        Map<String, List<String>> innerMap = new HashMap<String, List<String>>();
        innerMap.put("restaurant", resultRestaurant);
        RestaurantResult rstrst = new RestaurantResult();
        rstrst.setData(innerMap);
        rstrst.setStatus(200);
        rstrst.setMsg("ok");
        String result = JSON.toJSONString(rstrst);
        //System.out.println(result);
        return result;
    }
    @POST
    public static String getSetMeal(final JSONObject querySetMealJson){
        int setMealLength = 0;
        String[] setMealCode = new String[10];
        Integer[] setMealWeight = new Integer[10];
        try {
            String userId = querySetMealJson.getString("userId");
            String flightNo = querySetMealJson.getString("flightNo");
            String airportCode;
            String position;
            if ( querySetMealJson.containsKey("airportCode") ) {
                airportCode = querySetMealJson.getString("airportCode");
            }
            if ( querySetMealJson.containsKey("position") ) {
                position = querySetMealJson.getString("position");
            }
            JSONArray setMealInfo = querySetMealJson.getJSONArray("setMealInfo");
            //System.out.println(setMealInfo);
            setMealLength = setMealInfo.size();
            for (int i = 0; i < setMealInfo.size(); i++){
                JSONObject oj = setMealInfo.getJSONObject(i);
                setMealCode[i] = setMealInfo.getJSONObject(i).getString("setMealCode");
                setMealWeight[i] = setMealInfo.getJSONObject(i).getInteger("setMealWeight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"internal server error\",\"status\":\"500\"}";
        }
        List<String> resultSetMeal = new ArrayList<String>();
        if ( setMealLength > 0) {
            for (int i = 0; i < setMealLength; i++){
                resultSetMeal.add(setMealCode[i]);
            }
        }
        Map<String, List<String>> innerMap = new HashMap<String, List<String>>();
        innerMap.put("set_meal", resultSetMeal);
        SetMealResult rstrst = new SetMealResult();
        rstrst.setData(innerMap);
        rstrst.setStatus(200);
        rstrst.setMsg("ok");
        String result = JSON.toJSONString(rstrst);
        //System.out.println(result);
        return result;
    }

    @POST
    public static String getBrandRestaurant(final JSONObject queryBrandRestaurantJson){
        int brandRestaurantLength = 0;
        String[] brandRestaurantCode = new String[10];
        Integer[] brandRestaurantWeight = new Integer[10];
        try {
            String userId = queryBrandRestaurantJson.getString("userId");
            String flightNo = queryBrandRestaurantJson.getString("flightNo");
            String airportCode;
            String position;
            if ( queryBrandRestaurantJson.containsKey("airportCode") ) {
                airportCode = queryBrandRestaurantJson.getString("airportCode");
            }
            if ( queryBrandRestaurantJson.containsKey("position") ) {
                position = queryBrandRestaurantJson.getString("position");
            }
            JSONArray brandRestaurantInfo = queryBrandRestaurantJson.getJSONArray("brandRestaurantInfo");
            //System.out.println(brandRestaurantInfo);
            brandRestaurantLength = brandRestaurantInfo.size();
            for (int i = 0; i < brandRestaurantInfo.size(); i++){
                JSONObject oj = brandRestaurantInfo.getJSONObject(i);
                brandRestaurantCode[i] = brandRestaurantInfo.getJSONObject(i).getString("brandRestaurantCode");
                brandRestaurantWeight[i] = brandRestaurantInfo.getJSONObject(i).getInteger("brandRestaurantWeight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"internal server error\",\"status\":\"500\"}";
        }
        List<String> resultBrandRestaurant = new ArrayList<String>();
        if ( brandRestaurantLength > 0) {
            for (int i = 0; i < brandRestaurantLength; i++){
                resultBrandRestaurant.add(brandRestaurantCode[i]);
            }
        }
        Map<String, List<String>> innerMap = new HashMap<String, List<String>>();
        innerMap.put("brandRestaurant", resultBrandRestaurant);
        BrandRestaurantResult rstrst = new BrandRestaurantResult();
        rstrst.setData(innerMap);
        rstrst.setStatus(200);
        rstrst.setMsg("ok");
        String result = JSON.toJSONString(rstrst);
        //System.out.println(result);
        return result;
    }

    @POST
    public static String getCuisine(final JSONObject queryCuisineJson){
        int cuisineLength = 0;
        String[] cuisineCode = new String[10];
        Integer[] cuisineWeight = new Integer[10];
        try {
            String userId = queryCuisineJson.getString("userId");
            String flightNo = queryCuisineJson.getString("flightNo");
            String airportCode;
            String position;
            if ( queryCuisineJson.containsKey("airportCode") ) {
                airportCode = queryCuisineJson.getString("airportCode");
            }
            if ( queryCuisineJson.containsKey("position") ) {
                position = queryCuisineJson.getString("position");
            }
            JSONArray cuisineInfo = queryCuisineJson.getJSONArray("cuisineInfo");
            //System.out.println(cuisineInfo);
            cuisineLength = cuisineInfo.size();
            for (int i = 0; i < cuisineInfo.size(); i++){
                JSONObject oj = cuisineInfo.getJSONObject(i);
                cuisineCode[i] = cuisineInfo.getJSONObject(i).getString("cuisineCode");
                cuisineWeight[i] = cuisineInfo.getJSONObject(i).getInteger("cuisineWeight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"internal server error\",\"status\":\"500\"}";
        }
        List<String> resultCuisine = new ArrayList<String>();
        if ( cuisineLength > 0) {
            for (int i = 0; i < cuisineLength; i++){
                resultCuisine.add(cuisineCode[i]);
            }
        }
        Map<String, List<String>> innerMap = new HashMap<String, List<String>>();
        innerMap.put("cuisine", resultCuisine);
        CuisineResult rstrst = new CuisineResult();
        rstrst.setData(innerMap);
        rstrst.setStatus(200);
        rstrst.setMsg("ok");
        String result = JSON.toJSONString(rstrst);
        //System.out.println(result);
        return result;
    }

    @POST
    public static String getLounge(final JSONObject queryLoungeJson){
        int loungeLength = 0;
        String[] loungeCode = new String[10];
        Integer[] loungeWeight = new Integer[10];
        try {
            String userId = queryLoungeJson.getString("userId");
            String flightNo = queryLoungeJson.getString("flightNo");
            String airportCode;
            String position;
            if ( queryLoungeJson.containsKey("airportCode") ) {
                airportCode = queryLoungeJson.getString("airportCode");
            }
            if ( queryLoungeJson.containsKey("position") ) {
                position = queryLoungeJson.getString("position");
            }
            JSONArray loungeInfo = queryLoungeJson.getJSONArray("loungeInfo");
            //System.out.println(restaurantInfo);
            loungeLength = loungeInfo.size();
            for (int i = 0; i < loungeInfo.size(); i++){
                JSONObject oj = loungeInfo.getJSONObject(i);
                loungeCode[i] = loungeInfo.getJSONObject(i).getString("loungeCode");
                loungeWeight[i] = loungeInfo.getJSONObject(i).getInteger("loungeWeight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"internal server error\",\"status\":\"500\"}";
        }
        List<String> resultLounge = new ArrayList<String>();
        if ( loungeLength > 0) {
            for (int i = 0; i < loungeLength; i++){
                resultLounge.add(loungeCode[i]);
            }
        }
        Map<String, List<String>> innerMap = new HashMap<String, List<String>>();
        innerMap.put("lounge", resultLounge);
        LoungeResult rstrst = new LoungeResult();
        rstrst.setData(innerMap);
        rstrst.setStatus(200);
        rstrst.setMsg("ok");
        String result = JSON.toJSONString(rstrst);
        //System.out.println(result);
        return result;
    }


    @POST
    public static String getShop(final JSONObject queryShopJson){
        int shopLength = 0;
        String[] shopCode = new String[10];
        Integer[] shopWeight = new Integer[10];
        try {
            String userId = queryShopJson.getString("userId");
            String flightNo = queryShopJson.getString("flightNo");
            String airportCode;
            String position;
            if ( queryShopJson.containsKey("airportCode") ) {
                airportCode = queryShopJson.getString("airportCode");
            }
            if ( queryShopJson.containsKey("position") ) {
                position = queryShopJson.getString("position");
            }
            JSONArray shopInfo = queryShopJson.getJSONArray("shopInfo");
            //System.out.println(restaurantInfo);
            shopLength = shopInfo.size();
            for (int i = 0; i < shopInfo.size(); i++){
                JSONObject oj = shopInfo.getJSONObject(i);
                shopCode[i] = shopInfo.getJSONObject(i).getString("shopCode");
                shopWeight[i] = shopInfo.getJSONObject(i).getInteger("shopWeight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"internal server error\",\"status\":\"500\"}";
        }
        List<String> resultShop = new ArrayList<String>();
        if ( shopLength > 0) {
            for (int i = 0; i < shopLength; i++){
                resultShop.add(shopCode[i]);
            }
        }
        Map<String, List<String>> innerMap = new HashMap<String, List<String>>();
        innerMap.put("shop", resultShop);
        ShopResult rstrst = new ShopResult();
        rstrst.setData(innerMap);
        rstrst.setStatus(200);
        rstrst.setMsg("ok");
        String result = JSON.toJSONString(rstrst);
        //System.out.println(result);
        return result;
    }

    @POST
    public static String getBannerArticle(final JSONObject queryBannerArticleJson){
        int bannerArticleLength = 0;
        String[] bannerArticleId = new String[10];
        Integer[] bannerArticleWeight = new Integer[10];
        try {
            String userId = queryBannerArticleJson.getString("userId");
            String flightNo = queryBannerArticleJson.getString("flightNo");
            String airportCode;
            String position;
            if ( queryBannerArticleJson.containsKey("airportCode") ) {
                airportCode = queryBannerArticleJson.getString("airportCode");
            }
            if ( queryBannerArticleJson.containsKey("position") ) {
                position = queryBannerArticleJson.getString("position");
            }
            JSONArray bannerArticleInfo = queryBannerArticleJson.getJSONArray("bannerArticleInfo");
            //System.out.println(restaurantInfo);
            bannerArticleLength = bannerArticleInfo.size();
            for (int i = 0; i < bannerArticleInfo.size(); i++){
                JSONObject oj = bannerArticleInfo.getJSONObject(i);
                bannerArticleId[i] = bannerArticleInfo.getJSONObject(i).getString("bannerArticleId");
                bannerArticleWeight[i] = bannerArticleInfo.getJSONObject(i).getInteger("bannerArticleWeight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"internal server error\",\"status\":\"500\"}";
        }
        List<String> resultBannerArticle = new ArrayList<String>();
        if ( bannerArticleLength > 0) {
            for (int i = 0; i < bannerArticleLength; i++){
                resultBannerArticle.add(bannerArticleId[i]);
            }
        }
        Map<String, List<String>> innerMap = new HashMap<String, List<String>>();
        innerMap.put("bannerArticle", resultBannerArticle);
        BannerArticleResult rstrst = new BannerArticleResult();
        rstrst.setData(innerMap);
        rstrst.setStatus(200);
        rstrst.setMsg("ok");
        String result = JSON.toJSONString(rstrst);
        //System.out.println(result);
        return result;
    }


    @POST
    public static String getHomepageArticle(final JSONObject queryHomepageArticleJson){
        int homepageArticleLength = 0;
        String[] homepageArticleId = new String[10];
        Integer[] homepageArticleWeight = new Integer[10];
        try {
            String userId = queryHomepageArticleJson.getString("userId");
            String flightNo = queryHomepageArticleJson.getString("flightNo");
            String airportCode;
            String position;
            if ( queryHomepageArticleJson.containsKey("airportCode") ) {
                airportCode = queryHomepageArticleJson.getString("airportCode");
            }
            if ( queryHomepageArticleJson.containsKey("position") ) {
                position = queryHomepageArticleJson.getString("position");
            }
            JSONArray homepageArticleInfo = queryHomepageArticleJson.getJSONArray("homepageArticleInfo");
            //System.out.println(restaurantInfo);
            homepageArticleLength = homepageArticleInfo.size();
            for (int i = 0; i < homepageArticleInfo.size(); i++){
                JSONObject oj = homepageArticleInfo.getJSONObject(i);
                homepageArticleId[i] = homepageArticleInfo.getJSONObject(i).getString("homepageArticleId");
                homepageArticleWeight[i] = homepageArticleInfo.getJSONObject(i).getInteger("homepageArticleWeight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"internal server error\",\"status\":\"500\"}";
        }
        List<String> resultHomepageArticle = new ArrayList<String>();
        if ( homepageArticleLength > 0) {
            for (int i = 0; i < homepageArticleLength; i++){
                resultHomepageArticle.add(homepageArticleId[i]);
            }
        }
        Map<String, List<String>> innerMap = new HashMap<String, List<String>>();
        innerMap.put("homepageArticle", resultHomepageArticle);
        HomepageArticleResult rstrst = new HomepageArticleResult();
        rstrst.setData(innerMap);
        rstrst.setStatus(200);
        rstrst.setMsg("ok");
        String result = JSON.toJSONString(rstrst);
        //System.out.println(result);
        return result;
    }


    @POST
    public static String getPageArticle(final JSONObject queryPageArticleJson){
        int pageArticleLength = 0;
        String[] pageArticleId = new String[10];
        Integer[] pageArticleWeight = new Integer[10];
        try {
            String userId = queryPageArticleJson.getString("userId");
            String flightNo = queryPageArticleJson.getString("flightNo");
            String airportCode;
            String position;
            if ( queryPageArticleJson.containsKey("airportCode") ) {
                airportCode = queryPageArticleJson.getString("airportCode");
            }
            if ( queryPageArticleJson.containsKey("position") ) {
                position = queryPageArticleJson.getString("position");
            }
            JSONArray pageArticleInfo = queryPageArticleJson.getJSONArray("pageArticleInfo");
            //System.out.println(restaurantInfo);
            pageArticleLength = pageArticleInfo.size();
            for (int i = 0; i < pageArticleInfo.size(); i++){
                JSONObject oj = pageArticleInfo.getJSONObject(i);
                pageArticleId[i] = pageArticleInfo.getJSONObject(i).getString("pageArticleId");
                pageArticleWeight[i] = pageArticleInfo.getJSONObject(i).getInteger("pageArticleWeight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"internal server error\",\"status\":\"500\"}";
        }
        List<String> resultPageArticle = new ArrayList<String>();
        if ( pageArticleLength > 0) {
            for (int i = 0; i < pageArticleLength; i++){
                resultPageArticle.add(pageArticleId[i]);
            }
        }
        Map<String, List<String>> innerMap = new HashMap<String, List<String>>();
        innerMap.put("pageArticle", resultPageArticle);
        PageArticleResult rstrst = new PageArticleResult();
        rstrst.setData(innerMap);
        rstrst.setStatus(200);
        rstrst.setMsg("ok");
        String result = JSON.toJSONString(rstrst);
        //System.out.println(result);
        return result;
    }


    @POST
    public static String getType(final JSONObject queryTypeJson){
        int typeLength = 0;
        String[] typeId = new String[10];
        Integer[] typeWeight = new Integer[10];
        try {
            String userId = queryTypeJson.getString("userId");
            String flightNo = queryTypeJson.getString("flightNo");
            String airportCode;
            String position;
            if ( queryTypeJson.containsKey("airportCode") ) {
                airportCode = queryTypeJson.getString("airportCode");
            }
            if ( queryTypeJson.containsKey("position") ) {
                position = queryTypeJson.getString("position");
            }
            JSONArray typeInfo = queryTypeJson.getJSONArray("typeInfo");
            //System.out.println(restaurantInfo);
            typeLength = typeInfo.size();
            for (int i = 0; i < typeInfo.size(); i++){
                JSONObject oj = typeInfo.getJSONObject(i);
                typeId[i] = typeInfo.getJSONObject(i).getString("typeId");
                typeWeight[i] = typeInfo.getJSONObject(i).getInteger("typeWeight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"internal server error\",\"status\":\"500\"}";
        }
        List<String> resultType = new ArrayList<String>();
        if ( typeLength > 0) {
            for (int i = 0; i < typeLength; i++){
                resultType.add(typeId[i]);
            }
        }
        Map<String, List<String>> innerMap = new HashMap<String, List<String>>();
        innerMap.put("type", resultType);
        TypeResult rstrst = new TypeResult();
        rstrst.setData(innerMap);
        rstrst.setStatus(200);
        rstrst.setMsg("ok");
        String result = JSON.toJSONString(rstrst);
        //System.out.println(result);
        return result;
    }


    @POST
    public static String getProduct(final JSONObject queryTypeProductJson){
        int typeProductLength = 0;
        String[] typeProductCode = new String[10];
        Integer[] typeProductWeight = new Integer[10];
        try {
            String userId = queryTypeProductJson.getString("userId");
            String flightNo = queryTypeProductJson.getString("flightNo");
            String airportCode;
            String position;
            if ( queryTypeProductJson.containsKey("airportCode") ) {
                airportCode = queryTypeProductJson.getString("airportCode");
            }
            if ( queryTypeProductJson.containsKey("position") ) {
                position = queryTypeProductJson.getString("position");
            }
            JSONArray typeProductInfo = queryTypeProductJson.getJSONArray("typeProductInfo");
            //System.out.println(restaurantInfo);
            typeProductLength = typeProductInfo.size();
            for (int i = 0; i < typeProductInfo.size(); i++){
                JSONObject oj = typeProductInfo.getJSONObject(i);
                typeProductCode[i] = typeProductInfo.getJSONObject(i).getString("typeProductCode");
                typeProductWeight[i] = typeProductInfo.getJSONObject(i).getInteger("typeProductWeight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"internal server error\",\"status\":\"500\"}";
        }
        Map<String,String> resultTypeProduct = new HashMap<>();
        if ( typeProductLength > 0) {
            for (int i = 0; i < typeProductLength; i++){
                resultTypeProduct.put("loungeCode",typeProductCode[i]);
            }
        }
        Map<String, Map<String,String>> innerMap = new HashMap<String, Map<String,String>>();
        innerMap.put("typeProduct", resultTypeProduct);
        TypeProductResult rstrst = new TypeProductResult();
        rstrst.setData(innerMap);
        rstrst.setStatus(200);
        rstrst.setMsg("ok");
        String result = JSON.toJSONString(rstrst);
        //System.out.println(result);
        return result;
    }




}
