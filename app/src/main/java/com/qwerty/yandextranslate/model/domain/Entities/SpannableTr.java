package com.qwerty.yandextranslate.model.domain.Entities;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;

import com.qwerty.yandextranslate.R;
import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.Ex;
import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.Mean;
import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.Syn;
import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.Tr;
import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.Tr_;

import java.util.ArrayList;
import java.util.List;

public class SpannableTr {
    private Context mContext;

    private CharSequence syns;
    private CharSequence means;
    private CharSequence exs;

    public SpannableTr(Tr tr, Context context) {
        mContext = context;
        syns = createSyns(tr.getSyn());
        means = createMeans(tr.getMean());
        exs = createExs(tr.getEx());
    }

    private CharSequence createSyns(List<Syn> synList) {
        CharSequence result = "";
        if (synList != null && synList.size() != 0) {
            for (int i = 0; i < synList.size() - 1; i++) {
                result = TextUtils.concat(result, createSyn(synList.get(i), true), " ");
            }
            result = TextUtils.concat(result, createSyn(synList.get(synList.size() - 1), false));
        }
        Log.d("tag_", "syn: " + result + (synList == null));
        return result;
    }

    private CharSequence createSyn(Syn syn, boolean needComma) {
        SpannableString spannableSyn;
        String s = syn.getText();
        String g = syn.getGen();

        if (s == null) {
            return "";
        }

        if (g == null) {
            g = "";
        } else {
            g = " " + g;
        }

        spannableSyn = new SpannableString(s + g + (needComma ? "," : ""));
        spannableSyn.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorSyn)),
                0,
                spannableSyn.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (!g.equals("")) {
            spannableSyn.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorGen)),
                    s.length(),
                    s.length() + g.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableSyn.setSpan(new StyleSpan(Typeface.ITALIC),
                    s.length(),
                    s.length() + g.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableSyn;
    }

    private CharSequence createMeans(List<Mean> meanList) {
        CharSequence result = "";
        if (meanList != null && meanList.size() != 0) {
            result = "(";
            for (int i = 0; i < meanList.size() - 1; i++) {
                result = TextUtils.concat(result, meanList.get(i).getText(), ", ");
            }
            result = TextUtils.concat(result, meanList.get(meanList.size() - 1).getText(), ")");
            SpannableString means = new SpannableString(result);
            means.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorMean)),
                    0,
                    means.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            result = means;
        }
        return result;
    }

    private CharSequence createExs(List<Ex> exList) {
        CharSequence result = "";
        if (exList != null && exList.size() != 0) {
            for (int i = 0; i < exList.size() - 1; i++) {
                String text = exList.get(i).getText();
                List<Tr_> trList = exList.get(i).getTr();
                if (text == null) continue;
                String tr = "";
                if (trList != null) {
                    for (int j = 0; j < trList.size() - 1; j++) {
                        tr += trList.get(j).getText() + "; ";
                    }
                    tr += trList.get(trList.size() - 1).getText();
                    tr = " - " + tr;
                }
                result = TextUtils.concat(result, exList.get(i).getText(), tr, "\n");
            }
            result = TextUtils.concat(result, exList.get(exList.size() - 1).getText());
            SpannableString exs = new SpannableString(result);
            exs.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorEx)),
                    0,
                    exs.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            exs.setSpan(new StyleSpan(Typeface.ITALIC),
                    0,
                    exs.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            result = exs;
            Log.d("tag_", "ex: " + result);
        }
        return result;
    }

    public CharSequence getSyns() {
        return syns;
    }

    public CharSequence getMeans() {
        return means;
    }

    public CharSequence getExs() {
        return exs;
    }
}
