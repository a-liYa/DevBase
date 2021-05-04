package com.aliya.monitor

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

class MonitorInitializer : ContentProvider() {
    override fun onCreate(): Boolean {
        AppFpsMonitor.init(context!!)
        //        AppBlockCanaryContext.init(getContext());
        /*BlockCanary.install(getContext(), new BlockCanaryContext() {

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
        }).start();*/return false
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        return 0
    }
}