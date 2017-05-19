package gdin.com.penpi.myRecord;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import gdin.com.penpi.R;
import gdin.com.penpi.internetUtils.OrderHandle;
import gdin.com.penpi.domain.Order;
import gdin.com.penpi.homeIndex.HomeActivity;

public class EvaluationActivity extends AppCompatActivity {

    private EditText ed;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Intent intent = new Intent(EvaluationActivity.this, HomeActivity.class);
                startActivity(intent);
                Toast.makeText(EvaluationActivity.this, "已完成", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.item_tool_bar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ed = (EditText) findViewById(R.id.forOrder_evaluation);

        findViewById(R.id.order_evaluation_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int orderId = getIntent().getIntExtra("orderID", 1);
                        Order order = new Order();
                        order.setOrderID(orderId);
                        order.setState("已完成");
                        Order temp = new OrderHandle().alterOrder(order);
                        if (temp != null) handler.sendEmptyMessage(0x123);
                    }
                }).start();
            }
        });

        findViewById(R.id.order_evaluation_reset_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.setText("");
            }
        });
    }
}