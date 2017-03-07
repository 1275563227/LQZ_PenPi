package gdin.com.penpi.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import gdin.com.penpi.R;
import gdin.com.penpi.commonUtils.MyBitmap;
import gdin.com.penpi.login.LoginActivity;

public class PersonalPageActivity extends AppCompatActivity {

    private ImageView personHead;
    private Bitmap personHeadBitmap;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                if (personHeadBitmap != null)
                    personHead.setImageBitmap(personHeadBitmap);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_home_page);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");

        TextView tv_userName = (TextView) findViewById(R.id.user_name);
        tv_userName.setText(LoginActivity.getUser().getUsername());

        personHead = (ImageView) findViewById(R.id.user_detail_icon);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    personHeadBitmap = MyBitmap.getImageBitmap(LoginActivity.getUser().getUsername() + "/head_image.jpg");
                    if (personHeadBitmap == null)
                        personHeadBitmap = BitmapFactory.decodeResource(PersonalPageActivity.this.getResources(), R.drawable.header_icon);
                    handler.sendEmptyMessage(0x123);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}