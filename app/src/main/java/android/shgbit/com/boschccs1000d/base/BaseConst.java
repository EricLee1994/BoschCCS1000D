package android.shgbit.com.boschccs1000d.base;

import android.os.Environment;

/**
 * Created by user on 2016-12-8.
 */
public final class BaseConst {
    public final static boolean isDebug = true;

    public final static String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public final static String LOC_ROOT = SD_PATH + "DCN_CSSTD";
    public final static String API_URI = "/api/";

    // ---------------------------------- HTTP Function --------------------------------------------
    public static final String FUNC_LOGIN = "login";
    public static final String FUNC_LOGOUT = "logout";
    public static final String FUNC_SYSINFO = "system-info";
    public static final String FUNC_SEATS = "seats";

    public static final String FUNC_SPKAVAIL = "speakers/available";
    public static final String FUNC_SPKER = "speakers";
    public static final String FUNC_SPK_SEATID = "speakers/";

    public static final String FUNC_WAITAVAIL = "waiting-list/available";
    public static final String FUNC_WAITLIST = "waiting-list";
    public static final String FUNC_WAITLIST_SEATID = "waiting-list/";

    public static final String FUNC_PRIORITY = "priority";
    public static final String FUNC_SYSSTATUS = "system/status";

    // --------------------------------- VOTING ----------------------------------------------------


    // ----------------------------------- Error Code ----------------------------------------------
    public final static String HTTP_NO_NETWORK = "600000";//无网络
}
