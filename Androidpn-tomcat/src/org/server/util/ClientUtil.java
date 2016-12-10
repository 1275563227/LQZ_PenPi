package org.server.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.server.domain.Order;
import org.server.domain.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClientUtil {

	public static String receive(HttpServletRequest request) {
		/**
		 * 接受JSON
		 */
		StringBuffer sb = new StringBuffer("");
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			result = sb.toString();

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 解析JSON
	public static User JsontoUser(String result) {
		try {
//			User user = new User();
//			Gson gson = new Gson();
//			List<User> userList = gson.fromJson(result,
//					new TypeToken<List<User>>() {
//					}.getType());
//			for (User userTemp : userList) {
//				user.setUsername(userTemp.getUsername());
//				user.setPhone_number(userTemp.getPhone_number());
//				user.setPassword(userTemp.getPassword());
//			}
			Gson gson = new Gson();
			User user = gson.fromJson(result, User.class);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Order JsontoOrder(String result) {
		try {
			Gson gson = new Gson();
			Order order = gson.fromJson(result, Order.class);
			return order;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
