package gdin.com.penpi.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/5.
 * 发布订单信息的一个bean
 */
public class Order {
    int id;
    String start_place;
    String end_place;
    String name;
    String phone_number;
    String charges;
    String remark;
    String state;       //订单的状态

    public Order() {
    }

    public Order(String name, String start_place, String end_place, String charges) {
        this.start_place = start_place;
        this.end_place = end_place;
        this.name = name;
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
                '}';
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderJson;
    }
}
