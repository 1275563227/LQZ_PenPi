package com.penpi.server.unuser;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GsonUtils {

	/**
	 * 对象转换成json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		return new Gson().toJson(obj);
	}

	/**
	 * 将json字符串转成对象
	 * 
	 * @param jsonData
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String jsonData, Class<T> type) {
		Gson gson = new Gson();
		return gson.fromJson(jsonData, type);
	}

	/**
	 * 将json数组解析成相应的映射对象列表
	 * 
	 * @param jsonData
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> fromJsons(String jsonData, Class<T> type) {
		Gson gson = new Gson();

		List<T> result = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(jsonData).getAsJsonArray();
		for (JsonElement elem : array) {
			result.add(gson.fromJson(elem, type));
		}
		// List<T> result = gson.fromJson(jsonData, new TypeToken<List<T>>() {
		// }.getType());
		return result;
	}
}
