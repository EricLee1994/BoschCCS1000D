package android.shgbit.com.boschccs1000d.activity;

import android.content.Context;
import android.content.Intent;
import android.shgbit.com.boschccs1000d.R;
import android.shgbit.com.boschccs1000d.base.BaseMgr;
import android.shgbit.com.boschccs1000d.controllers.CSS1000DController;
import android.shgbit.com.boschccs1000d.controllers.TCPNoticeTrace;
import android.shgbit.com.boschccs1000d.http.IInfoCallback;
import android.shgbit.com.boschccs1000d.models.User;
import android.shgbit.com.boschccs1000d.utils.DSLog;
import android.shgbit.com.boschccs1000d.utils.LogAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;

    private TextView mEdtCentAddr;
    private TextView mEdtCentPort;
    private TextView mEdtCCSAddr;
    private ImageView mImageView;


    private Button mBtnInit;
    private Button mBtnSendTrace;
    private TextView mEdtUsername;
    private TextView mEdtPassword;

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
        initData();
        DSLog.d(TAG, "open");
    }

    public void initData(){


        BaseMgr.CCSD_ADDR = mEdtCCSAddr.getText().toString();
        BaseMgr.CENTPORT = mEdtCentPort.getText().toString();
        BaseMgr.CENTADDR = mEdtCentAddr.getText().toString();
        User.UserName = mEdtUsername.getText().toString();
        User.Password = mEdtPassword.getText().toString();

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
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("info", "日志开启！");
        BaseMgr.LOGLIST.add(map);
        logAdapter = new LogAdapter(this, BaseMgr.LOGLIST);
        mLogListView.setAdapter(logAdapter);
//        mLogListView.setEnabled(false);

        noticeTrace = TCPNoticeTrace.getInstance();

//        mBtnInit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Log.e("ZMG", "IP: " + mEdtCentAddr.getText().toString().trim());
//                    Log.e("ZMG", "Port: " + Integer.parseInt(mEdtCentPort.getText().toString().trim()));
//
//                    new Thread(new TCPClient()).start();
//
//                } catch (UnknownError e) {
//                    Log.e("ZMG", "Error msg: " + e);
//                }
//            }
//        });
//
//        mBtnSendTrace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int rand = Math.abs(new Random().nextInt() % 999);
//                String msg = String.format("M%03d", rand);
//
//                noticeTrace.Notice(msg);
//            }
//        });

        mImageView.setOnClickListener(this);

        IInfoCallback iInfoCallback = new IInfoCallback() {
            @Override
            public void onUIChange(String UserName, String Password, String CssdAddr, String CentAddr, String CentPort) {
                mEdtUsername.setText(UserName);
                mEdtPassword.setText(Password);
                mEdtCCSAddr.setText(CssdAddr);
                mEdtCentAddr.setText(CentAddr);
                mEdtCentPort.setText(CentPort);

                Log.e(TAG, "UIChange");
            }

        };
        mCss1000dController.setIInfoCallback(iInfoCallback);
    }

    CSS1000DController mCss1000dController = new CSS1000DController(context);

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.imgview:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCss1000dController.Open();
        if (!BaseMgr.SESSIONID.equals(""))
        {
            mCss1000dController.getSpk(true);
        }
        logAdapter.notifyDataSetChanged();
        Log.e(TAG, "onResume");
        // CSS1000DController Start

    }

    @Override
    protected void onPause() {
        super.onPause();
        mCss1000dController.Close();
        noticeTrace.Close();
        // CSS1000DController Stop
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // CSS1000DController Close
        BaseMgr.LOGLIST.clear();
    }


    class TCPClient implements Runnable {

        @Override
        public void run() {
            boolean ret = noticeTrace.Open(BaseMgr.CENTADDR, Integer.parseInt(BaseMgr.CENTPORT));
            if (!ret) {
                Log.e("ZMG", "Connect with Server failed.");
            } else {
                isInited = true;
            }
        }
    }
}
