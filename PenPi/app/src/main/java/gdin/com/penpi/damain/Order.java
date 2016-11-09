package gdin.com.penpi.damain;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/5.
 */
public class Order {
    String start_plac;
    String end_plac;
    String name;
    String phone_number;
    String remark;

    public String getEnd_plac() {
        return end_plac;
    }

    public void setEnd_plac(String end_plac) {
        this.end_plac = end_plac;
    }

    public String getStart_plac() {
        return start_plac;
    }

    public void setStart_plac(String start_plac) {
        this.start_plac = start_plac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Order [start_plac=" + start_plac + ", end_plac=" + end_plac
                + ", name=" + name + ", phone_number=" + phone_number
                + ", remark=" + remark + "]";
    }

    public JSONObject toJSONObj() {
        JSONObject orderJson = new JSONObject();
        try {
            orderJson.put("start_plac", this.start_plac);
            orderJson.put("end_plac", this.end_plac);
            orderJson.put("name", this.name);
            orderJson.put("phone_number", this.phone_number);
            orderJson.put("remark", this.remark);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderJson;
    }
}
