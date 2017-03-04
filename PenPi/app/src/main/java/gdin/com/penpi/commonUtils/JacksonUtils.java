package gdin.com.penpi.commonUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtils {
	/**
	 * 将java对象,map,list集合转换成json字符串
	 * 
	 * @param value 任意对象
	 * @return 返回String类型的json输数据
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
	 * @param <T> 类
	 */
	public static <T> T readJson(String jsonData, Class<T> type) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			return objectMapper.readValue(jsonData, type);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
