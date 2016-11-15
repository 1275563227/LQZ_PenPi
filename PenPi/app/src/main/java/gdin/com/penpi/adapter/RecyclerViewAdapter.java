package gdin.com.penpi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import gdin.com.penpi.R;
import gdin.com.penpi.activity.SubActivity;


/**
 * Author       : yanbo
 * Date         : 2015-06-02
 * Time         : 09:47
 * Description  :
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private int[] colors = {R.color.color_0, R.color.color_1, R.color.color_2, R.color.color_3,
            R.color.color_4, R.color.color_5, R.color.color_6, R.color.color_7,
            R.color.color_8, R.color.color_9,};

    private Context mContext;

    public RecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.ltem_space, parent, false);
        CardView view = (CardView)LayoutInflater.from(parent.getContext()).inflate(R.layout.ltem_order_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.itemView.setBackgroundColor(mContext.getResources().getColor(colors[position%(colors.length)]));
//        holder.mTextTitle.setText(position + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, SubActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return colors.length * 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextTitle;
        public final TextView mTextContent;
        public final ImageView mImageView;

        public ViewHolder(CardView view) {
            super(view);
            mTextTitle = (TextView) view.findViewById(R.id.tv_title);
            mTextContent =  (TextView) view.findViewById(R.id.tv_content);
            mImageView =(ImageView)view.findViewById(R.id.iv_icon);
        }
    }
}
