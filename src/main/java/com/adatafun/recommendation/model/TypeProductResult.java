package com.adatafun.recommendation.model;

import java.util.List;
import java.util.Map;

/**
 * Created by yanggf on 2017/9/5.
 */
public class TypeProductResult {
    private Integer state;
    private String message;
    private Map<String, List<String>> data;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, List<String>> getData() {
        return data;
    }

    public void setData(Map<String, List<String>> data) {
        this.data = data;
    }
}
