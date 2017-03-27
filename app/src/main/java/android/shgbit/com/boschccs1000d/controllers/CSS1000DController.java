package android.shgbit.com.boschccs1000d.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.shgbit.com.boschccs1000d.activity.MainActivity.*;
import android.shgbit.com.boschccs1000d.base.BaseApp;
import android.shgbit.com.boschccs1000d.base.BaseMgr;
import android.shgbit.com.boschccs1000d.request.waitlist.DeleteWaitRequest;
import android.shgbit.com.boschccs1000d.request.account.LoginRequest;
import android.shgbit.com.boschccs1000d.request.account.LogoutRequest;
import android.shgbit.com.boschccs1000d.request.info.SeatsRequest;
import android.shgbit.com.boschccs1000d.request.speaker.SpeakersRequest;
import android.shgbit.com.boschccs1000d.request.speaker.SpkAvailRequest;
import android.shgbit.com.boschccs1000d.request.speaker.SpkDelRequest;
import android.shgbit.com.boschccs1000d.request.info.SysInfoRequest;
import android.shgbit.com.boschccs1000d.request.waitlist.WaitListAvailRequest;
import android.shgbit.com.boschccs1000d.request.waitlist.WaitListRequest;
import android.shgbit.com.boschccs1000d.models.SpkEntry;
import android.shgbit.com.boschccs1000d.models.User;
import android.shgbit.com.boschccs1000d.base.BaseRequest.IHttpCallback;
import android.shgbit.com.boschccs1000d.activity.MainActivity.IInfoCallback;
import android.shgbit.com.boschccs1000d.activity.MainActivity.ITraceCallback;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wa.util.WALog;

import org.json.JSONArray;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2016-12-13.
 */
public class CSS1000DController implements SharedPreferences.OnSharedPreferenceChangeListener {

    Context context;
    public String TAG = "Controller";

    public CSS1000DController(Context context) {
        this.context = context;
    }



    public ITraceCallback traceCallback = null;
    public ISessionCallback sessionCallback = null;
    public IInfoCallback infoCallback = null;
    public IShowLogCallback showLogCallback = null;
    public void setITraceCallback(ITraceCallback traceCallback){
        this.traceCallback = traceCallback;
    }
    public void setIInfoCallback(IInfoCallback infoCallback){
        this.infoCallback = infoCallback;
    }
    public void setISessionCallback(ISessionCallback sessionCallback){
        this.sessionCallback = sessionCallback;
    }
    public void setIShowLogCallback(IShowLogCallback showLogCallback){
        this.showLogCallback = showLogCallback;
    }


    public void Open() {
        // Login Request
//        SharedPreferences config = BaseApp.appContext.getSharedPreferences("config", Context.MODE_PRIVATE);
//        String USERNAME = config.getString("username", "");
//        String PASSWORD = config.getString("password", "");
//        String CSSDADDR = config.getString("cssaddr", "");
//        String CENTPORT = config.getString("centport", "");
//        String CENTADDR = config.getString("centaddr", "");
//        Log.e(TAG,CENTADDR);
//        if (!USERNAME.isEmpty()&&!CSSDADDR.isEmpty()&&!CENTADDR.isEmpty()&&!CENTPORT.isEmpty()) {
//            User.USERNAME = USERNAME;
//            User.PASSWORD = PASSWORD;
//            BaseMgr.CCSD_ADDR = CSSDADDR;
//            BaseMgr.CENTADDR = CENTADDR;
//            BaseMgr.CENTPORT = CENTPORT;
//            infoCallback.onUIChange(User.USERNAME, User.PASSWORD, BaseMgr.CCSD_ADDR, BaseMgr.CENTADDR, BaseMgr.CENTPORT);
//        }
//        showLogCallback.onShowLog("Username:"+ User.USERNAME + " Password:" + User.PASSWORD + " CCSD_ADDR:" + BaseMgr.CCSD_ADDR + " CentAddr:" +BaseMgr.CENTADDR + " CentPort:" + BaseMgr.CENTPORT);

//        config.registerOnSharedPreferenceChangeListener(this);
//        LoginRequest mLoginRequest = new LoginRequest(context, USERNAME, PASSWORD);
        LoginRequest mLoginRequest = new LoginRequest(context, User.USERNAME, User.PASSWORD);
        mLoginRequest.httpSend(new IHttpCallback() {

            @Override
            public void onSuccess(String result) {
                WALog.i(TAG, "OpenSuccess" + result);
                User user = new GsonBuilder().create().fromJson(result, User.class);
                BaseMgr.SESSIONID = user.getSid();
                sessionCallback.onGetId(user.getId());
                showLogCallback.onShowLog(result);

            }

            @Override
            public void onFailure(String result) {
                WALog.e(TAG, "OpenFailure" + result);
                showLogCallback.onShowLog(result);
                Open();
            }
        });


        // Init TCP Client

    }

