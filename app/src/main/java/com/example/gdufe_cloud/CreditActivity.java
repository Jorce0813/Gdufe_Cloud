package com.example.gdufe_cloud;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
 * @author: Luhengxun
 * @describe: 查看学分的Activity
 */
public class CreditActivity extends AppCompatActivity {
    private List<Course> courseList = new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    private int all_credit = 0; //已获总学分

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        dbHelper = new MyDatabaseHelper(this,"CourseDb.db",null,1); //打开数据库
        initCourses(); //初始化课程数据
        TextView all_credit_view = (TextView) findViewById(R.id.all_credit_text);
        all_credit_view.setText(String.valueOf(all_credit)); //通过initCourses初始化后设置总学分
        CreditAdapter creditAdapter = new CreditAdapter(CreditActivity.this,R.layout.course_item2,courseList);
        ListView listView = (ListView) findViewById(R.id.credit_listview);
        listView.setAdapter(creditAdapter);
    }

    /*
     * 初始化课程列表--->从SQLite数据库中取出信息
     */
    private void initCourses(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("course",null,null,null,null,null,null);
        //从corsor中取出查询到的数据
        if(cursor.moveToFirst()){
            do{
                Course course = new Course();
                course.setCourse_id(cursor.getString(cursor.getColumnIndex("course_id")));
                course.setCourse_name(cursor.getString(cursor.getColumnIndex("course_name")));
                course.setCourse_time(cursor.getString(cursor.getColumnIndex("course_time")));
                course.setCourse_location(cursor.getString(cursor.getColumnIndex("course_location")));
                course.setCourse_credit(cursor.getInt(cursor.getColumnIndex("course_credit")));
                course.setCourse_score(cursor.getInt(cursor.getColumnIndex("course_score")));

                if(cursor.getInt(cursor.getColumnIndex("course_score"))>60){
                    all_credit+=cursor.getInt(cursor.getColumnIndex("course_credit")); //计算已获总学分
                }
                courseList.add(course); //将当前对象添加到courseList中

            }while (cursor.moveToNext());
        }
        cursor.close(); //关闭查询流对象
    }
}
