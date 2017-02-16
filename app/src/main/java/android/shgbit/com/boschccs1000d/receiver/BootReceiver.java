package android.shgbit.com.boschccs1000d.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.shgbit.com.boschccs1000d.activity.MainActivity;

/**
 * Created by Eric on 2017/2/8.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent mIntent = new Intent(context, MainActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        }
    }
}