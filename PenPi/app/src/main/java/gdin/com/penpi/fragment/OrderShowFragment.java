package gdin.com.penpi.fragment;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.adapter.RecyclerViewAdapter;
import gdin.com.penpi.bean.Order;
import gdin.com.penpi.util.ComparatorDateUtil;
import gdin.com.penpi.util.SubmitUtil;


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

    private boolean mIsRefreshing = false;

    private void refreshOrders() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mIsRefreshing = true;
                    if (orderList != null) {
                        orderList.clear();

                    }
                    orderList = SubmitUtil.getOrdersfromServe();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (orderList == null) {
                            SubmitUtil.showToast(getActivity(), "订单已被抢光");
                        } else {
                            //刷新排序（时间）
                            ComparatorDateUtil c = new ComparatorDateUtil();
                            Collections.sort(orderList, c);
                            adapter = new RecyclerViewAdapter(orderList);
                            mRecyclerView.setAdapter(adapter);
                        }
                        swipeRefresh.setRefreshing(false);
                        mIsRefreshing = false;
                    }
                });
            }
        }).start();


        /*
        * RecycleView下拉拉取新数据的时候，同时在向上
        * 滑动RecycleView时程序就崩溃了
        * */
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mIsRefreshing) {
                    Log.d("OrderShowFragment",String.valueOf(mIsRefreshing));
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_show, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

//        Log.i("OrderShowFragment", orderList.toString());
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
                orderList = SubmitUtil.getOrdersfromServe();
//                Log.i("OrderShowFragment", "orderList = " + orderList.toString());
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
                ComparatorDateUtil c = new ComparatorDateUtil();
                Collections.sort(orderList, c);
                adapter = new RecyclerViewAdapter(orderList);
                mRecyclerView.setAdapter(adapter);
            }
            if (msg.what == 0x124) {
                SubmitUtil.showToast(getActivity(), "订单已被抢光");
            }
        }
    };

}
