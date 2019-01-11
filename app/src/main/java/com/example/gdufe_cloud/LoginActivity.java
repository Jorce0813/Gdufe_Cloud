package com.example.gdufe_cloud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
/*
 * @author: Lu Hengxun,29/11/2018
 * @describe: 登录的Activity
 */
public class LoginActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText username_text;
    private EditText password_text;
    private CheckBox remember_password;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //1.获取学号，密码，记住密码的勾选，登录按钮,进度圈
        username_text = (EditText) findViewById(R.id.username);
        password_text = (EditText) findViewById(R.id.password);
        remember_password = (CheckBox) findViewById(R.id.remember_password);
        login_button = (Button) findViewById(R.id.login_button);

        //2.定义SharedPreferences管理器,获取勾选框的值
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password",false);

        //3.如果记住密码框的值为true，将学号，密码，勾选框的值从xml中取出并set到对应位置上
        if(isRemember){
            String prefusername = pref.getString("username",""); //数据文件中存储的学号
            String prefpassword = pref.getString("password",""); //数据文件中存储的密码
            username_text.setText(prefusername);
            password_text.setText(prefpassword);
            remember_password.setChecked(true);
        }

        //4.设置登录按钮监听事件
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //4.1数据交互验证(学号+密码)
                String username = username_text.getText().toString();
                String password = password_text.getText().toString();
                String prefusername = pref.getString("username",""); //数据文件中存储的学号
                String prefpassword = pref.getString("password",""); //数据文件中存储的密码

                //4.2验证通过则重新put学号，密码，勾选项
                if(username.equals(prefusername) && password.equals(prefpassword)){
                    editor = pref.edit();
                    editor.putString("username",username);
                    editor.putString("password",password);

                    if(remember_password.isChecked()){
                        editor.putBoolean("remember_password",true);
                    }else{
                        editor.putBoolean("remember_password",false);
                    }

                    //4.3提交eidtor,启动活动前往主页
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);
                    startActivity(intent);
                    finish();

                    //4.4验证不通过弹出提示
                }else{
                    Toast.makeText(LoginActivity.this,"学号或密码错误!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
