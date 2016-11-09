package gdin.com.penpi.myActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gdin.com.penpi.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private int[] colors = {R.color.color_0, R.color.color_1, R.color.color_2, R.color.color_3,
            R.color.color_4, R.color.color_5, R.color.color_6, R.color.color_7,
            R.color.color_8, R.color.color_9,};

    private Context mContext;

    public HomeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.mTextView.setBackgroundColor(mContext.getResources().getColor(colors[position%(colors.length)]));
        SharedPreferences preferences = mContext.getSharedPreferences("data", 0);
        holder.mTextView.setText(preferences.getString("poi" + position, "text"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        SharedPreferences preferences = mContext.getSharedPreferences("data", 0);
        return preferences.getInt("poiSize", 12);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView;

        public ViewHolder(TextView view) {
            super(view);
            mTextView = view;
        }
    }
}
