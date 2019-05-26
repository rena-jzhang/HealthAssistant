package com.example.finalproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.finalproject.Data.User;
import com.example.finalproject.Data.myStatic;
import com.example.finalproject.Fragment.FoodListFrag.BreakfastList;
import com.example.finalproject.Fragment.FoodListFrag.Food;

import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_Breakfast = "create table Breakfast ("
            + "id integer primary key autoincrement, "
            + "email text,"
            + "year integer, "
            + "month integer, "
            + "date integer, "
            + "amt integer, "
            + "name text, "
            + "picId integer, "
            + "cal integer)";
    public static final String CREATE_Lunch = "create table Lunch ("
            + "id integer primary key autoincrement, "
                        + "email text,"

            + "year integer, "
            + "month integer, "
            + "date integer, "
            + "amt integer, "
            + "name text, "
            + "picId integer, "
            + "cal integer)";

    public static final String CREATE_Dinner = "create table Dinner ("
            + "id integer primary key autoincrement, "
                        + "email text,"

            + "year integer, "
            + "month integer, "
            + "date integer, "
            + "amt integer, "
            + "name text, "
            + "picId integer, "
            + "cal integer)";

    public static final String CREATE_UserInfo = "create table UserInfo ("
            + "id integer primary key autoincrement, "
            + "email text, "
            + "password text, "
            + "weight float, "
            + "weightRecord float, "
            + "weightTarget float) ";

    private Context mContext;
    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Breakfast);
        db.execSQL(CREATE_Lunch);
        db.execSQL(CREATE_Dinner);
        db.execSQL(CREATE_UserInfo);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}