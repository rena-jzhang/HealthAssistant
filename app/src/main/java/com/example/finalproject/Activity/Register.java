package com.example.finalproject.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Module.Code;
import com.example.finalproject.R;
import com.example.finalproject.Data.User;
import com.example.finalproject.Data.myStatic;

import static android.view.View.VISIBLE;

public class Register extends AppCompatActivity {

    private String etCode;
    private String realPwd;
    private String realCode;
    private EditText name;
    private EditText pwd;
    private EditText pwd2;
    private EditText tel;
    private EditText et_code;
    //全局变量引用
    User tester = myStatic.getInstance().getTester();
SQLiteDatabase db=myStatic.getInstance().getDb();
    //界面跳转方法
    public void showNextPage(Context packageContext, Class<?> cls) {
        // 在这里跳到第二个页面
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar=findViewById(R.id.toolbar4);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
        // 显示标题和子标题
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextPage(Register.this,LoginActivity.class);
            }
        });

        //界面控制button回到登录界面
        Button returnToLogin2 = (Button) findViewById(R.id.back_to_login);
        Button makeSure=(Button)findViewById(R.id.button2);


        makeSure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //tester.setName(name.getText().toString());
               // tester.setPassword(pwd2.getText().toString());
               ContentValues values = new ContentValues();
                values.put("email", name.getText().toString());
                values.put("password", pwd2.getText().toString());
                values.put("weight", 45);
                values.put("weightRecord",45);
                values.put("weightTarget", 40);
                db.insert("UserInfo", null, values);
                values.clear();
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        returnToLogin2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        //所有的输入以及hint效果
        name = (EditText) findViewById(R.id.name);
        setHint("长度大于6", name);
        pwd = (EditText) findViewById(R.id.pwd);
        pwd2 = (EditText) findViewById(R.id.pwd2);
        tel = (EditText) findViewById(R.id.tel);

        et_code = (EditText) findViewById(R.id.et_code);
        etCode = et_code.getText().toString().toLowerCase();
        setHint("点击查看验证码", et_code);


        //判断两次输入是否一致
        final TextView warn = (TextView) findViewById(R.id.warn);
        pwd2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                } else {
                    if (pwd.getText().toString().equals(pwd2.getText().toString()) == false) {
                        //弹出提示
                        warn.setVisibility(VISIBLE);
                        //清空pwd2
                        pwd2.setText("");
                    } else {
                        realPwd = pwd.getText().toString();
                        warn.setText("密码设置成功");
                    }
                }
            }
        });

        //发送验证短信
        Button sub = (Button) findViewById(R.id.sub);

        sub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Uri smsToUri = Uri.parse("smsto:" + tel.getText().toString());
                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                intent.putExtra("sms_body", "hello");
                startActivity(intent);
            }
        });

        initView();

        et_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                } else {
                    if (!et_code.getText().toString().equals(realCode)) {
                        //弹出提示
                        //Toast.makeText(Register.this, "验证码错误", Toast.LENGTH_SHORT).show();
                        //清空pwd2
                        et_code.setText("");
                    }
                }
            }
        });


    }

    //hint 设置
    private EditText setHint(String string, EditText editText) {
        SpannableString s = new SpannableString(string);//定义hint的值
        AbsoluteSizeSpan as = new AbsoluteSizeSpan(14, true);//设置字体大小 true表示单位是sp
        s.setSpan(as, 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(s));
        return editText;
    }

    //打电话跳转
    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:10086");
        intent.setData(data);
        //检查权限是否己被授与
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            //没有被授与，则申请打电话的权限
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.SEND_SMS
            }, 1);

            return;
        }
        //己被授与，则可以打电话
        this.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户授与了打电话的权限
                    call();
                } else {
                    Toast.makeText(this, "用户拒绝了App申请发送短信的权限",
                            Toast.LENGTH_SHORT).show();
                }
        }
    }

    //校验码
    private void initView() {
        final ImageView iv_code = (ImageView) findViewById(R.id.iv_code);
        iv_code.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();

        iv_code.setOnClickListener(new OnClickListener() {
            boolean flag = false;

            @Override
            public void onClick(View v) {
                iv_code.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
            }
        });

    }


}


