package gdin.com.penpi.bean;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/5.
 * 发布订单信息的一个bean
 */
public class Order implements Serializable {
    int id;
    String start_place;
    String end_place;
    String name;
    String phone_number;
    String charges;
    String remark;
    String state;       //订单的状态
    String date;

    public Order() {
    }

    public Order(String name, String start_place, String end_place, String charges, String date) {
        this.name = name;
        this.start_place = start_place;
        this.end_place = end_place;
        this.date = date;
        this.charges = charges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart_place() {
        return start_place;
    }

    public void setStart_place(String start_place) {
        this.start_place = start_place;
    }

    public String getEnd_place() {
        return end_place;
    }

    public void setEnd_place(String end_place) {
        this.end_place = end_place;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", start_place='" + start_place + '\'' +
                ", end_place='" + end_place + '\'' +
                ", name='" + name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", charges='" + charges + '\'' +
                ", remark='" + remark + '\'' +
                ", state='" + state + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (!start_place.equals(order.start_place)) return false;
        if (!end_place.equals(order.end_place)) return false;
        if (!name.equals(order.name)) return false;
        if (phone_number != null ? !phone_number.equals(order.phone_number) : order.phone_number != null)
            return false;
        if (!charges.equals(order.charges)) return false;
        if (remark != null ? !remark.equals(order.remark) : order.remark != null) return false;
        return state != null ? state.equals(order.state) : order.state == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + start_place.hashCode();
        result = 31 * result + end_place.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (phone_number != null ? phone_number.hashCode() : 0);
        result = 31 * result + charges.hashCode();
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    public JSONObject toJSONObj() {
        JSONObject orderJson = new JSONObject();
        try {
            orderJson.put("start_place", this.start_place);
            orderJson.put("end_place", this.end_place);
            orderJson.put("name", this.name);
            orderJson.put("phone_number", this.phone_number);
            orderJson.put("charges", this.charges);
            orderJson.put("remark", this.remark);
            orderJson.put("state", this.state);
            orderJson.put("date",this.date);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return orderJson;
    }

    /*
    * 判断Order是否是同个
    * 直接根据时间判断
    * */
}
