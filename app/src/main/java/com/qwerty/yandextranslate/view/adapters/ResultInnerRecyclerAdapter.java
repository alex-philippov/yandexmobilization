package com.qwerty.yandextranslate.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qwerty.yandextranslate.R;
import com.qwerty.yandextranslate.model.domain.Entities.SpannableTr;

import java.util.List;

public class ResultInnerRecyclerAdapter extends RecyclerView.Adapter<ResultInnerRecyclerAdapter.ViewHolder> {
    private List<SpannableTr> mTrList;
    private Context mContext;

    public ResultInnerRecyclerAdapter(List<SpannableTr> list, Context context) {
        mTrList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_recycler_inner_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SpannableTr tr = mTrList.get(position);

        Log.d("tag_", (position + 1) + "exrec: " + tr.getExs());
        Log.d("tag_", (position + 1) +"synsrec: " + tr.getSyns());

        holder.tvNumb.setText((position + 1) + ".");
        holder.tvSynMean.setText(TextUtils.concat(tr.getSyns(), " \n", tr.getMeans()));
        holder.tvEx.setText(tr.getExs());
    }

    @Override
    public int getItemCount() {
        return mTrList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumb;
        TextView tvSynMean;
        TextView tvEx;

        ViewHolder(View view) {
            super(view);
            tvNumb = (TextView) view.findViewById(R.id.tv_tr_numb);
            tvSynMean = (TextView) view.findViewById(R.id.tv_syn_mean);
            tvEx = (TextView) view.findViewById(R.id.tv_ex);
        }
    }
}
