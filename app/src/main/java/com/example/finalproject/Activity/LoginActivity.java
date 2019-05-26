package com.example.finalproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalproject.Fragment.FoodListFrag.Food;
import com.example.finalproject.R;
import com.example.finalproject.Data.User;
import com.example.finalproject.Data.myStatic;

import static android.content.ContentValues.TAG;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    User test = myStatic.getInstance().getTester();
    SQLiteDatabase db=myStatic.getInstance().getDb();
    private int year=myStatic.getInstance().getYear();
    private int month=myStatic.getInstance().getMonth();
    private int date=myStatic.getInstance().getDate();

    private static final int REQUEST_READ_CONTACTS = 0;
    SharedPreferences settings;/**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    // private UserLoginTask mAuthTask = null;

    // UI references.
    private TextView mEmailView;
    private EditText mPasswordView;
    private CheckBox mCheckBox;
    Button mEmailSignInButton;
    Cursor cursor;
    private View mProgressView;

    private Button toDownload;
    private Button toRegister;

    public void showNextPage(Context packageContext, Class<?> cls) {
        // 在这里跳到第二个页面
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        // Set up the login form.
        mEmailView = (TextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        //进行页面跳转设置
        toRegister = findViewById(R.id.toRegister);
        // toDownload = findViewById(R.id.ToDownload);
        mEmailSignInButton = (Button) findViewById(R.id.to_sign_in);
        mProgressView = findViewById(R.id.login_progress);
        mCheckBox=findViewById(R.id.checkBox);
    }

    public void toRegister() {

        Intent intent = new Intent(LoginActivity.this, Register.class);
        startActivity(intent);

    }

    public boolean checkEmail(String email)
    {
        Cursor cursor=db.query("UserInfo",null,"email=?",new String[]{email},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }
    public boolean checkPassword(String email,String password)
    {
        Cursor cursor=db.query("UserInfo",null,"email=? and password=?",new String[]{email,password},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean attemptLogin() {

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!checkEmail(email)) {
            Log.d(TAG, "email" + email.toString());
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !checkPassword(email,password)) {
            mPasswordView.setError("Please reenter your password!");
            mPasswordView.setText("");
            focusView = mPasswordView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            mCheckBox.setChecked(false);
            SharedPreferences.Editor editor = getSharedPreferences("login",
                    MODE_PRIVATE).edit();
            editor.putBoolean("isChecked",false);
            editor.apply();

        } else {
            test.setName(email);
            test.setPassword(password);
            if(mCheckBox.isChecked())
            {
                SharedPreferences.Editor editor = getSharedPreferences("login",
                        MODE_PRIVATE).edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.putBoolean("isChecked",true);
                editor.apply();
            }
            else
            {
                SharedPreferences.Editor editor = getSharedPreferences("login",
                        MODE_PRIVATE).edit();
                editor.putBoolean("isChecked",false);
                editor.apply();
            }
            return true;
        }
        return false;
    }
    public Food initFood()
    {
        String name = cursor.getString(cursor.getColumnIndex
                ("name"));
        int amt = cursor.getInt(cursor.getColumnIndex
                ("amt"));
        int cal = cursor.getInt(cursor.getColumnIndex("cal"));
        int picId = cursor.getInt(cursor.getColumnIndex
                ("picId"));
        int rYear = cursor.getInt(cursor.getColumnIndex("year"));
        int rMonth = cursor.getInt(cursor.getColumnIndex("month"));
        int rDate = cursor.getInt(cursor.getColumnIndex("date"));
        Food temp=new Food(cal,name,picId,amt);
        return temp;
    }
    public void initTester()
    {
        //放到登录那里
        cursor=db.query("Breakfast",null,"year=? and month=? and date=? and email=?",new String[]{year+"",month+"",date+"",test.getName()},null,null,null);
        if (cursor.moveToFirst()) {
            do {
// 遍历Cursor对象，取出数据并打印
                test.getBreakfastList().add(initFood());
                int temp=test.getBreakfastCal();
                test.setBreakfastCal(temp+initFood().getCalorie()*initFood().getAmt()/100);
            } while (cursor.moveToNext());
        }
        cursor.close();
        cursor=db.query("Lunch",null,"year=? and month=? and date=? and email=?",new String[]{year+"",month+"",date+"",test.getName()},null,null,null);
        if (cursor.moveToFirst()) {
            do {
// 遍历Cursor对象，取出数据并打印
                test.getLunchList().add(initFood());
                int temp=test.getLunchCal();
                test.setLunchCal(temp+initFood().getCalorie()*initFood().getAmt()/100);

            } while (cursor.moveToNext());
        }
        cursor.close();
        cursor=db.query("Dinner",null,"year=? and month=? and date=? and email=?",new String[]{year+"",month+"",date+"",test.getName()},null,null,null);
        if (cursor.moveToFirst()) {
            do {
// 遍历Cursor对象，取出数据并打印
                test.getDinnerList().add(initFood());
                int temp=test.getDinnerCal();
                test.setDinnerCal(temp+initFood().getCalorie()*initFood().getAmt()/100);
            } while (cursor.moveToNext());
        }
        cursor.close();

    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        String email = pref.getString("email", "");
        String password = pref.getString("password", "");
        if(pref.getBoolean("isChecked",false))
        {
            mCheckBox.setChecked(true);
            mEmailView.setText(email);
            mPasswordView.setText(password);
        }
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextPage(LoginActivity.this, Register.class);
            }
        });

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attemptLogin()) {
                    Cursor cursor=db.query("UserInfo",null,"email=? and password=?",new String[]{test.getName(),test.getPassword()},null,null,null);
                    if (cursor.moveToFirst()) {
                        do {
                            float weight = cursor.getFloat(cursor.getColumnIndex
                                    ("weight"));
                            float weightRecord = cursor.getFloat(cursor.getColumnIndex
                                    ("weightRecord"));
                            float weightTarget = cursor.getFloat(cursor.getColumnIndex("weightTarget"));
                            test.setWeight(weight);
                            test.setWeight_record(weightRecord);
                            test.setWeightTarget(weightTarget);
                        } while (cursor.moveToNext());
                    }
                    initTester();
                    showNextPage(LoginActivity.this, MainPage.class);
                }
            }
        });
    }




}

