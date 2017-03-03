package gdin.com.penpi.homeIndex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.MapView;

import gdin.com.penpi.R;
import gdin.com.penpi.baiduMap.MapMarkerOverlay;


public class MapShowFragment extends Fragment {

    private MapView mapView;

    private MapMarkerOverlay mapMarkerOverlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map_show, container, false);
        mapView = (MapView) view.findViewById(R.id.map_view);

        /**
         * 这个view实际上就是我们看见的绘制在表面的地图图层
         */
        View v = mapView.getChildAt(0);
        v.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    HomeActivity.mViewPager.requestDisallowInterceptTouchEvent(false);
                } else {
                    HomeActivity.mViewPager.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }

        });

        mapMarkerOverlay = MapMarkerOverlay.getInstance(mapView, getActivity());

        return view;
    }

    /**
     * 实现地图生命周期管理
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapMarkerOverlay.getLocationClient().stop();// 停止定位
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
