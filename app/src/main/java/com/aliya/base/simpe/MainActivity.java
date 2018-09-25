package com.aliya.base.simpe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.base.widget.dialog.TaskAlertDialog;

import java.util.Stack;

public class MainActivity extends Activity implements View.OnClickListener, ViewGroup
        .OnHierarchyChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDecorChildView(0).setFitsSystemWindows(false);

        findViewById(R.id.tv).setOnClickListener(this);

//        mFrameLayout = findViewById(R.id.id_frame);
//        ViewParent parent = mFrameLayout.getParent();
//        do {
//            Log.e("TAG", "parent: " + parent);
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//            }
//            if (parent instanceof View) {
//                if (parent.getParent() == null) {
//                    if (parent instanceof FrameLayout) {
//                        ((FrameLayout) parent).setOnHierarchyChangeListener(this);
//                    }
//                }
//                parent = parent.getParent();
//            } else {
//                break;
//
//            }
//        } while (true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        moveTaskToBack(true); // 返回键切至后台不关闭页面
    }

    public void onClick(View v) {
//        startActivity(new Intent(this, SecondActivity.class));
//        getDecorChildView(0);
//        printView();

    }

    private void printView() {
        Stack<View> stack = new Stack<>();
        View decorView = getWindow().getDecorView();
        decorView.setTag(R.id.all, 0);
        stack.add(decorView);
        while (!stack.isEmpty()) {
            View pop = stack.pop();
            Integer depth = (Integer) pop.getTag(R.id.all);
            Log.e("TAG", String.format("%" + (depth * 4 + 1) + "s", "") + pop);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            if (pop instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) pop).getChildCount(); i++) {
                    View child = ((ViewGroup) pop).getChildAt(i);
                    child.setTag(R.id.all, depth + 1);
                    stack.add(child);
                }
            }
        }
    }

    @Override
    public void onChildViewAdded(View parent, View child) {
//        if (child.getId() == android.R.id.statusBarBackground) {
//            child.setBackgroundResource(R.color.statusBarColor);
//        }
        Log.e("TAG", "onChildViewAdded: " + child);
    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
        Log.e("TAG", "onChildViewRemoved: " + child);
    }

    public View getDecorChildView(int index) {
        View v = ((ViewGroup) getWindow().getDecorView()).getChildAt(index);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        Log.e("TAG", "pt-pb : mt-mb = " + v.getPaddingTop() + "-" + v.getPaddingBottom()
                + " : " + lp.topMargin + "-" + lp.bottomMargin);
        return v;
    }

}
