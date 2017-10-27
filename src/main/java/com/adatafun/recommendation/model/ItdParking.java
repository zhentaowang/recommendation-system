/**
 * ItdParking.java
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
 * ItdParking.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-10-23 14:56:03 Created By wzt
*/
@ApiModel(value="ItdParking",description="itd_parking")
public class ItdParking {
    @ApiModelProperty(value="",name="fdId")
    private Long fdId;

    @ApiModelProperty(value="",name="fdCode")
    private String fdCode;

    @ApiModelProperty(value="",name="fdName")
    private String fdName;

    @ApiModelProperty(value="",name="fdSid")
    private Long fdSid;

    @ApiModelProperty(value="",name="fdType")
    private String fdType;

    @ApiModelProperty(value="",name="fdPlace1")
    private String fdPlace1;

    @ApiModelProperty(value="",name="fdPlace2")
    private String fdPlace2;

    @ApiModelProperty(value="",name="fdAddress")
    private String fdAddress;

    @ApiModelProperty(value="",name="fdRule")
    private String fdRule;

    @ApiModelProperty(value="",name="fdLg")
    private String fdLg;

    @ApiModelProperty(value="",name="fdDel")
    private Long fdDel;

    @ApiModelProperty(value="",name="fdCurrencytype")
    private String fdCurrencytype;

    @ApiModelProperty(value="",name="fdHourlyrate")
    private Long fdHourlyrate;

    @ApiModelProperty(value="",name="fdCappingfees")
    private Long fdCappingfees;

    @ApiModelProperty(value="",name="fdCurrencytype2")
    private String fdCurrencytype2;

    @ApiModelProperty(value="",name="fdHourlyrate2")
    private Long fdHourlyrate2;

    @ApiModelProperty(value="",name="fdCappingfees2")
    private Long fdCappingfees2;

    @ApiModelProperty(value="",name="fdServicecharge")
    private Long fdServicecharge;

    @ApiModelProperty(value="",name="fdServicecharge2")
    private Long fdServicecharge2;

    @ApiModelProperty(value="",name="fdServicedescription")
    private String fdServicedescription;

    @ApiModelProperty(value="",name="fdOtherid")
    private String fdOtherid;

    @ApiModelProperty(value="",name="fdShowapp")
    private Long fdShowapp;

    @ApiModelProperty(value="",name="fdScode")
    private String fdScode;

    @ApiModelProperty(value="",name="fdTerminal")
    private String fdTerminal;

    @ApiModelProperty(value="",name="fdTypeid")
    private Long fdTypeid;

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
     * @return fd_place1 
     */
    public String getFdPlace1() {
        return fdPlace1;
    }

    /**
     * 
     * @param fdPlace1 
     */
    public void setFdPlace1(String fdPlace1) {
        this.fdPlace1 = fdPlace1;
    }

    /**
     * 
     * @return fd_place2 
     */
    public String getFdPlace2() {
        return fdPlace2;
    }

    /**
     * 
     * @param fdPlace2 
     */
    public void setFdPlace2(String fdPlace2) {
        this.fdPlace2 = fdPlace2;
    }

    /**
     * 
     * @return fd_address 
     */
    public String getFdAddress() {
        return fdAddress;
    }

    /**
     * 
     * @param fdAddress 
     */
    public void setFdAddress(String fdAddress) {
        this.fdAddress = fdAddress;
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
     * @return fd_hourlyrate 
     */
    public Long getFdHourlyrate() {
        return fdHourlyrate;
    }

    /**
     * 
     * @param fdHourlyrate 
     */
    public void setFdHourlyrate(Long fdHourlyrate) {
        this.fdHourlyrate = fdHourlyrate;
    }

    /**
     * 
     * @return fd_cappingfees 
     */
    public Long getFdCappingfees() {
        return fdCappingfees;
    }

    /**
     * 
     * @param fdCappingfees 
     */
    public void setFdCappingfees(Long fdCappingfees) {
        this.fdCappingfees = fdCappingfees;
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
     * @return fd_hourlyrate2 
     */
    public Long getFdHourlyrate2() {
        return fdHourlyrate2;
    }

    /**
     * 
     * @param fdHourlyrate2 
     */
    public void setFdHourlyrate2(Long fdHourlyrate2) {
        this.fdHourlyrate2 = fdHourlyrate2;
    }

    /**
     * 
     * @return fd_cappingfees2 
     */
    public Long getFdCappingfees2() {
        return fdCappingfees2;
    }

    /**
     * 
     * @param fdCappingfees2 
     */
    public void setFdCappingfees2(Long fdCappingfees2) {
        this.fdCappingfees2 = fdCappingfees2;
    }

    /**
     * 
     * @return fd_servicecharge 
     */
    public Long getFdServicecharge() {
        return fdServicecharge;
    }

    /**
     * 
     * @param fdServicecharge 
     */
    public void setFdServicecharge(Long fdServicecharge) {
        this.fdServicecharge = fdServicecharge;
    }

    /**
     * 
     * @return fd_servicecharge2 
     */
    public Long getFdServicecharge2() {
        return fdServicecharge2;
    }

    /**
     * 
     * @param fdServicecharge2 
     */
    public void setFdServicecharge2(Long fdServicecharge2) {
        this.fdServicecharge2 = fdServicecharge2;
    }

    /**
     * 
     * @return fd_servicedescription 
     */
    public String getFdServicedescription() {
        return fdServicedescription;
    }

    /**
     * 
     * @param fdServicedescription 
     */
    public void setFdServicedescription(String fdServicedescription) {
        this.fdServicedescription = fdServicedescription;
    }

    /**
     * 
     * @return fd_otherid 
     */
    public String getFdOtherid() {
        return fdOtherid;
    }

    /**
     * 
     * @param fdOtherid 
     */
    public void setFdOtherid(String fdOtherid) {
        this.fdOtherid = fdOtherid;
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
     * @return fd_typeid 
     */
    public Long getFdTypeid() {
        return fdTypeid;
    }

    /**
     * 
     * @param fdTypeid 
     */
    public void setFdTypeid(Long fdTypeid) {
        this.fdTypeid = fdTypeid;
    }
}