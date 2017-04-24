package com.qwerty.yandextranslate.view.ui.dictionary_screen;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.qwerty.yandextranslate.R;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;
import com.qwerty.yandextranslate.presenter.DictionaryPresenter;
import com.qwerty.yandextranslate.view.adapters.DictionaryViewPagerAdapter;
import com.qwerty.yandextranslate.view.interfaces.DictionaryView;

public class DictionaryFragment extends MvpAppCompatFragment implements DictionaryView {
    public static final String TAG = "tag_dict_fragment";
    public static final int HISTORY_FRAGMENT = 0;
    public static final int FAVORITES_FRAGMENT = 1;

    @InjectPresenter(type = PresenterType.GLOBAL, tag = DictionaryPresenter.TAG)
    DictionaryPresenter presenter;

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private DictionaryViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private OnDictionaryIteration mDictionaryListener;
    private int mCurrentPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment[] mFragments = {
                new HistoryListFragment(),
                new FavoritesListFragment()
        };
        String[] titles = {
                "История",
                "Избранное"
        };
        mViewPagerAdapter = new DictionaryViewPagerAdapter(getFragmentManager(), mFragments, titles);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = (Toolbar) view.findViewById(R.id.tb_history);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(null);

        mViewPager = (ViewPager) view.findViewById(R.id.vp_dictionary);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabLayout = (TabLayout) view.findViewById(R.id.tl_history);
        mTabLayout.setupWithViewPager(mViewPager);

        setHasOptionsMenu(true);
    }

    public void cleanDictionary() {
        if (mCurrentPage == 0) {
            presenter.deleteHistory();
        } else if (mCurrentPage == 1) {
            presenter.deleteFavorites();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDictionaryListener = (OnDictionaryIteration) context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dictionary, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.clear_menu_item) {
            int title = 0;
            int message = 0;
            switch (mCurrentPage) {
                case 0:
                    title = R.string.history;
                    message = R.string.delete_history_message;
                    break;
                case 1:
                    title = R.string.favorites;
                    message = R.string.delete_favorites_message;
                    break;
            }
            DeleteDialogFragment
                    .newInstance(title, message)
                    .show(getChildFragmentManager(), "dialog");
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabLayout = null;
        mToolbar = null;
        mViewPager = null;
    }

}
