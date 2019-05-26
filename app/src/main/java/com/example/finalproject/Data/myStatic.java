package com.example.finalproject.Data;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.Database.MyDatabaseHelper;

import java.util.Calendar;

public class myStatic extends Application {

    private User tester;

    public MyDatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    private int year,month,date;

    //单例模式
    private static myStatic singleInstance;

    public static myStatic getInstance(){
        return singleInstance;
    }

    @Override
    public void onCreate() {
        tester=new User();
        tester.setTargetCal(1700);
        tester.setBreakTarget(600);
        tester.setLunchTarget(600);
        tester.setDinnerTarget(500);

        //设定目标的体重
        tester.setWeight(45);
        tester.setWeight_record(45);
        tester.setWeightTarget(40);

        //初始化test的三餐list
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        date=calendar.get(Calendar.DATE);

        dbHelper = new MyDatabaseHelper(this, "newDB", null, 1);
        db=dbHelper.getWritableDatabase();
        super.onCreate();

        singleInstance = this;

    }
    public User getTester() {
        return tester;
    }
    public void setTester(User tester) {
        this.tester = tester;
    }

}
