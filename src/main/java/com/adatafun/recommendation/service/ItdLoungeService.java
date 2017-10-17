package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.ItdLoungeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ItdLoungeService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/3.
 */
@Service
public class ItdLoungeService {
    private final ItdLoungeMapper itdLoungeMapper;

    @Autowired
    public ItdLoungeService(ItdLoungeMapper itdLoungeMapper) {
        this.itdLoungeMapper = itdLoungeMapper;
    }

    public List<Map<String, Object>> getLoungeListByCode (List<Map> list) throws Exception {
        Map<String,Object> paramLounge = new HashMap<>();
        StringBuilder code = new StringBuilder();
        for(Map attribute : list) {
            code.append("'").append(attribute.get("loungeCode").toString()).append("'").append(",");
        }
        paramLounge.put("code", code.substring(0,code.length() - 1));
        return itdLoungeMapper.getLoungeListByCode(paramLounge);
    }
}
