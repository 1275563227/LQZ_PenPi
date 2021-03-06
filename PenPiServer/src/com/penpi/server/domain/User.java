package com.penpi.server.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class User {
	private Integer userID;

	private String username;
	private String password;
	private String phoneNumber;
	private String gender; // 性别
	private String authority; // 权限
	private Date loginTime; // 登录时间
	private Date logoutTime; // 退出时间

	// 在1 的一方,表达持有多的一方的引用=>使用集合
	@JsonIgnore
	private Set<Order> sendOrders = new HashSet<Order>();
	@JsonIgnore
	private Set<Order> takeOrders = new HashSet<Order>();

	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username
				+ ", password=" + password + ", phoneNumber=" + phoneNumber
				+ ", gender=" + gender + ", authority=" + authority
				+ ", loginTime=" + loginTime + ", logoutTime=" + logoutTime
				+ ", sendOrders=" + null + ", takeOrders=" + null
				+ "]";
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public Set<Order> getSendOrders() {
		return sendOrders;
	}

	public void setSendOrders(Set<Order> sendOrders) {
		this.sendOrders = sendOrders;
	}

	public Set<Order> getTakeOrders() {
		return takeOrders;
	}

	public void setTakeOrders(Set<Order> takeOrders) {
		this.takeOrders = takeOrders;
	}
}