package com.adatafun.recommendation.utils;

import com.adatafun.recommendation.model.User;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.SearchResult;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ElasticSearch.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
public class ElasticSearch {

    private JestService jestService;
    private JestClient jestClient;

    @Before
    public void setUp() throws Exception {

        jestService = new JestService();
        jestClient = jestService.getJestClient();
    }

    @After
    public void tearDown() throws Exception {

        jestService.closeJestClient(jestClient);
    }

    public boolean createIndex(Map<String, Object> param) throws Exception {

        return jestService.createIndex(jestClient, param.get("indexName").toString());

    }

    public boolean createIndexMapping(Map<String, Object> param) throws Exception {

//        String source = "{\"" + param.get("typeName").toString() + "\":{\"properties\":{"
//                + "\"id\":{\"type\":\"integer\"}"
//                + ",\"name\":{\"type\":\"string\",\"index\":\"not_analyzed\"}"
//                + ",\"birth\":{\"type\":\"date\",\"format\":\"strict_date_optional_time||epoch_millis\"}"
//                + "}}}";
        String source = "{\"" + param.get("typeName").toString() + "\":{\"properties\":{"
                + "\"id\":{\"type\":\"string\"}"
                + "\"userId\":{\"type\":\"string\",\"index\":\"not_analyzed\"}"
                + ",\"restaurantCode\":{\"type\":\"string\",\"index\":\"not_analyzed\"}"
                + ",\"consumptionNum\":{\"type\":\"integer\",\"index\":\"not_analyzed\"}"
                + ",\"collectionNum\":{\"type\":\"integer\",\"index\":\"not_analyzed\"}"
                + ",\"commentNum\":{\"type\":\"integer\",\"index\":\"not_analyzed\"}"
                + ",\"multiTimeConsumption\":{\"type\":\"boolean\",\"index\":\"not_analyzed\"}"
                + ",\"perCustomerTransaction\":{\"type\":\"string\",\"index\":\"not_analyzed\"}"
                + ",\"averageOrderAmount\":{\"type\":\"double\",\"index\":\"not_analyzed\"}"
                + ",\"restaurantPreferences\":{\"type\":\"string\",\"index\":\"not_analyzed\"}"
                + "}}}";
        return jestService.createIndexMapping(jestClient, param.get("indexName").toString(), param.get("typeName").toString(), source);

    }

    public String getIndexMapping(Map<String, Object> param) throws Exception {

        return jestService.getIndexMapping(jestClient, param.get("indexName").toString(), param.get("typeName").toString());

    }

    public boolean index(Map<String, Object> param, List<Object> userList) throws Exception {

        return jestService.index(jestClient, param.get("indexName").toString(), param.get("typeName").toString(), userList);

    }

    public List<User> termQuery(Map<String, Object> param) throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder;//单值完全匹配查询
        queryBuilder = QueryBuilders
                .termQuery("userId", param.get("userId").toString());
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(jestClient, param.get("indexName").toString(), param.get("typeName").toString(), query);
        List<SearchResult.Hit<User, Void>> hits = result.getHits(User.class);
        List<User> userList = new ArrayList<>();
        for (SearchResult.Hit<User, Void> hit : hits) {
            userList.add(hit.source);
        }
        return userList;

    }

    public List<User> termsQuery(Map<String, Object> param) throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                /* 多值完全匹配查询 */
                .termsQuery("name", "T:o\"m-", "J,e{r}r;y:");
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        SearchResult result = jestService.search(jestClient, param.get("indexName").toString(), param.get("typeName").toString(), query);
        List<SearchResult.Hit<User, Void>> hits = result.getHits(User.class);
        List<User> userList = new ArrayList<>();
        for (SearchResult.Hit<User, Void> hit : hits) {
            userList.add(hit.source);
        }
        return userList;

    }

    public List<User> wildcardQuery(Map<String, Object> param) throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .wildcardQuery("restaurantCode", "*000*");//通配符和正则表达式查询
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        SearchResult result = jestService.search(jestClient, param.get("indexName").toString(), param.get("typeName").toString(), query);
        List<SearchResult.Hit<User, Void>> hits = result.getHits(User.class);
        List<User> userList = new ArrayList<>();
        for (SearchResult.Hit<User, Void> hit : hits) {
            userList.add(hit.source);
        }
        return userList;

    }

    public List<User> prefixQuery(Map<String, Object> param) throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .prefixQuery("restaurantCode", "R");//前缀查询
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        SearchResult result = jestService.search(jestClient, param.get("indexName").toString(), param.get("typeName").toString(), query);
        List<SearchResult.Hit<User, Void>> hits = result.getHits(User.class);
        List<User> userList = new ArrayList<>();
        for (SearchResult.Hit<User, Void> hit : hits) {
            userList.add(hit.source);
        }
        return userList;

    }

    public List<User> rangeQuery(Map<String, Object> param) throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .rangeQuery("birth")
                .gte("2017-09-01T00:00:00")
                .lte("2017-10-01T00:00:00")
                .includeLower(true)
                .includeUpper(true);//区间查询
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        SearchResult result = jestService.search(jestClient, param.get("indexName").toString(), param.get("typeName").toString(), query);
        List<SearchResult.Hit<User, Void>> hits = result.getHits(User.class);
        List<User> userList = new ArrayList<>();
        for (SearchResult.Hit<User, Void> hit : hits) {
            userList.add(hit.source);
        }
        return userList;

    }

    public List<User> queryString(Map<String, Object> param) throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .queryStringQuery(QueryParser.escape("T:o\""));//文本检索，应该是将查询的词先分成词库中存在的词，然后分别去检索，存在任一存在的词即返回，查询词分词后是OR的关系。需要转义特殊字符
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        SearchResult result = jestService.search(jestClient, param.get("indexName").toString(), param.get("typeName").toString(), query);
        List<SearchResult.Hit<User, Void>> hits = result.getHits(User.class);
        List<User> userList = new ArrayList<>();
        for (SearchResult.Hit<User, Void> hit : hits) {
            userList.add(hit.source);
        }
        return userList;

    }

    public Double count(Map<String, Object> param) throws Exception {

        String[] name = new String[]{ "T:o\"m-", "Jerry" };
        String from = "2016-09-01T00:00:00";
        String to = "2016-10-01T00:00:00";
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termsQuery("name", name))
                .must(QueryBuilders.rangeQuery("birth").gte(from).lte(to));
        searchSourceBuilder.query(queryBuilder);
        String query = searchSourceBuilder.toString();
        return jestService.count(jestClient, param.get("indexName").toString(), param.get("typeName").toString(), query);

    }

    public User get(Map<String, Object> param) throws Exception {

        User user = new User();
        JestResult result = jestService.get(jestClient, param.get("indexName").toString(), param.get("typeName").toString(), param.get("id").toString());
        if (result.isSucceeded()) {
            user = result.getSourceAsObject(User.class);
            return user;
        } else {
            return user;
        }

    }

    public boolean deleteIndexDocument(Map<String, Object> param) throws Exception {

        return jestService.delete(jestClient, param.get("indexName").toString(), param.get("typeName").toString(), param.get("id").toString());

    }

    public boolean deleteIndex(Map<String, Object> param) throws Exception {

        return jestService.delete(jestClient, param.get("indexName").toString());
    }

}