package com.adatafun.recommendation.model;

import java.util.List;
import java.util.Map;

/**
 * Created by yanggf on 2017/9/5.
 */
public class BrandRestaurantResult {
    private Integer status;
    private String msg;
    private Map<String, List<String>> data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, List<String>> getData() {
        return data;
    }

    public void setData(Map<String, List<String>> data) {
        this.data = data;
    }
}
