package gdin.com.penpi.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.List;

import gdin.com.penpi.bean.Order;

/**
 * Created by Administrator on 2016/11/12.
 */
public class ClientUtils {
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
