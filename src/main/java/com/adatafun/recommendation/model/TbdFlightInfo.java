/**
 * TbdFlightInfo.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-09-21 13:53:06 Created By wzt
*/
package com.adatafun.recommendation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * TbdFlightInfo.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-09-21 13:53:06 Created By wzt
*/
@ApiModel(value="TbdFlightInfo",description="tbd_flight_info")
public class TbdFlightInfo {
    @ApiModelProperty(value="",name="id", required=true)
    @NotEmpty
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    @ApiModelProperty(value="",name="flightNo")
    private String flightNo;

    @ApiModelProperty(value="",name="flightDate")
    private Date flightDate;

    @ApiModelProperty(value="",name="flightCategory")
    private String flightCategory;

    @ApiModelProperty(value="",name="airlineName")
    private String airlineName;

    @ApiModelProperty(value="",name="airportDepartCode")
    private String airportDepartCode;

    @ApiModelProperty(value="",name="airportDepartIata")
    private String airportDepartIata;

    @ApiModelProperty(value="",name="airportDepartName")
    private String airportDepartName;

    @ApiModelProperty(value="",name="airportArriveCode")
    private String airportArriveCode;

    @ApiModelProperty(value="",name="airportArriveIata")
    private String airportArriveIata;

    @ApiModelProperty(value="",name="airportArriveName")
    private String airportArriveName;

    @ApiModelProperty(value="",name="boardingGate")
    private String boardingGate;

    @ApiModelProperty(value="",name="terminalDepart")
    private String terminalDepart;

    @ApiModelProperty(value="",name="terminalArrive")
    private String terminalArrive;

    @ApiModelProperty(value="",name="timezoneDepart")
    private String timezoneDepart;

    @ApiModelProperty(value="",name="timezoneArrive")
    private String timezoneArrive;

    @ApiModelProperty(value="",name="cityDepartName")
    private String cityDepartName;

    @ApiModelProperty(value="",name="cityArriveName")
    private String cityArriveName;

    @ApiModelProperty(value="",name="departTimePlan")
    private Date departTimePlan;

    @ApiModelProperty(value="",name="arriveTimePlan")
    private Date arriveTimePlan;

    @ApiModelProperty(value="",name="departTimePredict")
    private Date departTimePredict;

    @ApiModelProperty(value="",name="arriveTimePredict")
    private Date arriveTimePredict;

    @ApiModelProperty(value="",name="departTimeActual")
    private Date departTimeActual;

    @ApiModelProperty(value="",name="arriveTimeActual")
    private Date arriveTimeActual;

    @ApiModelProperty(value="",name="boardingStatus")
    private String boardingStatus;

    @ApiModelProperty(value="",name="flightStatus")
    private String flightStatus;

    @ApiModelProperty(value="",name="alternateInfo")
    private String alternateInfo;

    @ApiModelProperty(value="",name="fillFlightNo")
    private String fillFlightNo;

    @ApiModelProperty(value="",name="currentStatus")
    private String currentStatus;

    @ApiModelProperty(value="",name="currentStatusCode")
    private String currentStatusCode;

    @ApiModelProperty(value="",name="centerId")
    private String centerId;

    @ApiModelProperty(value="",name="setType")
    private String setType;

    @ApiModelProperty(value="",name="toType")
    private String toType;

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
     * @return flight_no 
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * 
     * @param flightNo 
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * 
     * @return flight_date 
     */
    public Date getFlightDate() {
        return flightDate;
    }

    /**
     * 
     * @param flightDate 
     */
    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    /**
     * 
     * @return flight_category 
     */
    public String getFlightCategory() {
        return flightCategory;
    }

    /**
     * 
     * @param flightCategory 
     */
    public void setFlightCategory(String flightCategory) {
        this.flightCategory = flightCategory;
    }

    /**
     * 
     * @return airline_name 
     */
    public String getAirlineName() {
        return airlineName;
    }

