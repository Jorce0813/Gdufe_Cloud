package com.example.gdufe_cloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Author:creat by Lu Hengxun on : 2018/12/1
 * Descibe: 学分展示列表的适配器
 */
public class CreditAdapter extends ArrayAdapter<Course>{
    private int resourseId;

    public CreditAdapter(Context context, int resource, List<Course> objects) {
        super(context, resource, objects);
        this.resourseId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourseId,parent,false);

        TextView cc_name = view.findViewById(R.id.cc_name);
        TextView cc_score = view.findViewById(R.id.cc_score);
        TextView cc_credit = view.findViewById(R.id.cc_credit);
        cc_name.setText(course.getCourse_name());
        cc_score.setText(String.valueOf(course.getCourse_score()));

        if(course.getCourse_score()>=60){ //分数大于60分才能获得学分
            cc_credit.setText(String.valueOf(course.getCourse_credit()));
        }else{
            cc_credit.setText("0");
        }
        return view;
    }
}
