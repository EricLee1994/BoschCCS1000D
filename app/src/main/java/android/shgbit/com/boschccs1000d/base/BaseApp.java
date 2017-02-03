package android.shgbit.com.boschccs1000d.base;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * Created by Zhumg on 2016-12-9.
 */
public class BaseApp extends Application{

    private static Context appContext;
    private static String PACKAGE_NAME;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();

        if(BaseConst.isDebug) {
            // 设置异常crash操作处理
        }

        PACKAGE_NAME = getPackageName();

        x.Ext.init(this);
        x.Ext.setDebug(BaseConst.isDebug);
    }

    public static Context getAppContext() { return appContext; }


}
