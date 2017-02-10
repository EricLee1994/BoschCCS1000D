package android.shgbit.com.boschccs1000d.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2016-12-10.
 */
public class BaseMgr {

    //public static String CCSD_ADDR = "http://172.16.16.41:3000";
    public static String CCSD_ADDR;

    public static String CENTADDR;

    public static String CENTPORT;

    public static String SESSIONID = "";

    public static List<Map<String, Object>> LOGLIST = new ArrayList<Map<String, Object>>();

    public static List<Map<String, Object>> SpkAvailList = new ArrayList<Map<String, Object>>();

    public static int POINTID;

    public static SimpleDateFormat FOMAT = new SimpleDateFormat("HH:mm:ss ");

}
