package com.example.finalproject.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.finalproject.Fragment.FoodListFrag.Food;
import com.example.finalproject.Fragment.FoodListFrag.FoodList;
import com.example.finalproject.Module.PopupWindow.BottomPopupOption;
import com.example.finalproject.R;
import com.example.finalproject.Data.User;
import com.example.finalproject.Data.myStatic;

import java.util.List;

public class FoodPicker extends AppCompatActivity {


    //全局变量引用
    User test = myStatic.getInstance().getTester();
    private int pat = 1;
    FragmentManager fragmentManager = getFragmentManager();
    private GestureDetector mGestureDetector;

    public void showNextPage(Context packageContext, Class<?> cls) {
        // 在这里跳到第二个页面
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
    }
    public void showPreviousPage(Context packageContext, Class<?> cls) {
        // 在这里跳到第二个页面
        Intent intent = new Intent(packageContext, preContext.getClass());
        startActivity(intent);
        //  overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
    }

    public void setPreContext(Context preContext) {
        this.preContext = preContext;
    }

    private Context preContext;
    public void slide(final Context packageContext,final Class<?> cls) {
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            /**
             * 监听手势滑动事件:e1表示滑动的起点，e2表示滑动终点， velocityX表示水平速度， velocityY表示垂直速度
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {

                // 判断纵向滑动是否过大，过大的话不允许切换界面
                // 判断纵向滑动是否过大，过大的话不允许切换界面
                if (Math.abs(e2.getRawY() - e1.getRawY()) > 200) {
                    return true;
                }

                // 判断滑动是否龟速，如果速度过慢也不允许滑动到另一页
                if (Math.abs(velocityX) < 100) {

                    return true;
                }

                // 向右滑动,上一页
                if (e2.getRawX() - e1.getRawX() > 200) {
                    showPreviousPage( packageContext,  cls);
                    return true;
                }
                // 向左滑动,下一页
                if (e1.getRawX() - e2.getRawX() > 200) {
                    showNextPage( packageContext,  cls);
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    pat = 1;
                    return true;
                case R.id.navigation_dashboard:
                    pat = 2;
                    return true;
                case R.id.navigation_notifications:
                    pat = 3;
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_picker);

        /*
        //去掉状态栏(顶部显示时间、电量的部分)，设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        */

        //在当前界面加入FoodList碎片
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        final FoodList foodList = new FoodList();
        fragmentTransaction.add(R.id.container4, foodList, "foodlist");
        fragmentTransaction.commit();

        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("请选择食物");
        setSupportActionBar(toolbar);

        // 显示标题和子标题
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextPage(FoodPicker.this,MainPage.class);
            }
        });

        //设置底部的navigationBar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.addfood);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FoodList fd = (FoodList) fragmentManager.findFragmentByTag("foodlist");

        ListView listView = (ListView) fd.getView().findViewById(R.id.list_view);
        final List<Food> fList = fd.getfList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food food = fList.get(position);
                BottomPopupOption bottomPopupOption = new BottomPopupOption(com.example.finalproject.Activity.FoodPicker.this, "bottomPopUp", test);
                bottomPopupOption.showPopupWindow(food, pat);
            }
        });
    }


}
