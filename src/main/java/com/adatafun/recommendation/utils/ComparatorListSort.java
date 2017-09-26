package com.adatafun.recommendation.utils;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by wzt on 2017/9/25.
 */
public class ComparatorListSort implements Comparator<Map> {
    private String orderByKey;
    public ComparatorListSort(String orderByKey){
        this.orderByKey= orderByKey;
    }
    @Override
    public int compare(Map o1, Map o2) {
        int order = 0;
        Integer d1 = Integer.parseInt(o1.get(orderByKey).toString());
        Integer d2 = Integer.parseInt(o2.get(orderByKey).toString());

        if (d1 < d2) {
            order = 1;
        } else if (d1 > d2) {
            order = -1;
        }
        return order;
    }
}
