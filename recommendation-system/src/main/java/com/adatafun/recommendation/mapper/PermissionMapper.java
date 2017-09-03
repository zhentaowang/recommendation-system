package com.adatafun.recommendation.mapper;

import com.adatafun.recommendation.model.Permission;
import java.util.Map;

/**
 * Created by wzt on 2016/12/26.
 */
public interface PermissionMapper {
    Permission selectById(Map<String, Object> map);
}
