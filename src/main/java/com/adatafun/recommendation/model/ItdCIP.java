/**
 * ItdCIP.java
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
 * ItdCIP.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-10-23 14:56:03 Created By wzt
*/
@ApiModel(value="ItdCIP",description="itd_cip")
public class ItdCIP {
    @ApiModelProperty(value="",name="fdId")
    private Long fdId;

    @ApiModelProperty(value="",name="fdCode")
    private String fdCode;

    @ApiModelProperty(value="",name="fdName")
    private String fdName;

    @ApiModelProperty(value="",name="fdUsedescription")
    private String fdUsedescription;

    @ApiModelProperty(value="",name="fdLocation")
    private String fdLocation;

    @ApiModelProperty(value="",name="fdPrice")
    private Long fdPrice;

    @ApiModelProperty(value="",name="fdBatchprice")
    private Long fdBatchprice;

    @ApiModelProperty(value="",name="fdBatchnum")
    private Long fdBatchnum;

    @ApiModelProperty(value="",name="fdNote")
    private String fdNote;

    @ApiModelProperty(value="",name="fdSid")
    private Long fdSid;

    @ApiModelProperty(value="",name="fdLg")
    private String fdLg;

    @ApiModelProperty(value="",name="fdDel")
    private Long fdDel;

    @ApiModelProperty(value="",name="fdCurrencytype")
    private String fdCurrencytype;

    @ApiModelProperty(value="",name="fdBusinesshours")
    private String fdBusinesshours;

    @ApiModelProperty(value="",name="fdType")
    private String fdType;

    @ApiModelProperty(value="",name="fdVoucher")
    private String fdVoucher;

    @ApiModelProperty(value="",name="fdTerminal")
    private String fdTerminal;

    @ApiModelProperty(value="",name="fdFname")
    private String fdFname;

    @ApiModelProperty(value="",name="fdSerivce")
    private String fdSerivce;

    @ApiModelProperty(value="",name="fdCurrencytype2")
    private String fdCurrencytype2;

    @ApiModelProperty(value="",name="fdPrice2")
    private Long fdPrice2;

    @ApiModelProperty(value="",name="fdBatchprice2")
    private Long fdBatchprice2;

    @ApiModelProperty(value="",name="fdShowapp")
    private Long fdShowapp;

    @ApiModelProperty(value="",name="fdScode")
    private String fdScode;

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
     * @return fd_usedescription 
     */
    public String getFdUsedescription() {
        return fdUsedescription;
    }

    /**
     * 
     * @param fdUsedescription 
     */
    public void setFdUsedescription(String fdUsedescription) {
        this.fdUsedescription = fdUsedescription;
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
     * @return fd_batchprice 
     */
    public Long getFdBatchprice() {
        return fdBatchprice;
    }

    /**
     * 
     * @param fdBatchprice 
     */
    public void setFdBatchprice(Long fdBatchprice) {
        this.fdBatchprice = fdBatchprice;
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
     * @return fd_voucher 
     */
    public String getFdVoucher() {
        return fdVoucher;
    }

    /**
     * 
     * @param fdVoucher 
     */
    public void setFdVoucher(String fdVoucher) {
        this.fdVoucher = fdVoucher;
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
     * @return fd_fname 
     */
    public String getFdFname() {
        return fdFname;
    }

    /**
     * 
     * @param fdFname 
     */
    public void setFdFname(String fdFname) {
        this.fdFname = fdFname;
    }

    /**
     * 
     * @return fd_serivce 
     */
    public String getFdSerivce() {
        return fdSerivce;
    }

    /**
     * 
     * @param fdSerivce 
     */
    public void setFdSerivce(String fdSerivce) {
        this.fdSerivce = fdSerivce;
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
     * @return fd_batchprice2 
     */
    public Long getFdBatchprice2() {
        return fdBatchprice2;
    }

    /**
     * 
     * @param fdBatchprice2 
     */
    public void setFdBatchprice2(Long fdBatchprice2) {
        this.fdBatchprice2 = fdBatchprice2;
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
}