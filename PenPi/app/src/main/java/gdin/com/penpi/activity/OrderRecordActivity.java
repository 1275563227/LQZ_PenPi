package gdin.com.penpi.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.adapter.SimpleFragmentPagerAdapter;
import gdin.com.penpi.fragment.MyTakeRecordFragment;
import gdin.com.penpi.fragment.MySendRecordFragment;

public class OrderRecordActivity extends AppCompatActivity {

    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_record);

        mToolbar = (Toolbar) findViewById(R.id.record_tool_bar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MySendRecordFragment());
        fragments.add(new MyTakeRecordFragment());

        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
