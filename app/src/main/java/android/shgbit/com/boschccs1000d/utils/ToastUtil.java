package android.shgbit.com.boschccs1000d.utils;

import android.os.Handler;
import android.os.Looper;
import android.shgbit.com.boschccs1000d.base.BaseApp;
import android.widget.Toast;

/**
 * Created by user on 2016-12-13.
 */
public class ToastUtil {

    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Toast toast = null;
    private static Object synObj = new Object();

    public static void showToast(final String msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(final int msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(final String msg, final int len) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast != null) {
                                toast.cancel();
                            }
                            toast = Toast.makeText(BaseApp.getAppContext(), msg, len);
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }

    public static void showMessage(final int msg, final int len) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast != null) {
                                toast.cancel();
                            }
                            toast = Toast.makeText(BaseApp.getAppContext(), msg, len);
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }
}
