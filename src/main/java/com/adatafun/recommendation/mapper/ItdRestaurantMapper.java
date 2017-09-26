package com.adatafun.recommendation.mapper;

import com.adatafun.recommendation.model.ItdRestaurant;

import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/9/21.
 */
public interface ItdRestaurantMapper {
    List<ItdRestaurant> getRestaurantListByCode(Map<String, Object> map);
}
