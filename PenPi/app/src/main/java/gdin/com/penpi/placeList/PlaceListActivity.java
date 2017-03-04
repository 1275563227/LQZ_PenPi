package gdin.com.penpi.placeList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import gdin.com.penpi.R;
import gdin.com.penpi.amap.MyPoiSearch;
import gdin.com.penpi.homeIndex.MapShowFragment;

/**
 * 点击首页ToolBar的“地址栏”时 调用该类
 * <p/>
 * 作用：
 * 改变EditText的地址，会刷出该地址附近的信息
 */
public class PlaceListActivity extends AppCompatActivity implements View.OnClickListener {

    private PlaceListAdapter adapter;
    private EditText et_place;

    private Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_list);
        this.initView(); // 初始化界面
        this.map = MapShowFragment.getMap();
    }

    public void initView() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.place_list_cecyclerView);
        et_place = (EditText) findViewById(R.id.et_location2);
        TextView tv_city = (TextView) findViewById(R.id.tv_palce);
        if (map != null)
            tv_city.setText(map.get("City"));
        else
            tv_city.setText("无");
        findViewById(R.id.bt_place_list_srue).setOnClickListener(this);
        findViewById(R.id.map_delete).setOnClickListener(this);

        // 设置RecyclerView的Adapter
        adapter = new PlaceListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        // 第一次设置
        MyPoiSearch.getInstance(PlaceListActivity.this, adapter).searchWithKeyword("广东技术师范学院", "");
        et_place.setText("广东技术师范学院");
        et_place.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!"".equals(s.toString().trim()))
                    MyPoiSearch.getInstance(PlaceListActivity.this, adapter).searchWithKeyword(s.toString(), "");
                else
                    Toast.makeText(PlaceListActivity.this, "输入为空", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void setResultAndBack(String s) {

        // 需要返回的数据存入到intent中
        Intent intent = new Intent();
        intent.putExtra("myLocation", s);

        //设置返回数据
        setResult(RESULT_OK, intent);

        //关闭Activity
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_place_list_srue:
                this.setResultAndBack(et_place.getText().toString().trim());
                break;
            case R.id.map_delete:
                et_place.setText("");
                break;
        }
    }
}
