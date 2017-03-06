package gdin.com.penpi.myRecord;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.commonUtils.FormatUtils;
import gdin.com.penpi.commonUtils.OrderHandle;
import gdin.com.penpi.domain.Order;

public class MyTakeRecordAdapter extends RecyclerView.Adapter<MyTakeRecordAdapter.ViewHolder> {

    private Context mContext;
    private List<Order> mOrderList;

    public MyTakeRecordAdapter(List<Order> orderList) {
        mOrderList = orderList;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Toast.makeText(mContext, "已送达", Toast.LENGTH_SHORT).show();
                MyTakeRecordAdapter.this.notifyDataSetChanged();
                MySendRecordFragment.getAdapter().notifyDataSetChanged();
            }
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_out_order_recycle, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Order order = mOrderList.get(position);
        holder.order_id.setText(String.valueOf(order.getOrderID()));
        holder.startPlace.setText(order.getStartPlace());
        holder.endPlace.setText(order.getEndPlace());
        holder.order_name.setText(order.getSendOrderPeopleName());
        holder.telephone.setText(order.getSendOrderPeoplePhone());
        holder.Date.setText(FormatUtils.formatTime(order.getSendOrderDate()));
        holder.charges.setText(String.valueOf(order.getCharges()));

        switch (order.getState()) {
            case "已抢":
                holder.foruse.setEnabled(true);
                holder.foruse.setText("确认送达？");
                break;
            case "已送达":
                holder.foruse.setEnabled(false);
                holder.foruse.setText("已送达,待付款");
                break;
            case "已付款":
                holder.foruse.setEnabled(false);
                holder.foruse.setText("已付款,待评价");
                break;
            case "已完成":
                holder.foruse.setEnabled(false);
                holder.foruse.setText("已完成");
                break;
        }

        holder.itemView.findViewById(R.id.foruse_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("已抢".equals(order.getState())) {
                    new AlertDialog.Builder(mContext).setTitle("确定送达？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            order.setState("已送达");
                                            Order temp = new OrderHandle().alterOrder(order);
                                            if (temp != null) handler.sendEmptyMessage(0x123);
                                        }
                                    }).start();
                                }
                            })
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }
            }
        });
    }

    /**
     * 返回RecyclerView子项的数目
     * 当用户提交订单信息，Item数目+1，并刷新RecyclerView
     */
    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView order_id;
        TextView order_name;
        TextView startPlace;
        TextView endPlace;
        TextView charges;
        TextView telephone;
        Button foruse;
        TextView Date;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            order_id = (TextView) view.findViewById(R.id.order_people_id);
            order_name = (TextView) view.findViewById(R.id.order_people_name);
            startPlace = (TextView) view.findViewById(R.id.order_start_place);
            endPlace = (TextView) view.findViewById(R.id.order_end_place);
            telephone = (TextView) view.findViewById(R.id.Phonenum);
            charges = (TextView) view.findViewById(R.id.order_charges_name);
            Date = (TextView) view.findViewById(R.id.date_name);
            foruse = (Button) view.findViewById(R.id.foruse_icon);
        }
    }
}