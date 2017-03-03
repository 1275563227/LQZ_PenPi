package gdin.com.penpi.myRecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import gdin.com.penpi.R;
import gdin.com.penpi.homeIndex.HomeActivity;

public class EvaluationActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        mToolbar = (Toolbar) findViewById(R.id.item_tool_bar);
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
                Intent intent = new Intent(EvaluationActivity.this, HomeActivity.class);
                startActivity(intent);
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
