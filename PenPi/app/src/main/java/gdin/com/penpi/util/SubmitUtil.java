package gdin.com.penpi.util;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gdin.com.penpi.bean.Order;

public class SubmitUtil {

//    private static String myURL = "http://192.168.1.118:8080/";
//    private static String myURL = "http://192.168.1.168:8080/";
    private static String myURL = "http://penpi.lqzcloud.cn:8080/Androidpn-tomcat/";

    /**
     * 在屏幕中央显示一个Toast
     * @param text
     */
    public static void showToast(Context context, CharSequence text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 80);
        toast.show();
    }

    public static void addOrdertoServe(Order order) {
        Log.d("SubmitUtil", "start...");
        String response = ConnectUtil
                .send_httpClient_Post(myURL + "servlet/addOrderServlet", order);

        Log.d("SubmitUtil", "response = " + response);

        if (response != null) {
            Order order2 = SpiltStringUtil.responseToOrder(response);

            Log.d("SubmitUtil", "解析..." + order2.toString());
            Log.d("SubmitUtil", "end...");
        }
    }

    public static List<Order> getOrdersfromServe() {
        List<Order> orderList;

        List<String> ssList = new ArrayList<>();
        ssList.add("未抢");
        String response = ConnectUtil
                .send_httpClient_Post(myURL + "servlet/findOrderByStateServlet", ssList.toArray(new String[ssList.size()]));

        Log.d("SubmitUtil", "response = " + response);
        if (response != null) {
            orderList = SpiltStringUtil.responseToOrders(response);
            if (orderList != null) {
                for (Order orderTemp : orderList) {
                    try {
                        Log.d("SubmitUtil", "解析..." + orderTemp.toString());
                    } catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                Log.d("SubmitUtil", "end...");
                return orderList;
            }
            else {
                Log.d("SubmitUtil", "response = null");
                return null;
            }
        } else
            return null;
    }

    public static boolean changeOrderStatetoServlet(String id, String state){
        List<String> ssList = new ArrayList<>();
        ssList.add(state);
        ssList.add(id);
        Log.d("SubmitUtil", "start...");
        Log.d("SubmitUtil", "id = " + id + ", state = " + state);
        String response = ConnectUtil
                .send_httpClient_Post(myURL + "servlet/changeOrderStateServlet", ssList.toArray(new String[ssList.size()]));
        Log.d("SubmitUtil", "response = " + response);
        Log.d("SubmitUtil", "end...");
        return SpiltStringUtil.responseToOrdersChange(response);
    }
}
