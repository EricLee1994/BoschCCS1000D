package android.shgbit.com.boschccs1000d.controllers;

import android.content.Context;
import android.shgbit.com.boschccs1000d.http.IHttpCallback;
import android.shgbit.com.boschccs1000d.http.account.SeatsRequest;
import android.shgbit.com.boschccs1000d.http.account.SpeakersRequest;
import android.shgbit.com.boschccs1000d.http.account.SpkAvailRequest;
import android.shgbit.com.boschccs1000d.http.account.WaitListAvailRequest;
import android.shgbit.com.boschccs1000d.http.account.WaitListRequest;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 2016-12-12.
 */
public class GetMicStatus {

    Context context;

    private static final int DEFAULT_TIMEOUT = 1;  // 1s
    private static final int TIMEOUT_COUNT = 3;		// 3 times

    private int mTimeout;

    private Timer mTimer = null;
    private TimerTask mTimerTask = null;

    public GetMicStatus() {
        this.mTimeout = DEFAULT_TIMEOUT * 500;
    }

    public void doQuerySeats() {
        if (mTimer == null)
            mTimer = new Timer();

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {

                @Override
                public void run() {
                    // Do Request
                    SeatsRequest mSeatsRequest = new SeatsRequest(context);
                    mSeatsRequest.httpSend(new IHttpCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("GXK","seats"+result);
                        }

                        @Override
                        public void onFailure(String result) {
                            Log.e("GXK","seats"+result);
                        }
                    });

                }

            };
        }

        if (mTimer != null && mTimerTask != null) {
            mTimer.schedule(mTimerTask, 0, mTimeout);
        }
    }

    public void doQueryAvailSpk() {
        if (mTimer == null)
            mTimer = new Timer();

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {

                @Override
                public void run() {
                    // Do Request
                    SpkAvailRequest mSpkAvailRequest = new SpkAvailRequest(context);
                    //

                    mSpkAvailRequest.httpSend(new IHttpCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("GXK","AvailSpk"+result);
                        }

                        @Override
                        public void onFailure(String result) {
                            Log.e("GXK","AvailSpk"+result);
                        }
                    });

                }

            };
        }

        if (mTimer != null && mTimerTask != null) {
            mTimer.schedule(mTimerTask, 0, mTimeout);
        }
    }

    public void doQuerySpkGet() {
        if (mTimer == null)
            mTimer = new Timer();

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {

                @Override
                public void run() {
                    // Do Request
                    SpeakersRequest mSpkRequest = new SpeakersRequest(context,true);
                    //

                    mSpkRequest.httpSend(new IHttpCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("GXK","SpkGetRequest"+result);
                        }

                        @Override
                        public void onFailure(String result) {
                            Log.e("GXK","SpkGetRequest"+result);
                        }
                    });

                }

            };
        }

        if (mTimer != null && mTimerTask != null) {
            mTimer.schedule(mTimerTask, 0, mTimeout);
        }
    }

    public void doQueryAvailWait() {
        if (mTimer == null)
            mTimer = new Timer();

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {

                @Override
                public void run() {
                    // Do Request
                    WaitListAvailRequest mWaitListAvailRequest = new WaitListAvailRequest(context);
                    //

                    mWaitListAvailRequest.httpSend(new IHttpCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("GXK","AvailWait"+result);
                        }

                        @Override
                        public void onFailure(String result) {
                            Log.e("GXK","AvailWait"+result);
                        }
                    });

                }

            };
        }

        if (mTimer != null && mTimerTask != null) {
            mTimer.schedule(mTimerTask, 0, mTimeout);
        }
    }

    public void doQueryWaitGet() {
        if (mTimer == null)
            mTimer = new Timer();

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {

                @Override
                public void run() {
                    // Do Request
                    WaitListRequest mWaitListRequest = new WaitListRequest(context);
                    //

                    mWaitListRequest.httpSend(new IHttpCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("GXK","WaitGetRequest"+result);
                        }

                        @Override
                        public void onFailure(String result) {
                            Log.e("GXK","WaitGetRequest"+result);
                        }
                    });

                }

            };
        }

        if (mTimer != null && mTimerTask != null) {
            mTimer.schedule(mTimerTask, 0, mTimeout);
        }
    }

    public void Stop() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }
}
