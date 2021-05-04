package com.aliya.monitor;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.aliya.monitor.block.BlockCanary;
import com.aliya.monitor.block.BlockCanaryContext;
import com.aliya.monitor.block.BlockInfo;

public class MonitorInitializer extends ContentProvider {
    public MonitorInitializer() {
    }

    @Override
    public boolean onCreate() {
//        AppFpsMonitor.init(getContext());
//        AppBlockCanaryContext.init(getContext());
        BlockCanary.install(getContext(), new BlockCanaryContext() {

            public int provideBlockThreshold() {
                return 100;
            }

            public void onBlock(Context context, BlockInfo blockInfo) {
                Log.e("TAG", "onBlock: " + blockInfo);
            }

            @Override
            public boolean stopWhenDebugging() {
                return false;
            }
        }).start();
        return false;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
       return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
