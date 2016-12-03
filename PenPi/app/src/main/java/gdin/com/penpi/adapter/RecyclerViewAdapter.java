package gdin.com.penpi.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.bean.Order;
import gdin.com.penpi.util.SubmitUtil;


/**
 * Author       : yanbo
 * Date         : 2015-06-02
 * Time         : 09:47
 * Description  :
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;

    private List<Order> mOrderList;

    private int mPosition;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                SubmitUtil.showToast(mContext, "抢单成功，请在'我的记录'查看详细信息");
                mOrderList.remove(mPosition);
                notifyDataSetChanged();
            }
            if (msg.what == 0x124) {
                SubmitUtil.showToast(mContext, "抢单失败，该订单已被其他人获取");
            }
        }
    };

    static class ViewHolder extends RecyclerView.ViewHolder {
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

    public RecyclerViewAdapter(List<Order> orderList) {
        mOrderList = orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.ltem_order_show, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mOrderList.size() != 0) {
            final Order order = mOrderList.get(position);
            if (order != null) {
                holder.peopleName.setText(order.getName());
                holder.startPlace.setText(order.getStart_place());
                holder.endPlace.setText(order.getEnd_place());
                holder.charges.setText(order.getCharges());
                holder.date.setText(order.getDate());
                /**
                 * 点击抢按钮的点击事件
                 */
                holder.itemView.findViewById(R.id.grab_icon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                boolean isChange = SubmitUtil.changeOrderStatetoServlet(Integer.toString(order.getId()), "已抢");
                                Log.i("RecyclerViewAdapter", isChange + "");
                                mPosition = position;
                                if (isChange) {
                                    handler.sendEmptyMessage(0x123);
                                } else
                                    handler.sendEmptyMessage(0x124);
                            }
                        }).start();
                    }
                });
            }
        }
    }

    /**
     * 返回RecyclerView子项的数目
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
