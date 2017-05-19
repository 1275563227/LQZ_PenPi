package gdin.com.penpi.commonUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import gdin.com.penpi.internetUtils.ConnectServer;
import gdin.com.penpi.login.LoginActivity;

public class MyBitmap {

    /**
     * 从指定URL获取图片
     */
    public static Bitmap getImageBitmap(String url) {
        URL imgUrl;
        Bitmap bitmap = null;
        String finallyUrl = ConnectServer.imgURL + url;
        Log.d("[MyBitmap]", "getImageBitmap = " + finallyUrl);
        try {
            imgUrl = new URL(finallyUrl);
            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static boolean sendImageBitmap(Bitmap bit) {
        final gdin.com.penpi.domain.Bitmap bitmap = new gdin.com.penpi.domain.Bitmap();
        bitmap.setUsername(LoginActivity.getUser().getUsername());
        bitmap.setImgName("head_image.jpg");
        bitmap.setImgStr(bitmap2StrByBase64(bit));

        String responseData = ConnectServer.connect(ConnectServer.requestURL + "bitmapAction_uploadPhoto", bitmap);
        if (responseData != null && responseData.charAt(0) == '{') {
            Map map = JacksonUtils.readJson(responseData, Map.class);
            if (map != null && map.size() > 0) {
                if ((Boolean) map.get("validate"))
                    return true;
            }
        }
        return false;
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     */
    public static String bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}