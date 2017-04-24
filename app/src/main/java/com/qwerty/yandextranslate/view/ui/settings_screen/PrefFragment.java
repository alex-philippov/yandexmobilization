package com.qwerty.yandextranslate.view.ui.settings_screen;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import com.qwerty.yandextranslate.R;


public class PrefFragment extends PreferenceFragmentCompat {
    public static final String TAG = "tag_pref_fragment";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
