package gdin.com.penpi.amap;

import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.Map;

import gdin.com.penpi.homeIndex.MapShowFragment;


public class MyPoiSearch implements PoiSearch.OnPoiSearchListener {

    private Context mContext;
    private MyPoiSearch myPoiSearch;
    private Map<String, String> map;
    private LatLonPoint point;

    public MyPoiSearch(Context mContext) {
        this.mContext = mContext;
        this.map = MapShowFragment.getMap();
        this.point = new LatLonPoint(Double.parseDouble(map.get("Latitude")), Double.parseDouble(map.get("Longitude")));
    }

    public MyPoiSearch getInstance(Context context) {
        if (myPoiSearch == null) {
            synchronized (MyPoiSearch.class) {
                if (myPoiSearch == null) myPoiSearch = new MyPoiSearch(context);
            }
        }
        return myPoiSearch;
    }

    /**
     * @param keyWord     表示搜索字符串
     * @param ctgr        参数表示POI搜索类型，二者选填其一:
     *                    POI搜索类型共分为以下20种：汽车服务|汽车销售|
     *                    汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
     *                    住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
     *                    金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
     * @param city        表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
     * @param pageSize    设置每页最多返回多少条poiitem
     * @param currentPage 设置查询页码
     */
    public void searchWithKeyword(String keyWord, String ctgr, String city, int pageSize, int currentPage) {

        PoiSearch.Query query = new PoiSearch.Query(keyWord, ctgr, city);
        query.setPageSize(pageSize);
        query.setPageNum(currentPage);
        PoiSearch mPoiSearch = new PoiSearch(mContext, query);
        mPoiSearch.setOnPoiSearchListener(this);
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        mPoiSearch.setBound(new PoiSearch.SearchBound(point, 5000, true));
        mPoiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
