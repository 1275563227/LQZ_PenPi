package gdin.com.penpi.adapter;

/**
 * Created by Administrator on 2016/11/30.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.bean.Order;


/**
 * Created by Administrator on 2016/11/27.
 */
public class OutRecordRecyclerAdapter extends RecyclerView.Adapter<OutRecordRecyclerAdapter.ViewHolder> {
    /*private int[] colors = {R.color.color_0, R.color.color_1, R.color.color_2, R.color.color_3,
            R.color.color_4, R.color.color_5, R.color.color_6, R.color.color_7,
            R.color.color_8, R.color.color_9,};*/

    private Context mContext;

    private List<Order> mOrderList;

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    //定义OnItemClickListener的接口,便于在实例化的时候实现它的点击效果
    public interface OnItemClickListener {
        void onItemClick(View view, int itemCount, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            foruse.setOnClickListener(this);
        }

        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //此处调用的是onItemClick方法，而这个方法是会在RecyclerAdapter被实例化的时候实现
                mOnItemClickListener.onItemClick(v, getItemCount(), getPosition());
            }
        }
    }

    public OutRecordRecyclerAdapter(List<Order> orderList) {
        mOrderList = orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_out_order_recycle, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Log.i("forsee","----------------------------------------------------------------------------------------------------------------------");
        int legh = mOrderList.size();
        Order order = mOrderList.get(legh - position - 1);
        holder.order_id.setText(order.getId().substring(0, 11));
        holder.order_name.setText(order.getName());
        holder.startPlace.setText(order.getStart_place());
        holder.endPlace.setText(order.getEnd_place());
        holder.telephone.setText(order.getPhone_number());
        holder.charges.setText(order.getCharges());
        holder.Date.setText(order.getDate());
        // holder.foruse.setEnabled(true);
        // holder.foruse.setText("完成");

        if (order.getState().equals("完成")) {
            holder.foruse.setText("已完成");
            holder.foruse.setEnabled(false);
        } else {
            holder.foruse.setEnabled(true);
            holder.foruse.setText("完成");
        }
    }

    @Override
    /*
    * 返回RecyclerView子项的数目
    * 当用户提交订单信息，Item数目+1，并刷新RecyclerView
    * */
    public int getItemCount() {
        return mOrderList.size();
    }

}