    /**
     * 
     * @param airlineName 
     */
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    /**
     * 
     * @return airport_depart_code 
     */
    public String getAirportDepartCode() {
        return airportDepartCode;
    }

    /**
     * 
     * @param airportDepartCode 
     */
    public void setAirportDepartCode(String airportDepartCode) {
        this.airportDepartCode = airportDepartCode;
    }

    /**
     * 
     * @return airport_depart_iata 
     */
    public String getAirportDepartIata() {
        return airportDepartIata;
    }

    /**
     * 
     * @param airportDepartIata 
     */
    public void setAirportDepartIata(String airportDepartIata) {
        this.airportDepartIata = airportDepartIata;
    }

    /**
     * 
     * @return airport_depart_name 
     */
    public String getAirportDepartName() {
        return airportDepartName;
    }

    /**
     * 
     * @param airportDepartName 
     */
    public void setAirportDepartName(String airportDepartName) {
        this.airportDepartName = airportDepartName;
    }

    /**
     * 
     * @return airport_arrive_code 
     */
    public String getAirportArriveCode() {
        return airportArriveCode;
    }

    /**
     * 
     * @param airportArriveCode 
     */
    public void setAirportArriveCode(String airportArriveCode) {
        this.airportArriveCode = airportArriveCode;
    }

    /**
     * 
     * @return airport_arrive_iata 
     */
    public String getAirportArriveIata() {
        return airportArriveIata;
    }

    /**
     * 
     * @param airportArriveIata 
     */
    public void setAirportArriveIata(String airportArriveIata) {
        this.airportArriveIata = airportArriveIata;
    }

    /**
     * 
     * @return airport_arrive_name 
     */
    public String getAirportArriveName() {
        return airportArriveName;
    }

    /**
     * 
     * @param airportArriveName 
     */
    public void setAirportArriveName(String airportArriveName) {
        this.airportArriveName = airportArriveName;
    }

    /**
     * 
     * @return boarding_gate 
     */
    public String getBoardingGate() {
        return boardingGate;
    }

    /**
     * 
     * @param boardingGate 
     */
    public void setBoardingGate(String boardingGate) {
        this.boardingGate = boardingGate;
    }

    /**
     * 
     * @return terminal_depart 
     */
    public String getTerminalDepart() {
        return terminalDepart;
    }

    /**
     * 
     * @param terminalDepart 
     */
    public void setTerminalDepart(String terminalDepart) {
        this.terminalDepart = terminalDepart;
    }

    /**
     * 
     * @return terminal_arrive 
     */
    public String getTerminalArrive() {
        return terminalArrive;
    }

    /**
     * 
     * @param terminalArrive 
     */
    public void setTerminalArrive(String terminalArrive) {
        this.terminalArrive = terminalArrive;
    }

    /**
     * 
     * @return timezone_depart 
     */
    public String getTimezoneDepart() {
        return timezoneDepart;
    }

    /**
     * 
     * @param timezoneDepart 
     */
    public void setTimezoneDepart(String timezoneDepart) {
        this.timezoneDepart = timezoneDepart;
    }

    /**
     * 
     * @return timezone_arrive 
     */
    public String getTimezoneArrive() {
        return timezoneArrive;
    }

    /**
     * 
     * @param timezoneArrive 
     */
    public void setTimezoneArrive(String timezoneArrive) {
        this.timezoneArrive = timezoneArrive;
    }

    /**
     * 
     * @return city_depart_name 
     */
    public String getCityDepartName() {
        return cityDepartName;
    }

    /**
     * 
     * @param cityDepartName 
     */
    public void setCityDepartName(String cityDepartName) {
        this.cityDepartName = cityDepartName;
    }

    /**
     * 
     * @return city_arrive_name 
     */
    public String getCityArriveName() {
        return cityArriveName;
    }

    /**
     * 
     * @param cityArriveName 
     */
    public void setCityArriveName(String cityArriveName) {
        this.cityArriveName = cityArriveName;
    }

