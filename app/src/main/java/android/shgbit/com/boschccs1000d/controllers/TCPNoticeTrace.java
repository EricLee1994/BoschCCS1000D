package android.shgbit.com.boschccs1000d.controllers;

import android.shgbit.com.boschccs1000d.base.BaseMgr;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Created by user on 2016-12-13.
 */
public class TCPNoticeTrace {
    private static TCPNoticeTrace ourInstance = new TCPNoticeTrace();

    public static TCPNoticeTrace getInstance() {
        return ourInstance;
    }

    private TCPNoticeTrace() {
    }

    private boolean needReconnect = true;
    private boolean isConnected;
    private Socket mTcpClient;
    private DataOutputStream mDataStrm;
    private String TAG = "TCPNoticeTrace";

    public boolean Open(String server, int port) {
        needReconnect = true;
        return Connect(server, port);
    }

    private boolean Connect(String server, int port) {

        mTcpClient = new Socket();
        while (isServerClose(mTcpClient)) {
            try {
                Log.e(TAG, "C: Server IP: " + server + ":" + port);
                InetAddress serverAddr = InetAddress.getByName(server);
                SocketAddress serverSocket = new InetSocketAddress(serverAddr, port);

                Log.e(TAG, "C: Connecting...");
                mTcpClient.connect(serverSocket, 3000);

                isConnected = true;
                mDataStrm = new DataOutputStream(mTcpClient.getOutputStream());
//            while (true) {
//                mTcpClient.sendUrgentData(0xFF);
//                Thread.sleep(3*1000);
//            }

            } catch (UnknownHostException e) {
                e.printStackTrace();
                Log.e(TAG, "open error");
                isConnected = false;
//            Connect(server, port);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "open error1");
                isConnected = false;
                Connect(server, port);
            } catch (Exception e) {
                Log.e(TAG, "open error2");
                isConnected = false;
            }
        }
        return isConnected;
    }

    public Boolean isServerClose(Socket socket){
        try{
            socket.sendUrgentData(0);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        }catch(Exception se){
            isConnected = false;
            return true;
        }
    }

    public boolean Notice(String msg) {
        boolean isSuccess = false;

        if (!isConnected) {
            // 未与中控设备取得链接，发送数据被忽略；

            return isSuccess;
        }

        try {
            mDataStrm.writeBytes(new String(msg.getBytes("UTF-8"), "iso-8859-1"));

            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "pipe");
            Connect(BaseMgr.CENTADDR, Integer.parseInt(BaseMgr.CENTPORT));
        }

        return isSuccess;
    }

    public void Close() {
        try {
            if (mTcpClient != null && mDataStrm != null) {
                isConnected = false;
                mTcpClient.close();
                mDataStrm.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isNeedReconnect() {
        return needReconnect;
    }

    public void setNeedReconnect(boolean needReconnect) {
        this.needReconnect = needReconnect;
    }
}
