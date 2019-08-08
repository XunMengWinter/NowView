package top.wefor.nowview.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import top.wefor.nowview.App;
import top.wefor.nowview.Constants;
import top.wefor.nowview.PreferencesHelper;
import top.wefor.nowview.R;
import top.wefor.nowview.data.Urls;
import top.wefor.nowview.utils.ImageUtil;
import top.wefor.nowview.utils.NowAppUtil;

/*
 * Created by ice on 15/10/26.
 *
 * Thanks drakeet
 */

public class WebActivity extends BaseActivity implements View.OnTouchListener {

    public static final String EXTRA_URL = "extra_url";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_PIC_URL = "extra_pic_url";
    public static final String EXTRA_SUMMARY = "extra_summary";

    @BindView(R.id.webView) WebView mWebView;
    @BindView(R.id.progressbar) ProgressBar mProgressbar;
    @BindView(R.id.wechat_textView) TextView mWechatTextView;
    @BindView(R.id.wechatcircle_textView) TextView mWechatcircleTextView;
    @BindView(R.id.qq_textView) TextView mQqTextView;
    @BindView(R.id.qzone_textView) TextView mQzoneTextView;
    @BindView(R.id.other_textView) TextView mOtherTextView;
    @BindView(R.id.cardView) CardView mCardView;
    @BindView(R.id.fab) FloatingActionButton mFab;
    @BindView(R.id.loading_view) AVLoadingIndicatorView mLoadingView;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout mCoordinatorLayout;

    public static void startThis(Context context, String url, String title, String picUrl, String summary) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WebActivity.EXTRA_TITLE, title);
        intent.putExtra(WebActivity.EXTRA_URL, url);
        intent.putExtra(WebActivity.EXTRA_PIC_URL, picUrl);
        intent.putExtra(WebActivity.EXTRA_SUMMARY, summary);
        context.startActivity(intent);
    }


    Context mContext;
    String mUrl, mTitle;
    private Map<String, String> mUrlMap;

    private String picUrl, summary;
    private Bitmap bitmap;

    private boolean isMenuShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        mContext = this;

        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        picUrl = getIntent().getStringExtra(EXTRA_PIC_URL);
        summary = getIntent().getStringExtra(EXTRA_SUMMARY);

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Drawable drawable = Glide.with(WebActivity.this).load(picUrl).submit(120, 120).get();
                    bitmap = ImageUtil.drawableToBitmap(drawable);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new ViewClient());

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(PreferencesHelper.get().isJSEnabled());
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(Constants.WEB_CACHE_DIR);

        if (NowAppUtil.isWifiConnected())
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        else
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        if (mUrl != null && mUrl.contains(Urls.MONO)
                && App.sMonoToken != null) {
            mUrlMap = new HashMap<>();
            mUrlMap.put("HTTP-AUTHORIZATION", App.sMonoToken);
        }
        if (mUrlMap != null)
            mWebView.loadUrl(mUrl, mUrlMap);
        else
            mWebView.loadUrl(mUrl);

        mWebView.setOnTouchListener(this);
        setTitle(mTitle);
        mFab.hide();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    @Override
    protected void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.i("xyz", newProgress + " progress");
            mProgressbar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressbar.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
            } else {
                mProgressbar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }

    private class ViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (url != null) view.loadUrl(url);
//            return true;
//        }
    }

    private int direction = 0;   //0     1左右   2上下
    private int oldY, oldX, maxY, miniY, lastX, lastY;
    private boolean isShowFab;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v instanceof WebView) {
            WebView.HitTestResult hr = ((WebView) v).getHitTestResult();
            Log.i("xyz ", "getExtra = " + hr.getExtra() + "\t\t Type=" + hr.getType());
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = (int) event.getRawX();
                oldY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                int dx = (int) event.getRawX() - oldX;
                int dy = (int) event.getRawY() - oldY;

                direction = (dx * dx > dy * dy) ? 1 : 2;
                break;
            case MotionEvent.ACTION_UP:
                if (direction == 2) {
                    if ((event.getRawY() - oldY > 72) && !isShowFab) {
                        mFab.show();
                        isShowFab = true;
                    } else if (isShowFab && oldY - event.getRawY() > 72) {
                        mFab.hide();
                        isShowFab = false;
                        if (isMenuShow) {
                            mCardView.setVisibility(View.GONE);
                            isMenuShow = false;
                        }
                    }

                }
        }

        return false;
    }

}
