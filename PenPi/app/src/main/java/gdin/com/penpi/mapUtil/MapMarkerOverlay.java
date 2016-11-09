package gdin.com.penpi.mapUtil;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import gdin.com.penpi.R;


/**
 * Created by Administrator on 2016/11/3.
 */

public class MapMarkerOverlay extends MapLocation {

    private View pop;
    private TextView tv_title;
    private Context context;

    private int height;
    private int width;

    public MapMarkerOverlay(MapView mapView, Context context) {
        super(mapView, context);
        this.context = context;

        initMarker();
        baiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
        baiduMap.setOnMarkerDragListener(mOnMarkerDragListener);
    }

    public MapMarkerOverlay(MapView mapView, Context context, int height, int width) {
        super(mapView, context);
        this.context = context;

        this.height = height;
        this.width = width;

        initMarker();
        baiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
        baiduMap.setOnMarkerDragListener(mOnMarkerDragListener);
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

    BaiduMap.OnMarkerClickListener mOnMarkerClickListener = new BaiduMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            // 显示一个泡泡
            if (pop == null) {
                pop = View.inflate(context, R.layout.map_pop,
                        null);
//                tv_title = (TextView) map_pop.findViewById(R.id.tv_title);
                mapView.addView(pop, createLayoutParams(marker.getPosition()));
            } else {
                mapView.updateViewLayout(pop,
                        createLayoutParams(marker.getPosition()));
            }
//            tv_title.setText(marker.getTitle());
            return true;
        }
    };


    /**
     * 初始化标志
     */
    private void initMarker() {
        MarkerOptions options = new MarkerOptions();
        BitmapDescriptor icon = BitmapDescriptorFactory
                .fromResource(R.drawable.map_marker_overlay);
        options.position(gjs) // 位置
                .title("广技师") // title
                .icon(icon) // 图标
                .draggable(true); // 设置图标可以拖动

//        baiduMap.addOverlay(options);

    }

    /**
     * 创建一个布局参数
     *
     * @param position
     * @return
     */
    private MapViewLayoutParams createLayoutParams(LatLng position) {
        MapViewLayoutParams.Builder buidler = new MapViewLayoutParams.Builder();
        buidler.layoutMode(MapViewLayoutParams.ELayoutMode.absoluteMode); // 指定坐标类型为经纬度
//        buidler.position(position); // 设置标志的位置
        buidler.width(width - 50);
        if (height <= 1280)
            buidler.point(new Point(width / 2, height - (int) (height * 0.25)));
        else buidler.point(new Point(width / 2, height - (int) (height * 0.35)));
//        buidler.yOffset(-25); // 设置View往上偏移
        MapViewLayoutParams params = buidler.build();
        return params;
    }
}
