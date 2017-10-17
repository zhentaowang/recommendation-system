package com.adatafun.recommendation.mapper;

import java.util.List;
import java.util.Map;

/**
 * TbdBannerMapper.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/16.
 */
public interface TbdBannerMapper {
    List<Map<String,Object>> getBannerListById(Map<String, Object> map);
}
