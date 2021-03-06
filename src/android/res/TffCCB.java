package org.apache.cordova.tffccb;

import android.app.Activity;
import android.util.Log;

import com.ccb.ccbnetpay.message.CcbPayResultListener;
import com.ccb.ccbnetpay.platform.CcbPayPlatform;
import com.ccb.ccbnetpay.platform.Platform;
import com.ccb.ccbnetpay.util.CcbSdkLogUtil;
import com.ccb.ccbnetpay.util.IPUtil;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class TffCCB extends CordovaPlugin {
    private Activity activity;
    public static final String TAG = "建行支付";
    private CcbPayResultListener listener = null;
    private String pub;
    private String txcode;
    private String merchantid;
    private String posid;
    private String branchid;
    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Log.i("initialize","============================");
        activity = cordova.getActivity();
        pub = webView.getPreferences().getString("PUB", "");
        txcode = webView.getPreferences().getString("TXCODE", "");
        merchantid = webView.getPreferences().getString("MERCHANTID", "");
        posid = webView.getPreferences().getString("POSID", "");
        branchid = webView.getPreferences().getString("BRANCHID", "");
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false if not.
     */
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        String ip = IPUtil.getIPAddress();
        Url url = new Url();
        JSONObject arguments = args.getJSONObject(0);
        String price = arguments.getString("payment");
        String orderid = arguments.getString("orderid");
        String remark1 = arguments.getString("remark1");
        String installnum = arguments.getString("installnum");
        String params = url.make(pub, txcode, merchantid, posid, branchid, price, ip, orderid, installnum, remark1);
        CcbSdkLogUtil.d(params);
        listener = new CcbPayResultListener() {
            @Override
            public void onSuccess(Map<String, String> result) {
                Log.i(TAG, "支付成功 --" + result);
                for (Map.Entry entry : result.entrySet()) {
                    Log.i(TAG, "key --" + entry.getKey() + "  value --" + entry.getValue());
                }
                callbackContext.success(new JSONObject(result));
            }

            @Override
            public void onFailed(String msg) {
                Log.i(TAG, "支付失败 --" + msg);
                callbackContext.error(msg);
            }   
        };
        Platform ccbPayPlatform = new CcbPayPlatform
                .Builder()
                .setActivity(activity)
                .setListener(listener)
                .setParams(params).setPayStyle(Platform.PayStyle.APP_OR_H5_PAY).build();
        ccbPayPlatform.pay();
        return true;
    }
}
