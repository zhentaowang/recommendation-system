package com.adatafun.recommendation.service;

import com.adatafun.recommendation.model.User;
import com.adatafun.recommendation.utils.ElasticSearch;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ElasticSearchService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
@Service
public class ElasticSearchService {

     private static ElasticSearch elasticSearch = new ElasticSearch();

    /**
     * 搜索文档 - 多条件完全匹配查询
     * @param param
     * indexName 索引名称
     * typeName 索引类型
     */
    static Boolean articleQuery(Map<String, Object> param) {

        try {
            elasticSearch.setUp();
            Boolean result = elasticSearch.articleQuery(param);
            elasticSearch.tearDown();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 搜索文档 - 多条件完全匹配查询
     * @param param
     * indexName 索引名称
     * typeName 索引类型
     */
    static User getUserPreference(Map<String, Object> param) {

        try {
            elasticSearch.setUp();
            User result = elasticSearch.getUserPreference(param);
            elasticSearch.tearDown();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 搜索文档 - 单值完全匹配查询
     * @param param
     * indexName 索引名称
     * typeName 索引类型
     */
    static List<User> getUserBehaviorLabel(Map<String, Object> param) {

        try {
            elasticSearch.setUp();
            List<User> result = elasticSearch.termQuery(param);
            elasticSearch.tearDown();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 搜索文档 - 多值完全匹配查询
     * @param param
     * indexName 索引名称
     * typeName 索引类型
     */
    static List<User> getRestaurantPrivilegeLabel(Map<String, Object> param, List<Map<String, Object>> productList) {

        try {
            elasticSearch.setUp();
            List<User> result = elasticSearch.privilegeLabelQuery(param, productList);
            elasticSearch.tearDown();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 创建索引
     * @param request
     * indexName 索引名称
     */
    public static String createIndex(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            elasticSearch.createIndex(param);
            elasticSearch.tearDown();
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * Put映射
     * @param request
     * indexName 索引名称
     * typeName 索引类型
     */
    public static String createIndexMapping(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            elasticSearch.createIndexMapping(param);
            elasticSearch.tearDown();
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * Get映射
     * @param request
     * indexName 索引名称
     * typeName 索引类型
     */
    public static String getIndexMapping(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            LZResult<String> result = new LZResult<>(elasticSearch.getIndexMapping(param));
            elasticSearch.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 索引文档
     * @param request
     * indexName 索引名称
     * typeName 索引类型
     */
    public static String index(JSONObject request) {

        try {
            elasticSearch.setUp();
            List<Object> userList = new ArrayList<>();
            JSONArray jsonArray = JSON.parseArray(request.getString("data"));
            for (int i = 0; i < jsonArray.size(); i++) {
                User user = JSON.toJavaObject(jsonArray.getJSONObject(i), User.class);
                userList.add(user);
            }
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            elasticSearch.index(param, userList);
            elasticSearch.tearDown();
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 单值完全匹配查询
     * @param request
     * indexName 索引名称
     * typeName 索引类型
     */
    public static String termQuery(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            param.put("userId", request.getString("userId"));
            LZResult<List<User>> result = new LZResult<>(elasticSearch.termQuery(param));
            elasticSearch.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 多值完全匹配查询
     * @param request
     * indexName 索引名称
     * typeName 索引类型
     */
    public static String termsQuery(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            param.put("name", request.getString("name"));
            LZResult<List<User>> result = new LZResult<>(elasticSearch.termsQuery(param));
            elasticSearch.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 通配符和正则表达式查询
     * @param request
     * indexName 索引名称
     * typeName 索引类型
     */
    public static String wildcardQuery(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            LZResult<List<User>> result = new LZResult<>(elasticSearch.wildcardQuery(param));
            elasticSearch.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 前缀查询
     * @param request
     * indexName 索引名称
     * typeName 索引类型
     */
    public static String prefixQuery(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            LZResult<List<User>> result = new LZResult<>(elasticSearch.prefixQuery(param));
            elasticSearch.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 区间查询
     * @param request
     * indexName 索引名称
     * typeName 索引类型
     */
    public static String rangeQuery(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            LZResult<List<User>> result = new LZResult<>(elasticSearch.rangeQuery(param));
            elasticSearch.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 文本检索
     * @param request
     * indexName 索引名称
     * typeName 索引类型
     */
    public static String queryString(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            LZResult<List<User>> result = new LZResult<>(elasticSearch.queryString(param));
            elasticSearch.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 统计总数
     * @param request
     * indexName 索引名称
     * typeName 索引类型
     */
    public static String count(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            LZResult<Double> result = new LZResult<>(elasticSearch.count(param));
            elasticSearch.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 搜索文档 - 通过id查询
     * @param request
     * indexName 索引名称
     * typeName 索引类型
     */
    public static String getById(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            param.put("id", request.getString("id"));
            LZResult<User> result = new LZResult<>(elasticSearch.get(param));
            elasticSearch.tearDown();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 删除索引文档 - 依据id
     * @param request
     * indexName 索引名称
     * typeName 索引类型
     * id 文档id
     */
    public static String deleteIndexDocument(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            param.put("typeName", request.getString("typeName"));
            param.put("id", request.getString("id"));
            elasticSearch.deleteIndexDocument(param);
            elasticSearch.tearDown();
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }

    /**
     * 删除索引 - 依据索引名称
     * @param request  indexName 索引名称
     */
    public static String deleteIndex(JSONObject request) {

        try {
            elasticSearch.setUp();
            Map<String, Object> param = new HashMap<>();
            param.put("indexName", request.getString("indexName"));
            elasticSearch.deleteIndex(param);
            elasticSearch.tearDown();
            return JSON.toJSONString(LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display()));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display()));
        }

    }
}
