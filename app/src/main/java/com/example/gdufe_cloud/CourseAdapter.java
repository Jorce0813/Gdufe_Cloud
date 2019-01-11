package com.example.gdufe_cloud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Author:creat by Lu Hengxun on : 2018/11/30
 * Descibe: 课程适配器
 */
public class CourseAdapter extends ArrayAdapter<Course>{
    private int resourseId;
    private Course course;

    public CourseAdapter(Context context, int resourceId, List<Course> objects) {
        super(context, resourceId, objects);
        this.resourseId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        course = getItem(position); //获取当前项的course实例
        View view = LayoutInflater.from(getContext()).inflate(resourseId,parent,false);
        TextView course_name = view.findViewById(R.id.course_name);
        course_name.setText(course.getCourse_name());
        return view;
    }
}
