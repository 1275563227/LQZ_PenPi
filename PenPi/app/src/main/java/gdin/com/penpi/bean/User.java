package gdin.com.penpi.bean;

/**
 * Created by chen on 2016/11/13.
 */

public class User {

    /**
     * ACCOUNT : 123
     * YZM : 123
     * PWD : 123
     */

    private String ACCOUNT;
    private String YZM;
    private String PWD;

    public String getACCOUNT() {
        return ACCOUNT;
    }

    public void setACCOUNT(String ACCOUNT) {
        this.ACCOUNT = ACCOUNT;
    }

    public String getYZM() {
        return YZM;
    }

    public void setYZM(String YZM) {
        this.YZM = YZM;
    }

    public String getPWD() {
        return PWD;
    }

    public void setPWD(String PWD) {
        this.PWD = PWD;
    }

    @Override
    public String toString() {
        return "User{" +
                "ACCOUNT='" + ACCOUNT + '\'' +
                ", YZM='" + YZM + '\'' +
                ", PWD='" + PWD + '\'' +
                '}';
    }
}
