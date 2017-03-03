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

import java.util.List;

import gdin.com.penpi.R;
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
