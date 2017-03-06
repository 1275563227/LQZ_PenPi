package gdin.com.penpi.placeList;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.commonUtils.JacksonUtils;
import gdin.com.penpi.domain.Address;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> {

    private static PlaceListActivity mContext;
    private List<Address> addresses;

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Toast.makeText(mContext, "正在更新...", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public PlaceListAdapter(PlaceListActivity context) {
        mContext = context;
    }

    public void setAddresses(List<Address> list) {
        this.addresses = list;
        handler.sendEmptyMessage(0x123);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_space, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d("[PlaceListAdapter]", "onBindViewHolder..." + position);
        if (addresses.size() != 0) {
            holder.tv_title.setText(addresses.get(position).getTitle());
            holder.tv_content.setText(addresses.get(position).getText());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.setResultAndBack(JacksonUtils.writeJSON(addresses.get(position)));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (addresses != null)
            return addresses.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_content;
        public ImageView imageView;

        public ViewHolder(LinearLayout view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title_show);
            tv_content = (TextView) view.findViewById(R.id.tv_content_show);
            imageView = (ImageView) view.findViewById(R.id.iv_list_show);
        }
    }
}
