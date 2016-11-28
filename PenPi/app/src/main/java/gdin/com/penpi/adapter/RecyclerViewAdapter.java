package gdin.com.penpi.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.bean.Order;


/**
 * Author       : yanbo
 * Date         : 2015-06-02
 * Time         : 09:47
 * Description  :
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    /*private int[] colors = {R.color.color_0, R.color.color_1, R.color.color_2, R.color.color_3,
            R.color.color_4, R.color.color_5, R.color.color_6, R.color.color_7,
            R.color.color_8, R.color.color_9,};*/

    private Context mContext;

    private List<Order> mOrderList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView peopleName;
        TextView startPlace;
        TextView endPlace;
        TextView charges;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            peopleName = (TextView) view.findViewById(R.id.people_name);
            startPlace = (TextView) view.findViewById(R.id.start_place);
            endPlace = (TextView) view.findViewById(R.id.end_place);
            charges = (TextView) view.findViewById(R.id.charges_name);
        }
    }

    public RecyclerViewAdapter(List<Order> orderList) {
        mOrderList = orderList;
    }

    /*public RecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.ltem_space, parent, false);
        /*CardView view = (CardView)LayoutInflater.from(parent.getContext()).inflate(R.layout.ltem_order_show, parent, false);
        return_page new ViewHolder(view);*/

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.ltem_order_show, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        /*
         * 点击抢按钮的点击事件
         * */
        holder.itemView.findViewById(R.id.grab_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder(0);
            }
        });

        return holder;
    }

    public void addOrder(int pos) {
        Order order = new Order("邓文","广州","梅州","3.0");
        mOrderList.add(pos,order);
        notifyItemInserted(pos);
    }

    /*@Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.findViewById(R.id.grab_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*
                * 抢单按钮的跳转活动
                * *//*
                mContext.startActivity(new Intent(mContext, PersonalPageActivity.class));
            }
        });
    }*/


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = mOrderList.get(position);
        holder.peopleName.setText(order.getName());
        holder.startPlace.setText(order.getStart_place());
        holder.endPlace.setText(order.getEnd_place());
        holder.charges.setText(order.getCharges());
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
