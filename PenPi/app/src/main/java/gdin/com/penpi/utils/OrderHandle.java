package gdin.com.penpi.utils;

import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gdin.com.penpi.bean.Order;

/**
 * 连接服务器之后接受数据并解析
 * Created by Administrator on 2017/2/25.
 */
public class OrderHandle {

    private String requestURL = "http://www.lqzcloud.cn/PenPiServer/orderAction_";

    public final static String NOGRAP = "未抢";
    public final static String HASGRAP = "已抢";

    public boolean saveOrder(Order order) {
        String responseData = Connect.connect(requestURL + "saveOrder", order);
        if (responseData != null && responseData.length() > 0) {
            Map<String, Object> map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return true;
            }
        }
        return false;
    }

    public boolean deleteOrder(Integer ID) {
        Order order = new Order();
        order.setOrderID(ID);
        String responseData = Connect.connect(requestURL + "deleteOrder", order);
        if (responseData != null && responseData.length() > 0) {
            Map<String, Object> map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return true;
            }
        }
        return false;
    }

    public List<Order> findAllOrder() {
        String responseData = Connect.connect(requestURL + "findAllOrder", null);
        if (responseData != null && responseData.length() > 0) {
            Map<String, Object> map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate")) {
                    Order orders[] = JacksonUtils.readJson((String) map.get("returnInfo"), Order[].class);
                    return Arrays.asList(orders);
                }
            }
        }
        return null;
    }

    public Order findOrderByID(Integer ID) {
        Order order = new Order();
        order.setOrderID(ID);
        String responseData = Connect.connect(requestURL + "findOrderByID", order);
        if (responseData != null && responseData.length() > 0) {
            Map<String, Object> map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate")) {
                    return JacksonUtils.readJson((String) map.get("returnInfo"), Order.class);
                }
            }
        }
        return null;
    }

    public List<Order> findOrderByState(String state) {
        Order order = new Order();
        order.setState(state);
        String responseData = Connect.connect(requestURL + "findOrderByState", order);
        if (responseData != null && responseData.length() > 0) {
//            Log.i(OrderHandle.class.getName(), "responseData = " + responseData);
            Map<String, Object> map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate")) {
                    Order orders[] = JacksonUtils.readJson((String) map.get("returnInfo"), Order[].class);
                    for (Order o: orders) {
                        Log.i(getClass().getName(), o.toString());
                    }
//                    Log.i(OrderHandle.class.getName(), Arrays.asList(orders).toString());
                    return Arrays.asList(orders);
                }
            }
        }
        return null;
    }

    public boolean alterOrder(Order order) {
        String responseData = Connect.connect(requestURL + "alterOrder", order);
        if (responseData != null && responseData.length() > 0) {
            Map<String, Object> map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return true;
            }
        }
        return false;
    }

    public boolean alterOrderState(Integer ID, String state){
        Order order = new Order();
        order.setOrderID(ID);
        order.setState(state);
        String responseData = Connect.connect(requestURL + "alterOrder", order);
        if (responseData != null && responseData.length() > 0) {
            Map<String, Object> map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return true;
            }
        }
        return false;
    }
}
