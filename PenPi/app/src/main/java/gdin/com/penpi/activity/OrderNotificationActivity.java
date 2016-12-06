package gdin.com.penpi.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import gdin.com.penpi.R;
import gdin.com.penpi.bean.Order;
import gdin.com.penpi.client.LogUtil;
import gdin.com.penpi.util.SpiltStringUtil;

public class OrderNotificationActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private static final String LOGTAG = LogUtil
            .makeLogTag(OrderNotificationActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_notification);

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

        SharedPreferences preferences = getSharedPreferences("NotificationData", MODE_PRIVATE);
        String notificationMessage = preferences.getString("notificationMessage", null);

//        Log.i("notificationMessage", "OrderNotificationActivity: " + notificationMessage);
        Order order = SpiltStringUtil.messageToOrder(notificationMessage);

        people_name.setText(order.getPhone_number());
        start_place.setText(order.getStart_place());
        end_place.setText(order.getEnd_place());
        charges_name.setText(order.getCharges());
    }
}
