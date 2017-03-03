package gdin.com.penpi.myRecord;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import gdin.com.penpi.domain.Order;
import gdin.com.penpi.dbUtils.DBManger;
import gdin.com.penpi.dbUtils.MyDatabaseHelper;
import gdin.com.penpi.commonUtils.ComparatorDate;
import gdin.com.penpi.commonUtils.OrderHandle;
import gdin.com.penpi.commonUtils.UserHandle;

/**
 * Created by Administrator on 2016/11/30.
 */
public class MySendRecordFragment extends android.support.v4.app.Fragment {


    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private MySendRecordAdapter adapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private View view;

    private boolean flag = false;
    private Context mContext;
    MyDatabaseHelper dbhelper;
    private List<Order> orderList = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x333) {
                //对从服务器传入的orderList进行排序
                ComparatorDate c = new ComparatorDate();
                Collections.sort(orderList, c);
                adapter = new MySendRecordAdapter(orderList);
                mRecyclerView.setAdapter(adapter);
            }
            if (msg.what == 0x334) {
                Toast.makeText(MySendRecordFragment.this.getActivity(), "你没有发单记录！", Toast.LENGTH_SHORT).show();
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
                if (orderList != null)
                    orderList.clear();
                orderList = new UserHandle().findMySendOrders(1);
                if (orderList != null) {
                    handler.sendEmptyMessage(0x333);
                } else
                    handler.sendEmptyMessage(0x334);
            }
        }).start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initOrders();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        flag = false;
        view = inflater.inflate(R.layout.out_order_recycle, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rc_main);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        adapter = new MySendRecordAdapter(orderList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickListener(new MySendRecordAdapter.OnItemClickListener() {
            //此处实现onItemClick的接口
            @Override
            public void onItemClick(View view, final int position, final int indext) {

                final Button bt = (Button) view.findViewById(R.id.foruse_icon);

                new AlertDialog.Builder(getActivity()).setTitle("确定付款？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                flag = true;
                                dbhelper = DBManger.getInstance(mContext);
                                SQLiteDatabase db = dbhelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(MyDatabaseHelper.TABLE_STATE, "完成");
                                int result = db.update(MyDatabaseHelper.TABLE_OUT_NAME,
                                        values, MyDatabaseHelper.TABLE_ORDER_ID + "= '" + orderList.get(position - 1 - indext).getOrderID() + "'", null);
                                dbhelper.close();

                                orderList.get(position - 1 - indext).setState("完成");
                                bt.setEnabled(false);
                                bt.setText("已完成");

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO
                                        //SubmitUtil.changeOrderStatetoServlet(orderList3.get(position - 1 - indext).getOrderID(), "完成");
                                        new OrderHandle().alterOrderState(orderList.get(position - 1 - indext).getOrderID(), OrderHandle.HASGRAP);
                                        //SubmitUtil.changeOrderStatetoServlet(order.getId(), "已抢")
                                    }
                                }).start();

                                Intent intent = new Intent(getActivity(), EvaluationActivity.class);
                                startActivity(intent);

//                                Intent intent = new Intent(getActivity(), HomeActivity.class);
//                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bt.setEnabled(true);
                                bt.setText("完成");

//                                flag = true;
//                                Log.i("MySendRecordFragment", Boolean.valueOf(flag).toString());
                            }
                        }).show();


            }


        });
        return view;


    }
}
