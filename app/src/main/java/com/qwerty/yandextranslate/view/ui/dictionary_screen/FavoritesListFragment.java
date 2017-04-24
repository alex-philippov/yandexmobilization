package com.qwerty.yandextranslate.view.ui.dictionary_screen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.qwerty.yandextranslate.R;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;
import com.qwerty.yandextranslate.presenter.FavoritesListPresenter;
import com.qwerty.yandextranslate.view.adapters.DictionaryRecyclerAdapter;
import com.qwerty.yandextranslate.view.interfaces.DictionaryListView;
import com.qwerty.yandextranslate.view.interfaces.DictionaryView;
import com.qwerty.yandextranslate.view.interfaces.TranslateView$$State;

import java.util.ArrayList;
import java.util.List;


public class FavoritesListFragment extends MvpAppCompatFragment implements DictionaryListView {
    public static final String TAG = "tag_list_fragment";


    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private List<Translation> mList;
    private DictionaryRecyclerAdapter mAdapter;
    private OnDictionaryIteration mDictionaryListener;

    @InjectPresenter(type = PresenterType.GLOBAL, tag = FavoritesListPresenter.TAG)
    FavoritesListPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DictionaryRecyclerAdapter(new ArrayList<>(), presenter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView = null;
        mSearchView = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDictionaryListener = (OnDictionaryIteration) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDictionaryListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dictionary_inner_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_dictionary_list);
        mSearchView = (SearchView) view.findViewById(R.id.sv_search);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mSearchView.onActionViewExpanded();
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();
        addTextListener();
    }

    void addTextListener() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String tmp = newText.toLowerCase();
                List<Translation> filteredList = new ArrayList<>();
                for (Translation translation: mList) {
                    String original = translation.getOriginalText().toLowerCase();
                    String transl = translation.getTranslation().toLowerCase();
                    if (original.contains(tmp) || transl.contains(tmp)) {
                        filteredList.add(translation);
                    }
                }
                mAdapter.updateList(filteredList);
                return false;
            }
        });
    }


    @Override
    public void showList(List<Translation> list) {
        mList = list;
        mAdapter.updateList(mList);
    }

    @Override
    public void showTranslation() {
        mDictionaryListener.showTranslation();
    }
}
