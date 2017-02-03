package android.shgbit.com.boschccs1000d.http.account;

import android.content.Context;
import android.shgbit.com.boschccs1000d.base.BaseConst;
import android.shgbit.com.boschccs1000d.http.BaseRequest;

import org.xutils.http.HttpMethod;

/**
 * Created by Gxk on 2016/12/21.
 */
public class SpkAvailRequest extends BaseRequest {

    public SpkAvailRequest(Context context) {
        super(context, HttpMethod.GET, BaseConst.FUNC_SPKAVAIL);
    }

    public SpkAvailRequest(Context context, Boolean isPolling) {
        super(context, HttpMethod.GET, BaseConst.FUNC_SPKAVAIL);
        if (isPolling == true) {
            params.addParameter("isPolling", true);
        }
    }
}
