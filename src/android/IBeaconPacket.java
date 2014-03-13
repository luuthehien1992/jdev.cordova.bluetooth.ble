/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdev.cordova.bluetooth.ble;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Jeremy
 */
public class IBeaconPacket {

    private int rssi;
    private String mac;
    private int txPower;
    private long unixTime;

    public IBeaconPacket(int rssi, String mac, int txPower, long unixTime) {
        this.rssi = rssi;
        this.mac = mac;
        this.txPower = txPower;
        this.unixTime = unixTime;
    }

    public IBeaconPacket() {
    }

    public long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getTxPower() {
        return txPower;
    }

    public void setTxPower(int txPower) {
        this.txPower = txPower;
    }

    public String toJSon() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mac", getMac());
        jsonObject.put("rssi", getRssi());
        jsonObject.put("txPower", getTxPower());
        jsonObject.put("unixTime", getUnixTime());
        return jsonObject.toString();
    }
}
