package android.shgbit.com.boschccs1000d.http;

import android.content.Context;
import android.shgbit.com.boschccs1000d.R;
import android.shgbit.com.boschccs1000d.base.BaseConst;
import android.shgbit.com.boschccs1000d.base.BaseMgr;
import android.shgbit.com.boschccs1000d.utils.CommonUtils;
import android.shgbit.com.boschccs1000d.utils.ToastUtil;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2016-12-10.
 */
public class BaseRequest {
    private Context context;
    private boolean authorization;
    private String url = BaseMgr.CCSD_ADDR + BaseConst.API_URI;
    private String functionName;
    private HttpMethod method;

    public RequestParams params;

    public BaseRequest(Context context, HttpMethod method, String functionName) {
        this(context, method, functionName, true);
    }

    public BaseRequest(Context context, HttpMethod method, String functionName, boolean authorization) {
        this.context = context;
        this.method = method;
        this.functionName = functionName;
        this.authorization = authorization;
        params = new RequestParams(url + functionName);
        params.setUseCookie(true);
        params.setAsJsonContent(true);
        params.setMaxRetryCount(Integer.MAX_VALUE);
//        params.setConnectTimeout(Integer.MAX_VALUE);
    }

    public void httpSend(final IHttpCallback callBack) {
/*        if (!CommonUtils.isNetworkAvailable()) {
            String netException = context.getResources().getString(R.string.netException);
            ToastUtil.showToast(netException);
            callBack.onFailure(BaseConst.HTTP_NO_NETWORK);
            return;
        }*/

        if (authorization) {
            params.addHeader("Cookie", "sid=" + BaseMgr.SESSIONID);
        }

        doRequest(this.method, callBack);



    }

    private void doRequest(HttpMethod method, final IHttpCallback callBack) {
        x.http().request(method, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callBack.onSuccess(result);
                Log.e("GXK","doRequest-Success");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("GXK","doRequest-onError"+ex+isOnCallback);
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("info", ex.toString());
                BaseMgr.LOGLIST.add(map);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("GXK","doRequest-onCancelled"+cex);
            }

            @Override
            public void onFinished() {
                Log.e("GXK","doRequest-onFinished");
            }
        });
    }

}
