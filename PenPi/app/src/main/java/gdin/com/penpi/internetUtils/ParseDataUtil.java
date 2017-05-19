package gdin.com.penpi.internetUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gdin.com.penpi.commonUtils.JacksonUtils;
import gdin.com.penpi.domain.Order;
import gdin.com.penpi.domain.User;

/**
 * Created by 12755 on 2017/3/25.
 */
public class ParseDataUtil {

    public static Boolean getValidate(String responseData) {
        if (responseData != null && responseData.charAt(0) == '{') {
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return true;
            }
        }
        return false;
    }

    public static Order getOrder(String responseData) {
        if (responseData != null && responseData.charAt(0) == '{') {
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return JacksonUtils.readJson((String) map.get("returnInfo"), Order.class);
            }
        }
        return null;
    }

    public static List<Order> getOrderList(String responseData) {
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

    public static User getUser(String responseData) {
        if (responseData != null && responseData.charAt(0) == '{') {
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return JacksonUtils.readJson((String) map.get("returnInfo"), User.class);
            }
        }
        return null;
    }

    public static List<User> getUserList(String responseData) {
        if (responseData != null && responseData.charAt(0) == '{') {
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
}
