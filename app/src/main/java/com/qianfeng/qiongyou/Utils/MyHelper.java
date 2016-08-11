package com.qianfeng.qiongyou.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by feng on 2016/4/1.
 */
public class MyHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public MyHelper(Context context) {
        super(context, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table bar(_id integer primary key autoincrement,icon text,name text)");
        db.execSQL("create table hot_topic (_id integer primary key autoincrement,img text)");
        db.execSQL("create table promo (_id integer primary key autoincrement,img text)");
        db.execSQL("create table slide (_id integer primary key autoincrement,img text)");
        db.execSQL("create table jingxuan (_id integer primary key autoincrement,img text,title text,price text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
