package com.qwerty.yandextranslate.view.ui.settings_screen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qwerty.yandextranslate.common.utils.FragmentPresenter;
import com.qwerty.yandextranslate.R;

public class SettingsFragment extends Fragment {
    public static final String TAG = "tag_settings_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentPresenter fragmentPresenter = new FragmentPresenter(getChildFragmentManager());
        fragmentPresenter.addFragment(R.id.fl_pref_container, new PrefFragment());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "destroyVew settings");
    }
}
