package gdin.com.penpi.placeList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.domain.Address;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> {

    private PlaceListActivity mContext;
    private List<Address> addresses;

    public PlaceListAdapter(PlaceListActivity context) {
        this.mContext = context;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.ltem_space, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (addresses.size() != 0) {
            holder.tv_title.setText(addresses.get(position).getTitle());
            holder.tv_content.setText(addresses.get(position).getText());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.setResultAndBack(holder.tv_title.getText().toString().trim());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (addresses != null) {
            return addresses.size();
        }
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
