package gdin.com.penpi.amap;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;

import gdin.com.penpi.domain.Address;
import gdin.com.penpi.placeList.PlaceListAdapter;

public class MyPoiSearch implements PoiSearch.OnPoiSearchListener {

    private Context mContext;
    private PlaceListAdapter adapter;

    public MyPoiSearch(Context mContext, PlaceListAdapter adapter) {
        this.mContext = mContext;
        this.adapter = adapter;
    }

    /**
     * @param keyWord 表示搜索字符串
     * @param city    表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
     */
    public void searchWithKeyword(String keyWord, String city) {

        /**
         * @param ctgr        第二参数表示POI搜索类型，二者选填其一:
         *                    POI搜索类型共分为以下20种：汽车服务|汽车销售|
         *                    汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
         *                    住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
         *                    金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
         */
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", city);
        query.setPageSize(10);        // 设置每页最多返回多少条poiitem
        query.setPageNum(1);// 设置查询页码
        PoiSearch mPoiSearch = new PoiSearch(mContext, query);
        mPoiSearch.setOnPoiSearchListener(this);
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
//        mPoiSearch.setBound(new PoiSearch.SearchBound(point, 5000, true));
        mPoiSearch.searchPOIAsyn();
    }

    /**
     * 异步搜索回调
     *
     * @param poiResult 返回结果集
     * @param rCode     1000为成功
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        if (rCode == 1000 && poiResult != null) {
            ArrayList<Address> addresses = new ArrayList<>();
            ArrayList<PoiItem> items = poiResult.getPois();
            for (PoiItem item : items) {
                //获取经纬度对象
                LatLonPoint llp = item.getLatLonPoint();
                double lon = llp.getLongitude();
                double lat = llp.getLatitude();
                //获取标题
                String title = item.getTitle();
                //获取内容
                String text = item.getSnippet();
                Log.d("[MyPoiSearch]", title + "-------->" + text);
                addresses.add(new Address(lon, lat, title, text));
            }
            adapter.setAddresses(addresses);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
