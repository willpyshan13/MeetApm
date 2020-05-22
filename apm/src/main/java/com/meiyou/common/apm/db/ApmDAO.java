//package com.meiyou.common.apm.db;
//
//import android.content.Context;
//
//import com.meiyou.common.apm.net.bean.MetricsBean;
//import com.meiyou.sdk.common.database.BaseDAO;
//import com.meiyou.sdk.common.database.sqlite.WhereBuilder;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Ga Dao 层,提供单例直接使用
// */
//public class ApmDAO {
//    private BaseDAO baseDAO;
//
//    public ApmDAO(Context context) {
//        this.baseDAO = ApmDaoFactory.getInstance(context).getBaseDao();
//    }
//
//    private static ApmDAO instance;
//
//    public static ApmDAO getInstance(Context context) {
//        if (instance == null) {
//            instance = new ApmDAO(context);
//        }
//        return instance;
//    }
//
//    public List<MetricsBean> getApmList() {
//        return baseDAO.queryAll(MetricsBean.class);
//    }
//
//    /**
//     * 获取数据库数据，并转为可上传格式
//     * @return
//     */
//    public ArrayList<String[]> getApmData() {
//        
//        List<MetricsBean> gaList = getApmList();
//        if (gaList == null) gaList = new ArrayList<>();
//        ArrayList<String[]> list = new ArrayList<>();
//        
//        for (MetricsBean MetricsBean : gaList) {
//            String[] data = MetricsBean.getData();
//            list.add(data);
//        }
//        
//        //避免数据太大
//        int size = gaList.size();
//        if (size > 5000) {
//            list = new ArrayList<>(list.subList(size - 5000, size));
//        }
//        return list;
//    }
//
//
//    public boolean addApm(MetricsBean gaDO) {
////        LogUtils.w(JSON.toJSONString(gaDO));
//        int result = baseDAO.insert(gaDO);
//        return result > 0;
//    }
//
//
//    public boolean deleteApm(int mUid) {
//        int result = baseDAO.delete(MetricsBean.class, WhereBuilder.b("mUid", "=", mUid));
//        return result > 0;
//    }
//
//    public void deleteAll() {
//        baseDAO.deleteAll(MetricsBean.class);
//    }
//
//    public boolean updateGaUserId(List<MetricsBean> list) {
//        int result = baseDAO.updateAll(list, "mUid");
//        return result > 0;
//    }
//}
