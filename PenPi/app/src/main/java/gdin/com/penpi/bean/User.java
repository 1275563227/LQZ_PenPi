package gdin.com.penpi.bean;

import org.json.JSONObject;

/**
 * Created by chen on 2016/11/13.
 */

public class User {

    /**
     * ACCOUNT : 123
     * YZM : 123
     * PWD : 123
     */
    private int id;
    private String username;
    private String phone_number;
    private String password;
    private String YZM;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getYZM() {
        return YZM;
    }

    public void setYZM(String YZM) {
        this.YZM = YZM;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", password='" + password + '\'' +
                ", YZM='" + YZM + '\'' +
                '}';
    }

    public JSONObject toJSONObj() {
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("id", this.id);
            userJson.put("username", this.username);
            userJson.put("phone_number", this.phone_number);
            userJson.put("password", this.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userJson;
    }
}
