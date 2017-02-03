package android.shgbit.com.boschccs1000d.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.shgbit.com.boschccs1000d.base.BaseApp;

/**
 * Created by user on 2016-12-10.
 */
public class CommonUtils {

    /** 检查是否有网络 */
    public static boolean isNetworkAvailable() {
        NetworkInfo info = getNetworkInfo(BaseApp.getAppContext());
        if (info != null) {
            return info.isAvailable();
        }
        return false;
    }

    private static NetworkInfo getNetworkInfo(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}
