package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.ItdVVIPMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ItdVVIPService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/23.
 */
@Service
public class ItdVVIPService {
    private final ItdVVIPMapper itdVVIPMapper;

    @Autowired
    public ItdVVIPService(ItdVVIPMapper itdVVIPMapper) {
        this.itdVVIPMapper = itdVVIPMapper;
    }

    public List<Map<String, Object>> getVVIPList (String airportCode) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("airportCode", airportCode);
        return itdVVIPMapper.getVVIPList(map);
    }
}
