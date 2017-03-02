package gdin.com.penpi.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.adapter.RecyclerViewAdapter;
import gdin.com.penpi.domain.Order;
import gdin.com.penpi.utils.ComparatorDate;
import gdin.com.penpi.utils.OrderHandle;


/**
 * Author       : yanbo
 * Date         : 2015-06-01
 * Time         : 15:09
 * Description  :
 * 1、你想要控制其显示的方式，请通过布局管理器LayoutManager
 * 2、你想要控制Item间的间隔（可绘制），请通过ItemDecoration
 * 3、你想要控制Item增删的动画，请通过ItemAnimator
 * 4、你想要控制点击、长按事件
 */
public class OrderShowFragment extends Fragment {

    private List<Order> orderList = new ArrayList<>();

    private RecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;

    private boolean isRefreshing = false;

    private boolean hasConnectInternet = true;

    /**
     * 广播监听 判断网络是否连接成功
     */
    private BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectMgr = (ConnectivityManager) OrderShowFragment.this.getActivity().getSystemService(OrderShowFragment.this.getActivity().CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                hasConnectInternet = false;
                Toast.makeText(OrderShowFragment.this.getActivity(), "网络连接失败，请连接上网络！", Toast.LENGTH_LONG).show();
            }
        }
    };

    /**
     * 下拉列表的刷新函数
     */
    private void refreshOrders() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    isRefreshing = true;
                    if (orderList != null)
                        orderList.clear();

                    // 获取未抢的订单
                    orderList = new OrderHandle().findOrderByState(OrderHandle.NOGRAP);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (orderList == null) {
                            handler.sendEmptyMessage(0x124);
                        } else {
                            //刷新排序（时间）
                            ComparatorDate c = new ComparatorDate();
                            Collections.sort(orderList, c);
                            adapter = new RecyclerViewAdapter(orderList);
                            mRecyclerView.setAdapter(adapter);
                        }
                        swipeRefresh.setRefreshing(false);
                        isRefreshing = false;
                    }
                });
            }
        }).start();


        /**
         * RecycleView下拉拉取新数据的时候，同时在向上
         * 滑动RecycleView时程序就崩溃了
         */
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isRefreshing) {
                    Log.d("OrderShowFragment", String.valueOf(isRefreshing));
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //广播监听
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(connectionReceiver, intentFilter);

        View view = inflater.inflate(R.layout.fragment_order_show, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        adapter = new RecyclerViewAdapter(orderList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshOrders();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                orderList = new OrderHandle().findOrderByState(OrderHandle.NOGRAP);
                if (orderList != null) {
                    handler.sendEmptyMessage(0x123);
                } else
                    handler.sendEmptyMessage(0x124);
            }
        }).start();
        return view;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                //对从服务器传入的orderList进行排序
                ComparatorDate c = new ComparatorDate();
                Collections.sort(orderList, c);
                adapter = new RecyclerViewAdapter(orderList);
                mRecyclerView.setAdapter(adapter);
            }
            if (msg.what == 0x124) {
                if (hasConnectInternet)
                    Toast.makeText(OrderShowFragment.this.getActivity(), "订单已被抢光", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(OrderShowFragment.this.getActivity(), "网络连接失败，请连接上网络！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(connectionReceiver);
        super.onDestroy();
    }
}
