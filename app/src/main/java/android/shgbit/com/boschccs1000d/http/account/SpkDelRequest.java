package android.shgbit.com.boschccs1000d.http.account;

import android.content.Context;
import android.shgbit.com.boschccs1000d.base.BaseConst;
import android.shgbit.com.boschccs1000d.http.BaseRequest;

import org.xutils.http.HttpMethod;

/**
 * Created by Gxk on 2016/12/22.
 */
public class SpkDelRequest extends BaseRequest {

    //delete
    public SpkDelRequest(Context context) {
        super(context, HttpMethod.DELETE, BaseConst.FUNC_SPKER,true);
    }

    //delete seat id
    public SpkDelRequest(Context context,Integer seatidSpk) {
        super(context, HttpMethod.DELETE, BaseConst.FUNC_SPK_SEATID + seatidSpk,true);
    }
}
