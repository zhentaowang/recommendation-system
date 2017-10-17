package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.TbdBannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TbdBannerService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/16.
 */
@Service
public class TbdBannerService {
    private final TbdBannerMapper tbdBannerMapper;

    @Autowired
    public TbdBannerService(TbdBannerMapper tbdBannerMapper) {
        this.tbdBannerMapper = tbdBannerMapper;
    }

    public List<Map<String,Object>> getBannerListById (List<Map> list) throws Exception {
        Map<String,Object> paramBanner = new HashMap<>();
        StringBuilder ids = new StringBuilder();
        for(Map attribute : list) {
            ids.append("'").append(attribute.get("bannerArticleId").toString()).append("'").append(",");
        }
        paramBanner.put("ids", ids.substring(0, ids.length() - 1));
        return tbdBannerMapper.getBannerListById(paramBanner);
    }
}
