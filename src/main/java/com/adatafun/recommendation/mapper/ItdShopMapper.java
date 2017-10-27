package com.adatafun.recommendation.mapper;


import java.util.List;
import java.util.Map;

/**
 * ItdShopMapper.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/9/30.
 */
public interface ItdShopMapper {
    List<Map<String, Object>> getShopListByCode(Map<String, Object> map);
    List<Map<String, Object>> getShopList(Map<String, Object> map);
}
