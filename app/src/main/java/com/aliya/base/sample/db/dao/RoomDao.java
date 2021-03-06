package com.aliya.base.sample.db.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

/**
 * RoomDao接口 抽取公共api
 *
 * <p> {@link Insert} 和 {@link Update} 可通过 {@link OnConflictStrategy} 配置冲突策略 </p>
 *
 * @author a_liYa
 * @date 2019/3/6 22:28.
 */
public interface RoomDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T... items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<T> items);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(T item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(T... items);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(List<T> items);

    @Delete()
    void delete(T item);

    @Delete()
    void delete(T... items);

    @Delete()
    void delete(List<T> items);

}
