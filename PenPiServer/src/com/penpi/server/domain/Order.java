package com.penpi.server.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Order {
	private Integer orderID;

	private String startPlace;
	private String endPlace;

	private String latLngStrat; // 发单地址的经纬度
	private String latLngEnd; // 收货地址的经纬度

	private User sendOrderPeople; // 发订单的人,外键
	private String sendOrderPeopleName; // 发订单的人名，即收件名
	private String sendOrderPeoplePhone; // 发订单的人的电话，即收件电话
	private Date sendOrderDate; // 发订单的时间

	private User takeOrderPeople; // 拿订单的人
	private Date takeOrderDate; // 拿订单的时间

	private Double charges;// 费用

	private String state; // 状态
	private String remark; // 备注
	private String evaluate; // 评论

	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", startPlace=" + startPlace
				+ ", endPlace=" + endPlace + ", latLngStrat=" + latLngStrat
				+ ", latLngEnd=" + latLngEnd + ", sendOrderPeople="
				+ sendOrderPeople + ", sendOrderPeopleName="
				+ sendOrderPeopleName + ", sendOrderPeoplePhone="
				+ sendOrderPeoplePhone + ", sendOrderDate=" + sendOrderDate
				+ ", takeOrderPeople=" + takeOrderPeople + ", takeOrderDate="
				+ takeOrderDate + ", charges=" + charges + ", state=" + state
				+ ", remark=" + remark + ", evaluate=" + evaluate + "]";
	}

	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getEndPlace() {
		return endPlace;
	}

	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}

	public String getLatLngStrat() {
		return latLngStrat;
	}

	public void setLatLngStrat(String latLngStrat) {
		this.latLngStrat = latLngStrat;
	}

	public String getLatLngEnd() {
		return latLngEnd;
	}

	public void setLatLngEnd(String latLngEnd) {
		this.latLngEnd = latLngEnd;
	}

	public User getSendOrderPeople() {
		return sendOrderPeople;
	}

	public void setSendOrderPeople(User sendOrderPeople) {
		this.sendOrderPeople = sendOrderPeople;
	}

	public String getSendOrderPeopleName() {
		return sendOrderPeopleName;
	}

	public void setSendOrderPeopleName(String sendOrderPeopleName) {
		this.sendOrderPeopleName = sendOrderPeopleName;
	}

	public String getSendOrderPeoplePhone() {
		return sendOrderPeoplePhone;
	}

	public void setSendOrderPeoplePhone(String sendOrderPeoplePhone) {
		this.sendOrderPeoplePhone = sendOrderPeoplePhone;
	}

	public Date getSendOrderDate() {
		return sendOrderDate;
	}

	public void setSendOrderDate(Date sendOrderDate) {
		this.sendOrderDate = sendOrderDate;
	}

	public User getTakeOrderPeople() {
		return takeOrderPeople;
	}

	public void setTakeOrderPeople(User takeOrderPeople) {
		this.takeOrderPeople = takeOrderPeople;
	}

	public Date getTakeOrderDate() {
		return takeOrderDate;
	}

	public void setTakeOrderDate(Date takeOrderDate) {
		this.takeOrderDate = takeOrderDate;
	}

	public Double getCharges() {
		return charges;
	}

	public void setCharges(Double charges) {
		this.charges = charges;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

}
