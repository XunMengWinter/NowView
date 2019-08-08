package top.wefor.nowview.utils;

import android.content.SharedPreferences;

import java.util.Date;

import top.wefor.nowview.App;
import top.wefor.nowview.BuildConfig;
import top.wefor.nowview.Constants;

/**
 * Created on 16/6/21 16:32.
 *
 * @author ice, GitHub: https://github.com/XunMengWinter
 */
public class PrefUtil {

    public static boolean isNeedRefresh(String key) {
        if (BuildConfig.DEBUG) {
            return true;
        }
        SharedPreferences sharedPreferences = App.getInstance()
                .getSharedPreferences(Constants.PREFS_NAME, 0);
        long refreshTime = sharedPreferences.getLong(key, 0);
        return (new Date().getTime() - refreshTime > Constants.VALUE_REFRESH_INTERVAL);
    }

    public static void setRefreshTime(String key, long time) {
        SharedPreferences sharedPreferences = App.getInstance()
                .getSharedPreferences(Constants.PREFS_NAME, 0);
        sharedPreferences.edit().putLong(key, time).apply();
    }

}
