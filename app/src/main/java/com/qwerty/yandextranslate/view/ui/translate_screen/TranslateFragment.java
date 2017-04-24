package com.qwerty.yandextranslate.view.ui.translate_screen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.qwerty.yandextranslate.common.support.ClearFocusEditText;
import com.qwerty.yandextranslate.common.utils.Data;
import com.qwerty.yandextranslate.common.utils.SharedPreferenceManager;
import com.qwerty.yandextranslate.common.utils.Utils;
import com.qwerty.yandextranslate.model.domain.Entities.Language;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;
import com.qwerty.yandextranslate.presenter.TranslatePresenter;
import com.qwerty.yandextranslate.view.adapters.ResultInnerRecyclerAdapter;
import com.qwerty.yandextranslate.view.adapters.ResultRecyclerAdapter;
import com.qwerty.yandextranslate.view.interfaces.TranslateView;
import com.qwerty.yandextranslate.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.subjects.PublishSubject;

public class TranslateFragment extends MvpAppCompatFragment implements
        TranslateView, TextWatcher {
    public static String TAG = "tag_translate_fragment";

    @InjectPresenter(type = PresenterType.GLOBAL, tag = TranslatePresenter.TAG)
    TranslatePresenter presenter;

    private RelativeLayout mRlTranslateContainer;
    private RecyclerView mRvResult;
    private ImageView mIvSwapLangs;
    private AppCompatSpinner mSFromLang;
    private AppCompatSpinner mSToLang;
    private ClearFocusEditText mEtInputText;
    private ImageView mIvClearEtCross;
    private TextView mTvTranslation;
    private ArrayAdapter<Language> mFromLangAdapter;
    private ArrayAdapter<Language> mToLangAdapter;
    private ResultRecyclerAdapter mResultRvAdapter;
    private List<Language> mLanguageList;
    private PublishSubject<String> mInputTexSubject;
    private PublishSubject<Language> mFromLangSubject;
    private PublishSubject<Language> mToLangSubject;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInputTexSubject = PublishSubject.create();
        mFromLangSubject = PublishSubject.create();
        mToLangSubject = PublishSubject.create();
        presenter.subscribeUi(mInputTexSubject.debounce(150, TimeUnit.MILLISECONDS),
                mFromLangSubject, mToLangSubject);
        mLanguageList = new ArrayList<>();
        mFromLangAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                mLanguageList);
        mFromLangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mToLangAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                mLanguageList);
        mToLangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mResultRvAdapter = new ResultRecyclerAdapter(new ArrayList<>(), getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_translate,
                container,
                false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSFromLang = (AppCompatSpinner) view.findViewById(R.id.tv_lan_from);
        mSToLang = (AppCompatSpinner) view.findViewById(R.id.tv_lan_to);
        mIvSwapLangs = (ImageView) view.findViewById(R.id.iv_lang_swap);
        mEtInputText = (ClearFocusEditText) view.findViewById(R.id.et_translate);
        mIvClearEtCross = (ImageView) view.findViewById(R.id.iv_translate_cross);
        mTvTranslation = (TextView) view.findViewById(R.id.tv_translation);
        mRvResult = (RecyclerView) view.findViewById(R.id.rv_result);
        mRlTranslateContainer = (RelativeLayout) view.findViewById(R.id.rl_translated_container);
        ((TextView) view.findViewById(R.id.tv_dictionary_with)).setMovementMethod(LinkMovementMethod.getInstance());

        mSToLang.setAdapter(mToLangAdapter);
        mSFromLang.setAdapter(mFromLangAdapter);
        mRvResult.setAdapter(mResultRvAdapter);
        mRvResult.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSFromLang.setOnItemSelectedListener(new OnSpinnerItemSelectedListener());
        mSToLang.setOnItemSelectedListener(new OnSpinnerItemSelectedListener());

        mIvSwapLangs.setOnClickListener(v -> {
            swapSpinnersSelections(mSFromLang, mSToLang);
            mEtInputText.setText(mTvTranslation.getText());
        });
        mIvClearEtCross.setOnClickListener(v -> {
            mTvTranslation.setText("");
            mEtInputText.setText("");
            mRvResult.setAdapter(null);
        });
        mEtInputText.addTextChangedListener(this);

        mEtInputText.setOnFocusChangeListener((v, hasFocus) -> {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            if (!hasFocus && pref.getBoolean("switch_preference_history", false)) {
                presenter.saveTranslation();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSFromLang = null;
        mSToLang = null;
        mIvSwapLangs = null;
        mEtInputText = null;
        mIvClearEtCross = null;
        mRlTranslateContainer = null;
        mRvResult = null;
    }

    public void swapSpinnersSelections(AppCompatSpinner first, AppCompatSpinner second) {
        int firstCurrPosition = first.getSelectedItemPosition();
        int secondCurrPosition = second.getSelectedItemPosition();
        first.setSelection(secondCurrPosition);
        second.setSelection(firstCurrPosition);
    }

    @Override
    public void onSupportLangsLoadError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setSupportLangs(List<Language> langs) {
        mLanguageList = langs;
        Log.d(TAG, "langs: " + langs.toString());
        for (Language lang: langs) {
            System.out.println(lang + " " + lang.getName());
        }
        updateArrayAdapter(langs, mFromLangAdapter);
        updateArrayAdapter(langs, mToLangAdapter);
    }

    @Override
    public void restoreSpinnerLang() {
        Utils.setLastLangsSelections(mSFromLang, mSToLang, getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroyFragment();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showTranslation(Translation translation) {
        if (mRlTranslateContainer.getVisibility() == View.INVISIBLE)
            mRlTranslateContainer.setVisibility(View.VISIBLE);

        mTvTranslation.setText(translation.getTranslation());

        //mResultRvAdapter.updateDefs(translation.getDefList());
        mRvResult.setAdapter(new ResultRecyclerAdapter(translation.getDefList(), getActivity()));
    }

    @Override
    public void showSavedTranslation(Translation translation) {
        showTranslation(translation);
        mEtInputText.setText(translation.getOriginalText());
    }

    private void updateArrayAdapter(List<Language> list, ArrayAdapter<Language> adapter) {
        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

    //---------------------------------TextWatcher--------------------------------------------------
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().equals("")) {
            mTvTranslation.setText("");
            mIvClearEtCross.setVisibility(View.INVISIBLE);
            mRlTranslateContainer.setVisibility(View.INVISIBLE);
        } else {
            if (mIvClearEtCross.getVisibility() == View.INVISIBLE) {
                mIvClearEtCross.setVisibility(View.VISIBLE);
            }
        }
        if (mEtInputText.hasFocus())
            mInputTexSubject.onNext(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {}
    //----------------------------------------------------------------------------------------------


    /**
     * On Item Selected Listener for language spinners
     */
    private class OnSpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SharedPreferences prefs = getActivity()
                    .getSharedPreferences(Data.YANDEX_TRANSLATE_PREFERENCES, Context.MODE_PRIVATE);
            if (parent == mSFromLang) {
                prefs.edit()
                        .putInt(Data.LAST_FROM_LANG_PREFERENCE_KEY, position)
                        .apply();
                Log.d("tag_", mFromLangAdapter.getItem(position).getFullName());
                mFromLangSubject.onNext(mFromLangAdapter.getItem(position));
            } else if (parent == mSToLang) {
                prefs.edit()
                        .putInt(Data.LAST_TO_LANG_PREFERENCE_KEY, position)
                        .apply();
                Log.d("tag_", mToLangAdapter.getItem(position).getFullName());
                mToLangSubject.onNext(mFromLangAdapter.getItem(position));
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}
