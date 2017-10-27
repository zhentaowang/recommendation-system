package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.TbdUserFlightMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * TbdUserFlightService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/18.
 */
@Service
public class TbdUserFlightService {
    private final TbdUserFlightMapper tbdUserFlightMapper;

    @Autowired
    public TbdUserFlightService(TbdUserFlightMapper tbdUserFlightMapper) {
        this.tbdUserFlightMapper = tbdUserFlightMapper;
    }

    public Map<String, Object> getUserIdentityById (Map<String, Object> map) throws Exception {
        return tbdUserFlightMapper.getUserIdentityById(map);
    }
}
