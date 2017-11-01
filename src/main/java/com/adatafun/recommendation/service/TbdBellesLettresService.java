package com.adatafun.recommendation.service;

import com.adatafun.recommendation.mapper.TbdBellesLettresMapper;
import com.adatafun.recommendation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TbdBellesLettresService.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/19.
 */
@Service
public class TbdBellesLettresService {
    private final TbdBellesLettresMapper tbdBellesLettresMapper;

    @Autowired
    public TbdBellesLettresService(TbdBellesLettresMapper tbdBellesLettresMapper) {
        this.tbdBellesLettresMapper = tbdBellesLettresMapper;
    }

    public List<Map<String, Object>> getSubjectArticleListByLabel (User user) throws Exception {

        Map<String,Object> paramSubjectArticle = new HashMap<>();
        List<Map<String, Object>> subjectArticle;
        if (user == null) {
            subjectArticle = tbdBellesLettresMapper.getSubjectArticleList();
        } else {
            if (user.getBusiness() > 0) {
                paramSubjectArticle.put("business", "商旅出行");
            }
            if (user.getStudent() > 0) {
                paramSubjectArticle.put("student", "学生");
            }
            if (user.getCrowd() > 0) {
                paramSubjectArticle.put("crowd", "人群标签");
            }
            if (user.getEleven() > 0) {
                paramSubjectArticle.put("eleven", "十一");
            }
            if (user.getHome() > 0) {
                paramSubjectArticle.put("home", "家庭出行");
            }
            subjectArticle = tbdBellesLettresMapper.getSubjectArticleListByLabel(paramSubjectArticle);
        }
        return subjectArticle;
    }

    public List<Map<String, Object>> getSubjectArticleListBySoleLabel (String label) throws Exception {

        Map<String,Object> paramLabel = new HashMap<>();
        paramLabel.put("label", label);
        List<Map<String, Object>> list = tbdBellesLettresMapper.getSubjectArticleListBySoleLabel(paramLabel);
        if (list.size() == 0) {
            list = tbdBellesLettresMapper.getSubjectArticleList();
        }
        return list;
    }

    public List<Map<String, Object>> getSubjectArticleListExpectSoleLabel (String label) throws Exception {

        Map<String,Object> paramLabel = new HashMap<>();
        paramLabel.put("label", label);
        List<Map<String, Object>> list = tbdBellesLettresMapper.getSubjectArticleListExpectSoleLabel(paramLabel);
        if (list.size() == 0) {
            list = tbdBellesLettresMapper.getSubjectArticleList();
        }
        return list;
    }

}
