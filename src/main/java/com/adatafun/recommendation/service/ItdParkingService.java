package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.ItdParkingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ItdParkingService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/23.
 */
@Service
public class ItdParkingService {
    private final ItdParkingMapper itdParkingMapper;

    @Autowired
    public ItdParkingService(ItdParkingMapper itdParkingMapper) {
        this.itdParkingMapper = itdParkingMapper;
    }

    public List<Map<String, Object>> getParkingList (String airportCode) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("airportCode", airportCode);
        return itdParkingMapper.getParkingList(map);
    }
}
