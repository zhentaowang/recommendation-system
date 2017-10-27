/**
 * ItdCar.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-10-23 14:56:03 Created By wzt
*/
package com.adatafun.recommendation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ItdCar.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-10-23 14:56:03 Created By wzt
*/
@ApiModel(value="ItdCar",description="itd_car")
public class ItdCar {
    @ApiModelProperty(value="",name="fdId")
    private Long fdId;

    @ApiModelProperty(value="",name="fdCode")
    private String fdCode;

    @ApiModelProperty(value="",name="fdName")
    private String fdName;

    @ApiModelProperty(value="",name="fdScode")
    private String fdScode;

    @ApiModelProperty(value="",name="fdType")
    private Long fdType;

    @ApiModelProperty(value="",name="fdCars")
    private String fdCars;

    @ApiModelProperty(value="",name="fdCarsname")
    private String fdCarsname;

    @ApiModelProperty(value="",name="fdCitycode")
    private String fdCitycode;

    @ApiModelProperty(value="",name="fdCityname")
    private String fdCityname;

    @ApiModelProperty(value="",name="fdAreacode")
    private String fdAreacode;

    @ApiModelProperty(value="",name="fdAreaname")
    private String fdAreaname;

    @ApiModelProperty(value="",name="fdWithmiles")
    private Long fdWithmiles;

    @ApiModelProperty(value="",name="fdCurrencytype")
    private String fdCurrencytype;

    @ApiModelProperty(value="",name="fdStartingprice")
    private Long fdStartingprice;

    @ApiModelProperty(value="",name="fdKilometreprice")
    private Long fdKilometreprice;

    @ApiModelProperty(value="",name="fdCurrencytype2")
    private String fdCurrencytype2;

    @ApiModelProperty(value="",name="fdStartingprice2")
    private Long fdStartingprice2;

    @ApiModelProperty(value="",name="fdKilometreprice2")
    private Long fdKilometreprice2;

    @ApiModelProperty(value="",name="fdDel")
    private Long fdDel;

    @ApiModelProperty(value="",name="fdLg")
    private String fdLg;

    @ApiModelProperty(value="",name="fdLimit")
    private Long fdLimit;

    @ApiModelProperty(value="",name="fdNightfee")
    private Long fdNightfee;

    @ApiModelProperty(value="",name="fdNstart")
    private Date fdNstart;

    @ApiModelProperty(value="",name="fdNend")
    private Date fdNend;

    @ApiModelProperty(value="",name="fdShowapp")
    private Long fdShowapp;

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
     * @return fd_cars 
     */
    public String getFdCars() {
        return fdCars;
    }

    /**
     * 
     * @param fdCars 
     */
    public void setFdCars(String fdCars) {
        this.fdCars = fdCars;
    }

    /**
     * 
     * @return fd_carsname 
     */
    public String getFdCarsname() {
        return fdCarsname;
    }

    /**
     * 
     * @param fdCarsname 
     */
    public void setFdCarsname(String fdCarsname) {
        this.fdCarsname = fdCarsname;
    }

    /**
     * 
     * @return fd_citycode 
     */
    public String getFdCitycode() {
        return fdCitycode;
    }

    /**
     * 
     * @param fdCitycode 
     */
    public void setFdCitycode(String fdCitycode) {
        this.fdCitycode = fdCitycode;
    }

    /**
     * 
     * @return fd_cityname 
     */
    public String getFdCityname() {
        return fdCityname;
    }

    /**
     * 
     * @param fdCityname 
     */
    public void setFdCityname(String fdCityname) {
        this.fdCityname = fdCityname;
    }

    /**
     * 
     * @return fd_areacode 
     */
    public String getFdAreacode() {
        return fdAreacode;
    }

    /**
     * 
     * @param fdAreacode 
     */
    public void setFdAreacode(String fdAreacode) {
        this.fdAreacode = fdAreacode;
    }

    /**
     * 
     * @return fd_areaname 
     */
    public String getFdAreaname() {
        return fdAreaname;
    }

    /**
     * 
     * @param fdAreaname 
     */
    public void setFdAreaname(String fdAreaname) {
        this.fdAreaname = fdAreaname;
    }

    /**
     * 
     * @return fd_withmiles 
     */
    public Long getFdWithmiles() {
        return fdWithmiles;
    }

    /**
     * 
     * @param fdWithmiles 
     */
    public void setFdWithmiles(Long fdWithmiles) {
        this.fdWithmiles = fdWithmiles;
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
     * @return fd_startingprice 
     */
    public Long getFdStartingprice() {
        return fdStartingprice;
    }

    /**
     * 
     * @param fdStartingprice 
     */
    public void setFdStartingprice(Long fdStartingprice) {
        this.fdStartingprice = fdStartingprice;
    }

    /**
     * 
     * @return fd_kilometreprice 
     */
    public Long getFdKilometreprice() {
        return fdKilometreprice;
    }

    /**
     * 
     * @param fdKilometreprice 
     */
    public void setFdKilometreprice(Long fdKilometreprice) {
        this.fdKilometreprice = fdKilometreprice;
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
     * @return fd_startingprice2 
     */
    public Long getFdStartingprice2() {
        return fdStartingprice2;
    }

    /**
     * 
     * @param fdStartingprice2 
     */
    public void setFdStartingprice2(Long fdStartingprice2) {
        this.fdStartingprice2 = fdStartingprice2;
    }

    /**
     * 
     * @return fd_kilometreprice2 
     */
    public Long getFdKilometreprice2() {
        return fdKilometreprice2;
    }

    /**
     * 
     * @param fdKilometreprice2 
     */
    public void setFdKilometreprice2(Long fdKilometreprice2) {
        this.fdKilometreprice2 = fdKilometreprice2;
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
     * @return fd_limit 
     */
    public Long getFdLimit() {
        return fdLimit;
    }

    /**
     * 
     * @param fdLimit 
     */
    public void setFdLimit(Long fdLimit) {
        this.fdLimit = fdLimit;
    }

    /**
     * 
     * @return fd_nightfee 
     */
    public Long getFdNightfee() {
        return fdNightfee;
    }

    /**
     * 
     * @param fdNightfee 
     */
    public void setFdNightfee(Long fdNightfee) {
        this.fdNightfee = fdNightfee;
    }

    /**
     * 
     * @return fd_nstart 
     */
    public Date getFdNstart() {
        return fdNstart;
    }

    /**
     * 
     * @param fdNstart 
     */
    public void setFdNstart(Date fdNstart) {
        this.fdNstart = fdNstart;
    }

    /**
     * 
     * @return fd_nend 
     */
    public Date getFdNend() {
        return fdNend;
    }

    /**
     * 
     * @param fdNend 
     */
    public void setFdNend(Date fdNend) {
        this.fdNend = fdNend;
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
}