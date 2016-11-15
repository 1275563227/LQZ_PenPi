package gdin.com.penpi.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gdin.com.penpi.MainActivity;
import gdin.com.penpi.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    private ListView listView;
    private SimpleAdapter sim_adapter;
    private GridView GV;
    private List<Map<String, Object>> dataList;
    private int[] icon = {R.drawable.login_chat, R.drawable.login_qq, R.drawable.login_weibo};
    private String[] iconname = {"微信", "QQ", "微博"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        // ImageView imgLogin = (ImageView) findViewById(R.id.imglogin);
        // imgLogin.setOnClickListener(this);
        GV = (GridView) findViewById(R.id.gird1);
        dataList = new ArrayList<Map<String, Object>>();
        sim_adapter = new SimpleAdapter(this, getData(), R.layout.item_login, new String[]{"pic", "text"}, new int[]{R.id.pic, R.id.text});
        GV.setAdapter(sim_adapter);

    }

    private List<Map<String, Object>> getData() {

        for (int j = 0; j < 3; j++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pic", icon[j]);
            map.put("text", iconname[j]);
            dataList.add(map);
        }

        return dataList;
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.imglogin:
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
