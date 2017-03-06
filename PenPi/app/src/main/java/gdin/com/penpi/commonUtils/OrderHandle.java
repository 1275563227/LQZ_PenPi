package gdin.com.penpi.commonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gdin.com.penpi.domain.Order;
import gdin.com.penpi.login.LoginActivity;

/**
 * 连接服务器之后接受数据并解析
 * Created by Administrator on 2017/2/25.
 */
public class OrderHandle {

    private String requestURL = ConnectServer.requestURL + "orderAction_";

    public final static String NOGRAP = "未抢";
    public final static String HASGRAP = "已抢";

    public boolean saveOrder(Order order) {
        String responseData = ConnectServer.connect(requestURL + "saveOrder", order);
        if (responseData != null && responseData.charAt(0) == '{') {
            Map map = JacksonUtils.readJson(responseData, Map.class);
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
        String responseData = ConnectServer.connect(requestURL + "deleteOrder", order);
        if (responseData != null && responseData.charAt(0) == '{') {
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return true;
            }
        }
        return false;
    }

    public List<Order> findAllOrder() {
        String responseData = ConnectServer.connect(requestURL + "findAllOrder", null);
        if (responseData != null && responseData.charAt(0) == '{') {
            Map map = JacksonUtils.readJson(responseData, Map.class);
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
        String responseData = ConnectServer.connect(requestURL + "findOrderByID", order);
        if (responseData != null && responseData.charAt(0) == '{') {
            Map map = JacksonUtils.readJson(responseData, Map.class);
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
        String responseData = ConnectServer.connect(requestURL + "findOrderByState", order);
        if (responseData != null && responseData.charAt(0) == '{') {
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate")) {
                    Order orders[] = JacksonUtils.readJson((String) map.get("returnInfo"), Order[].class);
                    return new ArrayList<>(Arrays.asList(orders));
                }
            }
        }
        return null;
    }

    public Order alterOrder(Order order) {
        String responseData = ConnectServer.connect(requestURL + "alterOrder", order);
        if (responseData != null && responseData.charAt(0) == '{') {
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return JacksonUtils.readJson((String) map.get("returnInfo"), Order.class);
            }
        }
        return null;
    }

    public Order alterOrderState(Integer ID, String state){
        Order order = new Order();
        order.setOrderID(ID);
        order.setState(state);
        order.setTakeOrderPeople(LoginActivity.getUser());
        String responseData = ConnectServer.connect(requestURL + "alterOrder", order);
        if (responseData != null && responseData.charAt(0) == '{') {
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return JacksonUtils.readJson((String) map.get("returnInfo"), Order.class);
            }
        }
        return null;
    }
}