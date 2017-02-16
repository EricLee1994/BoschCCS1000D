package android.shgbit.com.boschccs1000d.request.waitlist;

import android.content.Context;
import android.shgbit.com.boschccs1000d.base.BaseConst;
import android.shgbit.com.boschccs1000d.base.BaseRequest;

import org.xutils.http.HttpMethod;

/**
 * Created by Eric on 2017/1/18.
 */

public class DeleteWaitRequest extends BaseRequest {

    public DeleteWaitRequest(Context context){
        super(context, HttpMethod.DELETE, BaseConst.FUNC_WAITLIST,true);
    }
    public DeleteWaitRequest(Context context,Integer seatidWait) {
        super(context, HttpMethod.DELETE, BaseConst.FUNC_WAITLIST_SEATID + seatidWait, true);
    }
}
