package com.adatafun.recommendation.utils;

import java.util.Comparator;
import java.util.Map;

/**
 * ComparatorListSort.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
public class ComparatorListSort implements Comparator<Map> {
    private String orderByKey;
    ComparatorListSort(String orderByKey){
        this.orderByKey= orderByKey;
    }
    @Override
    public int compare(Map o1, Map o2) {
        int order = 0;
        Double d1 = Double.parseDouble(o1.get(orderByKey).toString());
        Double d2 = Double.parseDouble(o2.get(orderByKey).toString());

        if (d1 < d2) {
            order = 1;
        } else if (d1 > d2) {
            order = -1;
        }
        return order;
    }
}
