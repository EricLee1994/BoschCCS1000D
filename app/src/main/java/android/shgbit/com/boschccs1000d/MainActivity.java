package android.shgbit.com.boschccs1000d;

import android.content.Context;
import android.content.SharedPreferences;
import android.shgbit.com.boschccs1000d.base.BaseMgr;
import android.shgbit.com.boschccs1000d.controllers.CSS1000DController;
import android.shgbit.com.boschccs1000d.controllers.TCPNoticeTrace;
import android.shgbit.com.boschccs1000d.utils.LogAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;

    private EditText mEdtCentAddr;
    private EditText mEdtCentPort;
    private EditText mEdtCCSAddr;
    private Button mBtnLogin;
    private Button mBtnLogout;
    private Button mBtnSysinfo;
    private Button mBtnShortSeats;
    private Button mBtnLongSeats;
//speaker
    private Button mBtnShortSpkAvail;
    private Button mBtnLongSpkAvail;
    private Button mBtnShortSpkGet;
    private Button mBtnLongSpkGet;
    private Button mBtnSpkPost;
    private Button mBtnSpkDel;
    private Button mBtnSpkDelid;
//wait-list
    private Button mBtnLongWaitAvail;
    private Button mBtnShortWaitAvail;
    private Button mBtnLongWaitGet;
    private Button mBtnShortWaitGet;
    private Button mBtnWaitPost;
    private Button mBtnWaitDel;
    private Button mBtnWaitDelid;

    private Button mBtnInit;
    private Button mBtnSendTrace;
    private EditText mEdtUsername;
    private EditText mEdtPassword;

    private ListView mLogListView;
    LogAdapter logAdapter = null;

    private TCPNoticeTrace noticeTrace;
    private boolean isInited = false;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBtnView();

        init();
        mCss1000dController.Open();
    }

    public void init(){
        BaseMgr.CCSD_ADDR = mEdtCCSAddr.getText().toString();
        Log.i(TAG, "CCSD_ADDR"+ BaseMgr.CCSD_ADDR);
        String USERNAME = mEdtUsername.getText().toString();
        String PASSWORD = mEdtPassword.getText().toString();
        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor  editor = sp.edit();
        editor.putString("username","test");
        editor.putString("password","test");
        editor.commit();
    }

    public void initBtnView(){
        mEdtCentAddr = (EditText) findViewById(R.id.edtCent);
        mEdtCentPort = (EditText) findViewById(R.id.edtCentPort);
        mEdtCCSAddr = (EditText) findViewById(R.id.edtCCSAddr);

        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mBtnLogout = (Button) findViewById(R.id.btnLogout);

        mLogListView = (ListView) findViewById(R.id.LogListView);
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("info", "日志开启！");
        BaseMgr.LOGLIST.add(map);
        logAdapter = new LogAdapter(this, BaseMgr.LOGLIST);
        mLogListView.setAdapter(logAdapter);
        mLogListView.setEnabled(false);

        mBtnSysinfo = (Button) findViewById(R.id.btnSysInfo);
//        mBtnShortSeats = (Button) findViewById(R.id.btnShortSeats);
//        mBtnLongSeats = (Button) findViewById(R.id.btnLongSeats);
////speaker
//        mBtnShortSpkAvail = (Button) findViewById(R.id.btnSpkAvail);
//        mBtnShortSpkGet = (Button) findViewById(R.id.btnSpksget);
//        mBtnLongSpkAvail = (Button) findViewById(R.id.btnLongSpkAvail);
//        mBtnLongSpkGet = (Button) findViewById(R.id.btnLongSpkGet);
//        mBtnSpkPost = (Button) findViewById(R.id.btnSpksPost);
//        mBtnSpkDel = (Button) findViewById(R.id.btnSpksDel);
//        mBtnSpkDelid = (Button) findViewById(R.id.btnSpksDelID);
////wait-list
//        mBtnShortWaitAvail = (Button) findViewById(R.id.btnWaitAvail);
//        mBtnShortWaitGet = (Button) findViewById(R.id.btnWait);
//        mBtnLongWaitAvail = (Button) findViewById(R.id.btnLongWaitAvail);
//        mBtnLongWaitGet = (Button) findViewById(R.id.btnLongWait);
//        mBtnWaitPost = (Button) findViewById(R.id.btnWaitPost);
//        mBtnWaitDel = (Button) findViewById(R.id.btnWaitDel);
//        mBtnWaitDelid = (Button) findViewById(R.id.btnWaitDelID);
//
        mBtnInit = (Button) findViewById(R.id.btnInit);
        mBtnSendTrace = (Button) findViewById(R.id.btnSendTCP);

        mEdtUsername = (EditText) findViewById(R.id.etUsername);
        mEdtPassword = (EditText) findViewById(R.id.etPassword);

        noticeTrace = TCPNoticeTrace.getInstance();

        mBtnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("ZMG", "IP: " + mEdtCentAddr.getText().toString().trim());
                    Log.e("ZMG", "Port: " + Integer.parseInt(mEdtCentPort.getText().toString().trim()));

                    new Thread(new TCPClient()).start();

                } catch (UnknownError e) {
                    Log.e("ZMG", "Error msg: " + e);
                }
            }
        });

        mBtnSendTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rand = Math.abs(new Random().nextInt() % 999);
                String msg = String.format("M%03d", rand);

                noticeTrace.Notice(msg);
            }
        });

        // Read Config

        // CSS1000DController Open

        mBtnLogin.setOnClickListener(this);
        mBtnSysinfo.setOnClickListener(this);
        mBtnLogout.setOnClickListener(this);
