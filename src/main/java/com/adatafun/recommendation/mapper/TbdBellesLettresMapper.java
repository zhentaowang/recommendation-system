package com.adatafun.recommendation.mapper;

import java.util.List;
import java.util.Map;

/**
 * TbdBellesLettresMapper.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/19.
 */
public interface TbdBellesLettresMapper {
    List<Map<String, Object>> getSubjectArticleListByLabel(Map<String, Object> map);
    List<Map<String, Object>> getSubjectArticleList();
}
