package com.aliya.base.sample.ui.activity;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aliya.base.sample.common.AppExecutors;
import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseActivity;
import com.aliya.base.sample.db.AppDatabase;
import com.aliya.base.sample.db.dao.ProductDao;
import com.aliya.base.sample.db.entity.ProductEntity;

import java.util.List;

/**
 * 数据库使用示例
 *
 * @author a_liYa
 * @date 2019/3/6 下午9:27.
 */
public class DatabaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        AppDatabase database = AppDatabase.getInstance(this);
        final ProductDao dao = database.productDao();

        dao.queryAll().observe(this,
                new Observer<List<ProductEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<ProductEntity> products) {
                        for (ProductEntity entity : products) {
                            Log.e("TAG", "onChanged: " + entity.getId() + " - " + entity.getName());
                        }
                    }
                }
        );

        AppExecutors.get().diskIO().execute(new Runnable() {
            @Override
            public void run() {
//                dao.insert(new ProductEntity(2, "s10plus", "samsung phone", 5999));
            }
        });

    }
}
