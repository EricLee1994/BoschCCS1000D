package android.shgbit.com.boschccs1000d.controllers;

import android.shgbit.com.boschccs1000d.base.BaseMgr;

import com.wa.util.WALog;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

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
    private static final String TAG = "TCPNoticeTrace";

    public boolean Open(String server, int port) {
        needReconnect = true;
        return Connect(server, port);
    }

    private boolean Connect(String server, int port) {
        WALog.i(TAG, "Connnect times");
        mTcpClient = new Socket();
        while (isServerClose(mTcpClient)) {
            try {
                WALog.i(TAG, "C: Server IP: " + server + ":" + port);
                InetAddress serverAddr = InetAddress.getByName(server);
                SocketAddress serverSocket = new InetSocketAddress(serverAddr, port);

                WALog.i(TAG, "C: Connecting...");
                mTcpClient.connect(serverSocket, 3000);

                isConnected = true;
                mDataStrm = new DataOutputStream(mTcpClient.getOutputStream());
            } catch (Exception e) {
                WALog.e(TAG, "Connect :" + e.toString());
                isConnected = false;
                Connect(BaseMgr.CENTADDR, Integer.parseInt(BaseMgr.CENTPORT));
            }
        }
        WALog.i(TAG, "isConnected:" + isConnected);
        return isConnected;
    }

    public Boolean isServerClose(Socket socket) {
        try {
            socket.sendUrgentData(0);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            WALog.i(TAG, "sendUrgentData");
            Thread.sleep(500);
            return false;
        } catch (Exception se) {
            WALog.e(TAG, "sendUrgentData: " + se.toString() );
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
        } catch (Exception e) {
            e.printStackTrace();
            WALog.e(TAG, "Notice:" + e.toString());
            isConnected = false;
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
                WALog.i(TAG, "TCPClient Close.");
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
