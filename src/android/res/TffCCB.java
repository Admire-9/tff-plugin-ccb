package org.apache.cordova.tffccb;

import android.util.Log;

import com.ccb.ccbnetpay.CcbMorePay;
import com.ccb.ccbnetpay.message.CcbPayResultListener;
import com.ccb.ccbnetpay.platform.CcbPayPlatform;
import com.ccb.ccbnetpay.platform.Platform;
import com.ccb.ccbnetpay.util.CcbSdkLogUtil;
import com.ccb.ccbnetpay.util.IPUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class TffCCB extends CordovaPlugin {
    public static final String TAG = "建行支付";
    private String price = null;
    private BusInfoEntity busInfo = null;
    private IntentFilter filter = null;

    private CcbPayResultListener listener = null;
    private static String Ip = "";

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false if not.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Ip = IPUtil.getIPAddress();
        String params = Util.make(busInfo, price, Ip);
        listener = new CcbPayResultListener() {
            @Override
            public void onSuccess(Map<String, String> result) {
                Log.d(TAG, "支付成功 --" + result);
                for (Map.Entry entry : result.entrySet()) {
                    Log.d(TAG, "key --" + entry.getKey() + "  value --" + entry.getValue());
                }
                callbackContext.success(1);
            }

            @Override
            public void onFailed(String msg) {
                Log.d(TAG, "支付失败 --" + msg);
                callbackContext.error(0);
            }   
        };
        CcbSdkLogUtil.d(params);
        Platform ccbPayPlatform = new CcbPayPlatform
                .Builder()
                .setListener(listener)
                .setParams(params).setPayStyle(Platform.PayStyle.APP_OR_H5_PAY).build();
        ccbPayPlatform.pay();
        return true;
    }
}
