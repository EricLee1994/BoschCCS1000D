package android.shgbit.com.boschccs1000d.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.shgbit.com.boschccs1000d.R;
import android.shgbit.com.boschccs1000d.controllers.CSS1000DController;
import android.view.View;
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

    private Button mBtnLSpkAvail;
    private Button mBtnSSpkAvail;
    private Button mBtnSpkPost;
    private Button mBtnSSpkGet;
    private Button mBtnLSpkGet;
    private Button mBtnSpkDel;
    private Button mBtnSpkDelById;

    private Button mBtnLSeats;
    private Button mBtnSSeats;

    private Button mBtnLWaitAvail;
    private Button mBtnSWaitAvail;
    private Button mBtnLWaitGet;
    private Button mBtnSWaitGet;
    private Button mBtnWaitPost;
    private Button mBtnWaitDel;
    private Button mBtnWaitDelById;

    CSS1000DController css1000DController = null;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        css1000DController = new CSS1000DController(getApplicationContext());
        initView();
    }
    private void initView(){

        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mBtnLogout = (Button) findViewById(R.id.btnLogout);
        mBtnSysInfo = (Button) findViewById(R.id.btnSysinfo);
        mBtnInit = (Button) findViewById(R.id.btnInit);
        mBtnSendTrance = (Button) findViewById(R.id.btnSendTCP);
        //spk
        mBtnSSpkAvail = (Button) findViewById(R.id.btnSSpkAvail);
        mBtnLSpkAvail = (Button) findViewById(R.id.btnLSpkAvail);
        mBtnSpkPost = (Button) findViewById(R.id.btnSpksPost);
        mBtnSSpkGet = (Button) findViewById(R.id.btnSSpksGet);
        mBtnLSpkGet = (Button) findViewById(R.id.btnLSpkGet);
        mBtnSpkDel = (Button) findViewById(R.id.btnSpksDel);
        mBtnSpkDelById = (Button) findViewById(R.id.btnSpksDelID);
        //seats
        mBtnLSeats = (Button) findViewById(R.id.btnLongSeats);
        mBtnSSeats = (Button) findViewById(R.id.btnShortSeats);
        //wait
        mBtnSWaitAvail = (Button) findViewById(R.id.btnSWaitAvail);
        mBtnLWaitAvail = (Button) findViewById(R.id.btnLWaitAvail);
        mBtnSWaitGet = (Button) findViewById(R.id.btnSWaitGet);
        mBtnLWaitGet = (Button) findViewById(R.id.btnLWaitGet);
        mBtnWaitPost = (Button) findViewById(R.id.btnWaitPost);
        mBtnWaitDel = (Button) findViewById(R.id.btnWaitDel);
        mBtnWaitDelById = (Button) findViewById(R.id.btnWaitDelID);

        mBtnLogin.setOnClickListener(onClickListener);
        mBtnLogout.setOnClickListener(onClickListener);
        mBtnSysInfo.setOnClickListener(onClickListener);
        mBtnLSpkAvail.setOnClickListener(onClickListener);
        mBtnSSpkAvail.setOnClickListener(onClickListener);
        mBtnSpkPost.setOnClickListener(onClickListener);
        mBtnSSpkGet.setOnClickListener(onClickListener);
        mBtnLSpkGet.setOnClickListener(onClickListener);
        mBtnSpkDel.setOnClickListener(onClickListener);
        mBtnSpkDelById.setOnClickListener(onClickListener);
        mBtnLSeats.setOnClickListener(onClickListener);
        mBtnSSeats.setOnClickListener(onClickListener);
        mBtnLWaitAvail.setOnClickListener(onClickListener);
        mBtnSWaitAvail.setOnClickListener(onClickListener);
        mBtnLWaitGet.setOnClickListener(onClickListener);
        mBtnSWaitGet.setOnClickListener(onClickListener);
        mBtnWaitPost.setOnClickListener(onClickListener);
        mBtnWaitDel.setOnClickListener(onClickListener);
        mBtnWaitDelById.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnLogin:
                    css1000DController.Open();
                    break;

                case R.id.btnLogout:
                    css1000DController.Close();
                    break;

                case R.id.btnSysinfo:
                    css1000DController.getSysInfo();
                    break;

                case R.id.btnSSpkAvail:
                    css1000DController.getSpkAvail(true);
                    break;

                case R.id.btnLSpkAvail:
                    css1000DController.getSpkAvail(false);
                    break;

                case R.id.btnSSpksGet:
                    css1000DController.getSpk(true);
                    break;

                case R.id.btnLSpkGet:
                    css1000DController.getSpk(false);
                    break;

                case R.id.btnSpksPost:
                    css1000DController.spkPost();

                case R.id.btnSpksDel:
                    css1000DController.deleteAllSpk();
                    break;

                case R.id.btnSpksDelID:
                    css1000DController.deleteBySpkID();
                    break;

                case R.id.btnSWaitAvail:
                    css1000DController.getWaitAvail(true);
                    break;

                case R.id.btnLWaitAvail:
                    css1000DController.getWaitAvail(false);
                    break;

                case R.id.btnSWaitGet:
                    css1000DController.getWait(true);
                    break;

                case R.id.btnLWaitGet:
                    css1000DController.getWait(false);
                    break;

                case R.id.btnWaitPost:
                    css1000DController.waitPost();
                    break;

                case R.id.btnWaitDel:
                    css1000DController.deleteAllWait();
                    break;

                case R.id.btnWaitDelID:
                    css1000DController.deleteByWaitId();
                    break;

//                case R.id.btnInit:
//                    new Thread(new TCPClient()).start();
//                    break;
//
//                case R.id.btnSendTCP:
//                    int rand = Math.abs(new Random().nextInt() % 999);
//                    String msg = String.format("M%03d", rand);
//                    noticeTrace.Notice(msg);

            }

        }
    };
}
