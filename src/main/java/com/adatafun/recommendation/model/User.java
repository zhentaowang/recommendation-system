package com.adatafun.recommendation.model;

import io.searchbox.annotations.JestId;

import java.util.Date;

/**
 * Created by wzt on 2017/9/3.
 */
public class User {

    @JestId
    private Integer id;
    private String userId;
    private String restaurantPreferences;

    public User() {
        super();
        // TODO Auto-generated constructor stub
    }
    public User(Integer id, String restaurantPreferences) {
        super();
        this.id = id;
        this.restaurantPreferences = restaurantPreferences;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", userId=" + userId + ", restaurantPreferences=" + restaurantPreferences + "]";
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getRestaurantPreferences() {
        return restaurantPreferences;
    }
    public void setRestaurantPreferences(String name) {
        this.restaurantPreferences = name;
    }

}
