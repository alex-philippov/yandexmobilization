package com.qwerty.yandextranslate.model.domain.Entities;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.qwerty.yandextranslate.R;
import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.Def;
import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.Tr;

import java.util.ArrayList;
import java.util.List;

public class SpannableDef {
    private CharSequence mText;
    private CharSequence mTs;
    private CharSequence mPos;
    private List<SpannableTr> mTrList;
    private Context mContext;

    public SpannableDef(Def def, Context context) {
        mContext = context;
        mPos = createPos(def.getPos());
        mTs = createTs(def.getTs());
        mText = def.getText();

        mTrList = new ArrayList<>();
        if (def.getTr() != null) {
            for (Tr tr: def.getTr()) {
                mTrList.add(new SpannableTr(tr, context));
            }
        }
    }

    private CharSequence createPos(String text) {
        CharSequence result = "";
        if (text != null) {
            SpannableString def = new SpannableString(text);
            def.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPos)),
                    0,
                    def.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            def.setSpan(new StyleSpan(Typeface.ITALIC),
                    0,
                    def.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            result = def;
        }
        return result;
    }

    private CharSequence createTs(String ts) {
        CharSequence result = "";
        if (ts != null) {
            SpannableString t = new SpannableString("[" + ts + "]");
            t.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorTs)),
                    0,
                    t.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            result = t;
        }
        return result;
    }

    public static List<SpannableDef> spannedList(List<Def> list, Context context) {
        List<SpannableDef> defList = new ArrayList<>();
        for (Def def: list) {
            defList.add(new SpannableDef(def, context));
        }
        return defList;
    }

    public CharSequence getText() {
        return mText;
    }

    public CharSequence getTs() {
        return mTs;
    }

    public CharSequence getPos() {
        return mPos;
    }

    public List<SpannableTr> getTrList() {
        return mTrList;
    }
}
