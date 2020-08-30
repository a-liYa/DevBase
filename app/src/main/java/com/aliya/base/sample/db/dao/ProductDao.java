package com.aliya.base.sample.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.aliya.base.sample.db.entity.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao extends RoomDao<ProductEntity> {

    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> queryAll();

    @Query("select * from products where id = :productId")
    LiveData<ProductEntity> query(int productId);

    @Query("select * from products where id = :productId")
    ProductEntity querySync(int productId);

}
