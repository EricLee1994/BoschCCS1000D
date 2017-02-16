package android.shgbit.com.boschccs1000d.request.info;

import android.content.Context;
import android.shgbit.com.boschccs1000d.base.BaseConst;
import android.shgbit.com.boschccs1000d.base.BaseRequest;

import org.xutils.http.HttpMethod;

/**
 * Created by Gxk on 2016/12/21.
 */
public class SysInfoRequest extends BaseRequest {
    public SysInfoRequest(Context context) {
        super(context, HttpMethod.GET, BaseConst.FUNC_SYSINFO, true);
    }
}
