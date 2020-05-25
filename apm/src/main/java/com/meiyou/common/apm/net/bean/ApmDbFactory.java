package com.meiyou.common.apm.net.bean;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Ga 数据库文件名
 *
 */
public class ApmDbFactory {
    private static ApmDbFactory daoFactory;
    //    private static final String DB_NAME = "apm.db";
    private static final String DATABASE_NAME = "apm_meiyou.db";
    private final ApmDatabase database;


    public static ApmDbFactory getInstance(Context context) {
        if (daoFactory == null) {
            daoFactory = new ApmDbFactory(context);
        }
        return daoFactory;
    }

//    private DaoConfig daoConfig;

    //Room 数据库 Scheme 有变化使用： 
    //http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0728/8278.html
    private ApmDbFactory(Context context) {
        database = Room.databaseBuilder(context, ApmDatabase.class, DATABASE_NAME)
                       .build();

    }

    public HttpDao getHttpDao() {
        return database.httpDao();
    }


    /**
     * 获取数据库数据，并转为可上传格式
     *
     * @return
     */
    public ArrayList<String[]> getApmData() {
        HttpDao dao = getHttpDao();
        List<MetricsBean> gaList = dao.getAll();
        if (gaList == null) gaList = new ArrayList<>();
        ArrayList<String[]> list = new ArrayList<>();

        for (MetricsBean MetricsBean : gaList) {
            String[] data = MetricsBean.getData();
            list.add(data);
        }

        //避免数据太大
        int size = gaList.size();
        if (size > 5000) {
            list = new ArrayList<>(list.subList(size - 5000, size));
        }
        return list;
    }


//    public BaseDAO getBaseDao() {
//        
//        return new DefaultBaseDAO(DbManager.get(DB_NAME).getDatabase());
//    }
}
