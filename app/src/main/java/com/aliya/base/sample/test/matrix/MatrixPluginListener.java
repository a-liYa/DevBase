package com.aliya.base.sample.test.matrix;

import android.content.Context;
import android.util.Log;

import com.tencent.matrix.plugin.DefaultPluginListener;
import com.tencent.matrix.report.Issue;
import com.tencent.matrix.util.MatrixLog;

/**
 * MatrixPluginListener
 *
 * @author a_liYa
 * @date 2020/12/15 10:15.
 */
public class MatrixPluginListener extends DefaultPluginListener {
    public static final String TAG = "MatrixPluginListener";
    public MatrixPluginListener(Context context) {
        super(context);
    }

    @Override
    public void onReportIssue(Issue issue) {
        super.onReportIssue(issue);
        MatrixLog.e(TAG, issue.toString());
        Log.e("TAG", "onReportIssue: " + issue.toString());
        //add your code to process data
    }
}
