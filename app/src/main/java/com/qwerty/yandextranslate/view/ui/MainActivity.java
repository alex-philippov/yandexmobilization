package com.qwerty.yandextranslate.view.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.qwerty.yandextranslate.model.domain.Entities.Translation;
import com.qwerty.yandextranslate.view.adapters.MainViewPagerAdapter;
import com.qwerty.yandextranslate.R;
import com.qwerty.yandextranslate.view.ui.dictionary_screen.DictionaryFragment;
import com.qwerty.yandextranslate.view.ui.dictionary_screen.OnDictionaryIteration;
import com.qwerty.yandextranslate.view.ui.settings_screen.SettingsFragment;
import com.qwerty.yandextranslate.view.ui.translate_screen.TranslateFragment;
import com.qwerty.yandextranslate.common.support.NonSwipeViewPager;
import com.qwerty.yandextranslate.common.utils.Utils;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        OnDictionaryIteration {

    public static final String TAG = "tag_main_activity";

    private Fragment mFragments[];
    private NonSwipeViewPager mViewPager;
    private MainViewPagerAdapter mViewPagerAdapter;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragments = new Fragment[] {
                new TranslateFragment(),
                new DictionaryFragment(),
                new SettingsFragment()
        };
        mViewPager = (NonSwipeViewPager) findViewById(R.id.vp_main);
        mViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        Menu menu = mBottomNavigationView.getMenu();
        this.onNavigationItemSelected(menu.findItem(R.id.translate_item));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.translate_item:
                mViewPager.setCurrentItem(0);
                break;

            case R.id.history_item:
                mViewPager.setCurrentItem(1);
                Utils.hideKeyboard(this);
                break;

            case R.id.settings_item:
                mViewPager.setCurrentItem(2);
                Utils.hideKeyboard(this);
                break;
        }
        return true;
    }

    @Override
    public void showTranslation() {
        Menu menu = mBottomNavigationView.getMenu();
        mBottomNavigationView.setSelectedItemId(menu.findItem(R.id.translate_item).getItemId());
    }
}
