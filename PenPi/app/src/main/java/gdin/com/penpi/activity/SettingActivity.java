package gdin.com.penpi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import gdin.com.penpi.R;

/**
 * author：Anumbrella
 * Date：16/6/24 下午6:12
 */
public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        mToolbar.setTitle("系统设置");
        setToolbar(mToolbar);

        SwitchCompat isPush = (SwitchCompat) findViewById(R.id.setting_switch);
        isPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    JPushInterface.stopPush(SettingActivity.this.getApplicationContext());
                    Toast.makeText(SettingActivity.this, "关闭了推送通知", Toast.LENGTH_SHORT).show();
                } else JPushInterface.init(SettingActivity.this.getApplicationContext());
            }
        });
    }

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}