package top.wefor.nowview;

import android.app.Application;

import top.wefor.nowview.utils.Toaster;

/**
 * Created on 2019-08-08.
 *
 * @author ice
 */
public class App extends Application {

    private static App sApp;

    private static Toaster sToaster;

    public static String sMonoToken = null;

    public static App getInstance() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        sToaster = new Toaster(this);
    }

    public static void showToast(String msg) {
        sToaster.showToast(msg);
    }

    public static void showToast(int resId) {
        showToast(App.getInstance().getString(resId));
    }

}
