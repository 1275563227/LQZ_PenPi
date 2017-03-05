package gdin.com.penpi.domain;

/**
 * Created by Administrator on 2017/3/5.
 */
public class Bitmap {
    private String username;
    private String imgStr;
    private String imgName;

    @Override
    public String toString() {
        return "Bitmap{" +
                "username='" + username + '\'' +
                ", imgStr='" + imgStr + '\'' +
                ", imgName='" + imgName + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
