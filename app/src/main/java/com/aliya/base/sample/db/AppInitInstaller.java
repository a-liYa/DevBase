package com.aliya.base.sample.db;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.aliya.base.AppUtils;

/**
 * AppInitInstaller
 *
 * @author a_liYa
 * @date 2019-07-02 15:54.
 */
public class AppInitInstaller extends ContentProvider {

    /**
     * 在 {@link Application#onCreate()} 之前回调，此处可以做一些非耗时操作
     */
    @Override
    public boolean onCreate() {
        AppUtils.init(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        return null;
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
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
