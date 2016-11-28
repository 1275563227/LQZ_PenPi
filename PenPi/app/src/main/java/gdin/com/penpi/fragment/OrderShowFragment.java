package gdin.com.penpi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gdin.com.penpi.R;
import gdin.com.penpi.adapter.RecyclerViewAdapter;
import gdin.com.penpi.bean.Order;


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

    private Order[] orders = {
      new Order("小王","遂溪","湛江","2.3"),new Order("小李","遂溪","湛江","2.3"),new Order("小张","遂溪","湛江","2.3"),new Order("小红","遂溪","湛江","2.3"),
            new Order("小王","遂溪","湛江","2.3"),new Order("小花","遂溪","湛江","2.3"),new Order("小哎","遂溪","湛江","2.3"),new Order("小二","遂溪","湛江","2.3"),
            new Order("小王","遂溪","湛江","2.3"),new Order("小王","遂溪","湛江","2.3"),new Order("小王","遂溪","湛江","2.3"),new Order("小王","遂溪","湛江","2.3")
    };
    private List<Order> orderList = new ArrayList<>();

    private RecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private View view;

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_order_show, container, false);
        return_page mRecyclerView;
    }*/

    private void initOrders() {
        orderList.clear();
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            int index = random.nextInt(orders.length);
            orderList.add(orders[index]);
        }
    }

    private void refreshOrders() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initOrders();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_show, container, false);
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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initOrders();
    }

}
