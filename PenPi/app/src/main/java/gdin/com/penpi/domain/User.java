package gdin.com.penpi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Created by chen on 2016/11/13.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer userID;

    private String username;
    private String password;
    private Integer phoneNumber;
    private String gender; // 性别
    private String authority; // 权限
    private Date loginTime; // 登录时间
    private Date logoutTime; // 退出时间

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", gender='" + gender + '\'' +
                ", authority='" + authority + '\'' +
                ", loginTime=" + loginTime +
                ", logoutTime=" + logoutTime +
                '}';
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

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
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
}
