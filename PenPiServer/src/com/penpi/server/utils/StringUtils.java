package com.penpi.server.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

public class StringUtils {

	/*
	 * 从客户端接受Json字符串，待用GsonUtils解析
	 */
	public static String receive(HttpServletRequest request) {
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
}
