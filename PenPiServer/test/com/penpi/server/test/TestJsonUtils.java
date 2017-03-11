package com.penpi.server.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.penpi.server.domain.Order;
import com.penpi.server.domain.User;
import com.penpi.server.unuser.GsonUtils;
import com.penpi.server.utils.JacksonUtils;

public class TestJsonUtils {

	@Test
	public void fun1() {
		User user = new User();
		user.setUsername("1231");
		user.setGender("男");

		Order order = new Order();
		order.setCharges(123.55);
		// user.getSendOrders().add(order);

		List<User> users = new ArrayList<User>();
		users.add(user);
		users.add(user);
		users.add(user);
		// 对象转换为Json
		String jsonData = GsonUtils.toJson(users);
		System.out.println(jsonData);

		// Json转换为对象
		// User userFromJson = GsonUtils.fromJson(jsonData, User.class);
		// System.out.println(userFromJson);
		// Set<Order> sendOrders = userFromJson.getSendOrders();
		// System.out.println(sendOrders);
		//
		// System.out.println(GsonUtils.toJson(false));
	}

	@Test
	public void fun2() {
		User user = new User();
		user.setUsername("1231");
		user.setGender("男");

		Order order = new Order();
		order.setCharges(123.55);
		user.getSendOrders().add(order);

		// List<User> users = new ArrayList<User>();
		// users.add(user);
		// users.add(user);
		// users.add(user);
		System.out.println(JacksonUtils.writeJSON(user));
	}
	
	@Test
	public void fun3() {
		String jsonData = JacksonUtils.writeJSON("55555");
		System.out.println(jsonData);
	}
}
