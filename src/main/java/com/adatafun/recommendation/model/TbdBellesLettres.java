/**
 * TbdBellesLettres.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-10-19 20:47:52 Created By wzt
*/
package com.adatafun.recommendation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * TbdBellesLettres.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-10-19 20:47:52 Created By wzt
*/
@ApiModel(value="TbdBellesLettres",description="tbd_belles_lettres")
public class TbdBellesLettres {
    @ApiModelProperty(value="",name="id")
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    @ApiModelProperty(value="",name="title")
    private String title;

    @ApiModelProperty(value="",name="subtitle")
    private String subtitle;

    @ApiModelProperty(value="",name="createTime")
    private Date createTime;

    @ApiModelProperty(value="",name="updateTime")
    private Date updateTime;

    @ApiModelProperty(value="",name="createBy")
    private Long createBy;

    @ApiModelProperty(value="",name="imgUrl")
    private String imgUrl;

    @ApiModelProperty(value="",name="clickNum")
    private Long clickNum;

    @ApiModelProperty(value="",name="sort")
    private Long sort;

    @ApiModelProperty(value="",name="personLabels")
    private String personLabels;

    @ApiModelProperty(value="",name="productLabels")
    private String productLabels;

    @ApiModelProperty(value="",name="airportCodeLabels")
    private String airportCodeLabels;

    @ApiModelProperty(value="",name="url")
    private String url;

    @ApiModelProperty(value="",name="use1")
    private Long use1;

    @ApiModelProperty(value="",name="content")
    private String content;

    /**
     * 
     * @return id 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return title 
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return subtitle 
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * 
     * @param subtitle 
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * @return create_by 
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 
     * @param createBy 
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 
     * @return img_url 
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 
     * @param imgUrl 
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 
     * @return click_num 
     */
    public Long getClickNum() {
        return clickNum;
    }

    /**
     * 
     * @param clickNum 
     */
    public void setClickNum(Long clickNum) {
        this.clickNum = clickNum;
    }

    /**
     * 
     * @return sort 
     */
    public Long getSort() {
        return sort;
    }

    /**
     * 
     * @param sort 
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * 
     * @return person_labels 
     */
    public String getPersonLabels() {
        return personLabels;
    }

    /**
     * 
     * @param personLabels 
     */
    public void setPersonLabels(String personLabels) {
        this.personLabels = personLabels;
    }

    /**
     * 
     * @return product_labels 
     */
    public String getProductLabels() {
        return productLabels;
    }

    /**
     * 
     * @param productLabels 
     */
    public void setProductLabels(String productLabels) {
        this.productLabels = productLabels;
    }

    /**
     * 
     * @return airport_code_labels 
     */
    public String getAirportCodeLabels() {
        return airportCodeLabels;
    }

    /**
     * 
     * @param airportCodeLabels 
     */
    public void setAirportCodeLabels(String airportCodeLabels) {
        this.airportCodeLabels = airportCodeLabels;
    }

    /**
     * 
     * @return url 
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url 
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return use1 
     */
    public Long getUse1() {
        return use1;
    }

    /**
     * 
     * @param use1 
     */
    public void setUse1(Long use1) {
        this.use1 = use1;
    }

    /**
     * 
     * @return content 
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content 
     */
    public void setContent(String content) {
        this.content = content;
    }
}