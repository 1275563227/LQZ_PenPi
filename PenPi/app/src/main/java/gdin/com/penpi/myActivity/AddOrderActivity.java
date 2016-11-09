package gdin.com.penpi.myActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import com.baidu.mapapi.map.MapView;

import gdin.com.penpi.R;
import gdin.com.penpi.mapUtil.MapLocation;

/**
 * Created by Administrator on 2016/11/7.
 */
public class AddOrderActivity extends AppCompatActivity {

    private MapView mapView;
    private EditText editText;
    private String location;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                editText.setText(location);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_add_order);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_input_delete);
            actionBar.setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");

        mapView = (MapView) findViewById(R.id.map_order_view);
//        new Thread() {
//            @Override
//            public void run() {
                MapLocation mapLocation = new MapLocation(mapView, AddOrderActivity.this);
//                location = mapLocation.getMyLocation();
                handler.sendEmptyMessage(0x123);
//            }
//        }.start();
        Log.i("Location", "getMyLocation = " + location);

        editText = (EditText) findViewById(R.id.et_start);
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
