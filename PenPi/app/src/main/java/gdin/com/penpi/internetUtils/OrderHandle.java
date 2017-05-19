package gdin.com.penpi.internetUtils;

import java.util.List;

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
        return ParseDataUtil.getValidate(responseData);
    }

    public boolean deleteOrder(Integer ID) {
        Order order = new Order();
        order.setOrderID(ID);
        String responseData = ConnectServer.connect(requestURL + "deleteOrder", order);
        return ParseDataUtil.getValidate(responseData);
    }

    public List<Order> findAllOrder() {
        String responseData = ConnectServer.connect(requestURL + "findAllOrder", null);
        return ParseDataUtil.getOrderList(responseData);
    }

    public Order findOrderByID(Integer ID) {
        Order order = new Order();
        order.setOrderID(ID);
        String responseData = ConnectServer.connect(requestURL + "findOrderByID", order);
        return ParseDataUtil.getOrder(responseData);
    }

    public List<Order> findOrderByState(String state) {
        Order order = new Order();
        order.setState(state);
        String responseData = ConnectServer.connect(requestURL + "findOrderByState", order);
        return ParseDataUtil.getOrderList(responseData);
    }

    public Order alterOrder(Order order) {
        String responseData = ConnectServer.connect(requestURL + "alterOrder", order);
        return ParseDataUtil.getOrder(responseData);
    }

    public Order alterOrderState(Integer ID, String state) {
        Order order = new Order();
        order.setOrderID(ID);
        order.setState(state);
        order.setTakeOrderPeople(LoginActivity.getUser());
        String responseData = ConnectServer.connect(requestURL + "alterOrder", order);
        return ParseDataUtil.getOrder(responseData);
    }


}