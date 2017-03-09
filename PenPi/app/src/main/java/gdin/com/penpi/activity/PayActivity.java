package gdin.com.penpi.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;

import c.b.BP;
import c.b.PListener;
import gdin.com.penpi.R;
import gdin.com.penpi.commonUtils.FormatUtils;
import gdin.com.penpi.commonUtils.JacksonUtils;
import gdin.com.penpi.commonUtils.OrderHandle;
import gdin.com.penpi.domain.Order;
import gdin.com.penpi.myRecord.EvaluationActivity;


public class PayActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    // 此为测试Appid,请将Appid改成你自己的Bmob AppId
    private String APPID = "9677a5cfead71e12a204cbf60cd9e026";
    // 此为微信支付插件的官方最新版本号,请在更新时留意更新说明
    private int PLUGINVERSION = 7;

    private Button go;
    private RadioGroup type;
    //TextView tv;
    private ProgressDialog dialog;
    private Order order;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Intent intent = new Intent(PayActivity.this, EvaluationActivity.class);
                intent.putExtra("orderID", order.getOrderID());
                PayActivity.this.startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        try {
            initView();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 初始化BmobPay对象,可以在支付时再初始化
        BP.init(APPID);
    }


    /**
     * 初始化toolbar和控件
     *
     * @throws ParseException
     */
    public void initView() throws ParseException {
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

        go = (Button) findViewById(R.id.go);
        type = (RadioGroup) findViewById(R.id.type);
//        tv = (TextView) findViewById(R.id.tv);

        type.setOnCheckedChangeListener(this);
        go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (type.getCheckedRadioButtonId() == R.id.alipay) // 当选择的是支付宝支付时
                    pay(true);
                else if (type.getCheckedRadioButtonId() == R.id.wxpay) // 调用插件用微信支付
                    pay(false);
            }
        });

        TextView tv_id = (TextView) findViewById(R.id.tv_pay_order_id);
        TextView tv_name = (TextView) findViewById(R.id.tv_pay_order_take_people_name);
        TextView tv_start_place = (TextView) findViewById(R.id.tv_pay_order_start_place);
        TextView tv_end_place = (TextView) findViewById(R.id.tv_pay_order_end_place);
        TextView tv_charges = (TextView) findViewById(R.id.tv_pay_charges);
        TextView tv_start_time = (TextView) findViewById(R.id.tv_pay_start_time);
        TextView tv_end_time = (TextView) findViewById(R.id.tv_pay_end_time);

        Intent intent = getIntent();
        order = JacksonUtils.readJson(intent.getStringExtra("order"), Order.class);

        assert order != null;
        tv_id.setText(String.valueOf(order.getOrderID()));
        tv_name.setText(order.getTakeOrderPeople().getUsername());
        tv_start_place.setText(order.getStartPlace());
        tv_end_place.setText(order.getEndPlace());
        tv_charges.setText(String.valueOf(order.getCharges()));
        tv_start_time.setText(FormatUtils.formatTime(order.getSendOrderDate()));
        tv_end_time.setText(FormatUtils.formatTime(order.getTakeOrderDate()));
    }

    /**
     * 检查某包名应用是否已经安装
     *
     * @param packageName 包名
     * @param browserUrl  如果没有应用市场，去官网下载
     * @return
     */
    private boolean checkPackageInstalled(String packageName, String browserUrl) {
        try {
            // 检查是否有支付宝客户端
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // 没有安装支付宝，跳转到应用市场
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                startActivity(intent);
            } catch (Exception ee) {// 连应用市场都没有，用浏览器去支付宝官网下载
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(browserUrl));
                    startActivity(intent);
                } catch (Exception eee) {
                    Toast.makeText(PayActivity.this,
                            "您的手机上没有没有应用市场也没有浏览器，我也是醉了，你去想办法安装支付宝/微信吧",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }

    private static final int REQUESTPERMISSION = 101;

    private void installApk(String s) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSION);
        } else {
            installBmobPayPlugin(s);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTPERMISSION) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    installBmobPayPlugin("bp.db");
                } else {
                    //提示没有权限，安装不了
                    Toast.makeText(PayActivity.this, "您拒绝了权限，这样无法安装支付插件", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * 调用支付
     *
     * @param alipayOrWechatPay 支付类型，true为支付宝支付,false为微信支付
     */
    void pay(final boolean alipayOrWechatPay) {
        if (alipayOrWechatPay) {
            if (!checkPackageInstalled("com.eg.android.AlipayGphone",
                    "https://www.alipay.com")) { // 支付宝支付要求用户已经安装支付宝客户端
                Toast.makeText(PayActivity.this, "请安装支付宝客户端", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        } else {
            if (checkPackageInstalled("com.tencent.mm", "http://weixin.qq.com")) {// 需要用微信支付时，要安装微信客户端，然后需要插件
                // 有微信客户端，看看有无微信支付插件
                int pluginVersion = BP.getPluginVersion(this);
                if (pluginVersion < PLUGINVERSION) {// 为0说明未安装支付插件,
                    // 否则就是支付插件的版本低于官方最新版
                    Toast.makeText(
                            PayActivity.this,
                            pluginVersion == 0 ? "监测到本机尚未安装支付插件,无法进行支付,请先安装插件(无流量消耗)"
                                    : "监测到本机的支付插件不是最新版,最好进行更新,请先更新插件(无流量消耗)",
                            Toast.LENGTH_SHORT).show();
//                    installBmobPayPlugin("bp.db");

                    installApk("bp.db");
                    return;
                }
            } else {// 没有安装微信
                Toast.makeText(PayActivity.this, "请安装微信客户端", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        showDialog("正在获取订单...\nSDK版本号:" + BP.getPaySdkVersion());

        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.bmob.app.sport",
                    "com.bmob.app.sport.wxapi.BmobActivity");
            intent.setComponent(cn);
            this.startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.bmob.app.sport",
                    "com.bmob.app.sport.wxapi.BmobActivity");
            intent.setComponent(cn);
            this.startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        BP.pay("订单编号：" + order.getOrderID(), order.getTakeOrderPeople().getUsername(),
                0.02, alipayOrWechatPay, new PListener() {

                    // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
                    @Override
                    public void unknow() {
                        Toast.makeText(PayActivity.this, "支付结果未知,请稍后手动查询", Toast.LENGTH_SHORT)
                                .show();
//                tv.append(name + "'s pay status is unknow\n\n");
                        hideDialog();
                    }

                    // 支付成功,如果金额较大请手动查询确认
                    @Override
                    public void succeed() {
                        Toast.makeText(PayActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
//                tv.append(name + "'s pay status is success\n\n");
                        hideDialog();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                order.setState("已付款");
                                Order temp = new OrderHandle().alterOrder(order);
                                if (temp != null) {
                                    handler.sendEmptyMessage(0x123);
                                }
                            }
                        }).start();
                    }

                    // 无论成功与否,返回订单号
                    @Override
                    public void orderId(String orderId) {
                        // 此处应该保存订单号,比如保存进数据库等,以便以后查询
//                order.setText(orderId);
//                tv.append(name + "'s orderid is " + orderId + "\n\n");
                        showDialog("获取订单成功!请等待跳转到支付页面~");
                    }

                    // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
                    @Override
                    public void fail(int code, String reason) {

                        // 当code为-2,意味着用户中断了操作
                        // code为-3意味着没有安装BmobPlugin插件
                        if (code == -3) {
                            Toast.makeText(
                                    PayActivity.this,
                                    "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
                                    Toast.LENGTH_SHORT).show();
//                    installBmobPayPlugin("bp.db");
                            installApk("bp.db");
                        } else {
                            Toast.makeText(PayActivity.this, "支付中断!", Toast.LENGTH_SHORT)
                                    .show();
                        }
//                tv.append(name + "'s pay status is fail, error code is \n"
//                        + code + " ,reason is " + reason + "\n\n");
                        hideDialog();
                    }
                });
    }

    /*// 执行订单查询
    void query() {
        showDialog("正在查询订单...");
        final String orderId = getOrder();

        BP.query(orderId, new QListener() {

            @Override
            public void succeed(String status) {
                Toast.makeText(PayActivity.this, "查询成功!该订单状态为 : " + status,
                        Toast.LENGTH_SHORT).show();
                tv.append("pay status of" + orderId + " is " + status + "\n\n");
                hideDialog();
            }

            @Override
            public void fail(int code, String reason) {
                Toast.makeText(PayActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                tv.append("query order fail, error code is " + code
                        + " ,reason is \n" + reason + "\n\n");
                hideDialog();
            }
        });
    }*/

    // 以下仅为控件操作，可以略过
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.alipay:
                // 以下仅为控件操作，可以略过
                go.setText("支付宝支付");
                break;
            case R.id.wxpay:
                // 以下仅为控件操作，可以略过
                go.setText("微信支付");
                break;
            case R.id.query:
                // 以下仅为控件操作，可以略过
                go.setText("其它支付");
                break;

            default:
                break;
        }
    }

    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }

    /**
     * 安装assets里的apk文件
     *
     * @param fileName
     */
    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