    /**
     * 
     * @return depart_time_plan 
     */
    public Date getDepartTimePlan() {
        return departTimePlan;
    }

    /**
     * 
     * @param departTimePlan 
     */
    public void setDepartTimePlan(Date departTimePlan) {
        this.departTimePlan = departTimePlan;
    }

    /**
     * 
     * @return arrive_time_plan 
     */
    public Date getArriveTimePlan() {
        return arriveTimePlan;
    }

    /**
     * 
     * @param arriveTimePlan 
     */
    public void setArriveTimePlan(Date arriveTimePlan) {
        this.arriveTimePlan = arriveTimePlan;
    }

    /**
     * 
     * @return depart_time_predict 
     */
    public Date getDepartTimePredict() {
        return departTimePredict;
    }

    /**
     * 
     * @param departTimePredict 
     */
    public void setDepartTimePredict(Date departTimePredict) {
        this.departTimePredict = departTimePredict;
    }

    /**
     * 
     * @return arrive_time_predict 
     */
    public Date getArriveTimePredict() {
        return arriveTimePredict;
    }

    /**
     * 
     * @param arriveTimePredict 
     */
    public void setArriveTimePredict(Date arriveTimePredict) {
        this.arriveTimePredict = arriveTimePredict;
    }

    /**
     * 
     * @return depart_time_actual 
     */
    public Date getDepartTimeActual() {
        return departTimeActual;
    }

    /**
     * 
     * @param departTimeActual 
     */
    public void setDepartTimeActual(Date departTimeActual) {
        this.departTimeActual = departTimeActual;
    }

    /**
     * 
     * @return arrive_time_actual 
     */
    public Date getArriveTimeActual() {
        return arriveTimeActual;
    }

    /**
     * 
     * @param arriveTimeActual 
     */
    public void setArriveTimeActual(Date arriveTimeActual) {
        this.arriveTimeActual = arriveTimeActual;
    }

    /**
     * 
     * @return boarding_status 
     */
    public String getBoardingStatus() {
        return boardingStatus;
    }

    /**
     * 
     * @param boardingStatus 
     */
    public void setBoardingStatus(String boardingStatus) {
        this.boardingStatus = boardingStatus;
    }

    /**
     * 
     * @return flight_status 
     */
    public String getFlightStatus() {
        return flightStatus;
    }

    /**
     * 
     * @param flightStatus 
     */
    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    /**
     * 
     * @return alternate_info 
     */
    public String getAlternateInfo() {
        return alternateInfo;
    }

    /**
     * 
     * @param alternateInfo 
     */
    public void setAlternateInfo(String alternateInfo) {
        this.alternateInfo = alternateInfo;
    }

    /**
     * 
     * @return fill_flight_no 
     */
    public String getFillFlightNo() {
        return fillFlightNo;
    }

    /**
     * 
     * @param fillFlightNo 
     */
    public void setFillFlightNo(String fillFlightNo) {
        this.fillFlightNo = fillFlightNo;
    }

    /**
     * 
     * @return current_status 
     */
    public String getCurrentStatus() {
        return currentStatus;
    }

    /**
     * 
     * @param currentStatus 
     */
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * 
     * @return current_status_code 
     */
    public String getCurrentStatusCode() {
        return currentStatusCode;
    }

    /**
     * 
     * @param currentStatusCode 
     */
    public void setCurrentStatusCode(String currentStatusCode) {
        this.currentStatusCode = currentStatusCode;
    }

    /**
     * 
     * @return center_id 
     */
    public String getCenterId() {
        return centerId;
    }

    /**
     * 
     * @param centerId 
     */
    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    /**
     * 
     * @return set_type 
     */
    public String getSetType() {
        return setType;
    }

    /**
     * 
     * @param setType 
     */
    public void setSetType(String setType) {
        this.setType = setType;
    }

    /**
     * 
     * @return to_type 
     */
    public String getToType() {
        return toType;
    }

    /**
     * 
     * @param toType 
     */
    public void setToType(String toType) {
        this.toType = toType;
    }
}