package com.meiyou.common.apm.core;

import android.content.Context;

import com.meiyou.common.apm.controller.ApmAgent;
import com.meiyou.common.apm.net.bean.ApmDbFactory;
import com.meiyou.common.apm.net.bean.HttpDao;
import com.meiyou.common.apm.net.bean.MetricsBean;
import com.meiyou.common.apm.util.AsyncTaskSerial;
import com.meiyou.common.apm.util.LogUtils;

/**
 * 入库Task
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 18/4/10 下午6:02
 */
public class PostTask extends AsyncTaskSerial<String, Void, Void> {
    private final MetricsBean bean;

    PostTask(MetricsBean bean) {
        this.bean = bean;
    }

    @Override
    protected Void doInBackground(String... strings) {
        Context context = ApmAgent.getContext();
        LogUtils.d(bean.toString());
        HttpDao dao = ApmDbFactory.getInstance(context).getHttpDao();
        dao.insertBean(bean);
//            ApmDAO.getInstance(context).addApm(bean);
        return null;
    }
}