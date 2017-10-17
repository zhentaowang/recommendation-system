package com.adatafun.recommendation.mapper;

import java.util.List;
import java.util.Map;

/**
 * ItdRestaurantMapper.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
public interface ItdRestaurantMapper {
    List<Map<String, Object>> getRestaurantListByCode(Map<String, Object> map);
    List<Map<String,Object>> getBrandRestaurantListByCode(Map<String, Object> map);
}
