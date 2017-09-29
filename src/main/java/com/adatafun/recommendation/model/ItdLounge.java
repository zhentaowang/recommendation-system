/**
 * ItdLounge.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-09-29 09:36:47 Created By wzt
*/
package com.adatafun.recommendation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ItdLounge.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
@ApiModel(value="ItdLounge",description="itd_lounge")
public class ItdLounge {
    @ApiModelProperty(value="",name="fdId", required=true)
    @NotEmpty
    private Long fdId;

    @ApiModelProperty(value="",name="fdCode")
    private String fdCode;

    @ApiModelProperty(value="",name="fdName")
    private String fdName;

    @ApiModelProperty(value="",name="fdSid")
    private Long fdSid;

    @ApiModelProperty(value="",name="fdTel")
    private String fdTel;

    @ApiModelProperty(value="",name="fdLocationguide")
    private String fdLocationguide;

    @ApiModelProperty(value="",name="fdRule")
    private String fdRule;

    @ApiModelProperty(value="",name="fdBusinesshours")
    private String fdBusinesshours;

    @ApiModelProperty(value="",name="fdLg")
    private String fdLg;

    @ApiModelProperty(value="",name="fdLocation")
    private String fdLocation;

    @ApiModelProperty(value="",name="fdType")
    private String fdType;

    @ApiModelProperty(value="",name="fdTerminal")
    private String fdTerminal;

    @ApiModelProperty(value="",name="fdRemark")
    private String fdRemark;

    @ApiModelProperty(value="",name="fdRegion")
    private String fdRegion;

    @ApiModelProperty(value="",name="fdInspection")
    private String fdInspection;

    @ApiModelProperty(value="",name="fdDel")
    private Long fdDel;

    @ApiModelProperty(value="",name="fdCounter")
    private Long fdCounter;

    @ApiModelProperty(value="",name="fdBoardinggate")
    private String fdBoardinggate;

    @ApiModelProperty(value="",name="fdEmail")
    private String fdEmail;

    @ApiModelProperty(value="",name="fdChildren")
    private String fdChildren;

    @ApiModelProperty(value="",name="fdServicehr")
    private Long fdServicehr;

    @ApiModelProperty(value="",name="fdAd")
    private String fdAd;

    @ApiModelProperty(value="",name="fdDtype")
    private String fdDtype;

    @ApiModelProperty(value="",name="fdNearestgate")
    private String fdNearestgate;

    @ApiModelProperty(value="",name="fdFoodtime")
    private String fdFoodtime;

    @ApiModelProperty(value="",name="fdEmergencyphone")
    private String fdEmergencyphone;

    @ApiModelProperty(value="",name="fdEquipmentName")
    private String fdEquipmentName;

    @ApiModelProperty(value="",name="fdIdentifyguideName")
    private String fdIdentifyguideName;

    @ApiModelProperty(value="",name="fdEquipmentCode")
    private String fdEquipmentCode;

    @ApiModelProperty(value="",name="fdIdentifyguideCode")
    private String fdIdentifyguideCode;

    @ApiModelProperty(value="",name="fdCurrencytype")
    private String fdCurrencytype;

    @ApiModelProperty(value="",name="fdPrice")
    private Long fdPrice;

    @ApiModelProperty(value="",name="fdCurrencytype2")
    private String fdCurrencytype2;

    @ApiModelProperty(value="",name="fdPrice2")
    private Long fdPrice2;

    @ApiModelProperty(value="",name="fdShowapp")
    private Long fdShowapp;

    @ApiModelProperty(value="",name="fdScode")
    private String fdScode;

    @ApiModelProperty(value="",name="fdStar")
    private Long fdStar;

    /**
     * 
     * @return fd_id 
     */
    public Long getFdId() {
        return fdId;
    }

    /**
     * 
     * @param fdId 
     */
    public void setFdId(Long fdId) {
        this.fdId = fdId;
    }

    /**
     * 
     * @return fd_code 
     */
    public String getFdCode() {
        return fdCode;
    }

    /**
     * 
     * @param fdCode 
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 
     * @return fd_name 
     */
    public String getFdName() {
        return fdName;
    }

    /**
     * 
     * @param fdName 
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 
     * @return fd_sid 
     */
    public Long getFdSid() {
        return fdSid;
    }

    /**
     * 
     * @param fdSid 
     */
    public void setFdSid(Long fdSid) {
        this.fdSid = fdSid;
    }

    /**
     * 
     * @return fd_tel 
     */
    public String getFdTel() {
        return fdTel;
    }

    /**
     * 
     * @param fdTel 
     */
    public void setFdTel(String fdTel) {
        this.fdTel = fdTel;
    }

