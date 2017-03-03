package gdin.com.penpi.placeList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;

import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.baiduMap.MapMarkerOverlay;
import gdin.com.penpi.domain.PoiSearchResults;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> {

    private PlaceListActivity mContext;
    private List<PoiSearchResults> list;

    public PlaceListAdapter(PlaceListActivity context, List<PoiSearchResults> list) {
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
//        SharedPreferences preferences = mContext.getSharedPreferences("map_location", 0);

        if (list.size() != 0) {
            holder.mTextView1.setText(list.get(position).getmName());
            holder.mTextView2.setText(list.get(position).getmAddress());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.setResultTo(holder.mTextView1.getText().toString());
                    // 清楚百度地图的覆盖物
                    MapMarkerOverlay mapMarkerOverlay = MapMarkerOverlay.getMapMarkerOverlay();
                    BaiduMap baiduMap = mapMarkerOverlay.getBaiduMap();
                    baiduMap.clear();
                }
            });
        }
//        else
//            holder.mTextView1.setText(preferences.getString("poi" + position, "text"));

    }

    @Override
    public int getItemCount() {
        if (list.size() != 0) {
            return list.size();
        }
//        else {
//            SharedPreferences preferences = mContext.getSharedPreferences("map_location", 0);
//            return preferences.getInt("poiSize", 5);
//        }
        return 0;
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
