package com.example.gdufe_cloud;

/**
 * Author:creat by Lu Hengxun on : 2018/11/30
 * Descibe: 课程的实体类
 */
public class Course {
    private String course_id; //课程编号
    private String course_name; //课程名称
    private String course_time; //上课时间
    private String course_location; //上课教室
    private int course_credit; //学分
    private int course_score; //成绩

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_time() {
        return course_time;
    }

    public void setCourse_time(String course_time) {
        this.course_time = course_time;
    }

    public String getCourse_location() {
        return course_location;
    }

    public void setCourse_location(String course_location) {
        this.course_location = course_location;
    }

    public int getCourse_credit() {
        return course_credit;
    }

    public void setCourse_credit(int course_credit) {
        this.course_credit = course_credit;
    }

    public int getCourse_score() {
        return course_score;
    }

    public void setCourse_score(int course_score) {
        this.course_score = course_score;
    }

}
