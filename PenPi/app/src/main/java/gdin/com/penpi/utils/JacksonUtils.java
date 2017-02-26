package gdin.com.penpi.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtils {
	/**
	 * 将java对象,map,list集合转换成json字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String writeJSON(Object value) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		String jsonData = null;
		try {
			jsonData = objectMapper.writeValueAsString(value);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonData;
	}

	/**
	 * 将json字符串转换成JavaBean对象,List<Map>集合,Array数组,Map集合
	 * 详细参考：http://www.cnblogs.com/hoojo/archive/2011/04/22/2024628.html
	 * 
	 * @param <T>
	 */
	public static <T> T readJson(String jsonData, Class<T> type) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		T bean = null;
		try {
			bean = objectMapper.readValue(jsonData, type);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 安卓端从服务器读取json，现为测试
	 * 
	 * @param jsonData
	 * @param type
	 * @return
	 */
	public static <T> Map<String, Object> androidReadJson(String jsonData,
			Class<T> type) {
		// TODO
		// ----------测试--------------
		// logger.info(jsonData);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = JacksonUtils.readJson(jsonData, Map.class);
		// logger.info(Boolean.toString((Boolean)map.get("validate")));
		// logger.info((String)map.get("returnInfo"));
		if (map != null && map.size() > 0) {
			return map;
		}
		return null;

		// ----------客户端可如下解析--------------
		// User[] users = JacksonUtils.readJson((String) map.get("returnInfo"),
		// User[].class);
		// for (User user2 : users) {
		// System.out.println(user2);
		// }
		// List<User> list = Arrays.asList(users);
		// ---------------------------
	}
}
