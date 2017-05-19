package gdin.com.penpi.homeIndex;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.amap.MakeMarker;
import gdin.com.penpi.domain.Order;
import gdin.com.penpi.commonUtils.FormatUtils;
import gdin.com.penpi.internetUtils.OrderHandle;

public class ShowOrderAdapter extends RecyclerView.Adapter<ShowOrderAdapter.ViewHolder> {

    private Context mContext;

    private List<Order> mOrderList;

    private int mPosition;

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

    /**
     * 构造函数
     */
    public ShowOrderAdapter(List<Order> mOrderList) {
        this.mOrderList = mOrderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mOrderList.size() != 0) {
            final Order order = mOrderList.get(position);
            if (order != null) {
                holder.tv_userName.setText(order.getSendOrderPeopleName());
                holder.tv_startPlace.setText(order.getStartPlace());
                holder.tv_endPlace.setText(order.getEndPlace());
                holder.tv_charges.setText(String.valueOf(order.getCharges()));
                holder.tv_date.setText(FormatUtils.formatTime(order.getSendOrderDate()));
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
                        // 跳转到地图界面
                        HomeActivity.getViewPager().setCurrentItem(1);
                        new MakeMarker(mContext, MapShowFragment.getaMap()).addMarker(mOrderList.get(position));
                    }
                });
            }
        }
    }

    /**
     * 返回RecyclerView子项的数目
     */
    @Override
    public int getItemCount() {
        if (mOrderList != null) {
            return mOrderList.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv_cardView;
        TextView tv_userName;
        TextView tv_startPlace;
        TextView tv_endPlace;
        TextView tv_charges;
        TextView tv_date;

        public ViewHolder(View view) {
            super(view);
            cv_cardView = (CardView) view.findViewById(R.id.card_view_order_show);
            tv_userName = (TextView) view.findViewById(R.id.people_name);
            tv_startPlace = (TextView) view.findViewById(R.id.start_place);
            tv_endPlace = (TextView) view.findViewById(R.id.end_place);
            tv_date = (TextView) view.findViewById(R.id.date_name);
            tv_charges = (TextView) view.findViewById(R.id.charges_name);
        }

    }
}
