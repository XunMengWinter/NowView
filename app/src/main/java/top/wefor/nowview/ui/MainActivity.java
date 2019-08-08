package top.wefor.nowview.ui;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import top.wefor.nowview.R;

/**
 * Created on 2019-08-08.
 *
 * @author ice
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.drawer_container) FrameLayout mDrawerContainer;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.drawer_container, new DrawerFragment())
                .commitAllowingStateLoss();
    }

}
