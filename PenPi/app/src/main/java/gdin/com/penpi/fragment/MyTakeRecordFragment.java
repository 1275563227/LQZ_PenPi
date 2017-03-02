package gdin.com.penpi.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.adapter.InRecordRecyclerAdapter;
import gdin.com.penpi.adapter.OutRecordRecyclerAdapter;
import gdin.com.penpi.domain.Order;
import gdin.com.penpi.db.DBManger;
import gdin.com.penpi.db.MyDatabaseHelper;
import gdin.com.penpi.utils.ComparatorDate;
import gdin.com.penpi.utils.UserHandle;

/**
 * Created by Administrator on 2016/11/30.
 */
public class MyTakeRecordFragment extends android.support.v4.app.Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private boolean isReceive = true;
    private boolean isComplete = false;

    //private  List<String> list = new ArrayList<>();
    //private out_order_record[] orders = {};
    //private List<out_order_record> orderList = new ArrayList<>();

    private InRecordRecyclerAdapter adapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private View view;

    //private Order[] order;
    //private List<Order> orderList2 = new ArrayList<>();

    private Context mContext;
    MyDatabaseHelper dbhelper;
    private List<Order> orderList3 = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x331) {
                //对从服务器传入的orderList进行排序
                ComparatorDate c = new ComparatorDate();
                Collections.sort(orderList3, c);
                adapter = new InRecordRecyclerAdapter(orderList3);
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
                if (orderList3 != null)
                    orderList3.clear();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                orderList3 = new UserHandle().findMyTakeOrders(1);
                if (orderList3 != null) {
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

        view = inflater.inflate(R.layout.out_order_recycle, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rc_main);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        adapter = new InRecordRecyclerAdapter(orderList3);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickListener(new InRecordRecyclerAdapter.OnItemClickListener() {
            //此处实现onItemClick的接口
            @Override
            public void onItemClick(View view, int position, int indext) {
                Button bt = (Button) view.findViewById(R.id.foruse_icon);
                //根订单好改写订单状态
                // Log.i("forsee","-------------------------------------------------------------- "  + Integer.toString(position) + "-------------------------------------------------------------- ");
                orderList3.get(position - 1 - indext).setState("完成");
                bt.setEnabled(false);
                bt.setText("待审核");

                dbhelper = DBManger.getInstance(mContext);
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(MyDatabaseHelper.TABLE_STATE, "完成");
                int result = db.update(MyDatabaseHelper.TABLE_IN_NAME,
                        values, MyDatabaseHelper.TABLE_ORDER_ID + "= '" + orderList3.get(position - 1 - indext).getOrderID() + "'", null);
                dbhelper.close();
                Log.i("isChangeSuccess", orderList3.get(position - 1 - indext).getOrderID().toString());

                //Log.i("fortext", orders[position].getOrder_id());
                //adapter.notifyDataSetChanged();
            }


        });
        return view;
    }
}