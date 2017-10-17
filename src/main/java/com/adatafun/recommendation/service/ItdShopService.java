package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.ItdShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ItdShopService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/3.
 */
@Service
public class ItdShopService {
    private final ItdShopMapper itdShopMapper;

    @Autowired
    public ItdShopService(ItdShopMapper itdShopMapper) {
        this.itdShopMapper = itdShopMapper;
    }

    public List<Map<String, Object>> getShopListByCode (List<Map> list) throws Exception {
        Map<String,Object> paramShop = new HashMap<>();
        StringBuilder code = new StringBuilder();
        for(Map attribute : list) {
            code.append("'").append(attribute.get("shopCode").toString()).append("'").append(",");
        }
        paramShop.put("code", code.substring(0,code.length() - 1));
        return itdShopMapper.getShopListByCode(paramShop);
    }
}
