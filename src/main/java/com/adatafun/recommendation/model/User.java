package com.adatafun.recommendation.model;

import io.searchbox.annotations.JestId;

/**
 * User.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
public class User {

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
    private Double  peopleConsumption;
    private Double  business;
    private Double  crowd;
    private Double  student;
    private Double  eleven;
    private Double  home;
    private Double  priority;
    private String productType;


    public User() {
        super();
        // TODO Auto-generated constructor stub
    }
    public User(String id, String userId, String restaurantCode, Double consumptionNum, Double collectionNum, Double commentNum,
                Double usageCounter, Double browseNum, Double browseHours, Boolean multiTimeConsumption,
                String  perCustomerTransaction, Double  averageOrderAmount, String  restaurantPreferences,
                Double peopleConsumption, Double business, Double crowd, Double student, Double eleven, Double home,
                Double priority, String productType) {
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
        this.peopleConsumption = peopleConsumption;
        this.business = business;
        this.crowd = crowd;
        this.student = student;
        this.eleven = eleven;
        this.home = home;
        this.priority = priority;
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", userId=" + userId + ", restaurantCode=" + restaurantCode + ", consumptionNum=" + consumptionNum +
                ", collectionNum=" + collectionNum + ", commentNum=" + commentNum + ", multiTimeConsumption=" + multitimeConsumption +
                ", usageCounter=" + usageCounter + ", browseNum=" + browseNum + ", browseHours=" + browseHours +
                ", perCustomerTransaction=" + perCustomerTransaction + ", averageOrderAmount=" + averageOrderAmount +
                ", peopleConsumption=" + peopleConsumption + ", restaurantPreferences=" + restaurantPreferences +
                ", business=" + business + ", crowd=" + crowd + ", student=" + student + ", eleven=" + eleven +
                ", home=" + home + ", priority=" + priority +"]";
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

    public Double getPeopleConsumption() {
        return peopleConsumption;
    }

    public void setPeopleConsumption(Double peopleConsumption) {
        this.peopleConsumption = peopleConsumption;
    }

    public Double getBusiness() {
        return business;
    }

    public void setBusiness(Double business) {
        this.business = business;
    }

    public Double getCrowd() {
        return crowd;
    }

    public void setCrowd(Double crowd) {
        this.crowd = crowd;
    }

    public Double getStudent() {
        return student;
    }

    public void setStudent(Double student) {
        this.student = student;
    }

    public Double getEleven() {
        return eleven;
    }

    public void setEleven(Double eleven) {
        this.eleven = eleven;
    }

    public Double getHome() {
        return home;
    }

    public void setHome(Double home) {
        this.home = home;
    }

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

}
