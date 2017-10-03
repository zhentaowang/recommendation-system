package com.adatafun.recommendation.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LZStatus;

import java.util.List;
import java.util.Map;

/**
 * ParameterDetection.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 2017/10/3.
 */
public class ParameterDetection {
    private int positionFlag;
    private int flightInfoFlag;
    private JSONObject param;
    private Map<String, Object> result;

    public ParameterDetection(int positionFlag, int flightInfoFlag, JSONObject param, Map<String, Object> result) {
        this.positionFlag = positionFlag;
        this.flightInfoFlag = flightInfoFlag;
        this.param = param;
        this.result = result;
    }

    public Map<String, Object> verifyValidity() throws Exception {
        if (!param.containsKey("position") ||
                param.getString("position").equals("")) {
            positionFlag = 0; //没有位置信息，位置推荐无效
        }
        if (!param.containsKey("flightNo") ||
                !param.containsKey("flightDate") ||
                param.getString("flightNo").equals("") ||
                param.getString("flightDate").equals("")) {
            flightInfoFlag = 0; //没有航班信息或航班信息不全，航班推荐无效
        }
        result.put("positionFlag", positionFlag);
        result.put("flightInfoFlag", flightInfoFlag);
        return result;
    }

    public Map<String, Object> verifyIntegrity (String productName, String orderByKey, String productCode) throws Exception {
        List<Map> list = JSON.parseArray(param.getString(productName), Map.class);
        result.put("status", LZStatus.DATA_TRANSFER_ERROR.value());
        result.put("msg", LZStatus.DATA_TRANSFER_ERROR.display());

        if (list.size() == 0) {
            return result;
        } else {
            for (Map attribute : list) {
                if (!attribute.containsKey(productCode) || !attribute.containsKey(orderByKey)
                        || attribute.get(productCode).equals("") || attribute.get(orderByKey).equals("")) {
                    return result;
                }
            }
        }

        //根据指定权重进行初始排序
        ComparatorListSort comparatorListSort = new ComparatorListSort(orderByKey);
        list.sort(comparatorListSort);

        result.put("list", list);
        result.put("status", LZStatus.SUCCESS.value());
        result.put("msg", LZStatus.SUCCESS.display());
        return result;
    }

}
