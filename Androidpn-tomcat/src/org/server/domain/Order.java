package org.server.domain;

import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2016/11/5.
 */
public class Order {
	String id;
	String start_place;
	String end_place;
	String name;
	String phone_number;
	String charges;
	String remark;
	String state;
	String date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCharges() {
		return charges;
	}

	public void setCharges(String charges) {
		this.charges = charges;
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
		return "Order [id=" + id + ", start_place=" + start_place
				+ ", end_place=" + end_place + ", name=" + name
				+ ", phone_number=" + phone_number + ", charges=" + charges
				+ ", remark=" + remark + ", state=" + state + ", date=" + date
				+ "]";
	}

	public String toResponse(){
		return this.getId() + ","
				+ this.getStart_place() + ","
				+ this.getEnd_place() + ","
				+ this.getName() + ","
				+ this.getPhone_number() + ","
				+ this.getCharges() + ","
				+ this.getRemark() + ","
				+ this.getState() + ","
				+ this.getDate();
	}

	public JSONObject toJSONObj() {
		JSONObject orderJson = new JSONObject();
		try {
			orderJson.put("id", this.id);
			orderJson.put("start_plac", this.start_place);
			orderJson.put("end_plac", this.end_place);
			orderJson.put("name", this.name);
			orderJson.put("phone_number", this.phone_number);
			orderJson.put("charges", this.charges);
			orderJson.put("remark", this.remark);
			orderJson.put("state", this.state);
			orderJson.put("data", this.date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderJson;
	}
}
