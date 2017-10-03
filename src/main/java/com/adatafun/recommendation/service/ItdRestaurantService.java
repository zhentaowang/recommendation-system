package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.ItdRestaurantMapper;
import com.adatafun.recommendation.model.ItdRestaurant;
import com.alibaba.fastjson.JSON;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ItdRestaurantService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/3.
 */
@Service
public class ItdRestaurantService {
    private final ItdRestaurantMapper itdRestaurantMapper;

    @Autowired
    public ItdRestaurantService(ItdRestaurantMapper itdRestaurantMapper) {
        this.itdRestaurantMapper = itdRestaurantMapper;
    }

    public List<ItdRestaurant> getRestaurantListByCode (List<Map> list) throws Exception {
        Map<String,Object> paramRestaurant = new HashMap<>();
        StringBuilder code = new StringBuilder();
        for(Map attribute : list) {
            code.append("'").append(attribute.get("restaurantCode").toString()).append("'").append(",");
        }
        paramRestaurant.put("code", code.substring(0,code.length() - 1));
        List<ItdRestaurant> itdRestaurantList = itdRestaurantMapper.getRestaurantListByCode(paramRestaurant);
        return itdRestaurantList;
    }
}
