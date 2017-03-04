package gdin.com.penpi.myRecord;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.commonUtils.ComparatorDate;
import gdin.com.penpi.commonUtils.UserHandle;
import gdin.com.penpi.domain.Order;

public class MyTakeRecordFragment extends android.support.v4.app.Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private MyTakeRecordAdapter adapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;

    private List<Order> orders = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x331) {
                //对从服务器传入的orderList进行排序
                ComparatorDate c = new ComparatorDate();
                Collections.sort(orders, c);
                adapter = new MyTakeRecordAdapter(orders);
                mRecyclerView.setAdapter(adapter);
            }
            if (msg.what == 0x332) {
                Toast.makeText(MyTakeRecordFragment.this.getActivity(), "你没有发单记录！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public static MyTakeRecordFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MyTakeRecordFragment pageFragment = new MyTakeRecordFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    private void initOrders() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (orders != null)
                    orders.clear();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                orders = new UserHandle().findMyTakeOrders(1);
                if (orders != null) {
                    handler.sendEmptyMessage(0x331);
                } else
                    handler.sendEmptyMessage(0x332);
            }
        }).start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initOrders();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.out_order_recycle, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rc_main);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        adapter = new MyTakeRecordAdapter(orders);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickListener(new MyTakeRecordAdapter.OnItemClickListener() {
            //此处实现onItemClick的接口
            @Override
            public void onItemClick(View view, int position, int indext) {
                Button bt = (Button) view.findViewById(R.id.foruse_icon);
                //根订单好改写订单状态
                orders.get(position - 1 - indext).setState("完成");
                bt.setEnabled(false);
                bt.setText("待审核");
            }
        });
        return view;
    }
}