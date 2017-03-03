package gdin.com.penpi.homeIndex;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.baiduMap.MapMarkerOverlay;
import gdin.com.penpi.domain.Order;
import gdin.com.penpi.dbUtils.DBManger;
import gdin.com.penpi.dbUtils.MyDatabaseHelper;
import gdin.com.penpi.commonUtils.FormatUtils;
import gdin.com.penpi.commonUtils.OrderHandle;


/**
 * Author       : yanbo
 * Date         : 2015-06-02
 * Time         : 09:47
 * Description  :
 */
public class ShowOrderAdapter extends RecyclerView.Adapter<ShowOrderAdapter.ViewHolder> {

    private Context mContext;

    private List<Order> mOrderList;

    private int mPosition;

    private MyDatabaseHelper dataHelper;

    private PoiSearch mPoiSearch = null;

    private PoiCitySearchOption poiCitySearchOption = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Toast.makeText(mContext, "抢单成功，请在'我的记录'查看详细信息", Toast.LENGTH_SHORT).show();
                mOrderList.remove(mPosition);
                notifyDataSetChanged();
            }
            if (msg.what == 0x124) {
                Toast.makeText(mContext, "抢单失败，该订单已被其他人获取", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView peopleName;
        TextView startPlace;
        TextView endPlace;
        TextView charges;
        TextView date;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            peopleName = (TextView) view.findViewById(R.id.people_name);
            startPlace = (TextView) view.findViewById(R.id.start_place);
            endPlace = (TextView) view.findViewById(R.id.end_place);
            date = (TextView) view.findViewById(R.id.date_name);
            charges = (TextView) view.findViewById(R.id.charges_name);
        }
    }

    private int i = 0;

    public ShowOrderAdapter(List<Order> orderList) {
        mOrderList = orderList;
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                // 获取POI检索结果
                if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                    Log.i("ShowOrderAdapter", "未找到结果,请重新输入");
                }
                if (poiResult.getAllPoi() == null) {
                    Log.i("ShowOrderAdapter", "未找到结果,请重新输入");
                } else {
                    LatLng poilocation = poiResult.getAllPoi().get(0).location;
                    if (poilocation != null) {
                        Double latitude = poilocation.latitude;
                        Double longitude = poilocation.longitude;
                        LatLng latLng = new LatLng(latitude, longitude);

                        MapMarkerOverlay mapMarkerOverlay = MapMarkerOverlay.getMapMarkerOverlay();
                        BaiduMap baiduMap = mapMarkerOverlay.getBaiduMap();
                        BitmapDescriptor icon = BitmapDescriptorFactory
                                .fromResource(R.drawable.map_icon_start);

                        i++;
                        if (i == 1) {
                            /**
                             * 为百度地图添加覆盖物
                             */
                            MarkerOptions options = new MarkerOptions();
                            options.position(latLng) // 位置
                                    .title("start_place") // content_title
                                    .icon(icon) // 图标
                                    .draggable(true); // 设置图标可以拖动

                            baiduMap.clear();
                            baiduMap.addOverlay(options);
                            Log.i("ShowOrderAdapter", "latLng start_place = " + latLng.toString());
                            baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(latLng));
                            baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(17));
                        }
                        if (i == 2) {
                            i = 0;
                            icon = BitmapDescriptorFactory
                                    .fromResource(R.drawable.map_icon_end);
                            MarkerOptions options = new MarkerOptions().icon(icon).title("end_place")
                                    .position(latLng).draggable(true);
                            baiduMap.addOverlay(options);
                            Log.i("ShowOrderAdapter", "latLng end_place = " + latLng.toString());
                        }
                    }
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.ltem_order_show, parent, false);
        ViewHolder holder = new ViewHolder(view);

        dataHelper = DBManger.getInstance(mContext);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mOrderList.size() != 0) {
            final Order order = mOrderList.get(position);
            if (order != null) {
                holder.peopleName.setText(order.getSendOrderPeopleName());
                holder.startPlace.setText(order.getStartPlace());
                holder.endPlace.setText(order.getEndPlace());
                holder.charges.setText(String.valueOf(order.getCharges()));
                holder.date.setText(FormatUtils.formatTime(order.getSendOrderDate()));
                /**
                 * 设置图片“抢”的点击事件
                 */
                holder.itemView.findViewById(R.id.grab_icon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Order orderReturn = new OrderHandle().alterOrderState(order.getOrderID(), OrderHandle.HASGRAP);
                                if (orderReturn != null) {
                                    mPosition = position;
                                    handler.sendEmptyMessage(0x123);
                                } else
                                    handler.sendEmptyMessage(0x124);
                            }
                        }).start();
                    }
                });
                /**
                 * 设置整体的点击事件
                 */
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeActivity.getViewPager().setCurrentItem(1);
                        poiCitySearchOption = new PoiCitySearchOption().city("广州").keyword(order.getStartPlace());
                        mPoiSearch.searchInCity(poiCitySearchOption);
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        poiCitySearchOption = new PoiCitySearchOption().city("广州").keyword(order.getEndPlace());
                        mPoiSearch.searchInCity(poiCitySearchOption);
                    }
                });
            }
        }
    }

    /**
     * 返回RecyclerView子项的数目
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mOrderList == null) {
            return 0;
        } else
            return mOrderList.size();
    }
}