    /**
     * 
     * @return fd_locationguide 
     */
    public String getFdLocationguide() {
        return fdLocationguide;
    }

    /**
     * 
     * @param fdLocationguide 
     */
    public void setFdLocationguide(String fdLocationguide) {
        this.fdLocationguide = fdLocationguide;
    }

    /**
     * 
     * @return fd_rule 
     */
    public String getFdRule() {
        return fdRule;
    }

    /**
     * 
     * @param fdRule 
     */
    public void setFdRule(String fdRule) {
        this.fdRule = fdRule;
    }

    /**
     * 
     * @return fd_businesshours 
     */
    public String getFdBusinesshours() {
        return fdBusinesshours;
    }

    /**
     * 
     * @param fdBusinesshours 
     */
    public void setFdBusinesshours(String fdBusinesshours) {
        this.fdBusinesshours = fdBusinesshours;
    }

    /**
     * 
     * @return fd_lg 
     */
    public String getFdLg() {
        return fdLg;
    }

    /**
     * 
     * @param fdLg 
     */
    public void setFdLg(String fdLg) {
        this.fdLg = fdLg;
    }

    /**
     * 
     * @return fd_location 
     */
    public String getFdLocation() {
        return fdLocation;
    }

    /**
     * 
     * @param fdLocation 
     */
    public void setFdLocation(String fdLocation) {
        this.fdLocation = fdLocation;
    }

    /**
     * 
     * @return fd_type 
     */
    public String getFdType() {
        return fdType;
    }

    /**
     * 
     * @param fdType 
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 
     * @return fd_terminal 
     */
    public String getFdTerminal() {
        return fdTerminal;
    }

    /**
     * 
     * @param fdTerminal 
     */
    public void setFdTerminal(String fdTerminal) {
        this.fdTerminal = fdTerminal;
    }

    /**
     * 
     * @return fd_remark 
     */
    public String getFdRemark() {
        return fdRemark;
    }

    /**
     * 
     * @param fdRemark 
     */
    public void setFdRemark(String fdRemark) {
        this.fdRemark = fdRemark;
    }

    /**
     * 
     * @return fd_region 
     */
    public String getFdRegion() {
        return fdRegion;
    }

    /**
     * 
     * @param fdRegion 
     */
    public void setFdRegion(String fdRegion) {
        this.fdRegion = fdRegion;
    }

    /**
     * 
     * @return fd_inspection 
     */
    public String getFdInspection() {
        return fdInspection;
    }

    /**
     * 
     * @param fdInspection 
     */
    public void setFdInspection(String fdInspection) {
        this.fdInspection = fdInspection;
    }

    /**
     * 
     * @return fd_del 
     */
    public Long getFdDel() {
        return fdDel;
    }

    /**
     * 
     * @param fdDel 
     */
    public void setFdDel(Long fdDel) {
        this.fdDel = fdDel;
    }

    /**
     * 
     * @return fd_counter 
     */
    public Long getFdCounter() {
        return fdCounter;
    }

    /**
     * 
     * @param fdCounter 
     */
    public void setFdCounter(Long fdCounter) {
        this.fdCounter = fdCounter;
    }

    /**
     * 
     * @return fd_boardinggate 
     */
    public String getFdBoardinggate() {
        return fdBoardinggate;
    }

    /**
     * 
     * @param fdBoardinggate 
     */
    public void setFdBoardinggate(String fdBoardinggate) {
        this.fdBoardinggate = fdBoardinggate;
    }

    /**
     * 
     * @return fd_email 
     */
    public String getFdEmail() {
        return fdEmail;
    }

    /**
     * 
     * @param fdEmail 
     */
    public void setFdEmail(String fdEmail) {
        this.fdEmail = fdEmail;
    }

    /**
     * 
     * @return fd_children 
     */
    public String getFdChildren() {
        return fdChildren;
    }

    /**
     * 
     * @param fdChildren 
     */
    public void setFdChildren(String fdChildren) {
        this.fdChildren = fdChildren;
    }

    /**
     * 
     * @return fd_servicehr 
     */
    public Long getFdServicehr() {
        return fdServicehr;
    }

    /**
     * 
     * @param fdServicehr 
     */
    public void setFdServicehr(Long fdServicehr) {
        this.fdServicehr = fdServicehr;
    }

    /**
     * 
     * @return fd_ad 
     */
    public String getFdAd() {
        return fdAd;
    }

    /**
     * 
     * @param fdAd 
     */
    public void setFdAd(String fdAd) {
        this.fdAd = fdAd;
    }

