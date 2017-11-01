package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.ItdCarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ItdCarService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/23.
 */
@Service
public class ItdCarService {
    private final ItdCarMapper itdCarMapper;

    @Autowired
    public ItdCarService(ItdCarMapper itdCarMapper) {
        this.itdCarMapper = itdCarMapper;
    }

    public List<Map<String, Object>> getCarList (String airportCode) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("airportCode", airportCode);
        return itdCarMapper.getCarList(map);
    }
}
