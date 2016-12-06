package gdin.com.penpi.baidumap;

import android.content.Context;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;

import gdin.com.penpi.R;


/**
 * Created by Administrator on 2016/11/3.
 */

public class MapMarkerOverlay extends MapLocation {

    protected LatLng gjs = new LatLng(23.137158, 113.377325);

    private View pop;
    private Context context;

    private static MapMarkerOverlay mapMarkerOverlay;

    public static MapMarkerOverlay getInstance(MapView mapView, Context context) {
        if (mapMarkerOverlay == null) {
            mapMarkerOverlay = new MapMarkerOverlay(mapView, context);
        }
        return mapMarkerOverlay;
    }

    public static MapMarkerOverlay getMapMarkerOverlay() {
        return mapMarkerOverlay;
    }

    public MapMarkerOverlay(MapView mapView, Context context) {
        super(mapView, context);
        this.context = context;

        initMarker();
    }

    /**
     * 初始化标志
     */
    private void initMarker() {
//        MarkerOptions options = new MarkerOptions();
//        BitmapDescriptor icon = BitmapDescriptorFactory
//                .fromResource(R.drawable.map_marker_overlay);
//        options.position(gjs) // 位置
//                .title("广东技术师范学院") // content_title
//                .icon(icon) // 图标
//                .draggable(false); // 设置图标不可以拖动
//
//        baiduMap.addOverlay(options);
//        baiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
//        baiduMap.setOnMarkerDragListener(mOnMarkerDragListener);
    }

    /**
     * 标志拖动监听器
     */
    BaiduMap.OnMarkerDragListener mOnMarkerDragListener = new BaiduMap.OnMarkerDragListener() {

        /** 标志开始拖动 */
        @Override
        public void onMarkerDragStart(Marker marker) {
            if (mapView != null)
                mapView.updateViewLayout(pop,
                        createLayoutParams(marker.getPosition()));
        }

        /** 标志拖动结束 */
        @Override
        public void onMarkerDragEnd(Marker marker) {
            mapView.updateViewLayout(pop,
                    createLayoutParams(marker.getPosition()));
        }

        /** 标志正在拖动 */
        @Override
        public void onMarkerDrag(Marker marker) {
            mapView.updateViewLayout(pop,
                    createLayoutParams(marker.getPosition()));
        }
    };

    /**
     * 点击地图覆盖物 的点击事件
     */
    BaiduMap.OnMarkerClickListener mOnMarkerClickListener = new BaiduMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            // 显示一个泡泡
            if (pop == null) {
                pop = View.inflate(context, R.layout.submit_order,
                        null);
                mapView.addView(pop, createLayoutParams(marker.getPosition()));
            } else {
                mapView.updateViewLayout(pop,
                        createLayoutParams(marker.getPosition()));
            }
            return true;
        }
    };

    /**
     * 创建一个布局参数
     *
     * @param position
     * @return
     */
    private MapViewLayoutParams createLayoutParams(LatLng position) {
        MapViewLayoutParams.Builder buidler = new MapViewLayoutParams.Builder();
        buidler.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode); // 指定坐标类型为经纬度
        buidler.position(position); // 设置标志的位置
        buidler.yOffset(-25); // 设置View往上偏移
        MapViewLayoutParams params = buidler.build();
        return params;
    }
}
