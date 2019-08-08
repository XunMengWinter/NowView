package top.wefor.nowview;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created on 2019-08-08.
 *
 * @author ice
 */
public class Constants {

    public static final String PREFS_NAME = "now";
    public static final String MY_EMAIL_GOOGLE = "XunMengWinter@gmail.com";
    public static final String JAVA_SCRIPT_ENABLED = "isJavaScriptEnabled";
    public static final String IS_FIRST = "isFirst";

    public static final String COVER_IMAGE = "coverImage";
    public static final String HEAD_IMAGES = "headImages";
    public static final String NG_IMAGES = "ngImages";
    public static final String DIR = "NowView";
    public static final String IMAGE_DIR = DIR + File.separator + "image";
    public static final String COVER_IMAGE_PATH = IMAGE_DIR + File.separator + "cover.jpg";
    public static final String WEB_CACHE_DIR = "webDetail";
    public static final String REALM_PRIMARY_KEY = "pk";
    public static final String LAST_GANK_BANNER = "last_gank_banner";


    public static final String COVER_SOURCE = "cover_source";
    public static final int TYPE_NG = 0;
    public static final int TYPE_GANK_MEIZHI = 1;
    public static final int TYPE_MAC = 2;
    public static final int TYPE_COLOR = 3;

    public static final int PAGE_COUNT = 7;
    public static final int PROFILE_SETTING = 1;

    // WeChat needs only sign, QQ don't need
    public static final String QQ_APP_ID = "1104867379";
    public static final String WECHAT_APP_ID = "wx8a7d12758ddd5649";
    public static final String WECHAT_APP_SECRET = "d4624c36b6795d1d99dcf0547af5443d";

    public static final String DRIBBBLE_CLIENT_ID = "78d32486880ef7f09a8779fc2c982fa180a27719f6ae5d5f3666b255a5d0c68f";
    public static final String DRIBBBLE_CLIENT_SECRET = "9ea833b72a9a2d6a5d15cb2ce8d5789c1933e22d7242226ecc3740db6e1a7e22";


    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);


    // requestCode
    public static final int WELCOME_ACTIVITY = 11;

    public static final int LIST_PAGE_SIZE = 12;
    public static final int LIST_FIRST_PAGE = 1;

    public static final String KEY_REFRESH_TIME_ZCOOL = "refreshTimeZcool";
    public static final String KEY_REFRESH_TIME_NG = "refreshTimeNg";
    public static final String KEY_REFRESH_TIME_ZHIHU = "refreshTimeZhihu";
    public static final String KEY_REFRESH_TIME_MOMENT = "refreshTimeMoment";
    public static final String KEY_REFRESH_TIME_MONO = "refreshTimeMono";

    public static final String KEY_IMAGES_STR = "images_str";

    public static final long VALUE_REFRESH_INTERVAL = 1000 * 60 * 10;
    public static final long VALUE_FINISH_DELAYED_TIME = 1000 * 5;


    /*将NowItem保存至另一个应用My Table 时需要用到的字段*/
    public static final class MyTableNote {
        //        public static final String UUID = "uuid";
        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        //        public static final String ADD_DATE = "add_date";
        //        public static final String LAST_EDIT_DATE = "last_edit_date";
        public static final String FROM = "from";
        //        public static final String DONE = "done";
        public static final String TYPE = "type";

        public static final String RESULT_TYPE = "result_type";
        public static final String RESULT_MESSAGE = "result_message";
        public static final int RESULT_TYPE_SUCCESS = 1;

        public static final int TYPE_NOW = 7;

        public static final String ACTION_NAME = "top.wefor.out_source_add";
        public static final String PACKAGE_NAME = "top.wefor.mytable";
    }


}
