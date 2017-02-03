package android.shgbit.com.boschccs1000d.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.shgbit.com.boschccs1000d.base.BaseApp;
import android.shgbit.com.boschccs1000d.base.BaseMgr;
import android.shgbit.com.boschccs1000d.http.IHttpCallback;
import android.shgbit.com.boschccs1000d.http.account.DeleteWaitRequest;
import android.shgbit.com.boschccs1000d.http.account.LoginRequest;
import android.shgbit.com.boschccs1000d.http.account.LogoutRequest;
import android.shgbit.com.boschccs1000d.http.account.SeatsRequest;
import android.shgbit.com.boschccs1000d.http.account.SpeakersRequest;
import android.shgbit.com.boschccs1000d.http.account.SpkAvailRequest;
import android.shgbit.com.boschccs1000d.http.account.SpkDelRequest;
import android.shgbit.com.boschccs1000d.http.account.SysInfoRequest;
import android.shgbit.com.boschccs1000d.http.account.WaitListAvailRequest;
import android.shgbit.com.boschccs1000d.http.account.WaitListRequest;
import android.shgbit.com.boschccs1000d.models.SpkEntry;
import android.shgbit.com.boschccs1000d.models.User;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2016-12-13.
 */
public class CSS1000DController {

    Context context;
    public String TAG = "Controller";
//    BaseApp mBaseApp = new BaseApp();
//    Context mControllerContext = mBaseApp.getApplicationContext();

    public CSS1000DController() {

    }

    public CSS1000DController(Context context) {
        this.context = context;
    }

    public void Open() {
        // Login Request
//        SharedPreferences perf = context.getSharedPreferences("config", Context.MODE_PRIVATE);
//        String mLoginUsername = perf.getString("username","");
//        String mLoginPassword = perf.getString("password","");

        LoginRequest mLoginRequest = new LoginRequest(context, "test", "test");
        mLoginRequest.httpSend(new IHttpCallback() {

            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "OpenSuccess" + result);
                User user = new GsonBuilder().create().fromJson(result, User.class);
                BaseMgr.SESSIONID = user.getSid();
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("info", result);
                BaseMgr.LOGLIST.add(map);
            }

            @Override
            public void onFailure(String result) {
                Log.e(TAG, "OpenFailure" + result);
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("info", result);
                BaseMgr.LOGLIST.add(map);
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

                }
                getShortSpk();
            }

            @Override
            public void onFailure(String result) {

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

    public void postSpk() {
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
            }

            @Override
            public void onFailure(String result) {

            }
        });

        // Close TCP
    }

}
