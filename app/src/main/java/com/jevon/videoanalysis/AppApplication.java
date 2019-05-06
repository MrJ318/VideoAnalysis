package com.jevon.videoanalysis;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

import cn.bmob.v3.Bmob;

/**
 *
 * @Author: Mr.J
 * @CreateDate: 2019/4/21 11:57
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

        //初始化BmobSDK
        Bmob.initialize(this, "6501413678cda993739d192f1f916b2d");
    }

    //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            Log.d("Mr.J", " onViewInitFinished is " + arg0);
        }

        @Override
        public void onCoreInitFinished() {
        }
    };
}
