package gdin.com.penpi.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.adapter.SpaceListAdapter;
import gdin.com.penpi.bean.PoiSearchResults;

/**
 * 点击首页ToolBar的“地址栏”时 调用该类
 *
 * 作用：
 *      改变EditText的地址，会刷出该地址附近的信息
 */
public class SpaceListActivity extends AppCompatActivity implements OnGetPoiSearchResultListener {

    private PoiSearch mPoiSearch = null;
    private PoiCitySearchOption poiCitySearchOption = null;
    private List<PoiSearchResults> list = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private SpaceListAdapter adapter;
    private EditText place;
    private TextView city;

    private String poiName;
    private String poiAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_list);

        place = (EditText) findViewById(R.id.et_location2);
        city = (TextView) findViewById(R.id.tv_palce);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_RecyclerView_1);
        ImageView iv_delete = (ImageView) findViewById(R.id.map_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place.setText("");
            }
        });

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        /**
         * 初始值
         */
        if (list != null)
            list.clear();
        poiCitySearchOption = new PoiCitySearchOption().city("广州").keyword("中山大道西293");
        mPoiSearch.searchInCity(poiCitySearchOption);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SpaceListAdapter(SpaceListActivity.this, list);
        mRecyclerView.setAdapter(adapter);

//        SharedPreferences preferences = getSharedPreferences("map_location", MODE_PRIVATE);
//        String location = preferences.getString("location", "");
//        place.setText(location);
        place.setText("广东技术师范学院本部");
        place.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String city = SpaceListActivity.this.city.getText().toString();
//                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(s.toString()).city(city));
                poiCitySearchOption = new PoiCitySearchOption().city(city).keyword(place.getText().toString());
                mPoiSearch.searchInCity(poiCitySearchOption);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (place.getText().toString().equals("")) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {

        // 获取POI检索结果
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            Toast.makeText(SpaceListActivity.this, "未找到结果,请重新输入", Toast.LENGTH_LONG).show();
            return;
        }
        list.clear();
        if (poiResult.getAllPoi() == null) {
            Toast.makeText(SpaceListActivity.this, "未找到结果,请重新输入", Toast.LENGTH_LONG).show();
            return;
        } else {
            for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
                poiName = poiResult.getAllPoi().get(i).name;
                poiAdd = poiResult.getAllPoi().get(i).address;
                LatLng poilocation = poiResult.getAllPoi().get(i).location;

                if (poilocation != null) {
                    Double latitude = poilocation.latitude;
                    Double longitude = poilocation.longitude;

                    // 实例化一个地理编码查询对象
                    GeoCoder geoCoder = GeoCoder.newInstance();
                    // 设置反地理编码位置坐标
                    ReverseGeoCodeOption op = new ReverseGeoCodeOption();
                    op.location(poilocation);
                    // 发起反地理编码请求(经纬度->地址信息)
                    geoCoder.reverseGeoCode(op);
                    geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

                        @Override
                        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
                            poiAdd = arg0.getAddress();
                        }

                        @Override
                        public void onGetGeoCodeResult(GeoCodeResult arg0) {
                        }

                    });
                    PoiSearchResults results = new PoiSearchResults(poiName, poiAdd, latitude, longitude);
                    list.add(results);
                    Log.i("SpaceListActivity", list.toString());
                } else {
                    Toast.makeText(SpaceListActivity.this, "未找到结果,请重新输入", Toast.LENGTH_LONG).show();
                }
            }

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    public void setResultTo(String s) {

        // 需要返回的数据存入到intent中
        Intent intent = new Intent();
        intent.putExtra("myLocation", s);

        //设置返回数据
        setResult(RESULT_OK, intent);

        //关闭Activity
        finish();
    }
}
