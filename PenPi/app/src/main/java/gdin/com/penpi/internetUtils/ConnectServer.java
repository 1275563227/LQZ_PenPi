package gdin.com.penpi.internetUtils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import gdin.com.penpi.commonUtils.JacksonUtils;

public class ConnectServer {

    private static final String baseURL = "http://123.207.245.81/penpi/";
//  private static final String baseURL = "http://192.168.1.168:8080/PenPiServer/";

    public static final String requestURL = baseURL + "forAndroid/";
    public static final String imgURL = baseURL + "upload/";

    public static String connect(String requestURL, Object value) {

        String response = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(requestURL);

            // 方法一
//			List<NameValuePair> params = new ArrayList<>();
//			params.add(new BasicNameValuePair("name", "admin"));
//			params.add(new BasicNameValuePair("pass", "123"));
//			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
//          httpPost.setEntity(entity);

            // 方法二
            if (value != null) {
                String jsonData = JacksonUtils.writeJSON(value);
                Log.i(ConnectServer.class.getName(), "sendData = " + jsonData);
                httpPost.setEntity(new StringEntity(jsonData, HTTP.UTF_8));
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);

            // 请求和响应都成功
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity, HTTP.UTF_8);
            }
            if (response != null && response.length() > 0){
                Log.i(ConnectServer.class.getName(), "responseData = " + response);
                return response;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(ConnectServer.class.getName(), "response = null");
        return null;
    }
}