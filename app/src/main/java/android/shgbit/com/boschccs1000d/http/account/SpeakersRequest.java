package android.shgbit.com.boschccs1000d.http.account;

import android.content.Context;
import android.shgbit.com.boschccs1000d.base.BaseConst;
import android.shgbit.com.boschccs1000d.http.BaseRequest;
import android.util.Log;

import org.xutils.http.HttpMethod;

/**
 * Created by Gxk on 2016/12/21.
 */
public class SpeakersRequest extends BaseRequest {

    //get-isPolling为false（默认，长连接）
    public SpeakersRequest(Context context) {
        super(context, HttpMethod.GET, BaseConst.FUNC_SPKER,true);
    }
    //get-isPolling为true
    public SpeakersRequest(Context context, Boolean isPolling) {
        super(context, HttpMethod.GET, BaseConst.FUNC_SPKER,true);
        if (isPolling == true) {
            params.addParameter("isPolling", true);
        }
    }
    //post
    public SpeakersRequest(Context context,String spkSeats) {
        super(context, HttpMethod.POST, BaseConst.FUNC_SPKER,true);
        /*JSONArray entries = new JSONArray();
        entries.put(1);
        entries.put(2);
        spkSeats = entries.toString();*/
        params.addBodyParameter("",spkSeats);
        //都分别试一下，先用setAsJsonContent，不行换下面两行
        //params.setAsJsonContent(true);
        //params.setBodyContent(new String(spkSeats)或者spkSeats或者"[1,2]");


    }

}
