package gdin.com.penpi.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gdin.com.penpi.homeIndex.HomeActivity;
import gdin.com.penpi.R;

public class LoginActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter sim_adapter;
    private GridView GV;
    private List<Map<String, Object>> dataList;
    private int[] icon = {R.drawable.login_chat, R.drawable.login_qq, R.drawable.login_weibo};
    private String[] iconname = {"微信", "QQ", "微博"};

    /*private ClearEditText inputNumberEt;
    private ClearEditText pdNumberEt;*/

    private Toolbar loginToolbar;       //设置ToolBar

    private Button loginButton;      //登录按钮
    private Button resisterButton;      //注册按钮

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

        /*inputNumberEt = (ClearEditText) findViewById(R.id.numberEt);
        inputNumberEt = (ClearEditText) findViewById(R.id.passwardEt);*/

        loginButton = (Button) findViewById(R.id.imglogin);
        resisterButton = (Button) findViewById(R.id.resister_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        resisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResisterActivity.class);
                startActivity(intent);
            }
        });

        /*
        * 设置返回
        * */
        loginToolbar = (Toolbar) this.findViewById(R.id.login_tool_bar);
        loginToolbar.setTitle("");
        setSupportActionBar(loginToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

}
