package gdin.com.penpi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.adapter.FragmentAdapter;
import gdin.com.penpi.baidumap.MapMarkerOverlay;
import gdin.com.penpi.client.Constants;
import gdin.com.penpi.client.NotificationSettingsActivity;
import gdin.com.penpi.client.ServiceManager;
import gdin.com.penpi.db.DBManger;
import gdin.com.penpi.db.MyDatabaseHelper;
import gdin.com.penpi.fragment.MapShowFragment;
import gdin.com.penpi.fragment.OrderShowFragment;
import gdin.com.penpi.login.LoginActivity;
import gdin.com.penpi.util.SubmitUtil;
import gdin.com.penpi.transformer.DepthPageTransformer;

/**
 * 首页的界面 包括抢单列表和显示地图
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

    private ActionBarDrawerToggle mDrawerToggle;        // 定义toolbar左上角的弹出左侧菜单按扭

    private TextView school;                            // ToolBar显示学校

    private MenuItem messageRecord;                     // 设置消息记录

    private MyDatabaseHelper dataHelper;

    private PoiSearch mPoiSearch = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                SubmitUtil.showToast(MainActivity.this, "与PN服务器连接成功");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件及布局
        initView();

        /**
         * 判断是否与“长连接”连接成功
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Constants.connectSucceed.contains("成功")) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (Constants.connectSucceed.contains("成功")) {
                        handler.sendEmptyMessage(0x123);
                    }
                }
            }
        }).start();
    }

    private void initView() {
        //MainActivity的布局文件中的主要控件初始化
        school = (TextView) this.findViewById(R.id.tv_location);

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
//        actionBar.setHomeAsUpIndicator(R.drawable.menu_main);
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
        fragments.add(new OrderShowFragment());
        fragments.add(new MapShowFragment());

        //创建ViewPager的adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setPageTransformer(true,new DepthPageTransformer());

        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);

        initPoiSearch();
    }

    /**
     * 初始化学校地址的监听，【只是为了反地理编译，感觉不必要这么麻烦，以后再改】
     */
    private void initPoiSearch() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                // 获取POI检索结果
                if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                    Log.i("RecyclerViewAdapter", "未找到结果,请重新输入");
                }
                if (poiResult.getAllPoi() == null) {
                    Log.i("RecyclerViewAdapter", "未找到结果,请重新输入");
                } else {
                    LatLng poilocation = poiResult.getAllPoi().get(0).location;
                    if (poilocation != null) {
                        Double latitude = poilocation.latitude;
                        Double longitude = poilocation.longitude;
                        LatLng latLng = new LatLng(latitude, longitude);

                        MapMarkerOverlay mapMarkerOverlay = MapMarkerOverlay.getMapMarkerOverlay();
                        BaiduMap baiduMap = mapMarkerOverlay.getBaiduMap();
                        baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(latLng));
                        baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(17));
                    }
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        });
    }

    private NavigationView.OnNavigationItemSelectedListener naviListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            //点击NavigationView中定义的menu item时触发反应
            Intent intent;
            switch (menuItem.getItemId()) {
                case R.id.menu_name:
                    intent = new Intent(MainActivity.this, PersonalPageActivity.class);
                    startActivity(intent);
                    break;
                case R.id.record_name:
                    dataHelper = DBManger.getInstance(MainActivity.this);
                    intent = new Intent(MainActivity.this, OrderRecordActivity.class);
                    startActivity(intent);
                    break;
                case R.id.config_name:
                    ServiceManager.viewNotificationSettings(MainActivity.this);
                    break;
                case R.id.outlogin_name:
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
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
        messageRecord = menu.findItem(R.id.message);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.message:
                Intent intent = new Intent(MainActivity.this, NotificationSettingsActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                //主界面左上角的icon点击反应
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            new AlertDialog.Builder(MainActivity.this).setTitle("温馨提示")//设置对话框标题
                    .setMessage("是否退出？")//设置显示的内容
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //别忘了这行，否则退出不起作用 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(startMain);
                            System.exit(0);//退出程序
                        }
                    })
                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件

                        }
                    }).show();//在按键响应事件中显示此对话框
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 选择地点
     */
    public void space_list(View view) {
        Intent intent = new Intent(MainActivity.this, SpaceListActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * 调用 SubmitOrderActivity
     */
    public void map_add(View view) {
        Intent intent = new Intent(MainActivity.this, SubmitOrderActivity.class);
        startActivity(intent);
    }

    /**
     * 点击头像进入登录界面
     */
    public void register(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * SpaceListActivity 的回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 取得MapLocationList回传的数据
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                school.setText(data.getStringExtra("myLocation"));
                PoiCitySearchOption poiCitySearchOption = new PoiCitySearchOption().city("广州").keyword(data.getStringExtra("myLocation"));
                mViewPager.setCurrentItem(1);
                mPoiSearch.searchInCity(poiCitySearchOption);
            }
        }
    }

    public static ViewPager getViewPager() {
        return mViewPager;
    }
}
