package android.shgbit.com.boschccs1000d.http.account;

import android.content.Context;
import android.shgbit.com.boschccs1000d.base.BaseConst;
import android.shgbit.com.boschccs1000d.http.BaseRequest;

import org.xutils.http.HttpMethod;

/**
 * Created by user on 2016-12-10.
 */
public class LoginRequest extends BaseRequest {
    Boolean a = true;
    public LoginRequest(Context context, String userName, String password) {
        super(context, HttpMethod.POST, BaseConst.FUNC_LOGIN, false);
        params.addParameter("override", a);
        params.addBodyParameter("username", userName);
        params.addBodyParameter("password", password);
    }
}
