package android.shgbit.com.boschccs1000d.activity;

import android.app.Activity;
import android.os.Bundle;
import android.shgbit.com.boschccs1000d.R;
import android.widget.Button;

/**
 * Created by Eric on 2017/2/23.
 */

public class TestActivity extends Activity{

    private Button mBtnLogin;
    private Button mBtnLogout;
    private Button mBtnSysInfo;
    private Button mBtnInit;
    private Button mBtnSendTrance;

    private Button mBtnSpkAvail;
    private Button mBtnSpkPost;
    private Button mBtnSpkGet;
    private Button mBtnSpkDel;
    private Button mBtnSpkDelById;
    private Button mBtnSeatsAvail;
    private Button mBtnSeatsPost;
    private Button mBtnSeatsGet;
    private Button mBtnWaitAvail;
    private Button mBtnWaitGet;
    private Button mBtnWaitPost;
    private Button mBtnWaitDel;
    private Button mBtnWaitDelById;







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }
    private void initView(){

    }
}
