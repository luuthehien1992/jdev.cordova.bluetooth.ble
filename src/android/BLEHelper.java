/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdev.cordova.bluetooth.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import java.util.UUID;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author Jeremy
 */
public class BLEHelper extends CordovaPlugin {

    BluetoothAdapter mBluetoothAdapter = null;
    Context context = null;
    BluetoothManager bluetoothManager = null;
    Activity activity = null;
    Gson gson = null;

    public BLEHelper() {
    }

    private void init() {
        activity = cordova.getActivity();
        context = activity.getApplicationContext();
        bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        gson = new Gson();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        init();

        Action emAction = Action.fromString(action);
        switch (emAction) {
            case ENABLE:
                this.enable(callbackContext);
                return true;

            case IS_ENABLE:
                this.isEnable(callbackContext);
                return true;

            case IBEACON_DISCOVER:
                this.ibeaconDiscover(callbackContext);
                return true;

            case IBEACON_END_DISCOVER:
                this.ibeaconEndDiscover(callbackContext);
                return true;

            case IBEACON_DISCOVER_FILTER:
                this.ibeaconDiscoverFilter(null, callbackContext); //None Use
                return true;

            case IS_DISCOVERING:
                this.isDiscovering(callbackContext);
                return true;

            case GET_IBEACON_PACKET:
                this.getIBeaconPacket(callbackContext);
                return true;

            default:
                return false;
        }
    }

    private void isEnable(CallbackContext callbackContext) {
        callbackContext.success(gson.toJson(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()));
    }

    private void enable(CallbackContext callbackContext) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, 0);
    }

    private void isDiscovering(CallbackContext callbackContext) {
        callbackContext.success(gson.toJson(mBluetoothAdapter.isDiscovering()));
    }

    private void ibeaconDiscover(CallbackContext callbackContext) {
        if (mBluetoothAdapter.startLeScan(ibeaconDiscoverCallback)) {
            callbackContext.success();
        } else {
            callbackContext.error(0);
        }
    }

    private void ibeaconEndDiscover(CallbackContext callbackContext) {
        mBluetoothAdapter.stopLeScan(ibeaconDiscoverCallback);

        callbackContext.success();
    }

    private void ibeaconDiscoverFilter(UUID[] uuis, CallbackContext callbackContext) {
        if (mBluetoothAdapter.startLeScan(uuis, ibeaconDiscoverCallback)) {
            callbackContext.success();
        } else {
            callbackContext.error(0);
        }
    }

    private static IBeaconPacket iBeaconPacket = null;
    private final LeScanCallback ibeaconDiscoverCallback = new BluetoothAdapter.LeScanCallback() {

        public void onLeScan(final BluetoothDevice device, final int rssi,
                final byte[] scanRecord) {

            if (isIBeaconPacket(scanRecord)) {
                long timestamp = System.currentTimeMillis() / 1000;
                iBeaconPacket = new IBeaconPacket(rssi, device.getAddress(), scanRecord[26], timestamp);
            }
        }
    };

    private void getIBeaconPacket(CallbackContext callbackContext) throws JSONException {
        if (iBeaconPacket != null) {
            callbackContext.success(gson.toJson(iBeaconPacket));
        }
        callbackContext.error(0);
    }

	private boolean isIBeaconPacket(byte[] scanRecord) {
        try {
            if (scanRecord[2] == (byte) 0x4C && scanRecord[3] == (byte) 0x00 && scanRecord[4] == (byte) 0x02 && scanRecord[5] == (byte) 0x15) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    private enum Action {

        IS_ENABLE("isEnable"),
        IS_DISCOVERING("isDiscovery"),
        IBEACON_DISCOVER("ibeaconDiscover"),
        IBEACON_END_DISCOVER("ibeaconEndDiscover"),
        IBEACON_DISCOVER_FILTER("ibeaconDiscoverFiler"),
        ENABLE("enable"),
        GET_IBEACON_PACKET("getIBeaconPacket");

        private final String statusCode;

        private Action(String s) {
            statusCode = s;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public static Action fromString(String action) {
            if (action != null) {
                for (Action emAction : Action.values()) {
                    if (action.equalsIgnoreCase(emAction.statusCode)) {
                        return emAction;
                    }
                }
            }
            return null;
        }
    }
}
