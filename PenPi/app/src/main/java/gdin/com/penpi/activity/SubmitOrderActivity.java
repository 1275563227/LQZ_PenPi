package gdin.com.penpi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import gdin.com.penpi.R;
import gdin.com.penpi.domain.Order;
import gdin.com.penpi.domain.User;
import gdin.com.penpi.dbUtils.MyDatabaseHelper;
import gdin.com.penpi.homeIndex.HomeActivity;
import gdin.com.penpi.commonUtils.OrderHandle;
import gdin.com.penpi.placeList.PlaceListActivity;

/**
 * Created by Administrator on 2016/11/7.
 */
public class SubmitOrderActivity extends AppCompatActivity {

    private EditText et_start;
    private EditText et_end;
    private EditText et_name;
    private EditText et_phone_number;
    private EditText et_remark;

    private Toolbar mItemToolbar;

    private Spinner spinner;//重量下拉表单
    private String[] arr = {"小于一公斤", "一到三公斤", "大于三公斤"};
    private ArrayAdapter<String> adapter;

    //声明类型和重量组件
    //private Spinner weight_spinner;
    private RadioButton common;
    private RadioButton Vip;
    //计算预计价格
    private double distanceOfOrder = 2;//暂时默认为2
    private double common_price;
    private double Vip_price;
    private double final_price;

//    private final Order order = new Order();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);

        //--重量选择下拉表单
        spinner = (Spinner) findViewById(R.id.map_pop_spinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.
                simple_list_item_multiple_choice, arr);
        spinner.setAdapter(adapter);
        //------------------

        initEditText();

        mItemToolbar = (Toolbar) findViewById(R.id.item_tool_bar);
        mItemToolbar.setTitle("");
        setSupportActionBar(mItemToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mItemToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*mapView = (MapView) findViewById(R.id.map_order_view);

        new MapLocation(mapView, SubmitOrderActivity.this);*/

    }

