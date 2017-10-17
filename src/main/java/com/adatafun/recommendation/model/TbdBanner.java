/**
 * TbdBanner.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-10-16 17:30:56 Created By wzt
*/
package com.adatafun.recommendation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * TbdBanner.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * 2017-10-16 17:30:56 Created By wzt
*/
@ApiModel(value="TbdBanner",description="tbd_banner")
public class TbdBanner {
    @ApiModelProperty(value="",name="fdCode")
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    @ApiModelProperty(value="",name="title")
    private String title;

    @ApiModelProperty(value="",name="type")
    private String type;

    @ApiModelProperty(value="",name="imageUrl")
    private String imageUrl;

    @ApiModelProperty(value="",name="url")
    private String url;

    @ApiModelProperty(value="",name="airportCode")
    private String airportCode;

    @ApiModelProperty(value="",name="createTime")
    private Date createTime;

    @ApiModelProperty(value="",name="remarks")
    private String remarks;

    @ApiModelProperty(value="",name="status")
    private String status;

    @ApiModelProperty(value="",name="sort")
    private Integer sort;

    @ApiModelProperty(value="",name="descriptionStr")
    private String descriptionStr;

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
     * @return type 
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return image_url 
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 
     * @param imageUrl 
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
     * @return airport_code 
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * 
     * @param airportCode 
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
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
     * @return remarks 
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 
     * @param remarks 
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 
     * @return status 
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return sort 
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 
     * @param sort 
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 
     * @return description_str 
     */
    public String getDescriptionStr() {
        return descriptionStr;
    }

    /**
     * 
     * @param descriptionStr 
     */
    public void setDescriptionStr(String descriptionStr) {
        this.descriptionStr = descriptionStr;
    }
}