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

    private final HybridRecommendationController hybridRecommendationController;

    @Autowired
    public BusinessService(HybridRecommendationController hybridRecommendationController) {
        this.hybridRecommendationController = hybridRecommendationController;
    }

    @Override
    public JSONObject handle(String operation,JSONObject request) {
        String success = null;

        switch (operation) {
            case "queryRestaurant":
                success = hybridRecommendationController.getRestaurant(request);
                break;
            case "queryLounge":
                success = hybridRecommendationController.getLounge(request);
                break;
            case "querySetMeal":
                success = hybridRecommendationController.getSetMeal(request);
                break;
            case "queryBrandRestaurant":
                success = hybridRecommendationController.getBrandRestaurant(request);
                break;
            case "queryCuisine":
                success = hybridRecommendationController.getCuisine(request);
                break;
            case "queryShop":
                success = hybridRecommendationController.getShop(request);
                break;
            case "queryBannerArticle":
                success = hybridRecommendationController.getBannerArticle(request);
                break;
            case "queryHomepageArticle":
                success = hybridRecommendationController.getHomepageArticle(request);
                break;
            case "queryPageArticle":
                success = hybridRecommendationController.getPageArticle(request);
                break;
            case "queryType":
                success = hybridRecommendationController.getType(request);
                break;
            case "queryProduct":
                success = hybridRecommendationController.getProduct(request);
                break;
            case "queryTypeProduct":
                success = hybridRecommendationController.getProduct(request);
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
