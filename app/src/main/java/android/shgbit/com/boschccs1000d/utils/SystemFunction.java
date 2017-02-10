package android.shgbit.com.boschccs1000d.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wa.util.WAUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 2017/2/9.
 */

public class SystemFunction {

    private static final int CMD_REBOOT = 1;
    private static final int CMD_SHUTDOWN = 2;
    private static final int CMD_OPEN_SCREEN = 3;
    private static final int CMD_CLOSE_SCREEN = 4;
    private static final int CMD_CAPTURE = 5;
    private static final int CMD_INSTALL_APK = 6;
    private static final int CMD_MODIFY_TIME = 7;
    private static final int CMD_APP_EXCEPTION	= 8;
    private static final int CMD_APP_MONITOR	= 9;

    private static final String CMD					= "cmd";
    private static final String CAPTURE_PATH 		= "path";
    private static final String INSTALL_APK_PATH 	= "path";
    private static final String TIME_DIFFER 		= "timediff";
    private static final String MONITOR_PKGNAME 	= "pkgname";

    private static final String BROADCAST_ACTION	= "com.wuchen.android.BroadcastReceiver.wamonitor";
    private static final String TAG = "SystemFunction";

    private static void SendBroadcase(Context context, int cmd, Map<String, String> arg) {

        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION);
        intent.putExtra(CMD, cmd);

        try {
            if (arg != null) {
                for(Map.Entry<String, String> entry : arg.entrySet()) {
                    intent.putExtra(entry.getKey(), entry.getValue());
                }
            }
        } catch (Exception e) {
            DSLog.e(TAG, "SendBroadcase Exception:" + WAUtils.CaughtException(e));
        }

        context.sendBroadcast(intent);

    }

    public static boolean CaptureScreen(Context context, String savePath) {

        DSLog.v(TAG, "CaptureScreen \"" + savePath + "\"");

        if (savePath.endsWith(".jpg") == false && savePath.endsWith(".png") == false
                && savePath.endsWith(".JPG") == false && savePath.endsWith(".PNG") == false) {
            return false;
        }

        String cmd = "screencap -p " + savePath + "\n";
        try {

            if (new File(savePath).exists() == true) {
                DSLog.i(TAG, "CaptureScreen File:" + savePath + " exist! delete it");
                new File(savePath).delete();
            }

//			Runtime.getRuntime().exec(cmd);

            Map<String, String> arg = new HashMap<String, String>();
            arg.put(CAPTURE_PATH, savePath);
            SendBroadcase(context, CMD_CAPTURE, arg);

            return true;

        } catch (Throwable e) {
            DSLog.e(TAG, "CaptureScreen exec (" + cmd + ") failed!");
        }

        return false;
    }

    public  static void Reboot(Context context) {

        DSLog.v(TAG, "Reboot");

        try {

            SendBroadcase(context, CMD_REBOOT, null);

        } catch (Throwable e) {
            DSLog.e(TAG, "Reboot exec (Reboot) failed");
        }

    }

    public  static void Shutdown(Context context) {

        DSLog.v(TAG, "Shutdown");

        SendBroadcase(context, CMD_SHUTDOWN, null);

    }

    public static void CloseScreen(Context context) {

        DSLog.v(TAG, "CloseScreen");

//		SystemParams.setScreen_state(MainActivity.SCREEN_OFF);

        try {
//			Runtime.getRuntime().exec("ut_vpp -b 4:1");

            SendBroadcase(context, CMD_CLOSE_SCREEN, null);

        } catch (Throwable e) {
            DSLog.e(TAG, "CloseScreen exec (ut_vpp -b 4:1) failed!");
        }

    }

    public static void OpenScreen(Context context) {

        DSLog.v(TAG, "OpenScreen");

//		SystemParams.setScreen_state(MainActivity.SCREEN_ON);

        try {
//			Runtime.getRuntime().exec("ut_vpp -b 4:0");

            SendBroadcase(context, CMD_OPEN_SCREEN, null);

        } catch (Throwable e) {
            DSLog.e(TAG, "OpenScreen exec (ut_vpp -b 4:0) failed!");
        }
    }

    public static void InstallApk(Context context, String path) {

        DSLog.v(TAG, "InstallApk " + path);

        String cmd = "pm install -r " + path + "\n";
        try {

//			Runtime.getRuntime().exec(cmd);

            Map<String, String> arg = new HashMap<String, String>();
            arg.put(INSTALL_APK_PATH, path);
            SendBroadcase(context, CMD_INSTALL_APK, arg);

        } catch (Throwable e) {
            DSLog.e(TAG, "InstallApk exec (" + cmd + ") failed!");
        }
    }

    public static void ModifySystemTime(Context context , long timediffer){

        try {

            Map<String, String> arg = new HashMap<String, String>();
            arg.put(TIME_DIFFER, Long.toString(timediffer));
            SystemFunction.SendBroadcase(context, CMD_MODIFY_TIME, arg);

//			SendBroadcase(context, CMD_MODIFY_TIME, Long.toString(timediffer));

        } catch (Throwable e) {
        }
    }


    public static void StartMonitor(Context context, String pkgname){

        DSLog.v(TAG, "StartMonitor " + pkgname);

        Map<String, String> arg = new HashMap<String, String>();
        arg.put(MONITOR_PKGNAME, pkgname);
        SendBroadcase(context, CMD_APP_MONITOR, arg);
    }

    public static void StopMonitor(Context context){

        DSLog.v(TAG, "StopMonitor");

        SendBroadcase(context, CMD_APP_MONITOR, null);
    }

}
