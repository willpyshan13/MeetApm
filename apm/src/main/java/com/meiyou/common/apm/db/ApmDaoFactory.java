//package com.meiyou.common.apm.db;
//
//import android.content.Context;
//
//import com.meiyou.sdk.common.database.BaseDAO;
//import com.meiyou.sdk.common.database.DaoConfig;
//import com.meiyou.sdk.common.database.DbManager;
//import com.meiyou.sdk.common.database.DefaultBaseDAO;
//
///**
// * Ga 数据库文件名
// *
// */
//public class ApmDaoFactory {
//    private static ApmDaoFactory daoFactory;
//    private static final String DB_NAME = "apm.db";
//
//    public static ApmDaoFactory getInstance(Context context) {
//        if (daoFactory == null) {
//            daoFactory = new ApmDaoFactory(context);
//        }
//        return daoFactory;
//    }
//
//    private DaoConfig daoConfig;
//
//    private ApmDaoFactory(Context context) {
//        daoConfig = new DaoConfig(context) {
//            @Override
//            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
//            }
//
//            @Override
//            public String getAuthority() {
//                return null;
//            }
//
//            @Override
//            public Class<?>[] getAllTableClassList() {
//                return new Class<?>[0];
//            }
//        };
//        daoConfig.setDbName(DB_NAME);
//        daoConfig.setDbVersion(1);
//        DbManager.create(daoConfig).init();
//
//    }
//
//    public BaseDAO getBaseDao() {
//        return new DefaultBaseDAO(DbManager.get(DB_NAME).getDatabase());
//    }
//}
