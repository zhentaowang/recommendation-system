/**
 * RecommendationRule.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-09-22 12:55:34 Created By wzt
*/
package com.adatafun.recommendation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * RecommendationRule.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
@ApiModel(value="RecommendationRule",description="recommendation_rule")
public class RecommendationRule {
    @ApiModelProperty(value="",name="ruleId", required=true)
    @NotEmpty
    private Long ruleId;

    @ApiModelProperty(value="",name="ruleName")
    private String ruleName;

    @ApiModelProperty(value="规则类型：0，地理位置；1，航班信息；2，优惠餐厅；3，用户行为",name="ruleType")
    private Byte ruleType;

    @ApiModelProperty(value="",name="typeName")
    private String typeName;

    @ApiModelProperty(value="类型权重",name="typeWeight")
    private Integer typeWeight;

    @ApiModelProperty(value="",name="ruleContent")
    private String ruleContent;

    /**
     * 
     * @return rule_id 
     */
    public Long getRuleId() {
        return ruleId;
    }

    /**
     * 
     * @param ruleId 
     */
    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * 
     * @return rule_name 
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * 
     * @param ruleName 
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * 规则类型：0，地理位置；1，航班信息；2，优惠餐厅；3，用户行为
     * @return rule_type 规则类型：0，地理位置；1，航班信息；2，优惠餐厅；3，用户行为
     */
    public Byte getRuleType() {
        return ruleType;
    }

    /**
     * 规则类型：0，地理位置；1，航班信息；2，优惠餐厅；3，用户行为
     * @param ruleType 规则类型：0，地理位置；1，航班信息；2，优惠餐厅；3，用户行为
     */
    public void setRuleType(Byte ruleType) {
        this.ruleType = ruleType;
    }

    /**
     * 
     * @return type_name 
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 
     * @param typeName 
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 类型权重
     * @return type_weight 类型权重
     */
    public Integer getTypeWeight() {
        return typeWeight;
    }

    /**
     * 类型权重
     * @param typeWeight 类型权重
     */
    public void setTypeWeight(Integer typeWeight) {
        this.typeWeight = typeWeight;
    }

    /**
     * 
     * @return rule_content 
     */
    public String getRuleContent() {
        return ruleContent;
    }

    /**
     * 
     * @param ruleContent 
     */
    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }
}