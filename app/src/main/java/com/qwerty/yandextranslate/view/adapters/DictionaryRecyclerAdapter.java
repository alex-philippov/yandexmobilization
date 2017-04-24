package com.qwerty.yandextranslate.view.adapters;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpPresenter;
import com.qwerty.yandextranslate.R;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;
import com.qwerty.yandextranslate.presenter.FavoritesListPresenter;
import com.qwerty.yandextranslate.presenter.SelectiveItems;

import java.util.List;

import io.realm.Realm;

public class DictionaryRecyclerAdapter extends RecyclerView.Adapter<DictionaryRecyclerAdapter.Holder>{
    private List<Translation> mList;
    private SelectiveItems mPresenter;

    public DictionaryRecyclerAdapter(List<Translation> list, SelectiveItems presenter) {
        mList = list;
        mPresenter = presenter;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_list_item, null);
        return new Holder(item);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Translation translation = mList.get(position);
        holder.setPosition(position);
        holder.tvTextToTranslate.setText(translation.getOriginalText());
        holder.tvTranslatedText.setText(translation.getTranslation());
        holder.tvLanguageCode.setText(translation.getLang().toUpperCase());
        if (translation.isFavorites()) {
            holder.cbFavorite.setChecked(true);
        } else {
            holder.cbFavorite.setChecked(false);
        }
    }

    public void updateList(List<Translation> list) {
        mList = list;
        Handler handler = new Handler();
        handler.post(this::notifyDataSetChanged);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        CheckBox cbFavorite;
        TextView tvTextToTranslate;
        TextView tvTranslatedText;
        TextView tvLanguageCode;
        RelativeLayout rlItemRoot;

        private int mPosition;

        Holder(View itemView) {
            super(itemView);
            cbFavorite = (CheckBox) itemView.findViewById(R.id.cb_favorite_item);
            tvTextToTranslate = (TextView) itemView.findViewById(R.id.tv_text_to_translate_item);
            tvTranslatedText = (TextView) itemView.findViewById(R.id.tv_translated_text_item);
            tvLanguageCode = (TextView) itemView.findViewById(R.id.tv_language_code_item);
            rlItemRoot = (RelativeLayout) itemView.findViewById(R.id.rl_item_root);

            rlItemRoot.setOnClickListener(v -> mPresenter.onItemSelected(mList.get(mPosition)));
            cbFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                    mList.get(mPosition).setFavorites(isChecked);
                realm.commitTransaction();
                mPresenter.onItemChangeChecked();
            });
        }

        void setPosition(int position) {
            this.mPosition = position;
        }
    }

}
