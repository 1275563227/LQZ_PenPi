package gdin.com.penpi.login;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import gdin.com.penpi.R;
import gdin.com.penpi.commonUtils.UserHandle;
import gdin.com.penpi.domain.User;
import gdin.com.penpi.commonUtils.ClearEditTextUtil;

public class RegisterActivity extends AppCompatActivity implements OnClickListener {
    String APPKEY = "189f9aed6f6f0";
    String APPSECRETE = "5353a661e3caac3429057bccb5614af9";

    private ClearEditTextUtil et_phoneNumber; // 手机号输入框
    private ClearEditTextUtil et_password;
    private EditText et_inputCode;            // 验证码输入框
    private Button bt_submitCode;             // 获取验证码按钮

    private String phoneNums;

    private User user = new User();

    private int i = 30;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 0x124) {
                Toast.makeText(RegisterActivity.this, "注册失败,已存在该用户", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        et_phoneNumber = (ClearEditTextUtil) findViewById(R.id.register_input_phone_et);
        et_password = (ClearEditTextUtil) findViewById(R.id.passward);
        et_inputCode = (EditText) findViewById(R.id.register_input_code_et);
        bt_submitCode = (Button) findViewById(R.id.register_request_code_btn);
        bt_submitCode.setOnClickListener(this);
        findViewById(R.id.register_commit_btn).setOnClickListener(this);

        // 启动短信验证sdk
        SMSSDK.initSDK(this, APPKEY, APPSECRETE);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handlerSMSSD.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void onClick(View v) {
        phoneNums = et_phoneNumber.getText().toString();
        switch (v.getId()) {
            case R.id.register_request_code_btn:
                // 1. 通过规则判断手机号
                if (!judgePhoneNums(phoneNums))
                    return;
                // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNums);

                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                bt_submitCode.setClickable(false);
                bt_submitCode.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handlerSMSSD.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handlerSMSSD.sendEmptyMessage(-8);
                    }
                }).start();
                break;

            case R.id.register_commit_btn:
                //将收到的验证码和手机号提交再次核对
                if (et_password.getText().toString().length() == 0 || et_password.getText().length() < 6 || et_password.getText().length() > 16) {
                    Toast.makeText(getApplicationContext(), "密码长度有误，需在6~16位！", Toast.LENGTH_SHORT).show();
                } else {
                    SMSSDK.submitVerificationCode("86", phoneNums, et_inputCode.getText().toString().trim());
                }
                //createProgressBar();
                break;
        }
    }

    private Handler handlerSMSSD = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                bt_submitCode.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                bt_submitCode.setText("获取验证码");
                bt_submitCode.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功

                        // 用户名和电话用同一个，可以在以后的用户设置改
                        user.setPhoneNumber(phoneNums);
                        user.setPassword(et_password.getText().toString().trim());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (new UserHandle().register(user.getPhoneNumber() ,user.getPassword())){
                                    handler.sendEmptyMessage(0x123);
                                } else handler.sendEmptyMessage(0x124);
                            }
                        }).start();

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码", Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "验证失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    /**
     * 判断手机号码是否合理
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 判断一个字符串的位数
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty())
            return false;
        return str.length() == length ? true : false;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    /**
     * progressbar
     */
    private void createProgressBar() {
        FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ProgressBar mProBar = new ProgressBar(this);
        mProBar.setLayoutParams(layoutParams);
        mProBar.setVisibility(View.VISIBLE);
        layout.addView(mProBar);
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}