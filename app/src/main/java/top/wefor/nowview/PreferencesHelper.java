package top.wefor.nowview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class PreferencesHelper {

    private volatile static PreferencesHelper sPreferencesHelper;

    public static PreferencesHelper get() {
        if (sPreferencesHelper == null) {
            synchronized (PreferencesHelper.class) {
                if (sPreferencesHelper == null) {
                    sPreferencesHelper = new PreferencesHelper(App.getInstance());
                }
            }
        }
        return sPreferencesHelper;
    }

    private final SharedPreferences mPreferences;

    private PreferencesHelper(Context context) {
        mPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPreferences.edit().clear().apply();
    }

    public boolean isFirst() {
        return mPreferences.getBoolean(Constants.IS_FIRST, true);
    }

    public void setFirst(boolean isFirst) {
        mPreferences.edit().putBoolean(Constants.IS_FIRST, isFirst).apply();
    }

    public boolean isJSEnabled() {
        return mPreferences.getBoolean(Constants.JAVA_SCRIPT_ENABLED, true);
    }

    public void setJSEnabled(boolean isJSEnabled) {
        mPreferences.edit().putBoolean(Constants.JAVA_SCRIPT_ENABLED, isJSEnabled).apply();
    }

    public int getHeadImageType() {
        return mPreferences.getInt(Constants.COVER_SOURCE, Constants.TYPE_MAC);
    }

    public void setHeadImageType(int headImageIndex) {
        mPreferences.edit().putInt(Constants.COVER_SOURCE, headImageIndex).apply();
    }

    public boolean isModuleSelected(@NonNull String name) {
        return mPreferences.getBoolean(name, true);
    }

    public void setModuleSelected(@NonNull String name, boolean isSelected) {
        mPreferences.edit().putBoolean(name, isSelected).apply();
    }

    public String getHeadImages() {
        return mPreferences.getString(Constants.HEAD_IMAGES, null);
    }

    @SuppressLint("ApplySharedPref")
    public void setHeadImages(String headImages) {
        mPreferences.edit().putString(Constants.HEAD_IMAGES, headImages).commit();
    }

    public String getCoverImage() {
        return mPreferences.getString(Constants.COVER_IMAGE, "");
    }

    public void setCoverImage(String imageUrl) {
        mPreferences.edit().putString(Constants.COVER_IMAGE, imageUrl).apply();
    }

    public void setNgImages(String images) {
        mPreferences.edit().putString(Constants.NG_IMAGES, images).apply();
    }

    public String getNgImages() {
        return mPreferences.getString(Constants.NG_IMAGES, "");
    }

    public void setLastGankBanner(String imageUrl) {
        mPreferences.edit().putString(Constants.LAST_GANK_BANNER, imageUrl).apply();
    }

    public String getLastGankBanner() {
        return mPreferences.getString(Constants.LAST_GANK_BANNER, "");
    }

}
