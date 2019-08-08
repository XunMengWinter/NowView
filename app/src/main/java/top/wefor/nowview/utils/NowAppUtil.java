package top.wefor.nowview.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import top.wefor.nowview.App;

/**
 * Created by ice on 16/4/13 01:14.
 */
public class NowAppUtil {

    public static Integer getWidth() {
        DisplayMetrics metrics = App.getInstance().getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static Integer getHeight() {
        DisplayMetrics metrics = App.getInstance().getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static boolean isWifiConnected() {
        ConnectivityManager connManager = (ConnectivityManager) App.getInstance()
                .getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isBelowLollipop() {
        return android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP;
    }

}