    public void getSysInfo() {
        //ok
        SysInfoRequest mSysInfoRequest = new SysInfoRequest(context);
        mSysInfoRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "sysinfo" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.e(TAG, "sysinfo" + result);
            }
        });

    }

    public void getSpkAvail(boolean isPolling) {
        if (isPolling == true) {
            getShortSpkAvail();
        } else {
            getLongSpkAvail();
        }
    }

    public void getSeats(boolean isPolling) {
        if (isPolling == true) {
            getShortSeatsInfo();
        } else {
            getLongSeatsInfo();
        }
    }

    public void getSpk(boolean isPolling) {
        if (isPolling == true) {
            Log.e(TAG, "getshortspk");
            getShortSpk();
        } else {
            getLongSpk();
        }
    }

    public void getWaitAvail(boolean isPolling) {
        if (isPolling == true) {
            getShortWaitAvail();
        } else {
            getLongWaitAvail();
        }
    }

    public void getWait(boolean isPolling) {
        if (isPolling == true) {
            getShortWait();
        } else {
            getLongWait();
        }
    }

    public void getShortSeatsInfo() {
        //长连接
//        GetMicStatus mGetMicStatus = new GetMicStatus();
//        mGetMicStatus.doQuerySeats();
        SeatsRequest seatsRequest = new SeatsRequest(context, true);
        seatsRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                getShortSeatsInfo();
            }

            @Override
            public void onFailure(String result) {

            }
        });

    }

    public void getLongSeatsInfo() {
        SeatsRequest seatsRequest = new SeatsRequest(context, false);
        seatsRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailure(String result) {

            }
        });
    }

    public void getShortSpkAvail() {
        //长连接
//        GetMicStatus mGetMicStatus = new GetMicStatus();
//        mGetMicStatus.doQueryAvailSpk();
        SpkAvailRequest spkAvailRequest = new SpkAvailRequest(context, true);
        spkAvailRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e("avail", result);
                Date curDate = new Date(System.currentTimeMillis());
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("info", result);
                map.put("time", BaseMgr.FOMAT.format(curDate));
                BaseMgr.LOGLIST.add(map);
                getShortSpkAvail();
            }

            @Override
            public void onFailure(String result) {

            }
        });
    }

    public void getLongSpkAvail() {
        SpkAvailRequest spkAvailRequest = new SpkAvailRequest(context, false);
        spkAvailRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailure(String result) {

            }
        });
    }

    public void getShortSpk() {
        SpeakersRequest speakersRequest = new SpeakersRequest(context, true);
        speakersRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement el = parser.parse(result);
                JsonArray jsonArray = null;
                jsonArray = el.getAsJsonArray();

                Iterator it = jsonArray.iterator();
                while (it.hasNext()){
                    SpkEntry spkEntry = null;
                    JsonElement e = (JsonElement) it.next();
                    spkEntry = gson.fromJson(e, SpkEntry.class);
                    BaseMgr.POINTID = spkEntry.getId();
                }
                Log.e(TAG,"id:"+BaseMgr.POINTID);
                traceCallback.onSendId(BaseMgr.POINTID);
                Date curDate = new Date(System.currentTimeMillis());
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("info", "获取当前发言话筒id：" + BaseMgr.POINTID);
                map.put("time", BaseMgr.FOMAT.format(curDate));
                BaseMgr.LOGLIST.add(map);
                getShortSpk();

            }

            @Override
            public void onFailure(String result) {
                Date curDate = new Date(System.currentTimeMillis());
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("info", result);
                map.put("time", BaseMgr.FOMAT.format(curDate));
                BaseMgr.LOGLIST.add(map);
            }
        });

    }

    public void getLongSpk() {
        SpeakersRequest speakersRequest = new SpeakersRequest(context, false);
        speakersRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailure(String result) {

            }
        });
    }

    public void spkPost() {
        //短链接
        //ok
        JSONArray entries = new JSONArray();
        entries.put(1);
        entries.put(2);
        String spkSeats = entries.toString();

        SpeakersRequest mSpkPostRequest = new SpeakersRequest(context, spkSeats);
        mSpkPostRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "spkPost" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.e(TAG, "spkPost" + result);
            }
        });

    }

    public void deleteAllSpk() {
        //ok
        SpkDelRequest mSpkDelRequest = new SpkDelRequest(context);
        mSpkDelRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "spkDel" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.e(TAG, "spkDel" + result);
            }
        });

    }

    public void deleteBySpkID() {
        //ok
        //传入要删除的seatid，eg：1
        SpkDelRequest mSpkDelIdRequest = new SpkDelRequest(context, 1);
        mSpkDelIdRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "spkDelID" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.e(TAG, "spkDelID" + result);
            }
        });

    }

    public void getShortWaitAvail() {
        //长连接
//        GetMicStatus mGetMicStatus = new GetMicStatus();
//        mGetMicStatus.doQueryAvailWait();
        WaitListAvailRequest waitListAvailRequest = new WaitListAvailRequest(context, true);
        waitListAvailRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                getShortWaitAvail();
            }

            @Override
            public void onFailure(String result) {

            }
        });
    }

    public void getLongWaitAvail() {
        WaitListAvailRequest waitListAvailRequest = new WaitListAvailRequest(context, false);
        waitListAvailRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailure(String result) {

            }
        });
    }

    public void getShortWait() {
        //长连接
//        GetMicStatus mGetMicStatus = new GetMicStatus();
//        mGetMicStatus.doQueryWaitGet();
        WaitListRequest waitListRequest = new WaitListRequest(context, true);
        waitListRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                getShortWait();
            }

            @Override
            public void onFailure(String result) {

            }
        });
    }

    public void getLongWait() {
        WaitListRequest waitListRequest = new WaitListRequest(context, false);
        waitListRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailure(String result) {

            }
        });
    }

    public void waitPost() {
        //短链接
        //ok
        int[] a = {2};
        String b = new Gson().toJson(a);
//        String waitPost = waitlistSbj.toString();
        WaitListRequest mWaitListRequest = new WaitListRequest(context, b);
        mWaitListRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "waitPost" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.e(TAG, "waitPost" + result);
            }
        });

    }

    public void deleteAllWait() {
        //ok
        DeleteWaitRequest mWaitListDelRequest = new DeleteWaitRequest(context);
        mWaitListDelRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "waitDel" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.e(TAG, "waitDel" + result);
            }
        });

    }

    public void deleteByWaitId() {
        //ok
        //传入要删除的seatid，eg：1
        DeleteWaitRequest mWaitListDelIdRequest = new DeleteWaitRequest(context, 2);
        mWaitListDelIdRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "waitDelID" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.e(TAG, "waitDelID" + result);
            }
        });

    }

    public void Start() {
        // Query CSSD Status

        // Check TCP timeout
    }

    public void Stop() {
        // Stop Query

        // Stop TCP Check
    }

    public void Close() {
        // Logout
        LogoutRequest mLogoutRequest = new LogoutRequest(context);
        mLogoutRequest.httpSend(new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                BaseMgr.SESSIONID = "";
                Date curDate = new Date(System.currentTimeMillis());
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("info", result);
                map.put("time", BaseMgr.FOMAT.format(curDate));
                BaseMgr.LOGLIST.add(map);
            }

            @Override
            public void onFailure(String result) {
                Date curDate = new Date(System.currentTimeMillis());
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("info", result);
                map.put("time", BaseMgr.FOMAT.format(curDate));
                BaseMgr.LOGLIST.add(map);

            }
        });

        // Close TCP
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("username")) {
            User.USERNAME = sharedPreferences.getString("username", "");
        }
        if (key.equals("password")) {
            User.PASSWORD = sharedPreferences.getString("password", "");
        }
        if (key.equals("cssaddr")) {
            BaseMgr.CCSD_ADDR = sharedPreferences.getString("cssaddr", "");
        }
        if (key.equals("centaddr")) {
            BaseMgr.CENTADDR = sharedPreferences.getString("centaddr", "");
        }
        if (key.equals("centport")) {
            BaseMgr.CENTPORT = sharedPreferences.getString("centport", "");
        }
        Log.e(TAG, User.USERNAME + User.PASSWORD);
        infoCallback.onUIChange(User.USERNAME, User.PASSWORD, BaseMgr.CCSD_ADDR, BaseMgr.CENTADDR, BaseMgr.CENTPORT);
    }


}
