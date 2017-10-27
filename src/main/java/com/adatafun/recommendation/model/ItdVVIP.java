/**
 * ItdVVIP.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-10-23 14:56:03 Created By wzt
*/
package com.adatafun.recommendation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ItdVVIP.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-10-23 14:56:03 Created By wzt
*/
@ApiModel(value="ItdVVIP",description="itd_vvip")
public class ItdVVIP {
    @ApiModelProperty(value="",name="fdId")
    private Long fdId;

    @ApiModelProperty(value="",name="fdCode")
    private String fdCode;

    @ApiModelProperty(value="",name="fdName")
    private String fdName;

    @ApiModelProperty(value="",name="fdSid")
    private Long fdSid;

    @ApiModelProperty(value="",name="fdLevel")
    private Long fdLevel;

    @ApiModelProperty(value="",name="fdType")
    private Long fdType;

    @ApiModelProperty(value="",name="fdMode")
    private Long fdMode;

    @ApiModelProperty(value="",name="fdBatchnum")
    private Long fdBatchnum;

    @ApiModelProperty(value="",name="fdNote")
    private String fdNote;

    @ApiModelProperty(value="",name="fdLg")
    private String fdLg;

    @ApiModelProperty(value="",name="fdDel")
    private Long fdDel;

    @ApiModelProperty(value="",name="fdBatchmoney")
    private Long fdBatchmoney;

    @ApiModelProperty(value="",name="fdApersoncost")
    private Long fdApersoncost;

    @ApiModelProperty(value="",name="fdCount")
    private Long fdCount;

    @ApiModelProperty(value="",name="fdCurrencytype")
    private String fdCurrencytype;

    @ApiModelProperty(value="",name="fdBatchmoney2")
    private Long fdBatchmoney2;

    @ApiModelProperty(value="",name="fdApersoncost2")
    private Long fdApersoncost2;

    @ApiModelProperty(value="",name="fdCurrencytype2")
    private String fdCurrencytype2;

    @ApiModelProperty(value="",name="fdBusinesshours")
    private String fdBusinesshours;

    @ApiModelProperty(value="",name="fdScode")
    private String fdScode;

    @ApiModelProperty(value="",name="fdLocationguide")
    private String fdLocationguide;

    @ApiModelProperty(value="",name="fdShowapp")
    private Long fdShowapp;

    @ApiModelProperty(value="",name="fdLevelname")
    private String fdLevelname;

    @ApiModelProperty(value="",name="fdBookinghr")
    private Long fdBookinghr;

    @ApiModelProperty(value="",name="fdWelcoming")
    private String fdWelcoming;

    @ApiModelProperty(value="",name="fdSeeing")
    private String fdSeeing;

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
     * @return fd_level 
     */
    public Long getFdLevel() {
        return fdLevel;
    }

    /**
     * 
     * @param fdLevel 
     */
    public void setFdLevel(Long fdLevel) {
        this.fdLevel = fdLevel;
    }

    /**
     * 
     * @return fd_type 
     */
    public Long getFdType() {
        return fdType;
    }

    /**
     * 
     * @param fdType 
     */
    public void setFdType(Long fdType) {
        this.fdType = fdType;
    }

    /**
     * 
     * @return fd_mode 
     */
    public Long getFdMode() {
        return fdMode;
    }

    /**
     * 
     * @param fdMode 
     */
    public void setFdMode(Long fdMode) {
        this.fdMode = fdMode;
    }

    /**
     * 
     * @return fd_batchnum 
     */
    public Long getFdBatchnum() {
        return fdBatchnum;
    }

    /**
     * 
     * @param fdBatchnum 
     */
    public void setFdBatchnum(Long fdBatchnum) {
        this.fdBatchnum = fdBatchnum;
    }

    /**
     * 
     * @return fd_note 
     */
    public String getFdNote() {
        return fdNote;
    }

    /**
     * 
     * @param fdNote 
     */
    public void setFdNote(String fdNote) {
        this.fdNote = fdNote;
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
     * @return fd_batchmoney 
     */
    public Long getFdBatchmoney() {
        return fdBatchmoney;
    }

    /**
     * 
     * @param fdBatchmoney 
     */
    public void setFdBatchmoney(Long fdBatchmoney) {
        this.fdBatchmoney = fdBatchmoney;
    }

    /**
     * 
     * @return fd_apersoncost 
     */
    public Long getFdApersoncost() {
        return fdApersoncost;
    }

    /**
     * 
     * @param fdApersoncost 
     */
    public void setFdApersoncost(Long fdApersoncost) {
        this.fdApersoncost = fdApersoncost;
    }

    /**
     * 
     * @return fd_count 
     */
    public Long getFdCount() {
        return fdCount;
    }

    /**
     * 
     * @param fdCount 
     */
    public void setFdCount(Long fdCount) {
        this.fdCount = fdCount;
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
     * @return fd_batchmoney2 
     */
    public Long getFdBatchmoney2() {
        return fdBatchmoney2;
    }

    /**
     * 
     * @param fdBatchmoney2 
     */
    public void setFdBatchmoney2(Long fdBatchmoney2) {
        this.fdBatchmoney2 = fdBatchmoney2;
    }

    /**
     * 
     * @return fd_apersoncost2 
     */
    public Long getFdApersoncost2() {
        return fdApersoncost2;
    }

    /**
     * 
     * @param fdApersoncost2 
     */
    public void setFdApersoncost2(Long fdApersoncost2) {
        this.fdApersoncost2 = fdApersoncost2;
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
     * @return fd_levelname 
     */
    public String getFdLevelname() {
        return fdLevelname;
    }

    /**
     * 
     * @param fdLevelname 
     */
    public void setFdLevelname(String fdLevelname) {
        this.fdLevelname = fdLevelname;
    }

    /**
     * 
     * @return fd_bookinghr 
     */
    public Long getFdBookinghr() {
        return fdBookinghr;
    }

    /**
     * 
     * @param fdBookinghr 
     */
    public void setFdBookinghr(Long fdBookinghr) {
        this.fdBookinghr = fdBookinghr;
    }

    /**
     * 
     * @return fd_welcoming 
     */
    public String getFdWelcoming() {
        return fdWelcoming;
    }

    /**
     * 
     * @param fdWelcoming 
     */
    public void setFdWelcoming(String fdWelcoming) {
        this.fdWelcoming = fdWelcoming;
    }

    /**
     * 
     * @return fd_seeing 
     */
    public String getFdSeeing() {
        return fdSeeing;
    }

    /**
     * 
     * @param fdSeeing 
     */
    public void setFdSeeing(String fdSeeing) {
        this.fdSeeing = fdSeeing;
    }
}