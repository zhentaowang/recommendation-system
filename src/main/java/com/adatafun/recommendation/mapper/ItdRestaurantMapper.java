package com.adatafun.recommendation.mapper;

import com.adatafun.recommendation.model.ItdRestaurant;

import java.util.List;
import java.util.Map;

/**
 * ItdRestaurantMapper.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
public interface ItdRestaurantMapper {
    List<ItdRestaurant> getRestaurantListByCode(Map<String, Object> map);
}
