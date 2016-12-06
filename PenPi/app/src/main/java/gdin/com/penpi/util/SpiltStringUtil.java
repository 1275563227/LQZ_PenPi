package gdin.com.penpi.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gdin.com.penpi.bean.Order;

/**
 * 项目名称：PenPi
 * 作者：Administrator
 * 时间：2016.11.27
 */
public class SpiltStringUtil {

    public static Order messageToOrder(String message) {

        Order order = new Order();
        String[] MessageSpilts = message.split(",");
        Log.i("SpiltStringUtil", message);
        if (MessageSpilts.length > 1) {
//            order.setId(Integer.parseInt(MessageSpilts[0]));
            order.setId(MessageSpilts[0]);
            order.setStart_place(MessageSpilts[1]);
            order.setEnd_place(MessageSpilts[2]);
            order.setName(MessageSpilts[3]);
            order.setPhone_number(MessageSpilts[4]);
            order.setCharges(MessageSpilts[5]);
            order.setRemark(MessageSpilts[6]);
            order.setState(MessageSpilts[7]);
            order.setDate(MessageSpilts[8]);
            return order;
        } else
            return null;
    }

    public static Order responseToOrder(String response) {

        String[] responseSpilts = response.split("---");
        return messageToOrder(responseSpilts[1]);
    }

    public static List<Order> responseToOrders(String response) {

        String[] responseSpilts = response.split("---");
        if (responseSpilts[1].equals("empty")) {
            Log.i("SpiltStringUtil", "responseToOrders = empty, return null");
            return null;
        } else {
            Log.i("SpiltStringUtil", "responseToOrders = " + responseSpilts[1]);
            String[] orders = responseSpilts[1].split("###");
            List<Order> orderList = new ArrayList<>();
            for (int i = orders.length - 1; i >= 0; i--) {
                orderList.add(messageToOrder(orders[i]));
            }
            return orderList;
        }
    }

    public static boolean responseToOrdersChange(String response) {
        if (response != null) {
            String[] responseSpilts = response.split("---");

            Log.i("SpiltStringUtil", responseSpilts[1]);
            return Boolean.parseBoolean(responseSpilts[1]);
        } else
            return false;
    }
}
