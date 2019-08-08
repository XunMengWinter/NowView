package top.wefor.nowview.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

/**
 * Created on 2019-08-08.
 *
 * @author ice
 */
public abstract class BaseFragment extends Fragment {

    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final int layoutId = getLayoutId();
        if (layoutId != 0) {
            View rootView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, rootView);
            return rootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

}
