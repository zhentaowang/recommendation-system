package com.adatafun.recommendation.model;

import io.searchbox.annotations.JestId;

/**
 * UserRest.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
public class UserRest {

    @JestId
    private String  id;
    private String  userId;
    private String  restaurantCode;
    private Double  consumptionNum;
    private Double  collectionNum;
    private Double  commentNum;
    private Double  usageCounter;
    private Double  browseNum;
    private Double  browseHours;
    private Boolean multitimeConsumption;
    private String  perCustomerTransaction;
    private Double  averageOrderAmount;
    private String  restaurantPreferences;
    private Double  behaviorScore;


    public UserRest() {
        super();
        // TODO Auto-generated constructor stub
    }
    public UserRest(String id, String userId, String restaurantCode, Double consumptionNum, Double collectionNum, Double commentNum,
                    Double usageCounter, Double browseNum, Double browseHours, Boolean multiTimeConsumption,
                    String  perCustomerTransaction, Double  averageOrderAmount, String  restaurantPreferences) {
        super();
        this.id = id;
        this.userId = userId;
        this.restaurantCode = restaurantCode;
        this.consumptionNum = consumptionNum;
        this.collectionNum = collectionNum;
        this.commentNum = commentNum;
        this.usageCounter = usageCounter;
        this.browseNum = browseNum;
        this.browseHours = browseHours;
        this.multitimeConsumption = multiTimeConsumption;
        this.perCustomerTransaction = perCustomerTransaction;
        this.averageOrderAmount = averageOrderAmount;
        this.restaurantPreferences = restaurantPreferences;
    }

    @Override
    public String toString() {
        return "UserRest [id=" + id + ", userId=" + userId + ", restaurantCode=" + restaurantCode + ", consumptionNum=" + consumptionNum +
                ", collectionNum=" + collectionNum + ", commentNum=" + commentNum + ", multiTimeConsumption=" + multitimeConsumption +
                ", usageCounter=" + usageCounter + ", browseNum=" + browseNum + ", browseHours=" + browseHours +
                ", perCustomerTransaction=" + perCustomerTransaction + ", averageOrderAmount=" + averageOrderAmount +
                ", restaurantPreferences=" + restaurantPreferences + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantCode() {
        return restaurantCode;
    }

    public void setRestaurantCode(String restaurantCode) {
        this.restaurantCode = restaurantCode;
    }

    public Double getConsumptionNum() {
        return consumptionNum;
    }

    public void setConsumptionNum(Double consumptionNum) {
        this.consumptionNum = consumptionNum;
    }

    public Double getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Double collectionNum) {
        this.collectionNum = collectionNum;
    }

    public Double getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Double commentNum) {
        this.commentNum = commentNum;
    }

    public Double getUsageCounter() {
        return usageCounter;
    }

    public void setUsageCounter(Double usageCounter) {
        this.usageCounter = usageCounter;
    }

    public Double getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Double browseNum) {
        this.browseNum = browseNum;
    }

    public Double getBrowseHours() {
        return browseHours;
    }

    public void setBrowseHours(Double browseHours) {
        this.browseHours = browseHours;
    }

    public Boolean getMultiTimeConsumption() {
        return multitimeConsumption;
    }

    public void setMultiTimeConsumption(Boolean multiTimeConsumption) {
        this.multitimeConsumption = multiTimeConsumption;
    }

    public String getPerCustomerTransaction() {
        return perCustomerTransaction;
    }

    public void setPerCustomerTransaction(String perCustomerTransaction) {
        this.perCustomerTransaction = perCustomerTransaction;
    }

    public Double getAverageOrderAmount() {
        return averageOrderAmount;
    }

    public void setAverageOrderAmount(Double averageOrderAmount) {
        this.averageOrderAmount = averageOrderAmount;
    }

    public String getRestaurantPreferences() {
        return restaurantPreferences;
    }

    public void setRestaurantPreferences(String restaurantPreferences) {
        this.restaurantPreferences = restaurantPreferences;
    }

    public Double getBehaviorScore() {
        return behaviorScore;
    }

    public void setBehaviorScore(Double behaviorScore) {
        this.behaviorScore = behaviorScore;
    }

}
