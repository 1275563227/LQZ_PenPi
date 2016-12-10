package gdin.com.penpi.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.activity.EvaluationActivity;
import gdin.com.penpi.adapter.OutRecordRecyclerAdapter;
import gdin.com.penpi.bean.Order;
import gdin.com.penpi.db.DBManger;
import gdin.com.penpi.db.MyDatabaseHelper;
import gdin.com.penpi.util.SubmitUtil;

/**
 * Created by Administrator on 2016/11/30.
 */
public class OutRecordFragment extends android.support.v4.app.Fragment {


    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private OutRecordRecyclerAdapter adapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private View view;

    private boolean flag = false;
    private Context mContext;
    MyDatabaseHelper dbhelper;
    private List<Order> orderList3 = new ArrayList<>();


    public static InRecordFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        InRecordFragment pageFragment = new InRecordFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    private void initOrders() {
        int i = 0;
        dbhelper = DBManger.getInstance(mContext);
        Cursor cursor = dbhelper.getReadableDatabase().rawQuery("select * from " + MyDatabaseHelper.TABLE_OUT_NAME, null);
        while (cursor.moveToNext()){
            Log.i("forsee", cursor.getString(0));
            Order or= new Order();
            or.setId(cursor.getString(0));
            or.setStart_place(cursor.getString(1));
            or.setEnd_place(cursor.getString(2));
            or.setName(cursor.getString(3));
            or.setPhone_number(cursor.getString(4));
            or.setCharges(cursor.getString(5));
            or.setRemark(cursor.getString(6));
            or.setState(cursor.getString(7));
            or.setDate(cursor.getString(8));
            orderList3.add(or);
            i++;
        }
        cursor.close();
        dbhelper.close();
        /*SharedPreferences pref = getActivity().getSharedPreferences("grab_order",getActivity().MODE_PRIVATE);
        Map<String, ?> map = pref.getAll();
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            list.add(entry.getValue().toString());
        }*/
        /** for (int i=0; i<list.size(); i++) {
         order[i] = SpiltStringUtil.messageToOrder(list.get(i));
         //Log.d("InRecordFragment",list.get(0));
         }*/
        //Log.d("InRecordFragment",map.toString());
        //pref.getString();
        /*for (int i = 0; i < list.size(); i++) {
            orderList2.add(SpiltStringUtil.messageToOrder(list.get(i)));
            Log.i("orderlist1", orderList2.get(i).getDate());
            Log.i("orderlist2", orderList2.get(i).getCharges());
        }
        for (int i = 0; i < orders.length; i++) {
            orderList.add(orders[i]);
        }*/
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
        adapter = new OutRecordRecyclerAdapter(orderList3);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickListener(new OutRecordRecyclerAdapter.OnItemClickListener() {
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
                                        values, MyDatabaseHelper.TABLE_ORDER_ID + "= '" + orderList3.get(position - 1 - indext).getId() + "'", null);
                                dbhelper.close();

                                orderList3.get(position - 1 -indext).setState("完成");
                                bt.setEnabled(false);
                                bt.setText("已完成");

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        SubmitUtil.changeOrderStatetoServlet(orderList3.get(position - 1 - indext).getId(), "完成");
                                        //SubmitUtil.changeOrderStatetoServlet(order.getId(), "已抢")
                                    }
                                }).start();

                                Intent intent = new Intent(getActivity(), EvaluationActivity.class);
                                startActivity(intent);

//                                Intent intent = new Intent(getActivity(), MainActivity.class);
//                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bt.setEnabled(true);
                                bt.setText("完成");

//                                flag = true;
//                                Log.i("OutRecordFragment", Boolean.valueOf(flag).toString());
                            }
                        }).show();


            }


        });
        return view;


  }
}
