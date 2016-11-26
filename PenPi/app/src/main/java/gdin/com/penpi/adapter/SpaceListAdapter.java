package gdin.com.penpi.adapter;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.bean.PoiSearchResults;
import gdin.com.penpi.activity.SpaceListActivity;

public class SpaceListAdapter extends RecyclerView.Adapter<SpaceListAdapter.ViewHolder> {

    private SpaceListActivity mContext;
    private List<PoiSearchResults> list;

    public SpaceListAdapter(SpaceListActivity context, List<PoiSearchResults> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.ltem_space, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SharedPreferences preferences = mContext.getSharedPreferences("data", 0);

        if (list.size() == 0)
            holder.mTextView1.setText(preferences.getString("poi" + position, "text"));
        else{
            holder.mTextView1.setText(list.get(position).getmName());
            holder.mTextView2.setText(list.get(position).getmAddress());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.setResultTo(holder.mTextView1.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list.size() == 0){
            SharedPreferences preferences = mContext.getSharedPreferences("data", 0);
            return preferences.getInt("poiSize", 12);
        }else return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView imageView;

        public ViewHolder(LinearLayout view) {
            super(view);
            mTextView1 = (TextView) view.findViewById(R.id.tv_title_show);
            mTextView2 = (TextView) view.findViewById(R.id.tv_content_show);
            imageView = (ImageView) view.findViewById(R.id.iv_list_show);
        }
    }
}