//        mBtnLongSeats.setOnClickListener(this);
//        mBtnShortSeats.setOnClickListener(this);
//
//        mBtnLongSpkAvail.setOnClickListener(this);
//        mBtnLongSpkGet.setOnClickListener(this);
//        mBtnShortSpkAvail.setOnClickListener(this);
//        mBtnShortSpkGet.setOnClickListener(this);
//        mBtnSpkPost.setOnClickListener(this);
//        mBtnSpkDel.setOnClickListener(this);
//        mBtnSpkDelid.setOnClickListener(this);
//
//        mBtnLongWaitAvail.setOnClickListener(this);
//        mBtnLongWaitGet.setOnClickListener(this);
//        mBtnShortWaitAvail.setOnClickListener(this);
//        mBtnShortWaitGet.setOnClickListener(this);
//        mBtnWaitPost.setOnClickListener(this);
//        mBtnWaitDel.setOnClickListener(this);
//        mBtnWaitDelid.setOnClickListener(this);

    }

    CSS1000DController mCss1000dController = new CSS1000DController(context);

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:


                mCss1000dController.Open();
                logAdapter.notifyDataSetChanged();
//                mLogListView.setSelection(mLogListView.getBottom());
                break;
//
//            case R.id.btnSysInfo:
//                mCss1000dController.getSysInfo();
//                break;
//
//            case R.id.btnShortSeats:
//                mCss1000dController.getSeats(true);
//                break;
//
//            case R.id.btnLongSeats:
//                mCss1000dController.getSeats(false);
//                break;
//
//            case R.id.btnLogout:
//                mCss1000dController.Close();
//                break;
//
//            //speakers
//            case R.id.btnSpkAvail:
//                mCss1000dController.getSpkAvail(true);
//                break;
//
//            case R.id.btnLongSpkAvail:
//                mCss1000dController.getSpkAvail(false);
//
//            case R.id.btnSpksget:
//                mCss1000dController.getSpk(true);
//                break;
//
//            case R.id.btnLongSpkGet:
//                mCss1000dController.getSpk(false);
//
//            case R.id.btnSpksPost:
//                mCss1000dController.postSpk();
//                break;
//
//            case R.id.btnSpksDel:
//                mCss1000dController.deleteAllSpk();
//                break;
//
//            case R.id.btnSpksDelID:
//                mCss1000dController.deleteBySpkID();
//                break;
//
//
//            //wait-list
//            case R.id.btnWaitAvail:
//                mCss1000dController.getWaitAvail(true);
//                break;
//
//            case R.id.btnLongWaitAvail:
//                mCss1000dController.getWaitAvail(false);
//
//            case R.id.btnWait:
//                mCss1000dController.getWait(true);
//                break;
//
//            case R.id.btnLongWait:
//                mCss1000dController.getWaitAvail(false);
//
//            case R.id.btnWaitPost:
//                mCss1000dController.waitPost();
//                break;
//
//            case R.id.btnWaitDel:
//                mCss1000dController.deleteAllWait();
//                break;
//
//            case R.id.btnWaitDelID:
//                mCss1000dController.deleteByWaitId();
//                break;
//
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // CSS1000DController Start

    }

    @Override
    protected void onPause() {
        super.onPause();

        noticeTrace.Close();
        // CSS1000DController Stop
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // CSS1000DController Close
    }



    class TCPClient implements Runnable {

        @Override
        public void run() {
            boolean ret = noticeTrace.Open(mEdtCentAddr.getText().toString().trim(), Integer.parseInt(mEdtCentPort.getText().toString().trim()));
            if (!ret) {
                Log.e("ZMG", "Connect with Server failed.");
            } else {
                isInited = true;
            }
        }
    }
}
