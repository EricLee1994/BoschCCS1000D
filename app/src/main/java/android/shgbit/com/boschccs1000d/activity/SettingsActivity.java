package android.shgbit.com.boschccs1000d.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.shgbit.com.boschccs1000d.R;
import android.shgbit.com.boschccs1000d.base.BaseApp;
import android.shgbit.com.boschccs1000d.base.BaseMgr;
import android.shgbit.com.boschccs1000d.models.User;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private EditText mEdtCentAddr;
    private EditText mEdtCentPort;
    private EditText mEdtCCSAddr;
    private Button mBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();

    }
    public void initView(){
        mEdtCentAddr = (EditText) findViewById(R.id.edtCent);
        mEdtCentPort = (EditText) findViewById(R.id.edtCentPort);
        mEdtCCSAddr = (EditText) findViewById(R.id.edtCCSAddr);
        mEdtUsername = (EditText) findViewById(R.id.etUsername);
        mEdtPassword = (EditText) findViewById(R.id.etPassword);
        mBtnSave = (Button) findViewById(R.id.btnSave);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String USERNAME = mEdtUsername.getText().toString();
                String PASSWORD = mEdtPassword.getText().toString();
                String CCSDADDR = mEdtCCSAddr.getText().toString();
                String CENTADDR = mEdtCentAddr.getText().toString();
                String CENTPORT = mEdtCentPort.getText().toString();
                SharedPreferences config = BaseApp.appContext.getSharedPreferences("config", MODE_PRIVATE);
                SharedPreferences.Editor  editor = config.edit();
                editor.putString("username", USERNAME);
                editor.putString("password", PASSWORD);
                editor.putString("centaddr", CENTADDR);
                editor.putString("centport", CENTPORT);
                editor.putString("cssaddr", CCSDADDR);
                editor.commit();
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mEdtUsername.setText(User.USERNAME);
        mEdtPassword.setText(User.PASSWORD);
        mEdtCCSAddr.setText(BaseMgr.CCSD_ADDR);
        mEdtCentAddr.setText(BaseMgr.CENTADDR);
        mEdtCentPort.setText(BaseMgr.CENTPORT);
    }
}
