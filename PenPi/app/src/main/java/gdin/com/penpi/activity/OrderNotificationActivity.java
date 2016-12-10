package gdin.com.penpi.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import gdin.com.penpi.R;
import gdin.com.penpi.bean.Order;
import gdin.com.penpi.client.LogUtil;
import gdin.com.penpi.db.DBManger;
import gdin.com.penpi.db.MyDatabaseHelper;
import gdin.com.penpi.util.SpiltStringUtil;
import gdin.com.penpi.util.SubmitUtil;

public class OrderNotificationActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private static final String LOGTAG = LogUtil.makeLogTag(OrderNotificationActivity.class);

    private MyDatabaseHelper dataHelper = DBManger.getInstance(OrderNotificationActivity.this);
    ;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                SubmitUtil.showToast(OrderNotificationActivity.this, "抢单成功，请在'我的记录'查看详细信息");
            }
            if (msg.what == 0x124) {
                SubmitUtil.showToast(OrderNotificationActivity.this, "抢单失败，该订单已被其他人获取");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_notification);

        SharedPreferences preferences = getSharedPreferences("NotificationData", MODE_PRIVATE);
        String notificationMessage = preferences.getString("notificationMessage", null);

        final Order order = SpiltStringUtil.messageToOrder(notificationMessage);

        mToolbar = (Toolbar) findViewById(R.id.item_tool_bar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderNotificationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView people_name = (TextView) findViewById(R.id.people_name);
        TextView start_place = (TextView) findViewById(R.id.start_place);
        TextView end_place = (TextView) findViewById(R.id.end_place);
        TextView charges_name = (TextView) findViewById(R.id.charges_name);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean isChange = SubmitUtil.changeOrderStatetoServlet(order.getId(), "已抢");
                        Log.i("OrderNotificationAct", isChange + "");
                        if (isChange) {
                            handler.sendEmptyMessage(0x123);

                            //建立本地数据库SQLite
                            order.setState("已抢");
                            SQLiteDatabase db = dataHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(MyDatabaseHelper.TABLE_ORDER_ID, order.getId());
                            values.put(MyDatabaseHelper.TABLE_STAART_PLACE, order.getStart_place());
                            values.put(MyDatabaseHelper.TABLE_END_PLACE, order.getEnd_place());
                            values.put(MyDatabaseHelper.TABLE_PEOPLE_NAME, order.getName());
                            values.put(MyDatabaseHelper.TABLE_PHONE, order.getPhone_number());
                            values.put(MyDatabaseHelper.TABLE_CHARGES, order.getCharges());
                            values.put(MyDatabaseHelper.TABLE_REMARK, order.getRemark());
                            values.put(MyDatabaseHelper.TABLE_STATE, order.getState());
                            values.put(MyDatabaseHelper.TABLE_DATE, order.getDate());
                            db.insert(MyDatabaseHelper.TABLE_IN_NAME, null, values);
                            db.close();
                        } else
                            handler.sendEmptyMessage(0x124);
                    }
                }).start();
            }
        });

        people_name.setText(order.getPhone_number());
        start_place.setText(order.getStart_place());
        end_place.setText(order.getEnd_place());
        charges_name.setText(order.getCharges());
    }
}
