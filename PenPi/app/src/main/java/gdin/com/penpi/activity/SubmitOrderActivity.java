package gdin.com.penpi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;

import java.util.Map;

import gdin.com.penpi.R;
import gdin.com.penpi.commonUtils.JacksonUtils;
import gdin.com.penpi.commonUtils.OrderHandle;
import gdin.com.penpi.domain.Order;
import gdin.com.penpi.domain.User;
import gdin.com.penpi.homeIndex.HomeActivity;
import gdin.com.penpi.login.LoginActivity;
import gdin.com.penpi.placeList.PlaceListActivity;

public class SubmitOrderActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private EditText et_startPlace;
    private EditText et_endPlace;
    private EditText et_userName;
    private EditText et_phone_number;
    private EditText et_remark;

    private Spinner spinner;    //重量下拉表单
    private String[] arr = {"小于一公斤", "一到三公斤", "大于三公斤"};

    //声明类型和重量组件
    private RadioButton rb_common;
    private RadioButton rb_Vip;

    private LatLng latLngStrat; // 发单地址的经纬度
    private LatLng latLngEnd; // 收货地址的经纬度

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        user = LoginActivity.getUser();
        this.initView();
    }

    private void initView() {
        //--重量选择下拉表单
        spinner = (Spinner) findViewById(R.id.map_pop_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, arr);
        spinner.setAdapter(adapter);

        findViewById(R.id.bt_submit_sure).setOnClickListener(this);
        findViewById(R.id.bt_submit_cancel).setOnClickListener(this);
        findViewById(R.id.bt_sumbit_charges_standrad).setOnClickListener(this);

        et_startPlace = (EditText) findViewById(R.id.et_start);
        et_endPlace = (EditText) findViewById(R.id.et_end);
        et_userName = (EditText) findViewById(R.id.et_name);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_remark = (EditText) findViewById(R.id.et_remark);

        et_startPlace.setOnTouchListener(this);
        et_endPlace.setOnTouchListener(this);

        rb_common = (RadioButton) findViewById(R.id.common_order);
        rb_Vip = (RadioButton) findViewById(R.id.Vip_order);

        Toolbar mItemToolbar = (Toolbar) findViewById(R.id.item_tool_bar);
        mItemToolbar.setTitle("");
        setSupportActionBar(mItemToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mItemToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 编辑框的点击事件
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.et_start:
                // 设置开始地址
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(SubmitOrderActivity.this, PlaceListActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.et_end:
                // 设置结束地址
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(SubmitOrderActivity.this, PlaceListActivity.class);
                    startActivityForResult(intent, 2);
                }
                break;
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.bt_submit_sure:
                // 提交订单
                sumbitOrder(view);
                break;
            case R.id.bt_submit_cancel:
                // 取消
                intent = new Intent(SubmitOrderActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_sumbit_charges_standrad:
                // 查看收费标准
                intent = new Intent(SubmitOrderActivity.this, ChargesStandradActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void sumbitOrder(View view) {
        double final_price = calculatePrice();
        final Order order = new Order();
        String errorInfo = "";

        if (!"".equals(et_startPlace.getText().toString().trim()))
            order.setStartPlace(et_startPlace.getText().toString().trim());
        else
            errorInfo += "快递地点-";

        if (!"".equals(et_endPlace.getText().toString().trim()))
            order.setEndPlace(et_endPlace.getText().toString().trim());
        else
            errorInfo += "送货地点-";

        order.setSendOrderPeople(user);
        order.setSendOrderPeopleName(et_userName.getText().toString().trim());

        if (latLngStrat != null && latLngEnd != null) {
            order.setLatLngStrat(JacksonUtils.writeJSON(latLngStrat));
            order.setLatLngEnd(JacksonUtils.writeJSON(latLngEnd));
        }

        if (!"".equals(et_phone_number.getText().toString().trim()))
            order.setSendOrderPeoplePhone(et_phone_number.getText().toString().trim());
        else
            errorInfo += "电话号码-";

        order.setCharges(final_price);
        order.setState(OrderHandle.NOGRAP);
        order.setRemark(et_remark.getText().toString().trim());

        if (errorInfo.length() > 0)
            Toast.makeText(this, errorInfo + "不能为空", Toast.LENGTH_SHORT).show();
        else
            new AlertDialog.Builder(this)
                    .setTitle("提示") //设置对话框标题
                    .setMessage("预计消费：" + final_price)  //设置显示的内容
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Thread() {
                                @Override
                                public void run() {
                                    new OrderHandle().saveOrder(order);
                                }
                            }.start();
                            finish();
                        }
                    })
                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();//在按键响应事件中显示此对话框
    }

    public double calculatePrice() {
        double common_price = 1;
        double vip_price = 2;
        double final_price = 0;
        double distanceOfOrder = 2;
        if (rb_common.isChecked()) {
            if (spinner.getSelectedItem().toString().equals(arr[0])) {
                common_price += 0.5 * (distanceOfOrder - 1);
            } else if (spinner.getSelectedItem().toString().equals(arr[1])) {
                common_price += 0.5 * (distanceOfOrder - 1) + 0.5;
            } else if (spinner.getSelectedItem().toString().equals(arr[2])) {
                common_price += 0.5 * (distanceOfOrder - 1) + 1;
            }
            if (common_price > 3) {
                common_price = 3;
            }
            final_price = common_price;
        } else if (rb_Vip.isChecked()) {
            if (spinner.getSelectedItem().toString().equals(arr[0])) {
                vip_price += 0.5 * (distanceOfOrder - 1);
            } else if (spinner.getSelectedItem().toString().equals(arr[1])) {
                vip_price += 0.5 * (distanceOfOrder - 1) + 0.5;
            } else if (spinner.getSelectedItem().toString().equals(arr[2])) {
                vip_price += 0.5 * (distanceOfOrder - 1) + 1;
            }
            if (vip_price > 5) {
                vip_price = 5;
            }
            final_price = vip_price;
        }
        return final_price;
    }

    /**
     * PlaceListActivity 的回调函数,取得MapLocationList回传的数据
     *
     * @param requestCode 1:开始地址  2:结束地址
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Map map = JacksonUtils.readJson(data.getStringExtra("address"), Map.class);
            assert map != null;
            et_startPlace.setText((String) map.get("title"));
            latLngStrat = new LatLng((Double) map.get("latitude"), (Double) map.get("longitude"));
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Map map = JacksonUtils.readJson(data.getStringExtra("address"), Map.class);
            assert map != null;
            et_endPlace.setText((String) map.get("title"));
            latLngEnd = new LatLng((Double) map.get("latitude"), (Double) map.get("longitude"));
        }
    }
}