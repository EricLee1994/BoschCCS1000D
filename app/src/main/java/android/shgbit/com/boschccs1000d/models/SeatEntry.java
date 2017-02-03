package android.shgbit.com.boschccs1000d.models;

/**
 * Created by user on 2016-12-12.
 */
public class SeatEntry {
    private int batteryCharges;
    private String batterySerialNo;
    private int batteryStatus;
    private int cameraId;
    private int cameraPrepos;
    private boolean connected;
    private boolean dual;
    private int id;
    private boolean identification;
    private String name;
    private boolean prio;
    private int rangeTest;
    private boolean selected;
    private int signalStatus;
    private boolean voting;

    public boolean isVoting() {
        return voting;
    }

    public void setVoting(boolean voting) {
        this.voting = voting;
    }

    public int getSignalStatus() {
        return signalStatus;
    }

    public void setSignalStatus(int signalStatus) {
        this.signalStatus = signalStatus;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getRangeTest() {
        return rangeTest;
    }

    public void setRangeTest(int rangeTest) {
        this.rangeTest = rangeTest;
    }

    public boolean isPrio() {
        return prio;
    }

    public void setPrio(boolean prio) {
        this.prio = prio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIdentification() {
        return identification;
    }

    public void setIdentification(boolean identification) {
        this.identification = identification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDual() {
        return dual;
    }

    public void setDual(boolean dual) {
        this.dual = dual;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public int getCameraPrepos() {
        return cameraPrepos;
    }

    public void setCameraPrepos(int cameraPrepos) {
        this.cameraPrepos = cameraPrepos;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public int getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(int batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getBatterySerialNo() {
        return batterySerialNo;
    }

    public void setBatterySerialNo(String batterySerialNo) {
        this.batterySerialNo = batterySerialNo;
    }

    public int getBatteryCharges() {
        return batteryCharges;
    }

    public void setBatteryCharges(int batteryCharges) {
        this.batteryCharges = batteryCharges;
    }
}
