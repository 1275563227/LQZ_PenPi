package gdin.com.penpi.placeList;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.domain.PoiSearchResults;

/**
 * 点击首页ToolBar的“地址栏”时 调用该类
 *
 * 作用：
 *      改变EditText的地址，会刷出该地址附近的信息
 */
public class PlaceListActivity extends AppCompatActivity {

    private List<PoiSearchResults> list = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private PlaceListAdapter adapter;
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


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlaceListAdapter(PlaceListActivity.this, list);
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
                String city = PlaceListActivity.this.city.getText().toString();
//                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(s.toString()).city(city));
//                poiCitySearchOption = new PoiCitySearchOption().city(city).keyword(place.getText().toString());
//                mPoiSearch.searchInCity(poiCitySearchOption);
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
