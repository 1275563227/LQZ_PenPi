package gdin.com.penpi.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/5.
 * 发布订单信息的一个bean
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order{
    private Integer orderID;

    private String startPlace;
    private String endPlace;

    private User sendOrderPeple; // 发订单的人
    private Date sendOrderdate; // 发订单的时间

    private User takeOrderPeple; // 拿订单的人
    private Date takeOrderdate; // 拿订单的时间

    private Double charges;// 费用

    private String state; // 状态
    private String remark; // 评论

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", startPlace='" + startPlace + '\'' +
                ", endPlace='" + endPlace + '\'' +
                ", sendOrderPeple=" + sendOrderPeple +
                ", sendOrderdate=" + sendOrderdate +
                ", takeOrderPeple=" + takeOrderPeple +
                ", takeOrderdate=" + takeOrderdate +
                ", charges=" + charges +
                ", state='" + state + '\'' +
                ", remark='" + remark + '\'' +
                '}';
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

    public User getSendOrderPeple() {
        return sendOrderPeple;
    }

    public void setSendOrderPeple(User sendOrderPeple) {
        this.sendOrderPeple = sendOrderPeple;
    }

    public Date getSendOrderdate() {
        return sendOrderdate;
    }

    public void setSendOrderdate(Date sendOrderdate) {
        this.sendOrderdate = sendOrderdate;
    }

    public User getTakeOrderPeple() {
        return takeOrderPeple;
    }

    public void setTakeOrderPeple(User takeOrderPeple) {
        this.takeOrderPeple = takeOrderPeple;
    }

    public Date getTakeOrderdate() {
        return takeOrderdate;
    }

    public void setTakeOrderdate(Date takeOrderdate) {
        this.takeOrderdate = takeOrderdate;
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
}
