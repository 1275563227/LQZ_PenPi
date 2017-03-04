package gdin.com.penpi.homeIndex;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gdin.com.penpi.R;
import gdin.com.penpi.activity.PersonalPageActivity;
import gdin.com.penpi.placeList.PlaceListActivity;
import gdin.com.penpi.activity.SubmitOrderActivity;
import gdin.com.penpi.login.LoginActivity;
import gdin.com.penpi.myRecord.MyOrderRecordActivity;

/**
 * 首页的界面 包括抢单列表和显示地图
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener{


    private NavigationView mNavigationView; //DrawerLayout中的左侧菜单控件

    private DrawerLayout mDrawerLayout;     //DrawerLayout控件

    public static ViewPager mViewPager;     //v4中的ViewPager控件

    private TextView tv_address;            // ToolBar显示的地址

    public static ViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化控件及布局
     */
    private void initView() {
        tv_address = (TextView) this.findViewById(R.id.tv_location);

        //将ToolBar与TabLayout结合放入AppBarLayout
        Toolbar mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mToolbar.setTitle("");
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);

        //Tab菜单，主界面上面的tab切换菜单
        TabLayout mTabLayout = (TabLayout) this.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);

        //初始化ToolBar
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
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
        fragments.add(new ShowOrderFragment());
        fragments.add(new MapShowFragment());

        //创建ViewPager的adapter
        HomeIndexAdapter adapter = new HomeIndexAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);

        // 设置标题栏的地址、右下角按钮、侧滑头像的点击事件
        findViewById(R.id.ll_home_palce).setOnClickListener(this);
        findViewById(R.id.bt_home_save_order).setOnClickListener(this);

        //获取头布局文件
        View headerView = mNavigationView.getHeaderView(0);
        headerView.findViewById(R.id.people_head_image).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.ll_home_palce:
                // 选择地点
                intent = new Intent(HomeActivity.this, PlaceListActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.bt_home_save_order:
                // 调用 SubmitOrderActivity
                intent = new Intent(HomeActivity.this, SubmitOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.people_head_image:
                // 点击头像进入登录界面
                intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * NavigationViewd的menu_item点击事件
     */
    private NavigationView.OnNavigationItemSelectedListener naviListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            //点击NavigationView中定义的menu item时触发反应
            Intent intent;
            switch (menuItem.getItemId()) {
                case R.id.menu_name:
                    intent = new Intent(HomeActivity.this, PersonalPageActivity.class);
                    startActivity(intent);
                    break;
                case R.id.record_name:
                    intent = new Intent(HomeActivity.this, MyOrderRecordActivity.class);
                    startActivity(intent);
                    break;
                case R.id.config_name:
                    break;
                case R.id.outlogin_name:
                    intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
            }
            //关闭DrawerLayout回到主界面选中的tab的fragment页
            mDrawerLayout.closeDrawer(mNavigationView);
            return false;
        }
    };

    /**
     * 主界面右上角的menu菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.message:
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
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            new AlertDialog.Builder(HomeActivity.this).setTitle("温馨提示") //设置对话框标题
                    .setMessage("是否退出？")//设置显示的内容
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //别忘了这行，否则退出不起作用 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(startMain);
                            System.exit(0);//退出程序
                        }
                    })
                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();//在按键响应事件中显示此对话框
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * PlaceListActivity 的回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 取得MapLocationList回传的数据
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                tv_address.setText(data.getStringExtra("myLocation"));
            }
        }
    }
}
