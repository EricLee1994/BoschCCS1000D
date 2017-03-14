package android.shgbit.com.boschccs1000d.activity;

import android.content.Context;
import android.content.Intent;
import android.shgbit.com.boschccs1000d.R;
import android.shgbit.com.boschccs1000d.base.BaseMgr;
import android.shgbit.com.boschccs1000d.controllers.CSS1000DController;
import android.shgbit.com.boschccs1000d.controllers.TCPNoticeTrace;
import android.shgbit.com.boschccs1000d.models.User;
import android.shgbit.com.boschccs1000d.adapter.LogAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends LogActivity implements View.OnClickListener {

    private Context context;

    private TextView mEdtCentAddr;
    private TextView mEdtCentPort;
    private TextView mEdtCCSAddr;
    private ImageView mImageView;

    private TextView mEdtUsername;
    private TextView mEdtPassword;

    private ListView mLogListView;
    LogAdapter logAdapter = null;

    private TCPNoticeTrace noticeTrace;
    private boolean isInited = false;

    private long exitTime = 0;

    CSS1000DController mCss1000dController = new CSS1000DController(context);

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBtnView();
        initData();
        initTcpClient();
        initSpk();

    }

    public void initSpk(){
        ISessionCallback iSessionCallback = new ISessionCallback() {
            @Override
            public void onGetId(int mSessionId) {
                if (mSessionId!=0){
                    mCss1000dController.getSpk(true);
                }
            }
        };
        mCss1000dController.setISessionCallback(iSessionCallback);
    }
    public void initTrace(){
        if(isInited == true) {
            ITraceCallback iTraceCallback = new ITraceCallback() {
                @Override
                public void onSendId(int mPointId) {
                    String msg = String.format("M%03d", mPointId);
                    noticeTrace.Notice(msg);
                }
            };

            mCss1000dController.setITraceCallback(iTraceCallback);
        }
        while (true){

            try {
                noticeTrace.Notice("123");
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }}

    public void initTcpClient(){
        try {
            new Thread(new TCPClient()).start();

        }catch (UnknownError e){
            showLog("initTrace failed!");
        }

    }

    public void initData(){

        BaseMgr.CCSD_ADDR = mEdtCCSAddr.getText().toString();
        BaseMgr.CENTPORT = mEdtCentPort.getText().toString();
        BaseMgr.CENTADDR = mEdtCentAddr.getText().toString();
        User.USERNAME = mEdtUsername.getText().toString();
        User.PASSWORD = mEdtPassword.getText().toString();
    }

    public void initBtnView(){
        //base info
        mEdtCentAddr = (TextView) findViewById(R.id.edtCent);
        mEdtCentPort = (TextView) findViewById(R.id.edtCentPort);
        mEdtCCSAddr = (TextView) findViewById(R.id.edtCCSAddr);
        mEdtUsername = (TextView) findViewById(R.id.etUsername);
        mEdtPassword = (TextView) findViewById(R.id.etPassword);
        mImageView  = (ImageView) findViewById(R.id.imgview);
        //Log
        mLogListView = (ListView) findViewById(R.id.LogListView);

        showLog("日志开启！");
        logAdapter = new LogAdapter(this, BaseMgr.LOGLIST);
        mLogListView.setAdapter(logAdapter);

        noticeTrace = TCPNoticeTrace.getInstance();

        mImageView.setOnClickListener(this);

        IInfoCallback infoCallback = new IInfoCallback() {
            @Override
            public void onUIChange(String mUserName, String mPassword, String mCssdAddr, String mCentAddr, String mCentPort) {
                mEdtUsername.setText(mUserName);
                mEdtPassword.setText(mPassword);
                mEdtCCSAddr.setText(mCssdAddr);
                mEdtCentAddr.setText(mCentAddr);
                mEdtCentPort.setText(mCentPort);

                Log.e(TAG, "UIChange");
            }

        };
        mCss1000dController.setIInfoCallback(infoCallback);

        IShowLogCallback showLogCallback = new IShowLogCallback() {
            @Override
            public void onShowLog(String log) {
                Date curDate = new Date(System.currentTimeMillis());
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("info", log);
                map.put("time", BaseMgr.FOMAT.format(curDate));
                BaseMgr.LOGLIST.add(map);
                logAdapter.notifyDataSetChanged();
            }
        };
        mCss1000dController.setIShowLogCallback(showLogCallback);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.imgview:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                mCss1000dController.Close();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        mCss1000dController.Open();

        Log.e(TAG, "123:"+ BaseMgr.SESSIONID );
        if (BaseMgr.SESSIONID != null) {
            Log.d("onresume", "sessionid");
//            mCss1000dController.getSpk(true);
        }
        logAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isInited = true) {
            noticeTrace.Close();
        }
        Log.d(TAG, "onPause");
        // CSS1000DController Stop
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // CSS1000DController Close
        mCss1000dController = null;
        BaseMgr.LOGLIST.clear();
        Log.d(TAG, "onDestroy");
    }


    class TCPClient implements Runnable {

        @Override
        public void run() {
            boolean ret = noticeTrace.Open(BaseMgr.CENTADDR, Integer.parseInt(BaseMgr.CENTPORT));
            showLog("open noticeTrace:" + ret);
            if (!ret) {
                Log.e(TAG, "Connect with Server failed.");
                isInited = false;
            } else {
                showLog("initTrace successfully!");
                Log.e(TAG, "Connect with Server successfully.");
                isInited = true;
                initTrace();
            }
            while (ret == false){
                ret = noticeTrace.Open(BaseMgr.CENTADDR, Integer.parseInt(BaseMgr.CENTPORT));
//                initTrace();
                showLog("try connect again.");
            }
            isInited = true;
            Log.e(TAG, "Connect with Server successfully1.");
        }
    }

    public interface IInfoCallback {
        void onUIChange(String mUserName, String mPassword, String mCssdAddr, String mCentAddr, String mCentPort);
    }

    public interface ITraceCallback{
        void onSendId(int mPointId);
    }

    public interface ISessionCallback{
        void onGetId(int mSessionId);
    }

    public interface IShowLogCallback{
        void onShowLog(String log);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