    private void initEditText() {
        et_start = (EditText) findViewById(R.id.et_start);
        et_end = (EditText) findViewById(R.id.et_end);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_remark = (EditText) findViewById(R.id.et_remark);

        common = (RadioButton) findViewById(R.id.common_order);
        Vip = (RadioButton) findViewById(R.id.Vip_order);
        //spinner = (Spinner)findViewById(R.id.map_pop_spinner);


        et_start.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(SubmitOrderActivity.this, PlaceListActivity.class);
                    startActivityForResult(intent, 1);
                }
                return false;
            }
        });

        et_end.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(SubmitOrderActivity.this, PlaceListActivity.class);
                    startActivityForResult(intent, 2);
                }
                return false;
            }
        });
    }

    /**
     * 提交订单
     * @param view
     */
    public void Submit_to_Sever(View view) {
        MyDatabaseHelper dataHelper;
        SQLiteDatabase db;


        et_start = (EditText) findViewById(R.id.et_start);
        et_end = (EditText) findViewById(R.id.et_end);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_remark = (EditText) findViewById(R.id.et_remark);

//        final Order order = new Order();
        //生成随机数，并且赋值order作为id


        /*dataHelper = DBManger.getInstance(SubmitOrderActivity.this);
        db = dataHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MyDatabaseHelper.TABLE_ORDER_ID,order.getId());
        values.put(MyDatabaseHelper.TABLE_STAART_PLACE,order.getStart_place());
        values.put(MyDatabaseHelper.TABLE_END_PLACE,order.getEnd_place());
        values.put(MyDatabaseHelper.TABLE_PEOPLE_NAME,order.getName());
        values.put(MyDatabaseHelper.TABLE_PHONE,order.getPhone_number());
        values.put(MyDatabaseHelper.TABLE_CHARGES,order.getCharges());
        values.put(MyDatabaseHelper.TABLE_REMARK,order.getRemark());
        values.put(MyDatabaseHelper.TABLE_STATE,order.getState());
        values.put(MyDatabaseHelper.TABLE_DATE,order.getDate());
        db.insert(MyDatabaseHelper.TABLE_IN_NAME,null,values);
        db.close();*/
        forCaculator(view);
    }

    /**
     * PlaceListActivity 的回调函数
     *
     * @param requestCode 请求码
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 取得MapLocationList回传的数据
        if (requestCode == 1) {
            if (resultCode == RESULT_OK)
                et_start.setText(data.getStringExtra("myLocation"));
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK)
                et_end.setText(data.getStringExtra("myLocation"));
        }
    }

    public void forCaculator(View view) {

        common_price = 1;
        Vip_price = 2;
        final_price = 0;
        if (common.isChecked()) {
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
        } else if (Vip.isChecked()) {
            if (spinner.getSelectedItem().toString().equals(arr[0])) {
                Vip_price += 0.5 * (distanceOfOrder - 1);
            } else if (spinner.getSelectedItem().toString().equals(arr[1])) {
                Vip_price += 0.5 * (distanceOfOrder - 1) + 0.5;
            } else if (spinner.getSelectedItem().toString().equals(arr[2])) {
                Vip_price += 0.5 * (distanceOfOrder - 1) + 1;
            }
            if (Vip_price > 5) {
                Vip_price = 5;
            }
            final_price = Vip_price;
        }

        final Order order = new Order();
        order.setStartPlace(et_start.getText().toString());
        order.setEndPlace(et_end.getText().toString());
        User user = new User();
        user.setUserID(1);
//        user.setUsername(et_name.getText().toString());
//        user.setPhoneNumber(et_phone_number.getText().toString());
        order.setSendOrderPeople(user);
        order.setSendOrderPeopleName(et_name.getText().toString());
        try {
            order.setSendOrderPeoplePhone(Integer.parseInt(et_phone_number.getText().toString().trim()));
        }catch (NumberFormatException e){
            e.printStackTrace();
            order.setSendOrderPeoplePhone(null);
        }
        order.setCharges(final_price);
        order.setState(OrderHandle.NOGRAP);
        order.setRemark(et_remark.getText().toString().trim());

        // TODO
//        MyDatabaseHelper dataHelper;
//        SQLiteDatabase db;
//        dataHelper = DBManger.getInstance(SubmitOrderActivity.this);
//        db = dataHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();

//        values.put(MyDatabaseHelper.TABLE_ORDER_ID,order.getId());
//        values.put(MyDatabaseHelper.TABLE_STAART_PLACE,order.getStart_place());
//        values.put(MyDatabaseHelper.TABLE_END_PLACE,order.getEnd_place());
//        values.put(MyDatabaseHelper.TABLE_PEOPLE_NAME,order.getName());
//        values.put(MyDatabaseHelper.TABLE_PHONE,order.getPhone_number());
//        values.put(MyDatabaseHelper.TABLE_CHARGES,order.getCharges());
//        values.put(MyDatabaseHelper.TABLE_REMARK,order.getRemark());
//        values.put(MyDatabaseHelper.TABLE_STATE,order.getState());
//        values.put(MyDatabaseHelper.TABLE_DATE,order.getDate());
//        db.insert(MyDatabaseHelper.TABLE_OUT_NAME,null,values);
//        db.close();

        new AlertDialog.Builder(SubmitOrderActivity.this)
                .setTitle("提示") //设置对话框标题
                .setMessage("预计消费：" + final_price)//设置显示的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        Log.i("HTTP", "开启线程准备发送");
                        Log.i("HTTP", order.toString());
                        new Thread() {
                            @Override
                            public void run() {

//                                SubmitUtil.addOrdertoServe(order);
                                new OrderHandle().saveOrder(order);
                            }
                        }.start();
                        finish();
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件

//                        Log.i("alertdialog", " 请保存数据！");
                    }
                }).show();//在按键响应事件中显示此对话框
    }

    /**
     * 取消点击事件
     * @param view
     */
    public void Return_Main(View view) {
        Intent intent = new Intent(SubmitOrderActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    /**
     * 收费标准点击事件
     * @param view
     */
    public void Charges_Standrad(View view) {
        Intent intent = new Intent(SubmitOrderActivity.this, ChargesStandradActivity.class);
        startActivity(intent);
    }
}