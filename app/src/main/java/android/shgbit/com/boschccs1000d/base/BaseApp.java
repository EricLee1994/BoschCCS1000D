package android.shgbit.com.boschccs1000d.base;

import android.app.Application;
import android.content.Context;
import android.shgbit.com.boschccs1000d.utils.CrashHandler;

import org.xutils.x;

/**
 * Created by Zhumg on 2016-12-9.
 */
public class BaseApp extends Application{

    public static Context appContext;
    private static String PACKAGE_NAME;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();

        if(BaseConst.isDebug) {
            // 设置异常crash操作处理
        }

//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(appContext);

        PACKAGE_NAME = getPackageName();

        x.Ext.init(this);
        x.Ext.setDebug(BaseConst.isDebug);
    }

    public static Context getAppContext() { return appContext; }


}
