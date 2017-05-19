package gdin.com.penpi.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.amap.api.maps.model.LatLng;

import gdin.com.penpi.commonUtils.JacksonUtils;
import gdin.com.penpi.commonUtils.Parameter;
import gdin.com.penpi.domain.Order;
import gdin.com.penpi.domain.User;
import gdin.com.penpi.internetUtils.ConnectServer;
import gdin.com.penpi.internetUtils.UserHandle;

/**
 * 改服务用于发送抢单者的位置
 */
public class SendLocationService extends Service {

    private MyThread myThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        myThread = new MyThread();
        myThread.start();
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        myThread.flag = false;
        super.onDestroy();
    }
}

class MyThread extends Thread {
    boolean flag = true;
    User user;
    LatLng location;

    @Override
    public void run() {
        user = new User();
        location = new LatLng(Double.parseDouble(Parameter.addressMap.get("latitude")),
                Double.parseDouble(Parameter.addressMap.get("longitude")));
        user.setLocation(JacksonUtils.writeJSON(location));
        new UserHandle().sendLocation(user);
        while (flag) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
