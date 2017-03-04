package gdin.com.penpi.domain;


public class Address {

    private double longitude;   //经度
    private double latitude;    //纬度
    private String title;       //信息标题
    private String text;        //信息内容

    public Address(double longitude, double latitude, String title, String text) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Address{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
