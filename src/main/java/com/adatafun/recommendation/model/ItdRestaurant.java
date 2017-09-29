/**
 * ItdRestaurant.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-09-20 17:28:49 Created By wzt
*/
package com.adatafun.recommendation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Transient;

/**
 * ItdRestaurant.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
@ApiModel(value="ItdRestaurant",description="itd_restaurant")
public class ItdRestaurant {
    @ApiModelProperty(value="",name="fdId")
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

    @ApiModelProperty(value="",name="fdContact")
    private String fdContact;

    @ApiModelProperty(value="",name="fdMode")
    private String fdMode;

    @ApiModelProperty(value="",name="fdEmail")
    private String fdEmail;

    @ApiModelProperty(value="",name="fdClass")
    private String fdClass;

    @ApiModelProperty(value="",name="fdBoardinggate")
    private String fdBoardinggate;

    @ApiModelProperty(value="",name="fdCity")
    private String fdCity;

    @ApiModelProperty(value="",name="fdCityid")
    private Long fdCityid;

    @ApiModelProperty(value="",name="fdAdtype")
    private Long fdAdtype;

    @ApiModelProperty(value="",name="fdAd")
    private String fdAd;

    @ApiModelProperty(value="",name="fdDtype")
    private String fdDtype;

    @ApiModelProperty(value="",name="fdNearestgate")
    private String fdNearestgate;

    @ApiModelProperty(value="",name="fdCompanyname")
    private String fdCompanyname;

    @ApiModelProperty(value="",name="fdCompanyaddress")
    private String fdCompanyaddress;

    @ApiModelProperty(value="",name="fdCompanyweb")
    private String fdCompanyweb;

    @ApiModelProperty(value="",name="fdIscoupon")
    private Long fdIscoupon;

    @ApiModelProperty(value="",name="fdIsflashsale")
    private Long fdIsflashsale;

    @ApiModelProperty(value="",name="fdTbigint")
    private String fdTbigint;

    @ApiModelProperty(value="",name="fdPrepaidcell")
    private String fdPrepaidcell;

    @ApiModelProperty(value="",name="fdShowapp")
    private Long fdShowapp;

    @ApiModelProperty(value="",name="fdFreeoffer")
    private Long fdFreeoffer;

    @ApiModelProperty(value="",name="fdSettlementdiscount")
    private Long fdSettlementdiscount;

    @ApiModelProperty(value="",name="fdCurrencys")
    private String fdCurrencys;

    @ApiModelProperty(value="",name="fdCouponnote")
    private String fdCouponnote;

    @ApiModelProperty(value="",name="fdFlashsalenote")
    private String fdFlashsalenote;

    @ApiModelProperty(value="",name="fdFreeoffernote")
    private String fdFreeoffernote;

    @ApiModelProperty(value="",name="fdSettlementdiscountnote")
    private String fdSettlementdiscountnote;

    @ApiModelProperty(value="",name="fdScode")
    private String fdScode;

    @ApiModelProperty(value="",name="fdMenuurl")
    private String fdMenuurl;

    @ApiModelProperty(value="",name="fdBrand")
    private String fdBrand;

    @Transient
    @ApiModelProperty(value="餐厅风格",name="fdCls")
    private String fdCls;


    private Double score;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getFdCls() {
        return fdCls;
    }

    public void setFdCls(String fdCls) {
        this.fdCls = fdCls;
    }

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
     * @return fd_contact 
     */
    public String getFdContact() {
        return fdContact;
    }

    /**
     * 
     * @param fdContact 
     */
    public void setFdContact(String fdContact) {
        this.fdContact = fdContact;
    }

    /**
     * 
     * @return fd_mode 
     */
    public String getFdMode() {
        return fdMode;
    }

    /**
     * 
     * @param fdMode 
     */
    public void setFdMode(String fdMode) {
        this.fdMode = fdMode;
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
     * @return fd_class 
     */
    public String getFdClass() {
        return fdClass;
    }

    /**
     * 
     * @param fdClass 
     */
    public void setFdClass(String fdClass) {
        this.fdClass = fdClass;
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
     * @return fd_city 
     */
    public String getFdCity() {
        return fdCity;
    }

    /**
     * 
     * @param fdCity 
     */
    public void setFdCity(String fdCity) {
        this.fdCity = fdCity;
    }

    /**
     * 
     * @return fd_cityid 
     */
    public Long getFdCityid() {
        return fdCityid;
    }

    /**
     * 
     * @param fdCityid 
     */
    public void setFdCityid(Long fdCityid) {
        this.fdCityid = fdCityid;
    }

    /**
     * 
     * @return fd_adtype 
     */
    public Long getFdAdtype() {
        return fdAdtype;
    }

    /**
     * 
     * @param fdAdtype 
     */
    public void setFdAdtype(Long fdAdtype) {
        this.fdAdtype = fdAdtype;
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
     * @return fd_companyname 
     */
    public String getFdCompanyname() {
        return fdCompanyname;
    }

    /**
     * 
     * @param fdCompanyname 
     */
    public void setFdCompanyname(String fdCompanyname) {
        this.fdCompanyname = fdCompanyname;
    }

    /**
     * 
     * @return fd_companyaddress 
     */
    public String getFdCompanyaddress() {
        return fdCompanyaddress;
    }

    /**
     * 
     * @param fdCompanyaddress 
     */
    public void setFdCompanyaddress(String fdCompanyaddress) {
        this.fdCompanyaddress = fdCompanyaddress;
    }

    /**
     * 
     * @return fd_companyweb 
     */
    public String getFdCompanyweb() {
        return fdCompanyweb;
    }

    /**
     * 
     * @param fdCompanyweb 
     */
    public void setFdCompanyweb(String fdCompanyweb) {
        this.fdCompanyweb = fdCompanyweb;
    }

    /**
     * 
     * @return fd_iscoupon 
     */
    public Long getFdIscoupon() {
        return fdIscoupon;
    }

    /**
     * 
     * @param fdIscoupon 
     */
    public void setFdIscoupon(Long fdIscoupon) {
        this.fdIscoupon = fdIscoupon;
    }

    /**
     * 
     * @return fd_isflashsale 
     */
    public Long getFdIsflashsale() {
        return fdIsflashsale;
    }

    /**
     * 
     * @param fdIsflashsale 
     */
    public void setFdIsflashsale(Long fdIsflashsale) {
        this.fdIsflashsale = fdIsflashsale;
    }

    /**
     * 
     * @return fd_tbigint 
     */
    public String getFdTbigint() {
        return fdTbigint;
    }

    /**
     * 
     * @param fdTbigint 
     */
    public void setFdTbigint(String fdTbigint) {
        this.fdTbigint = fdTbigint;
    }

    /**
     * 
     * @return fd_prepaidcell 
     */
    public String getFdPrepaidcell() {
        return fdPrepaidcell;
    }

    /**
     * 
     * @param fdPrepaidcell 
     */
    public void setFdPrepaidcell(String fdPrepaidcell) {
        this.fdPrepaidcell = fdPrepaidcell;
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
     * @return fd_freeoffer 
     */
    public Long getFdFreeoffer() {
        return fdFreeoffer;
    }

    /**
     * 
     * @param fdFreeoffer 
     */
    public void setFdFreeoffer(Long fdFreeoffer) {
        this.fdFreeoffer = fdFreeoffer;
    }

    /**
     * 
     * @return fd_settlementdiscount 
     */
    public Long getFdSettlementdiscount() {
        return fdSettlementdiscount;
    }

    /**
     * 
     * @param fdSettlementdiscount 
     */
    public void setFdSettlementdiscount(Long fdSettlementdiscount) {
        this.fdSettlementdiscount = fdSettlementdiscount;
    }

    /**
     * 
     * @return fd_currencys 
     */
    public String getFdCurrencys() {
        return fdCurrencys;
    }

    /**
     * 
     * @param fdCurrencys 
     */
    public void setFdCurrencys(String fdCurrencys) {
        this.fdCurrencys = fdCurrencys;
    }

    /**
     * 
     * @return fd_couponnote 
     */
    public String getFdCouponnote() {
        return fdCouponnote;
    }

    /**
     * 
     * @param fdCouponnote 
     */
    public void setFdCouponnote(String fdCouponnote) {
        this.fdCouponnote = fdCouponnote;
    }

    /**
     * 
     * @return fd_flashsalenote 
     */
    public String getFdFlashsalenote() {
        return fdFlashsalenote;
    }

    /**
     * 
     * @param fdFlashsalenote 
     */
    public void setFdFlashsalenote(String fdFlashsalenote) {
        this.fdFlashsalenote = fdFlashsalenote;
    }

    /**
     * 
     * @return fd_freeoffernote 
     */
    public String getFdFreeoffernote() {
        return fdFreeoffernote;
    }

    /**
     * 
     * @param fdFreeoffernote 
     */
    public void setFdFreeoffernote(String fdFreeoffernote) {
        this.fdFreeoffernote = fdFreeoffernote;
    }

    /**
     * 
     * @return fd_settlementdiscountnote 
     */
    public String getFdSettlementdiscountnote() {
        return fdSettlementdiscountnote;
    }

    /**
     * 
     * @param fdSettlementdiscountnote 
     */
    public void setFdSettlementdiscountnote(String fdSettlementdiscountnote) {
        this.fdSettlementdiscountnote = fdSettlementdiscountnote;
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
     * @return fd_menuurl 
     */
    public String getFdMenuurl() {
        return fdMenuurl;
    }

    /**
     * 
     * @param fdMenuurl 
     */
    public void setFdMenuurl(String fdMenuurl) {
        this.fdMenuurl = fdMenuurl;
    }

    /**
     * 
     * @return fd_brand 
     */
    public String getFdBrand() {
        return fdBrand;
    }

    /**
     * 
     * @param fdBrand 
     */
    public void setFdBrand(String fdBrand) {
        this.fdBrand = fdBrand;
    }
}