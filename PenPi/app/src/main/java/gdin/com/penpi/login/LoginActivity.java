package gdin.com.penpi.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gdin.com.penpi.R;
import gdin.com.penpi.commonUtils.UserHandle;
import gdin.com.penpi.domain.User;
import gdin.com.penpi.homeIndex.HomeActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Map<String, Object>> dataList;
    private int icon[] = {R.drawable.login_chat, R.drawable.login_qq, R.drawable.login_weibo};
    private String iconname[] = {"微信", "QQ", "微博"};

    private static User user;

    public static User getUser() {
        return user;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
            if (msg.what == 0x124) {
                Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        GridView GV = (GridView) findViewById(R.id.gird1);
        dataList = new ArrayList<>();
        SimpleAdapter sim_adapter = new SimpleAdapter(this, getData(), R.layout.item_login, new String[]{"pic", "text"}, new int[]{R.id.pic, R.id.text});
        GV.setAdapter(sim_adapter);

        findViewById(R.id.bt_login).setOnClickListener(this);
        findViewById(R.id.bt_login_register).setOnClickListener(this);

        // 设置返回
        Toolbar loginToolbar = (Toolbar) this.findViewById(R.id.login_tool_bar);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                // 登录
                EditText tv_login_name = (EditText) findViewById(R.id.tv_login_name);
                EditText tv_login_password = (EditText) findViewById(R.id.tv_login_password);
                final String name = tv_login_name.getText().toString().trim();
                final String pawword = tv_login_password.getText().toString().trim();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User temp = new UserHandle().login(name, pawword);
                        if (temp != null) {
                            user = temp;
                            handler.sendEmptyMessage(0x123);
                        } else handler.sendEmptyMessage(0x124);
                    }
                }).start();
                break;
            case R.id.bt_login_register:
                // 注册
                Intent intent = new Intent(LoginActivity.this, ResisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private List<Map<String, Object>> getData() {

        for (int j = 0; j < 3; j++) {
            Map<String, Object> map = new HashMap<>();
            map.put("pic", icon[j]);
            map.put("text", iconname[j]);
            dataList.add(map);
        }

        return dataList;
    }

}
