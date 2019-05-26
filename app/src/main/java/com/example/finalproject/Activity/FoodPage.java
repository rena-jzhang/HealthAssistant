package com.example.finalproject.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.Fragment.FoodListFrag.BreakfastList;
import com.example.finalproject.Fragment.FoodListFrag.DinnerList;
import com.example.finalproject.Fragment.FoodListFrag.LunchList;
import com.example.finalproject.R;
import com.example.finalproject.Data.User;
import com.example.finalproject.Data.myStatic;

public class FoodPage extends AppCompatActivity {
    //全局变量引用
    User test = myStatic.getInstance().getTester();
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;
    private FloatingActionButton floatingActionButton;
    private GestureDetector mGestureDetector;
private FloatingActionButton floatingActionButton2 ;
    //界面跳转方法
    public void showNextPage(Context packageContext, Class<?> cls) {
        // 在这里跳到第二个页面
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public void showPreviousPage(Context packageContext, Class<?> cls) {
        // 在这里跳到第二个页面
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
        //  overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
    }

    public void slide(final Context packageContext, final Class<?> cls) {
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
                    showPreviousPage(packageContext, cls);
                    return true;
                }
                // 向左滑动,下一页
                if (e1.getRawX() - e2.getRawX() > 200) {
                    showNextPage(packageContext, cls);
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_page);

        Toolbar toolbar=findViewById(R.id.toolbar2);
        toolbar.setTitle("饮食记录");
        setSupportActionBar(toolbar);
        // 显示标题和子标题
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextPage(FoodPage.this,MainPage.class);
            }
        });

           floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextPage(FoodPage.this,FoodPicker.class);
            }
        });



        //插入fragment进行三餐列表设置
         fragmentTransaction =
                fragmentManager.beginTransaction();
        BreakfastList breakfastList = new BreakfastList();
        breakfastList.setTitle("早餐");
        breakfastList.setfList(test.getBreakfastList());
        fragmentTransaction.add(R.id.container7, breakfastList, "breakfastList");

        LunchList lunchList = new LunchList();
        lunchList.setfList(test.getLunchList());
        lunchList.setTitle("午餐");
        fragmentTransaction.add(R.id.container7, lunchList, "lunchList");

        DinnerList dinnerList = new DinnerList();
        dinnerList.setfList(test.getDinnerList());
        dinnerList.setTitle("晚餐");
        fragmentTransaction.add(R.id.container7, dinnerList, "dinnerList");
        fragmentTransaction.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //设置每一个list的显示
        //获取三个fragment对象
        BreakfastList brk;
        brk = (BreakfastList) fragmentManager.findFragmentByTag("breakfastList");
        LunchList lunch;
        lunch = (LunchList) fragmentManager.findFragmentByTag("lunchList");
        DinnerList dinner = (DinnerList) fragmentManager.findFragmentByTag("dinnerList");

        //产生三个事件
        FragmentTransaction fragmentTransaction1=fragmentManager.beginTransaction();
        FragmentTransaction fragmentTransaction2=fragmentManager.beginTransaction();
        FragmentTransaction fragmentTransaction3=fragmentManager.beginTransaction();

        //判断是否要显示
        if (test.getBreakfastList().size() != 0) {
            View view = brk.getView();
            TextView textView = view.findViewById(R.id.title_bar);
            textView.setText(brk.getTitle());
            fragmentTransaction1.show(brk);
        }
        else
           fragmentTransaction1.hide(brk);
        fragmentTransaction1.commit();


        if (test.getLunchList().size() != 0) {
            View lunchview = lunch.getView();
            TextView lunchtextView = lunchview.findViewById(R.id.title_bar);
            lunchtextView.setText(lunch.getTitle());
            fragmentTransaction2.show(lunch);
        }
        else
            fragmentTransaction2.hide(lunch);
        fragmentTransaction2.commit();


        if (!test.getDinnerList().isEmpty()) {
            View dinnerView = dinner.getView();
            TextView dinnertextView = dinnerView.findViewById(R.id.title_bar);
            dinnertextView.setText(dinner.getTitle());
            fragmentTransaction3.show(dinner);
        }
        else fragmentTransaction3.hide(dinner);
        fragmentTransaction3.commit();
    }

    /**
     * 点击事件选择回调
     */
    public interface onPopupWindowItemClickListener {
        void onItemClick(int position);
    }

    public static interface onSetPreListener {
        public void update();
    }

    public void setPreListener(onSetPreListener preListener) {
        this.preListener = preListener;
    }
    private FoodPage.onSetPreListener preListener;
    private void func() {
        if (preListener != null)
            preListener.update();
    }
}
