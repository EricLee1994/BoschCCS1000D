package android.shgbit.com.boschccs1000d.activity;

import android.app.Activity;
import android.os.Bundle;
import android.shgbit.com.boschccs1000d.base.BaseMgr;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 2017/3/13.
 */

public class LogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLog(String content){
        Date curDate = new Date(System.currentTimeMillis());
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("info", content);
        map.put("time", BaseMgr.FOMAT.format(curDate));
        BaseMgr.LOGLIST.add(map);
    }
}
