package android.shgbit.com.boschccs1000d.request.account;

import android.content.Context;
import android.shgbit.com.boschccs1000d.base.BaseConst;
import android.shgbit.com.boschccs1000d.base.BaseRequest;

import org.xutils.http.HttpMethod;

/**
 * Created by user on 2016-12-10.
 */
public class LogoutRequest extends BaseRequest {
    public LogoutRequest(Context context) {
        super(context, HttpMethod.POST, BaseConst.FUNC_LOGOUT, true);
    }
}
