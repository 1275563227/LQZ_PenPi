package gdin.com.penpi.amap;

import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;

/**
 * Created by Administrator on 2017/3/5.
 */
public class MyRouteSearch implements RouteSearch.OnRouteSearchListener {


    public MyRouteSearch(Context context, LatLonPoint mStartPoint, LatLonPoint mEndPoint) {
        RouteSearch routeSearch = new RouteSearch(context);
        routeSearch.setRouteSearchListener(this);

        //初始化query对象，fromAndTo是包含起终点信息，walkMode是步行路径规划的模式
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, mEndPoint);

        /**
         * BUS_COMFORTABLE 最舒适。
         * BUS_DEFAULT 最快捷模式。
         * BUS_LEASE_CHANGE 最少换乘。
         * BUS_LEASE_WALK 最少步行。
         * BUS_NO_SUBWAY 不乘地铁。
         * BUS_SAVE_MONEY 最经济模式。
         */
        RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.BUS_COMFORTABLE);
        routeSearch.calculateWalkRouteAsyn(query);  //开始算路
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
