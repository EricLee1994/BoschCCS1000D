package android.shgbit.com.boschccs1000d.models;

import org.json.JSONObject;

/**
 * Created by user on 2016-12-12.
 */
public class SystemInfo {
    private String DeviceType;
    private String HostName;
    private JSONObject Networks;
    private String SystemType;
    private JSONObject Versions;

    public JSONObject getVersions() {
        return Versions;
    }

    public void setVersions(JSONObject versions) {
        Versions = versions;
    }

    public String getSystemType() {
        return SystemType;
    }

    public void setSystemType(String systemType) {
        SystemType = systemType;
    }

    public JSONObject getNetworks() {
        return Networks;
    }

    public void setNetworks(JSONObject networks) {
        Networks = networks;
    }

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String hostName) {
        HostName = hostName;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }
}
