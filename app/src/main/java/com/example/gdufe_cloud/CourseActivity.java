package com.example.gdufe_cloud;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
/*
 * @describe: 课程列表的Acivity:实现点击某一项弹出具体的信息、右滑删除功能、添加课程功能
 * @version: 实现逻辑：如果添加课程时输入框无数据时不能成功添加课程信息
 */

public class CourseActivity extends AppCompatActivity {
    private List<Course> courseList = new ArrayList<>();
    private MyListView myListView;
    private CourseAdapter adapter;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        dbHelper = new MyDatabaseHelper(this,"CourseDb.db",null,1); //操作Course.db数据库,版本号为1
        initCourses(); //初始化课程数据
        adapter = new CourseAdapter(CourseActivity.this,R.layout.course_item,courseList);
        myListView = (MyListView) findViewById(R.id.mylistview);
        ImageView add = (ImageView) findViewById(R.id.add_view); //添加课程的ImageView
        myListView.setAdapter(adapter);

        /*
         * 设置添加课程按钮的监听事件
         */
        add.setOnClickListener(new View.OnClickListener() { //设置ImageView监听事件
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseActivity.this);
                builder.setTitle("请输入课程信息");
                builder.setIcon(R.drawable.logo);

                //1.加载dialog自定义的布局文件
                View view1 = LayoutInflater.from(CourseActivity.this).inflate(R.layout.course_add_dialog,null);
                builder.setView(view1);

                //2.获取6个EditText(课程信息的6个属性)
                final EditText c_id = view1.findViewById(R.id.c_id);
                final EditText c_name = view1.findViewById(R.id.c_name);
                final EditText c_time = view1.findViewById(R.id.c_time);
                final EditText c_location = view1.findViewById(R.id.c_location);
                final EditText c_credit = view1.findViewById(R.id.c_credit);
                final EditText c_score = view1.findViewById(R.id.c_score);

                //3.设置确定+取消按钮监听事件，将课程信息更新到SQLite数据库并刷新ListView列表
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //0.判断输入框是否有信息输入
                        if(TextUtils.isEmpty(c_id.getText().toString())){
                            Toast.makeText(CourseActivity.this,"课程编号不能为空，课程添加失败",Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.isEmpty(c_name.getText().toString())){
                            Toast.makeText(CourseActivity.this,"课程名称不能为空，课程添加失败",Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.isEmpty(c_time.getText().toString())){
                            Toast.makeText(CourseActivity.this,"上课时间不能为空，课程添加失败",Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.isEmpty(c_location.getText().toString())){
                            Toast.makeText(CourseActivity.this,"上课课室不能为空，课程添加失败",Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.isEmpty(c_credit.getText().toString())){
                            Toast.makeText(CourseActivity.this,"课程学分不能为空，课程添加失败",Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.isEmpty(c_score.getText().toString())){
                            Toast.makeText(CourseActivity.this,"课程成绩不能为空，课程添加失败",Toast.LENGTH_SHORT).show();
                        }else{

                            //将课程信息更新到SQLite数据库并刷新ListView列表
                            //1.获取数据库对象
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();

                            //2.封装数据
                            values.put("course_id",c_id.getText().toString());
                            values.put("course_name",c_name.getText().toString());
                            values.put("course_time",c_time.getText().toString());
                            values.put("course_location",c_location.getText().toString());
                            values.put("course_credit",Integer.parseInt(c_credit.getText().toString()));
                            values.put("course_score",Integer.parseInt(c_score.getText().toString()));

                            //3.将数据插入到数据库中
                            db.insert("course",null,values);
                            Toast.makeText(CourseActivity.this,"课程添加成功",Toast.LENGTH_SHORT).show();

                            //4.刷新ListView列表(重启活动刷新实现数据同步)
                            Intent intent = new Intent(CourseActivity.this,CourseActivity.class);
                            startActivity(intent);
                            finish();

                        }

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });

        /*
         * 设置MyListView的删除监听事件--->即点击删除按钮删除课程信息事件
         */
        myListView.setOnDeleteListener(new MyListView.OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                //1.把删除的子项从ListView中删除并刷新ListView列表
                //!!!一定要先获取删除项的ID才能删除，否则数据库删除出错
                String remove_id = courseList.get(index).getCourse_id();
                courseList.remove(index);
                adapter.notifyDataSetChanged();

                //2.把删除的数据从SQLite数据库中删除(数据同步)
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("course","course_id = ?",new String[]{remove_id});

            }
        });

        /*
         * 设置myListView的点击监听事件
         */
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //定位当前点击的是哪个子项
                Course course = courseList.get(i);

                //1.实例化一个AlertDialog对象，设置标题，图标信息
                AlertDialog.Builder dialog = new AlertDialog.Builder(CourseActivity.this);
                dialog.setTitle("课程信息：");
                dialog.setIcon(R.drawable.logo);

                //2.将course转换为数组形式
                String[] arrays = new String[5];
                arrays[0] = "课程编号："+course.getCourse_id();
                arrays[1] = "课程名称："+course.getCourse_name();
                arrays[2] = "上课时间："+course.getCourse_time();
                arrays[3] = "上课教室："+course.getCourse_location();
                arrays[4] = "课程学分："+String.valueOf(course.getCourse_credit());

                //3.通过setItems展示详细信息
                dialog.setItems(arrays, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
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
                courseList.add(course); //将当前对象添加到courseList中
            }while (cursor.moveToNext());
        }
        cursor.close(); //关闭查询流对象
    }

}
