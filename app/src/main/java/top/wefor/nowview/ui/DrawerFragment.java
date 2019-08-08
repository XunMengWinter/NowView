package top.wefor.nowview.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import top.wefor.nowview.BuildConfig;
import top.wefor.nowview.Constants;
import top.wefor.nowview.PreferencesHelper;
import top.wefor.nowview.R;
import top.wefor.nowview.data.Urls;

/**
 * Created on 2019-08-08.
 *
 * @author ice
 */
public class DrawerFragment extends BaseFragment {

    @BindView(R.id.headPicture_linearLayout) LinearLayout mHeadPictureLinearLayout;
    @BindView(R.id.suggest_linearLayout) LinearLayout mSuggestLinearLayout;
    @BindView(R.id.search_view) TextView mSearchView;
    @BindView(R.id.other_rootView) LinearLayout mOtherRootView;
    @BindView(R.id.js_tv) TextView mJsTv;
    @BindView(R.id.js_cb) CheckBox mJsCb;
    @BindView(R.id.wiki_ib) ImageButton mWikiIb;
    @BindView(R.id.gank_tv) TextView mGankTv;
    @BindView(R.id.columnSelect_tv) TextView mColumnSelectTv;
    @BindView(R.id.headPicture_tv) TextView mHeadPictureTv;
    @BindView(R.id.about_tv) TextView mAboutTv;
    @BindView(R.id.thanks_tv) TextView mThanksTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_other;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDrawer();
    }

    private void initDrawer() {
        //视图默认为打开  default is checked in view
        if (PreferencesHelper.get().isJSEnabled()) {
            mJsCb.setChecked(true);
            mJsTv.setText(R.string.js_close_description);
        }

        mHeadPictureTv.setText(getResources().getStringArray(
                R.array.head_picture_source)[PreferencesHelper.get().getHeadImageType()]);

        mWikiIb.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra(WebActivity.EXTRA_TITLE, getString(R.string.wiki_title));
            intent.putExtra(WebActivity.EXTRA_URL, getString(R.string.wiki_url));
            startActivity(intent);
        });

        mJsCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                mJsTv.setText(R.string.js_close_description);
            else
                mJsTv.setText(R.string.js_open_description);
            PreferencesHelper.get().setJSEnabled(mJsCb.isEnabled());
        });

        mColumnSelectTv.setOnClickListener(v -> {
            View view = getLayoutInflater().inflate(R.layout.dialog_column_select, null);
            LinearLayout linearLayout = view.findViewById(R.id.linearLayout);
            linearLayout.addView(getCheckBox(getString(R.string.zcool)));
            linearLayout.addView(getCheckBox(getString(R.string.ng)));
            linearLayout.addView(getCheckBox(getString(R.string.mono)));
            linearLayout.addView(getCheckBox(getString(R.string.zhihu)));
            linearLayout.addView(getCheckBox(getString(R.string.moment)));

            new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.column_select))
                    .setView(view)
                    .create().show();
        });

        mHeadPictureLinearLayout.setOnClickListener(v -> {
            View view = getLayoutInflater().inflate(R.layout.dialog_head_picture, null);
            RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
            radioGroup.check(radioGroup.getChildAt(PreferencesHelper.get().getHeadImageType()).getId());
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);
                PreferencesHelper.get().setHeadImageType(index);
                mHeadPictureTv.setText(radioButton.getText());
                setHeadImages(index);
                setFinishNow();

            });

            new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.head_picture))
                    .setView(view)
                    .create().show();
        });

        mAboutTv.setOnClickListener(v -> {
            View view = getLayoutInflater().inflate(R.layout.dialog_about, null);
            TextView textView = view.findViewById(R.id.version_tv);
            textView.setHint(getString(R.string.about_version, "", BuildConfig.APK_DATE));
            new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.about))
                    .setView(view)
                    .setPositiveButton("wefor.top", (dialogInterface, i) -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(Urls.MY_WEBSITE));
                        startActivity(intent);
                    })
                    .create().show();
        });

        mThanksTv.setOnClickListener(v -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.thanks))
                    .setView(R.layout.dialog_thanks)
                    .setPositiveButton("GitHub", (dialogInterface, i) -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(Urls.MY_GITHUB));
                        startActivity(intent);
                    })
                    .create().show();
        });

        mSuggestLinearLayout.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.MY_EMAIL_GOOGLE});
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
            try {
                startActivity(Intent.createChooser(i, getString(R.string.send_email)));
            } catch (ActivityNotFoundException ex) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.send_email_failed).create().show();
            }
        });

//        mGankTv.setOnClickListener(v -> startActivity(new Intent(getActivity(), GankDailyActivity.class)));
//        mSearchView.setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchActivity.class)));
    }

    private View getCheckBox(final String name) {
        View view = getLayoutInflater().inflate(R.layout.item_column_select, null);
        CheckBox checkBox = view.findViewById(R.id.checkbox);
        checkBox.setText(name);
        checkBox.setChecked(PreferencesHelper.get().isModuleSelected(name));

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            PreferencesHelper.get().setModuleSelected(name, isChecked);
            setFinishNow();
        });
        return view;
    }

    private void setFinishNow() {
        //TODO
    }

    private void setHeadImages(int index) {
        switch (index) {
            case Constants.TYPE_NG:
                PreferencesHelper.get().setHeadImages(PreferencesHelper.get().getNgImages());
                break;
            case Constants.TYPE_GANK_MEIZHI:
                PreferencesHelper.get().setHeadImages("");
                break;
            case Constants.TYPE_MAC:
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(Urls.PIC_1);
                jsonArray.add(Urls.PIC_2);
                jsonArray.add(Urls.PIC_3);
                jsonArray.add(Urls.PIC_4);
                PreferencesHelper.get().setHeadImages(jsonArray.toJSONString());
                break;
            case Constants.TYPE_COLOR:
                PreferencesHelper.get().setHeadImages("");
                break;
        }
    }

}
