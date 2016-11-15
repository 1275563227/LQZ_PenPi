package gdin.com.penpi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.baidu.mapapi.map.MapView;

import gdin.com.penpi.R;
import gdin.com.penpi.baidumap.MapLocation;
import gdin.com.penpi.bean.Order;
import gdin.com.penpi.util.ClientUtils;

/**
 * Created by Administrator on 2016/11/7.
 */
public class AddOrderActivity extends AppCompatActivity {

    private MapView mapView;

    private EditText et_start;
    private EditText et_end;
    private EditText et_name;
    private EditText et_phone_number;
    private EditText et_remark;

    private String response;
    private Order order_response;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        initEditText();
        mapView = (MapView) findViewById(R.id.map_order_view);

        new MapLocation(mapView, AddOrderActivity.this);

    }

    private void initEditText() {
        et_start = (EditText) findViewById(R.id.et_start);
        et_end = (EditText) findViewById(R.id.et_end);

        et_start.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(AddOrderActivity.this, SpaceListActivity.class);
                    startActivityForResult (intent, 1);
                }
                return false;
            }
        });

        et_end.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(AddOrderActivity.this, SpaceListActivity.class);
                    startActivityForResult (intent, 2);
                }
                return false;
            }
        });
    }

    /**
     * 按钮点击事件
     * @param view
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                // 设置show组件显示服务器响应
                et_start.setText(order_response.getStart_plac());
                et_end.setText(order_response.getEnd_plac());
                et_name.setText(order_response.getName());
                et_phone_number.setText(order_response.getPhone_number());
                et_remark.setText(order_response.getRemark());
            }
        }
    };

    public void Submit_to_Sever(View view) {
        et_start = (EditText) findViewById(R.id.et_start);
        et_end = (EditText) findViewById(R.id.et_end);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_remark = (EditText) findViewById(R.id.et_remark);

        final Order order = new Order();
        order.setStart_plac(et_start.getText().toString());
        order.setEnd_plac(et_end.getText().toString());
        order.setName(et_name.getText().toString());
        order.setPhone_number(et_phone_number.getText().toString());
        order.setRemark(et_remark.getText().toString());

        order.setStart_plac("阳江");
        order.setEnd_plac("广技师");
        order.setName("傻吊志鹏");
        order.setPhone_number("110");
        order.setRemark("广师是我家，大家笑哈哈");

        Log.i("HTTP", "开启线程准备发送");
        Log.i("HTTP", order.toString());
        new Thread() {
            @Override
            public void run() {
                response = ClientUtils.sendPost("http://139.199.159.60:8080/Demo/servlet/testClientServlet", order);
                if (response != null) {
                    order_response = ClientUtils.paresJSON_withGSON(response);
                    handler.sendEmptyMessage(0x123);
                }
            }
        }.start();
    }

    /**
     * SpaceListActivity 的回调函数
     * @param requestCode 请求码
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 取得MapLocationList回传的数据
        if(requestCode == 1) {
            if (resultCode == RESULT_OK)
                et_start.setText(data.getStringExtra("myLocation"));
        }
        if(requestCode == 2) {
            if (resultCode == RESULT_OK)
                et_end.setText(data.getStringExtra("myLocation"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
}