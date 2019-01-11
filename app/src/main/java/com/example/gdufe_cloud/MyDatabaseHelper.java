package com.example.gdufe_cloud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author:creat by Lu Hengxun on : 2018/11/30
 * Descibe: 创建表的类：course表
 */
public class MyDatabaseHelper extends SQLiteOpenHelper{
    public static final String creat_course = "create table course(" +
            "course_id text primary key," +
            "course_name text," +
            "course_time text," +
            "course_location text," +
            "course_credit integer," +
            "course_score integer)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(creat_course);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
