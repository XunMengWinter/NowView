package top.wefor.nowview.data.http;

import com.orhanobut.logger.Logger;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created on 2018/4/13.
 *
 * @author ice
 */
public abstract class BaseObserver<T> implements Observer<T>, LifecycleObserver {

    public static final String TAG = "BaseObserver";

    protected abstract void onSucceed(T result);

    private Lifecycle mLifecycle;
    private Disposable mDisposable;

    public BaseObserver() {

    }

    public BaseObserver(Lifecycle lifecycle) {
        mLifecycle = lifecycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDisPose() {
//        Logger.t(TAG).i("dispose");
        if (mLifecycle != null) {
            mLifecycle.removeObserver(this);
            mLifecycle = null;
        }
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    @Override
    public void onComplete() {
//        Logger.i(TAG, "onComplete");
        onDisPose();
    }

    @Override
    public void onError(Throwable e) {
        if (e == null) {
            onFailed(null);
            onDisPose();
            Logger.e("onError null");
            return;
        }
        e.printStackTrace();
        Logger.w("onError " + e.getMessage());
        onFailed(e.getMessage());
        onDisPose();
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        if (mLifecycle != null)
            mLifecycle.addObserver(this);
    }

    @Override
    public void onNext(T result) {
        if (result != null)
            onSucceed(result);
        else
            onFailed(null);
    }

    protected void onFailed(@Nullable String msg) {

    }

}
