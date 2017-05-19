package gdin.com.penpi.internetUtils;

import java.util.List;

import gdin.com.penpi.domain.Order;
import gdin.com.penpi.domain.User;

/**
 * 连接服务器之后接受数据并解析
 * Created by Administrator on 2017/2/25.
 */
public class UserHandle {

    private String requestURL = ConnectServer.requestURL + "userAction_";

    public User login(String name, String password) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        String responseData = ConnectServer.connect(requestURL + "login", user);
        return ParseDataUtil.getUser(responseData);
    }

    public boolean register(String phone, String password) {
        User user = new User();
        user.setPhoneNumber(phone);
        user.setPassword(password);
        String responseData = ConnectServer.connect(requestURL + "register", user);
        return ParseDataUtil.getValidate(responseData);
    }

    public boolean saveUser(User user) {
        String responseData = ConnectServer.connect(requestURL + "saveUser", user);
        return ParseDataUtil.getValidate(responseData);
    }

    public boolean deleteUser(User user) {
        String responseData = ConnectServer.connect(requestURL + "deleteUser", user);
        return ParseDataUtil.getValidate(responseData);
    }

    public List<User> findAllUser() {
        String responseData = ConnectServer.connect(requestURL + "findAllUser", null);
        return ParseDataUtil.getUserList(responseData);
    }

    public User findUserByID(Integer ID) {
        User user = new User();
        user.setUserID(ID);
        String responseData = ConnectServer.connect(requestURL + "findUserByID", user);
        return ParseDataUtil.getUser(responseData);
    }

    public List<Order> findMySendOrders(Integer ID) {
        User user = new User();
        user.setUserID(ID);
        String responseData = ConnectServer.connect(requestURL + "findMySendOrders", user);
        return ParseDataUtil.getOrderList(responseData);
    }

    public List<Order> findMyTakeOrders(Integer ID) {
        User user = new User();
        user.setUserID(ID);
        String responseData = ConnectServer.connect(requestURL + "findMyTakeOrders", user);
        return ParseDataUtil.getOrderList(responseData);
    }

    public boolean alterUser(User user) {
        String responseData = ConnectServer.connect(requestURL + "alterUser", user);
        return ParseDataUtil.getValidate(responseData);
    }

    public User sendLocation(User user) {
        String responseData = ConnectServer.connect(requestURL + "receiveLocation", user);
        return ParseDataUtil.getUser(responseData);
    }

    public User getLocation(User user) {
        String responseData = ConnectServer.connect(requestURL + "receiveLocation", user);
        return ParseDataUtil.getUser(responseData);
    }
}
