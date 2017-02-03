package android.shgbit.com.boschccs1000d.http.account;

import android.content.Context;
import android.shgbit.com.boschccs1000d.base.BaseConst;
import android.shgbit.com.boschccs1000d.http.BaseRequest;

import org.xutils.http.HttpMethod;

/**
 * Created by Gxk on 2016/12/22.
 */
public class  WaitListAvailRequest extends BaseRequest {

    //available
    public WaitListAvailRequest(Context context) {
        super(context, HttpMethod.GET, BaseConst.FUNC_WAITAVAIL,true);
    }

    //available isPolling=true
    public WaitListAvailRequest(Context context,Boolean isPolling) {
        super(context, HttpMethod.GET, BaseConst.FUNC_WAITAVAIL,true);
    }
}
