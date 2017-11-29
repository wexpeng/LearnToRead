package com.hewozou.learntoread;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Mysql extends SQLiteOpenHelper {
    public Mysql(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table my(China primary key)");//建表
        db.execSQL("insert into my(China) values('中')");//插入一条初始数据
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
