package gdin.com.penpi.util;

import android.util.Log;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gdin.com.penpi.bean.Order;
import gdin.com.penpi.bean.User;

/**
 * Description: <br/>
 * site: <a href="http://www.crazyit.org">crazyit.org</a> <br/>
 * Copyright (C), 2001-2014, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 *
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class ConnectUtil {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url    发送请求的URL
     * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String send_URLConnection_Get(String url, String params) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url + "?" + params;
            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect(); // ①
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url    发送请求的URL
     * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String send_URLConnection_Post(String url, String params) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(params); // ②
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String send_HttpURLConnection_Get(String s) {

        HttpURLConnection connection = null;
        StringBuilder response = null;
        try {
            URL url = new URL(s);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            if (connection != null)
                connection.disconnect();

            if (response != null)
                return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String send_httpClient_Get(String url) {
        String response = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();

            // Get方法
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 请求和响应都成功
                HttpEntity httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String send_httpClient_Post(String url, Order order) {

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

    public static String send_httpClient_Post(String url, User user) {

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
            JSONObject userJSON = user.toJSONObj();
            httpPost.setEntity(new StringEntity(userJSON.toString(), "UTF-8"));

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

    public static String send_httpClient_Post(String url, String[] ss) {

        String response = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();

            // Post方法
            HttpPost httpPost = new HttpPost(url);

            // 方法一
            List<NameValuePair> params = new ArrayList<>();
            int i = 0;
            for (String temp: ss) {
                i++;
                if (i == 1) {
                    params.add(new BasicNameValuePair("state", temp));
                }
                if (i == 2) {
                    params.add(new BasicNameValuePair("id", temp));
                }
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
            httpPost.setEntity(entity);

            // 方法二
//            JSONObject userJSON = user.toJSONObj();
//            httpPost.setEntity(new StringEntity(userJSON.toString(), "UTF-8"));

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

    public static User paresJSON_withGSON_toUser(String result) {
        User user = new User();
        Gson gson = new Gson();
        try {
            List<User> userList = gson.fromJson(result,
                    new TypeToken<List<User>>() {
                    }.getType());

            for (User userTemp : userList) {
                user.setId(userTemp.getId());
                user.setPhone_number(userTemp.getPhone_number());
                user.setUsername(userTemp.getUsername());
                user.setPassword(userTemp.getPassword());
            }

            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static Order paresJSON_withGSON_toOrder(String result) {
        Order order = new Order();
        Gson gson = new Gson();
        try {
            List<Order> orderList = gson.fromJson(result,
                    new TypeToken<List<Order>>() {
                    }.getType());

            for (Order order2 : orderList) {
                order.setStart_place(order2.getStart_place());
                order.setEnd_place(order2.getEnd_place());
                order.setName(order2.getName());
                order.setPhone_number(order2.getPhone_number());
                order.setCharges(order2.getCharges());
                order.setRemark(order2.getRemark());
            }

            System.out.println(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }
}