    /**
     * 
     * @return fd_dtype 
     */
    public String getFdDtype() {
        return fdDtype;
    }

    /**
     * 
     * @param fdDtype 
     */
    public void setFdDtype(String fdDtype) {
        this.fdDtype = fdDtype;
    }

    /**
     * 
     * @return fd_nearestgate 
     */
    public String getFdNearestgate() {
        return fdNearestgate;
    }

    /**
     * 
     * @param fdNearestgate 
     */
    public void setFdNearestgate(String fdNearestgate) {
        this.fdNearestgate = fdNearestgate;
    }

    /**
     * 
     * @return fd_foodtime 
     */
    public String getFdFoodtime() {
        return fdFoodtime;
    }

    /**
     * 
     * @param fdFoodtime 
     */
    public void setFdFoodtime(String fdFoodtime) {
        this.fdFoodtime = fdFoodtime;
    }

    /**
     * 
     * @return fd_emergencyphone 
     */
    public String getFdEmergencyphone() {
        return fdEmergencyphone;
    }

    /**
     * 
     * @param fdEmergencyphone 
     */
    public void setFdEmergencyphone(String fdEmergencyphone) {
        this.fdEmergencyphone = fdEmergencyphone;
    }

    /**
     * 
     * @return fd_equipment_name 
     */
    public String getFdEquipmentName() {
        return fdEquipmentName;
    }

    /**
     * 
     * @param fdEquipmentName 
     */
    public void setFdEquipmentName(String fdEquipmentName) {
        this.fdEquipmentName = fdEquipmentName;
    }

    /**
     * 
     * @return fd_identifyguide_name 
     */
    public String getFdIdentifyguideName() {
        return fdIdentifyguideName;
    }

    /**
     * 
     * @param fdIdentifyguideName 
     */
    public void setFdIdentifyguideName(String fdIdentifyguideName) {
        this.fdIdentifyguideName = fdIdentifyguideName;
    }

    /**
     * 
     * @return fd_equipment_code 
     */
    public String getFdEquipmentCode() {
        return fdEquipmentCode;
    }

    /**
     * 
     * @param fdEquipmentCode 
     */
    public void setFdEquipmentCode(String fdEquipmentCode) {
        this.fdEquipmentCode = fdEquipmentCode;
    }

    /**
     * 
     * @return fd_identifyguide_code 
     */
    public String getFdIdentifyguideCode() {
        return fdIdentifyguideCode;
    }

    /**
     * 
     * @param fdIdentifyguideCode 
     */
    public void setFdIdentifyguideCode(String fdIdentifyguideCode) {
        this.fdIdentifyguideCode = fdIdentifyguideCode;
    }

    /**
     * 
     * @return fd_currencytype 
     */
    public String getFdCurrencytype() {
        return fdCurrencytype;
    }

    /**
     * 
     * @param fdCurrencytype 
     */
    public void setFdCurrencytype(String fdCurrencytype) {
        this.fdCurrencytype = fdCurrencytype;
    }

    /**
     * 
     * @return fd_price 
     */
    public Long getFdPrice() {
        return fdPrice;
    }

    /**
     * 
     * @param fdPrice 
     */
    public void setFdPrice(Long fdPrice) {
        this.fdPrice = fdPrice;
    }

    /**
     * 
     * @return fd_currencytype2 
     */
    public String getFdCurrencytype2() {
        return fdCurrencytype2;
    }

    /**
     * 
     * @param fdCurrencytype2 
     */
    public void setFdCurrencytype2(String fdCurrencytype2) {
        this.fdCurrencytype2 = fdCurrencytype2;
    }

    /**
     * 
     * @return fd_price2 
     */
    public Long getFdPrice2() {
        return fdPrice2;
    }

    /**
     * 
     * @param fdPrice2 
     */
    public void setFdPrice2(Long fdPrice2) {
        this.fdPrice2 = fdPrice2;
    }

    /**
     * 
     * @return fd_showapp 
     */
    public Long getFdShowapp() {
        return fdShowapp;
    }

    /**
     * 
     * @param fdShowapp 
     */
    public void setFdShowapp(Long fdShowapp) {
        this.fdShowapp = fdShowapp;
    }

    /**
     * 
     * @return fd_scode 
     */
    public String getFdScode() {
        return fdScode;
    }

    /**
     * 
     * @param fdScode 
     */
    public void setFdScode(String fdScode) {
        this.fdScode = fdScode;
    }

    /**
     * 
     * @return fd_star 
     */
    public Long getFdStar() {
        return fdStar;
    }

    /**
     * 
     * @param fdStar 
     */
    public void setFdStar(Long fdStar) {
        this.fdStar = fdStar;
    }
}