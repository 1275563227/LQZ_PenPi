package gdin.com.penpi.mapUtil;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gdin.com.penpi.damain.Order;

public class Utils {

	/**
	 * 在屏幕中央显示一个Toast
	 * @param text
	 */
	public static void showToast(Context context, CharSequence text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}



	public static String sendGet(String url) {
		String response = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();

			// Get方法
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 请求和响应都成功
				HttpEntity httpEntity = httpResponse.getEntity();
				response = EntityUtils.toString(httpEntity, "uft-8");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String sendPost(String url, Order order) {

		String response = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();

			// Post方法
			HttpPost httpPost = new HttpPost(url);

            // 方法一
//			List<NameValuePair> params = new ArrayList<>();
//			params.add(new BasicNameValuePair("name", "admin"));
//			params.add(new BasicNameValuePair("pass", "123"));
//			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
//          httpPost.setEntity(entity);

            // 方法二
            JSONObject orderJSON = order.toJSONObj();
            httpPost.setEntity(new StringEntity(orderJSON.toString(), "UTF-8"));

			HttpResponse httpResponse = httpClient.execute(httpPost);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 请求和响应都成功
				HttpEntity httpEntity = httpResponse.getEntity();
				response = EntityUtils.toString(httpEntity, "uft-8");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static Order paresJSON_withGSON(String result) {
		Order order = new Order();
		Gson gson = new Gson();
		try {
			List<Order> orderList = gson.fromJson(result,
					new TypeToken<List<Order>>() {
					}.getType());

			for (Order order2 : orderList) {
				order.setStart_plac(order2.getStart_plac());
				order.setEnd_plac(order2.getEnd_plac());
				order.setName(order2.getName());
				order.setPhone_number(order2.getPhone_number());
				order.setRemark(order2.getRemark());
			}

			System.out.println(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}
}
