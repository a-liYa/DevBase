package com.aliya.base.sample.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aliya.base.sample.db.converter.DateConverter;
import com.aliya.base.sample.db.dao.ProductDao;
import com.aliya.base.sample.db.entity.ProductEntity;

/**
 * 创建数据库基类
 *
 * @author a_liYa
 * @date 2019/3/6 16:49.
 */
@Database(entities = {ProductEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;
    public static final String DATABASE_NAME = "dev-sample-db";

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.e("TAG", "onCreate: database");

                    }
                })
//                .addMigrations() // 数据库升级专用
                .build();
    }

    public abstract ProductDao productDao();

}
