package gdin.com.penpi.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

import gdin.com.penpi.MainActivity;
import gdin.com.penpi.R;


public class MapShowFragment extends Fragment {

    private LatLng gjs = new LatLng(23.137158, 113.377325);

    private MapView mapView;
    private BaiduMap baiduMap;

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();

    EditText et_start;
    EditText et_end;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_show, container, false);
        mapView = (MapView) view.findViewById(R.id.map_view);
        et_start = (EditText) getActivity().findViewById(R.id.et_start);
        et_end = (EditText) getActivity().findViewById(R.id.et_end);

        View v = mapView.getChildAt(0);//这个view实际上就是我们看见的绘制在表面的地图图层
        v.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    MainActivity.mViewPager.requestDisallowInterceptTouchEvent(false);
                } else {
                    MainActivity.mViewPager.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }

        });

//        获取屏幕尺寸
//        DisplayMetrics dm2 = getResources().getDisplayMetrics();
//        Log.i("TAGFF", "heigth2 : " + dm2.heightPixels);
//        Log.i("TAGFF", "width2 : " + dm2.widthPixels);
//        new MapMarkerOverlay(mapView, this.getActivity(), dm2.heightPixels, dm2.widthPixels);

        initMapView();
//        new MapLocation(mapView, getActivity());

//        SharedPreferences preferences = getActivity().getSharedPreferences("data", 0);
        return view;
    }

    private void initMapView() {
        baiduMap = mapView.getMap(); // 获取地图控制器

        // 隐藏缩放按钮、比例尺
        mapView.showZoomControls(false); // 隐藏缩放按钮，默认是显示的

        // 获取获取最小（3）、最大缩放级别（20）
        // float maxZoomLevel = baiduMap.getMaxZoomLevel(); // 获取地图最大缩放级别
        // float minZoomLevel = baiduMap.getMinZoomLevel(); // 获取地图最小缩放级别
        // Log.i(TAG, "minZoomLevel = " + minZoomLevel + ", maxZoomLevel" + maxZoomLevel);
        mapView.showScaleControl(false); // 隐藏比例尺

        // 设置地图中心点为广技师
        //mapStatusUpdate = MapStatusUpdateFactory.newLatLng(gjs);
        //baiduMap.setMapStatus(mapStatusUpdate);

        // 设置地图缩放为15
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(17);
        baiduMap.setMapStatus(mapStatusUpdate);

        // 获取地图Ui控制器：隐藏指南针
        // UiSettings uiSettings = baiduMap.getUiSettings();
        // uiSettings.setCompassEnabled(false); // 不显示指南针

        // 定位
        mLocationClient = new LocationClient(getActivity().getApplicationContext()); // 声明LocationClient类
        mLocationClient.registerLocationListener(myListener); // 注册监听函数
        initLocation();
        baiduMap.setMyLocationEnabled(true); // 开启定位图层
        setMyLocationConfigeration(MyLocationConfiguration.LocationMode.FOLLOWING);
        mLocationClient.start(); // 开始定位
    }

    public class MyLocationListener implements BDLocationListener {

        // 在这个方法里面接收定位结果
        @Override
        public void onReceiveLocation(BDLocation location) {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", 0).edit();

            if (location != null) {
                MyLocationData.Builder builder = new MyLocationData.Builder();
                builder.accuracy(location.getRadius()); // 设置精度
                builder.direction(location.getDirection()); // 设置方向
                builder.latitude(location.getLatitude()); // 设置纬度
                builder.longitude(location.getLongitude()); // 设置经度
                MyLocationData locationData = builder.build();
                baiduMap.setMyLocationData(locationData); // 把定位数据显示到地图上
            }

            // Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nGPS定位成功");
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\n网络定位成功");
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());

                editor.putString("location", location.getAddrStr().substring(8));

                // 运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("离线定位成功，离线定位结果也是有效的");

            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息


            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());

                editor.putInt("poiSize", list.size());
                int i = 0;
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    editor.putString("poi" + i++, p.getName());
                }
                i = 0;
            }
            Log.i("Location", sb.toString());
//            et_start.setHint(location.getAddrStr());
//            et_start.setText(location.getAddrStr().substring(8));


            editor.commit();
        }
    }

    /**
     * 设置定位图层的配置
     */
    private void setMyLocationConfigeration(MyLocationConfiguration.LocationMode mode) {
        boolean enableDirection = true; // 设置允许显示方向
        BitmapDescriptor customMarker = BitmapDescriptorFactory.fromResource(R.drawable.map_location); // 自定义定位的图标
        MyLocationConfiguration config = new MyLocationConfiguration(mode, enableDirection, customMarker);
        baiduMap.setMyLocationConfigeration(config);
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 60000;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mLocationClient.stop(); // 停止定位
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
