/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 wangzhentao@iairportcloud.com
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

package com.adatafun.recommendation.controller;

import com.adatafun.recommendation.service.ElasticSearchService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.server.business.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * BusinessService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
@Component
public class BusinessService implements IBusinessService {

    private final RestaurantRecommendationController restaurantRecommendationController;
    private final LoungeRecommendationController loungeRecommendationController;
    private final ShopRecommendationController shopRecommendationController;
    private final ArticleRecommendationController articleRecommendationController;
    private final HybridRecommendationController hybridRecommendationController;

    @Autowired
    public BusinessService(RestaurantRecommendationController restaurantRecommendationController,
                           LoungeRecommendationController loungeRecommendationController,
                           ShopRecommendationController shopRecommendationController,
                           ArticleRecommendationController articleRecommendationController,
                           HybridRecommendationController hybridRecommendationController) {
        this.restaurantRecommendationController = restaurantRecommendationController;
        this.loungeRecommendationController = loungeRecommendationController;
        this.shopRecommendationController = shopRecommendationController;
        this.articleRecommendationController = articleRecommendationController;
        this.hybridRecommendationController = hybridRecommendationController;
    }

    @Override
    public JSONObject handle(String operation,JSONObject request) {
        String success = null;

        switch (operation) {
            case "queryRestaurant":
                success = restaurantRecommendationController.getRestaurant(request);
                break;
            case "queryCustomizationRestaurant":
                success = restaurantRecommendationController.getCustomizationRestaurant(request);
                break;
            case "queryBrandRestaurant":
                success = restaurantRecommendationController.getBrandRestaurant(request);
                break;
            case "queryLounge":
                success = loungeRecommendationController.getLounge(request);
                break;
            case "queryCustomizationLounge":
                success = loungeRecommendationController.getCustomizationLounge(request);
                break;
            case "queryShop":
                success = shopRecommendationController.getShop(request);
                break;
            case "queryCustomizationShop":
                success = shopRecommendationController.getCustomizationShop(request);
                break;
            case "queryBannerArticle":
                success = articleRecommendationController.getBannerArticle(request);
                break;
            case "queryNewSubjectArticle":
                success = articleRecommendationController.getNewSubjectArticle(request);
                break;
            case "queryPreferenceSubjectArticle":
                success = articleRecommendationController.getPreferenceSubjectArticle(request);
                break;
            case "queryProduct":
                success = hybridRecommendationController.getProduct(request);
                break;
            case "queryHomepageArticle":
                success = articleRecommendationController.getHomepageArticle(request);
                break;
            case "queryPageArticle":
                success = articleRecommendationController.getPageArticle(request);
                break;
            case "querySetMeal":
                success = hybridRecommendationController.getSetMeal(request);
                break;
            case "queryCuisine":
                success = hybridRecommendationController.getCuisine(request);
                break;
            case "queryType":
                success = hybridRecommendationController.getType(request);
                break;
            case "createIndex":
                success = ElasticSearchService.createIndex(request);
                break;
            case "createIndexMapping":
                success = ElasticSearchService.createIndexMapping(request);
                break;
            case "getIndexMapping":
                success = ElasticSearchService.getIndexMapping(request);
                break;
            case "index":
                success = ElasticSearchService.index(request);
                break;
            case "termQuery":
                success = ElasticSearchService.termQuery(request);
                break;
            case "termsQuery":
                success = ElasticSearchService.termsQuery(request);
                break;
            case "wildcardQuery":
                success = ElasticSearchService.wildcardQuery(request);
                break;
            case "prefixQuery":
                success = ElasticSearchService.prefixQuery(request);
                break;
            case "rangeQuery":
                success = ElasticSearchService.rangeQuery(request);
                break;
            case "queryString":
                success = ElasticSearchService.queryString(request);
                break;
            case "count":
                success = ElasticSearchService.count(request);
                break;
            case "getById":
                success = ElasticSearchService.getById(request);
                break;
            case "deleteIndexDocument":
                success = ElasticSearchService.deleteIndexDocument(request);
                break;
            case "deleteIndex":
                success = ElasticSearchService.deleteIndex(request);
                break;
            default:
                break;
        }
        return JSON.parseObject(success);
    }

}
