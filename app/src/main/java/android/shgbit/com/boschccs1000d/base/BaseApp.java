package android.shgbit.com.boschccs1000d.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.format.Time;

import com.wa.util.WALog;

import org.xutils.x;

import java.io.File;

/**
 * Created by Zhumg on 2016-12-9.
 */
public class BaseApp extends Application{

    public static Context appContext;
    private static String PACKAGE_NAME;
    private static final String TAG = "BaseApp";

    @Override
    public void onCreate() {
        super.onCreate();
        WALog.filepath = Environment.getExternalStorageDirectory() + "/BoschCCS1000D/Log";
        WALog.i(TAG, WALog.filepath);

        createLogDir();
        deleteLogFile(BaseConst.LOG_PATH, 30);
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

    public void createLogDir(){
        String ConfigPath = BaseConst.SD_PATH + BaseConst.APP_DIR + "/Log";
        File fileFolder = new File(ConfigPath);
        if (!fileFolder.exists()){
            try {
                fileFolder.mkdir();
                WALog.i(TAG, "Create Log Dir!");
            }catch (Exception e){
                WALog.e(TAG, "Create Log Dir Error :" + e.toString());
            }
        }
    }

    public void deleteLogFile(String logDir, int last) {
        try {

            File file = new File(logDir);
            if (file.isFile()) {
                WALog.w(TAG, "deleteLogFile " + logDir + "is a file! delete it");
                file.delete();
                return;
            }

            if (!file.isDirectory()) {
                WALog.w(TAG, "deleteLogFile " + logDir + "isn't a directory");
                return;
            }

            Time now = new Time();
            now.setToNow();
            Time time = new Time();
            int monthDay, month, year;

            File []logs = file.listFiles();
            for (int i = 0; i < logs.length; i++) {
                String strDate = "";
                try {
                    strDate = logs[i].getName().replace(".", "-").split("-")[1];
                    year = Integer.parseInt(strDate.substring(0, 4));
                    month = Integer.parseInt(strDate.substring(4, 6));
                    monthDay = Integer.parseInt(strDate.substring(6, 8));
                } catch (Throwable e) {
                    WALog.e(TAG, "deleteLogFile cann't parse " + logs[i].getName());
                    logs[i].delete();
                    continue;
                }

                try {
                    time.set(0, 0, 0, monthDay, month - 1, year);
                } catch (Throwable e) {
                    WALog.e(TAG, "deleteLogFile time.set(" + monthDay + ", " + (month - 1) + ", " + year + ") failed");
                    logs[i].delete();
                    continue;
                }

                int days = (int) ((now.toMillis(false) - time.toMillis(false)) / 3600 / 24 / 1000);
                if (days > last || days < 0) {
                    WALog.i(TAG, "deleteLogFile Log:" + logs[i].getPath() + "(days=" + days + ") is too old(last=" + last + ")");
                    logs[i].delete();
                }
            }

        } catch (Throwable e) {
            WALog.e(TAG, "deleteFile Throwable:" + e.toString());
        }
    }
}

