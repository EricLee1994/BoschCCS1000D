package android.shgbit.com.boschccs1000d.http;

/**
 * Created by user on 2016-12-9.
 */
public interface IHttpCallback {
    public void onSuccess(String result);
    public void onFailure(String result);
}
