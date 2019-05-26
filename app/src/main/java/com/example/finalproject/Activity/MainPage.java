package com.example.finalproject.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalproject.Fragment.MainPageFrag.ExerciseRecord;
import com.example.finalproject.Fragment.MainPageFrag.FoodRecord;
import com.example.finalproject.Fragment.MainPageFrag.WeightRecord;
import com.example.finalproject.Module.CircleProgress;
import com.example.finalproject.Module.PopupWindow.BottomPopupOption;
import com.example.finalproject.R;
import com.example.finalproject.Data.User;
import com.example.finalproject.Data.myStatic;

import java.util.Formatter;

import static android.content.ContentValues.TAG;

public class MainPage extends AppCompatActivity {

    //全局变量引用
    User test = myStatic.getInstance().getTester();
    FragmentManager fragmentManager = getFragmentManager();
    private GestureDetector mGestureDetector;

    public void setPreContext(Context preContext) {
        this.preContext = preContext;
    }

    private Context preContext;

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

    public static String trans(double value) {
        /*
         * %.2f % 表示 小数点前任意位数 2 表示两位小数 格式后的结果为 f 表示浮点型
         */
        return new Formatter().format("%.1f", value).toString();
    }

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
                    return true;
                case R.id.navigation_dashboard:
                    showNextPage(MainPage.this, FoodPicker.class);
                    return true;
                case R.id.navigation_notifications:
                    BottomPopupOption bottomPopupOption=new BottomPopupOption(com.example.finalproject.Activity.MainPage.this);
                   bottomPopupOption.setCallBackListener(new BottomPopupOption.onCallBackListener() {
                       @Override
                       public void update() {
                           onStart();
                       }
                   });
                    bottomPopupOption.showPopupWindow(1);

                    Log.d(TAG, "onNavigationItemSelected: ");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //加入三个模块
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        WeightRecord weightRecord = new WeightRecord();
        fragmentTransaction.add(R.id.container7, weightRecord, "weight");
        FoodRecord foodRecord = new FoodRecord();
        fragmentTransaction.add(R.id.container7, foodRecord, "food_record");
        ExerciseRecord exerciseRecord = new ExerciseRecord();
        fragmentTransaction.add(R.id.container7, exerciseRecord, "exercise_record");
        fragmentTransaction.commit();

        //底部的navigationbar的设置
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void update()
    {
        onStart();
    }

    public void toDownloadPage()
    {
        Log.d(TAG, "toDownloadPage: ");
        showNextPage(MainPage.this,DownloadList.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        updateCal();
        updateWeight();
        updateExercise();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: here");
    }

    public void updateWeight()
    {
        WeightRecord wt = (WeightRecord) fragmentManager.findFragmentByTag("weight");
        View view2 = wt.getView();
        TextView weightRecord=(TextView)view2.findViewById(R.id.cur_weight);
        weightRecord.setText(test.getWeight()+"");

        TextView weightTarget=(TextView)view2.findViewById(R.id.target_weight);
        weightTarget.setText(test.getWeightTarget()+"");
        Log.d(TAG, "updateWeight: ");
        //关于progressbar的显示
        float progress=100;
        if(test.getWeight_record()!=test.getWeightTarget())
        {
            progress=(test.getWeight_record()-test.getWeight())*100/(test.getWeight_record()-test.getWeightTarget());
            if(progress<0) progress=0;
        }
        test.setProgress(progress);
        CircleProgress circleProgress=view2.findViewById(R.id.circle_progress_bar2);
  //      Log.d(TAG, "progress: "+test.getProgress());
        circleProgress.setValue(test.getProgress());
//        Log.d(TAG, "updateWeight: "+test.getWeight());

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomPopupOption bottomPopupOption=new BottomPopupOption(com.example.finalproject.Activity.MainPage.this);
                bottomPopupOption.setCallBackListener(new BottomPopupOption.onCallBackListener() {
                    @Override
                    public void update() {
                        onStart();
                    }
                });
                bottomPopupOption.showPopupWindow(2);
            }
        });
    }

    public void updateCal()
    {
        FoodRecord fd = (FoodRecord) fragmentManager.findFragmentByTag("food_record");
        View view = fd.getView();

        //更新cal
        TextView leftCal=view.findViewById(R.id.cal_left);
        int calLeft=test.getTargetCal()-test.getLunchCal()-test.getDinnerCal()-test.getBreakfastCal();
        if(calLeft<0) calLeft=0;
        leftCal.setText(calLeft+"");

      //  Log.d(TAG, "updateCal: "+calLeft);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextPage(MainPage.this,FoodPage.class);
                // onStop();
            }
        });
        ProgressBar breakfastBar=view.findViewById(R.id.breakfastBar);
        breakfastBar.setProgress(test.getBreakfastCal()*100/test.getBreakTarget());
        ProgressBar lunchBar=view.findViewById(R.id.lunchBar);
        lunchBar.setProgress(test.getLunchCal()*100/test.getLunchTarget());
        ProgressBar dinnerBar=view.findViewById(R.id.dinnerBar);
        dinnerBar.setProgress(test.getDinnerCal()*100/test.getDinnerTarget());
    }

    public void updateExercise()
    {
        ExerciseRecord ex = (ExerciseRecord) fragmentManager.findFragmentByTag("exercise_record");
        View view3 = ex.getView();
        Button toDownload=(Button)view3.findViewById(R.id.to_download_page);
        toDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextPage(MainPage.this,DownloadList.class);
            }
        });
    }

}
