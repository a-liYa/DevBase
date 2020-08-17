package com.aliya.base.sample.db;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import android.content.Context;
import androidx.annotation.NonNull;
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
@Database(entities = {ProductEntity.class}, version = 3, exportSchema = true)
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
                    // 第一次创建数据库时调用，但是在创建所有表之后调用的
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.e("TAG", "onCreate: database");
                    }

                    // 当数据库被打开时调用
                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        Log.e("TAG", "onOpen: database");
                    }
                })
                .addMigrations(MIGRATION_1_2, MIGRATION_1_3, MIGRATION_2_3) // 数据库升级
                .build();
    }

    // 每次调用返回的都是同一个实例
    public abstract ProductDao productDao();

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.e("TAG", "migrate: version 1 -> 2");
        }
    };

    private static final Migration MIGRATION_1_3 = new Migration(1, 3) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.e("TAG", "migrate: version 1 -> 3");
        }
    };
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.e("TAG", "migrate: version 2 -> 3");
        }
    };

}
