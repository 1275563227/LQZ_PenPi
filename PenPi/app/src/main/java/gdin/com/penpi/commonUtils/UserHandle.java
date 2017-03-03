package gdin.com.penpi.commonUtils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gdin.com.penpi.domain.Order;
import gdin.com.penpi.domain.User;

/**
 * 连接服务器之后接受数据并解析
 * Created by Administrator on 2017/2/25.
 */
public class UserHandle {

    private String requestURL = Connect.requestURL + "userAction_";

    public boolean login(User user) {
        String responseData = Connect.connect(requestURL + "login", user);
        if (responseData != null && responseData.charAt(0) == '{') {
            Log.d(getClass().getName(), "responseData = " + responseData);
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return true;
            }
        }
        return false;
    }

    public boolean register(User user) {
        String responseData = Connect.connect(requestURL + "register", user);
        if (responseData != null && responseData.charAt(0) == '{') {
            Log.d(getClass().getName(), "responseData = " + responseData);
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return true;
            }
        }
        return false;
    }

    public boolean saveUser(User user) {
        String responseData = Connect.connect(requestURL + "saveUser", user);
        if (responseData != null && responseData.charAt(0) == '{') {
            Log.d(getClass().getName(), "responseData = " + responseData);
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return true;
            }
        }
        return false;
    }

    public boolean deleteUser(User user) {
        String responseData = Connect.connect(requestURL + "deleteUser", user);
        if (responseData != null && responseData.charAt(0) == '{') {
            Log.d(getClass().getName(), "responseData = " + responseData);
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return true;
            }
        }
        return false;
    }

    public List<User> findAllUser() {
        String responseData = Connect.connect(requestURL + "findAllUser", null);
        if (responseData != null && responseData.charAt(0) == '{') {
            Log.d(getClass().getName(), "responseData = " + responseData);
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate")) {
                    User users[] = JacksonUtils.readJson((String) map.get("returnInfo"), User[].class);
                    return new ArrayList<>(Arrays.asList(users));
                }
            }
        }
        return null;
    }

    public User findUserByID(Integer ID) {
        User user = new User();
        user.setUserID(ID);
        String responseData = Connect.connect(requestURL + "findUserByID", user);
        if (responseData != null && responseData.charAt(0) == '{') {
            Log.d(getClass().getName(), "responseData = " + responseData);
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return JacksonUtils.readJson((String) map.get("returnInfo"), User.class);
            }
        }
        return null;
    }

    public List<Order> findMySendOrders(Integer ID) {
        User user = new User();
        user.setUserID(ID);
        String responseData = Connect.connect(requestURL + "findMySendOrders", user);
        if (responseData != null && responseData.charAt(0) == '{') {
            Log.d(getClass().getName(), "findMySendOrders responseData = " + responseData);
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

    public List<Order> findMyTakeOrders(Integer ID) {
        User user = new User();
        user.setUserID(ID);
        String responseData = Connect.connect(requestURL + "findMyTakeOrders", user);
        if (responseData != null && responseData.charAt(0) == '{') {
            Log.d(getClass().getName(), "findMyTakeOrders responseData = " + responseData);
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

    public boolean alterUser(User user) {
        String responseData = Connect.connect(requestURL + "alterUser", user);
        if (responseData != null && responseData.charAt(0) == '{') {
            Log.d(getClass().getName(), "responseData = " + responseData);
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return true;
            }
        }
        return false;
    }
}
