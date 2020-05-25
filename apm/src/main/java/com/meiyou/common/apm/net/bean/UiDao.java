package com.meiyou.common.apm.net.bean;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * 页面Ui  Dao 统计
 * @since  18/1/24 下午3:08
 */
@Dao
public interface UiDao {
    @Query("SELECT * FROM MetricsBean")
    List<MetricsBean> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<MetricsBean> loadAllByIds(int[] userIds);

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
//           + "last_name LIKE :last LIMIT 1")
//    MetricsBean findByName(String first, String last);

    @Insert
    void insertAll(MetricsBean... users);
    
    @Insert
    void insertBean(MetricsBean bean);

    @Delete
    void delete(MetricsBean user);
}