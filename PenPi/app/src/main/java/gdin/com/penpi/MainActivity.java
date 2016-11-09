package gdin.com.penpi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import gdin.com.penpi.myActivity.AddOrderActivity;
import gdin.com.penpi.damain.Order;
import gdin.com.penpi.fragment.MapFragment;
import gdin.com.penpi.mapUtil.Utils;
import gdin.com.penpi.myActivity.MapLocationList;
import gdin.com.penpi.support_library_demo.FragmentAdapter;
import gdin.com.penpi.support_library_demo.InfoDetailsFragment;
import gdin.com.penpi.support_library_demo.SubActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个中文版Demo App搞定所有Android的Support Library新增所有兼容控件
 * 支持最新2015 Google I/O大会Android Design Support Library
 */
public class MainActivity extends AppCompatActivity {
    //将ToolBar与TabLayout结合放入AppBarLayout
    private Toolbar mToolbar;
    //DrawerLayout中的左侧菜单控件
    private NavigationView mNavigationView;
    //DrawerLayout控件
    private DrawerLayout mDrawerLayout;
    //Tab菜单，主界面上面的tab切换菜单
    private TabLayout mTabLayout;
    //v4中的ViewPager控件
    public static ViewPager mViewPager;

    private ActionBarDrawerToggle mDrawerToggle;        ////定义toolbar左上角的弹出左侧菜单按扭

    private MenuItem listenItem;
    private MenuItem nolistenItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件及布局
        initView();

        /**
         * 标题的定位
         */
        TextView location = (TextView) findViewById(R.id.tv_location);
    }

    private void initView() {
        //MainActivity的布局文件中的主要控件初始化
        mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mToolbar.setTitle("");
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        mTabLayout = (TabLayout) this.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);

        //初始化ToolBar
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
                R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.menumain);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        //对NavigationView添加item的监听事件
        mNavigationView.setNavigationItemSelectedListener(naviListener);
        //开启应用默认打开DrawerLayout
//        mDrawerLayout.openDrawer(mNavigationView);

        //初始化TabLayout的title数据集
        List<String> titles = new ArrayList<>();
        titles.add("列表");
        titles.add("地图");
        //初始化TabLayout的title
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        //初始化ViewPager的数据集
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new InfoDetailsFragment());
        fragments.add(new MapFragment());
//        fragments.add(new ShareFragment());
//        fragments.add(new AgendaFragment());
        //创建ViewPager的adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    private NavigationView.OnNavigationItemSelectedListener naviListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            //点击NavigationView中定义的menu item时触发反应
            switch (menuItem.getItemId()) {
                case R.id.menu_name:
                    Intent intent = new Intent(MainActivity.this, SubActivity.class);
                    startActivity(intent);
                    break;
                case R.id.record_name:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.config_name:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.outlogin_name:

                    break;
            }
            //关闭DrawerLayout回到主界面选中的tab的fragment页
            mDrawerLayout.closeDrawer(mNavigationView);
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //主界面右上角的menu菜单
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        menu_item = menu.findItem(R.id.record_listern);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.record_listern:
                if(!(item.isCheckable())){
                    listenItem.setChecked(true);
                }
                break;

            case R.id.record_nolistern:
                if(!(item.isCheckable())){
                    nolistenItem.setChecked(true);
                }
                break;

            case android.R.id.home:
                //主界面左上角的icon点击反应
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 按钮点击事件
     *
     * @param view
     */

    EditText et_start;
    EditText et_end;
    EditText et_name;
    EditText et_phone_number;
    EditText et_remark;

    String response;
    Order order_response;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                // 设置show组件显示服务器响应
                et_start.setText(order_response.getStart_plac());
                et_end.setText(order_response.getEnd_plac());
                et_name.setText(order_response.getName());
                et_phone_number.setText(order_response.getPhone_number());
                et_remark.setText(order_response.getRemark());
            }
        }
    };

    public void btn_Submit(View view) {
        et_start = (EditText) findViewById(R.id.et_start);
        et_end = (EditText) findViewById(R.id.et_end);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_remark = (EditText) findViewById(R.id.et_remark);

        final Order order = new Order();
        order.setStart_plac(et_start.getText().toString());
        order.setEnd_plac(et_end.getText().toString());
        order.setName(et_name.getText().toString());
        order.setPhone_number(et_phone_number.getText().toString());
        order.setRemark(et_remark.getText().toString());

        order.setStart_plac("阳江");
        order.setEnd_plac("广技师");
        order.setName("傻吊志鹏");
        order.setPhone_number("110");
        order.setRemark("广师是我家，大家笑哈哈");

        Log.i("HTTP", "开启线程准备发送");
        Log.i("HTTP", order.toString());
        new Thread() {
            @Override
            public void run() {
                response = Utils.sendPost("http://139.199.159.60:8080/Demo/servlet/testClientServlet", order);
                if (response != null) {
                    Log.i("TAG", response);
                    order_response = Utils.paresJSON_withGSON(response);
                    handler.sendEmptyMessage(0x123);
                }
            }
        }.start();
    }

    public void bt_map_add(View view) {
        Intent intent = new Intent(MainActivity.this, AddOrderActivity.class);
        startActivity(intent);
    }

    public void map_location_list(View view) {
        Intent intent = new Intent(MainActivity.this, MapLocationList.class);
        startActivity(intent);
    }
}
