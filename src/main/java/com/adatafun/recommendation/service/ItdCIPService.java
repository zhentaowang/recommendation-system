package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.ItdCIPMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ItdCIPService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/23.
 */
@Service
public class ItdCIPService {
    private final ItdCIPMapper itdCIPMapper;

    @Autowired
    public ItdCIPService(ItdCIPMapper itdCIPMapper) {
        this.itdCIPMapper = itdCIPMapper;
    }

    public List<Map<String, Object>> getCIPList (String airportCode) throws Exception {
        return itdCIPMapper.getCIPList(airportCode);
    }
}
