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

import com.adatafun.recommendation.mapper.PermissionMapper;
import com.adatafun.recommendation.model.Permission;
import com.adatafun.recommendation.model.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.server.business.IBusinessService;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * PermissionMapper.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/05/2017.
 */
@Service
public class BusinessService implements IBusinessService {

    private final PermissionMapper permissionMapper;
    private final ElasticSearchService elasticSearchService = new ElasticSearchService();

    @Autowired
    public BusinessService(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public JSONObject handle(String operation,JSONObject request) {
        String success = null;

        switch (operation) {
            case "view":
                success = view(request);
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
            case "queryRestaurant":
                success = RecommendationService.getRestaurant(request);
                //success = view(request);
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
            case "queryTypeProduct":
                success = RecommendationService.getTypeProduct(request);
                break;
            default:
                break;
        }
        return JSON.parseObject(success);
    }

    /**
     * 权限详情 - 根据permissionId查询
     * @para permissionId 权限id
     * @return 权限详情
     */
    public String view(JSONObject request) {

        try {
            Map<String, Object> param = new HashMap<>();
            param.put("airportCode", request.getString("airportCode"));
            param.put("permissionId", request.getLong("permissionId"));
            LZResult<Permission> result = new LZResult<>(permissionMapper.selectById(param));
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
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
                User user = JSON.toJavaObject(jsonArray.getJSONObject(i), User.class);
                user.setBirth(new Date());
                userList.add(user);
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
            param.put("name", request.getString("name"));
            LZResult<List<User>> result = new LZResult<>(elasticSearchService.termQuery(param));
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
            LZResult<List<User>> result = new LZResult<>(elasticSearchService.wildcardQuery(param));
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
            LZResult<List<User>> result = new LZResult<>(elasticSearchService.prefixQuery(param));
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
            LZResult<User> result = new LZResult<>(elasticSearchService.get(param));
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
