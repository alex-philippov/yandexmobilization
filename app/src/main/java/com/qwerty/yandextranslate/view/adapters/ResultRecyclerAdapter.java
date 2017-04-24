package com.qwerty.yandextranslate.view.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qwerty.yandextranslate.R;
import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.Def;
import com.qwerty.yandextranslate.model.domain.Entities.SpannableDef;

import java.util.List;

public class ResultRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SpannableDef> mDefList;
    private Context mContext;

    public ResultRecyclerAdapter(List<Def> defs, Context context) {
        mDefList = SpannableDef.spannedList(defs, context);
        mContext = context;
    }

    public void updateDefs(List<Def> defs) {
        mDefList = SpannableDef.spannedList(defs, mContext);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.result_recycler_item_type0, parent, false);
                return new ViewHolderTranscription(view);
            default:
                view = inflater.inflate(R.layout.result_recycler_item_type1, parent, false);
                return new ViewHolderPos(view);
        }
    }

    @Override
    public int getItemCount() {
        return mDefList.size() + 1;
    }

    private class ViewHolderTranscription extends RecyclerView.ViewHolder {
        TextView tvTextTranscription;

        ViewHolderTranscription(View view) {
            super(view);
            tvTextTranscription = (TextView) view.findViewById(R.id.tv_item_text);
        }
    }

    private class ViewHolderPos extends RecyclerView.ViewHolder {
        TextView tvPos;
        RecyclerView recyclerView;

        ViewHolderPos(View view) {
            super(view);
            tvPos = (TextView) view.findViewById(R.id.tv_item_pos);
            recyclerView = (RecyclerView) view.findViewById(R.id.rv_tr);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (mDefList.size() == 0) return;
        --position;

        switch (holder.getItemViewType()) {
            case 0:
                Log.d("tag_", "onBind_tr");
                ViewHolderTranscription viewHolderTranscription = (ViewHolderTranscription)holder;
                viewHolderTranscription.tvTextTranscription
                        .setText(mDefList.get(0).getText() + " " + mDefList.get(0).getTs());
                break;

            default:
                Log.d("tag_", "onBind_pos");
                ViewHolderPos viewHolderPos = (ViewHolderPos)holder;
                viewHolderPos.tvPos.setText(mDefList.get(position).getPos());
                viewHolderPos.recyclerView.setAdapter(new ResultInnerRecyclerAdapter(
                        mDefList.get(position).getTrList(), mContext));
                break;
        }
    }
}
