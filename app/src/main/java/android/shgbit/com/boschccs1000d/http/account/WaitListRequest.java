package android.shgbit.com.boschccs1000d.http.account;

import android.content.Context;
import android.shgbit.com.boschccs1000d.base.BaseConst;
import android.shgbit.com.boschccs1000d.http.BaseRequest;

import org.json.JSONArray;
import org.xutils.http.HttpMethod;

/**
 * Created by Gxk on 2016/12/22.
 */
public class WaitListRequest extends BaseRequest {

    //get
    public WaitListRequest(Context context) {
        super(context, HttpMethod.GET, BaseConst.FUNC_WAITLIST,true);
    }

    //get isPolling=true
    public WaitListRequest(Context context,Boolean isPolling) {
        super(context, HttpMethod.GET, BaseConst.FUNC_WAITLIST,true);
    }

    //post
    public WaitListRequest(Context context,String waitlistSbj) {
        super(context, HttpMethod.POST, BaseConst.FUNC_WAITLIST,true);
        params.addBodyParameter("",waitlistSbj);
    }

//    //delete
//    public WaitListRequest(Context context, ) {
//        super(context, HttpMethod.DELETE, BaseConst.FUNC_WAITLIST,true);
//    }
//
//    //delete seat id
//    public WaitListRequest(Context context,Integer seatidWait) {
//        super(context, HttpMethod.DELETE, BaseConst.FUNC_WAITLIST_SEATID + seatidWait, true);
//    }

}
