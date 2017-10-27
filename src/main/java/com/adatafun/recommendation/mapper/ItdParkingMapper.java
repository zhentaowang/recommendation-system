package com.adatafun.recommendation.mapper;

import java.util.List;
import java.util.Map;

/**
 * ItdParkingMapper.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/23.
 */
public interface ItdParkingMapper {
    List<Map<String, Object>> getParkingList(String airportCode);
}
