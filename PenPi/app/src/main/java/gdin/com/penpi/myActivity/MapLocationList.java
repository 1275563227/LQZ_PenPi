package gdin.com.penpi.myActivity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.List;

import gdin.com.penpi.R;

public class MapLocationList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location_list);

        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        String location = preferences.getString("location", "");

        mRecyclerView = (RecyclerView) findViewById(R.id.list_RecyclerView_1);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter(MapLocationList.this));

        EditText editText = (EditText) findViewById(R.id.et_location2);
        editText.setText(location);
    }
}
