package gdin.com.penpi.amap;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;

import java.util.List;
import java.util.Map;

import gdin.com.penpi.R;
import gdin.com.penpi.commonUtils.JacksonUtils;
import gdin.com.penpi.domain.Order;

/**
 * 高德地图的覆盖物
 * Created by Administrator on 2017/3/5.
 */
public class MakeMarker {

    private AMap aMap;
    private Context context;

    public MakeMarker(Context contex, AMap aMap) {
        this.context = contex;
        this.aMap = aMap;
    }

    public void addMarker(List<Order> orderList) {

        aMap.clear();
        for (Order order : orderList) {
            if (order.getLatLngStrat() != null) {
                Map map = JacksonUtils.readJson(order.getLatLngStrat(), Map.class);
                assert map != null;
                LatLng latLng = new LatLng((Double) map.get("latitude"), (Double) map.get("longitude"));
                aMap.addMarker(addMarker(latLng, order, 1));
            }
        }
    }

    public void addMarker(Order order) {

        aMap.clear();
        if (order.getLatLngStrat() != null && order.getLatLngEnd() != null) {
            Map map = JacksonUtils.readJson(order.getLatLngStrat(), Map.class);
            assert map != null;
            LatLng latLngSrart = new LatLng((Double) map.get("latitude"), (Double) map.get("longitude"));
            //LatLonPoint latLonPointStrart = new LatLonPoint((Double) map.get("latitude"), (Double) map.get("longitude"));
            aMap.addMarker(addMarker(latLngSrart, order, 2));

            map = JacksonUtils.readJson(order.getLatLngEnd(), Map.class);
            assert map != null;
            LatLng latLngEnd = new LatLng((Double) map.get("latitude"), (Double) map.get("longitude"));
            //LatLonPoint latLonPointEnd = new LatLonPoint((Double) map.get("latitude"), (Double) map.get("longitude"));
            aMap.addMarker(addMarker(latLngEnd, order, 3));

            //new MyRouteSearch(context, latLonPointStrart, latLonPointEnd);
        }
    }

    private MarkerOptions addMarker(LatLng latLng, Order order, int temp) {

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.draggable(true);//设置Marker可拖动

        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果

        switch (temp) {
            case 1:
                markerOption.title(order.getSendOrderPeopleName()).snippet("FROM: " + order.getStartPlace() + "\nTO    : " + order.getEndPlace());
                break;
            case 2:
                markerOption.title(order.getSendOrderPeopleName()).snippet("TO    : " + order.getEndPlace());
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.map_icon_start)));
                break;
            case 3:
                markerOption.title(order.getSendOrderPeopleName()).snippet("FROM: " + order.getStartPlace());
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.map_icon_end)));
                break;
        }

        return markerOption;
    }
}
