package com.meiyou.common.apm.net.bean;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * OKHTP 请求数据库Dao
 */
@Dao
public interface HttpDao {
    @Query("SELECT * FROM MetricsBean")
    List<MetricsBean> getAll();

    @Query("SELECT * FROM MetricsBean WHERE id IN (:ids)")
    List<MetricsBean> loadAllByIds(int[] ids);

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
//           + "last_name LIKE :last LIMIT 1")
//    MetricsBean findByName(String first, String last);

    @Insert
    void insertAll(MetricsBean... users);
    
    @Insert
    void insertBean(MetricsBean bean);

    @Delete
    void delete(MetricsBean user);

    @Query("DELETE FROM MetricsBean")
    void deleteAll();
    
}