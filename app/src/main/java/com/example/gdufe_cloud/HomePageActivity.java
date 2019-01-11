package com.example.gdufe_cloud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/*
 * Gdufe_Cloud的首页
 */

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ImageView course_view = (ImageView) findViewById(R.id.course_view);
        ImageView credit_view = (ImageView) findViewById(R.id.credit_view);
        ImageView meishi_view = (ImageView) findViewById(R.id.meishi_view);
        TextView gonggao_view = (TextView) findViewById(R.id.gonggao_view);
        TextView lianxi_view = (TextView) findViewById(R.id.lianxi_view);
        gonggao_view.setText("APP【Gdufe_Cloud】即将上线,旨在服务广东财经大学学生查询" +
                "课程&成绩，欢迎使用本APP~~~");
        lianxi_view.setText("1、关注公众号【缘来一回眸】"+"\n"+"2、扫描下方二维码来撩我呀：");

        //设置ImageView监听事件
        course_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this,CourseActivity.class);
                startActivity(intent);
            }
        });
        credit_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this,CreditActivity.class);
                startActivity(intent);
            }
        });
        meishi_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
                builder.setIcon(R.drawable.eat);
                builder.setTitle("真香警告!!!");
                builder.setMessage("整天就知道吃，关了对话框乖乖学习去~~~");
                builder.setPositiveButton("好咯", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("我不", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(HomePageActivity.this);
                        builder1.setIcon(R.drawable.eat);
                        builder1.setTitle("哟吼~不得了");
                        builder1.setMessage("给你3秒钟撤回刚刚的想法...");
                        builder1.show();
                    }
                });
                builder.show(); //显示对话框
            }
        });

    }
}
