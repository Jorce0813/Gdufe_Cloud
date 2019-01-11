package com.example.gdufe_cloud;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Author:creat by Lu Hengxun on : 2018/11/30
 * Descibe: 自定义的ListView[有滑动监听事件的ListView]
 */
public class MyListView extends ListView implements View.OnTouchListener,GestureDetector.OnGestureListener {
    private GestureDetector gestureDetector;   //监听手势的实例

    public interface OnDeleteListener{       //将要删除的某项位置  回调给 MainActivity进行处理
        void onDelete(int index );
    }
    private OnDeleteListener mListener;      //删除监听

    private View deleteButton;     //删除按钮的视图

    private ViewGroup itemLayout;   //需要操作项  的 ViewGroup对象

    private int selectedItem;   //选中位置

    private boolean isDeleteShown;   //是否有  删除按钮显示
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector=new GestureDetector(getContext(),this);
        setOnTouchListener(this);
    }
    public void setOnDeleteListener(OnDeleteListener l){
        this.mListener=l;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(isDeleteShown){
            itemLayout.removeView(deleteButton);
            deleteButton=null;
            isDeleteShown=false;
            return true;
        }
        else{
            //如果在空白地方继续滑动  ,  禁止非法位置出现  删除按钮
            if(AdapterView.INVALID_POSITION == pointToPosition((int)event.getX(), (int) event.getY()))
            {
                return false;
            }
            selectedItem=pointToPosition((int)event.getX(), (int)event.getY());
            return gestureDetector.onTouchEvent(event);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {  //点击按下事件
        if(!isDeleteShown){
            selectedItem=pointToPosition((int)e.getX(), (int)e.getY());
        }
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float x,  //手指滑动事件
                           float y) {
        if(!isDeleteShown&&Math.abs(x)>Math.abs(y)){
            deleteButton= LayoutInflater.from(getContext()).inflate(R.layout.delete_button,null);
            deleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemLayout.removeView(deleteButton);
                    deleteButton=null;
                    isDeleteShown=false;
                    mListener.onDelete(selectedItem);
                }
            });
            itemLayout=(ViewGroup) getChildAt(selectedItem - getFirstVisiblePosition());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            itemLayout.addView(deleteButton, params);
            isDeleteShown=true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
                            float arg3) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {

    }
    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        return false;
    }

}
