package com.qwerty.yandextranslate.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import io.realm.RealmList;

import static com.qwerty.yandextranslate.view.ui.translate_screen.TranslateFragment.TAG;

public class Utils {

    /**
     * hide soft keyboard
     * @param activity current activity
     */
    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            View view = activity.getWindow().getDecorView();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }


    }

    /**
     * Gone view when keyboard popup
     * @param rootView parent view
     * @param viewToGone view to gone
     */
    public static void setLayoutListenerToGoneView(View rootView, View viewToGone) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            int heightDiff = viewToGone.getRootView().getHeight() - (r.bottom - r.top);

            if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                //ok now we know the keyboard is up...
                viewToGone.setVisibility(View.GONE);
            } else {
                //ok now we know the keyboard is down...
                viewToGone.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Set the last used languages in the spinner from SharedPreferences
     */
    public static void setLastLangsSelections(AppCompatSpinner fromLangSpinner,
                                        AppCompatSpinner toLangSpinner,
                                        Context context) {
        SharedPreferences prefs = context
                .getSharedPreferences(Data.YANDEX_TRANSLATE_PREFERENCES, Context.MODE_PRIVATE);
        if (prefs.contains(Data.LAST_FROM_LANG_PREFERENCE_KEY)
                && prefs.contains(Data.LAST_TO_LANG_PREFERENCE_KEY)) {
            int fromPos = prefs.getInt(Data.LAST_FROM_LANG_PREFERENCE_KEY, 0);
            int toPos = prefs.getInt(Data.LAST_TO_LANG_PREFERENCE_KEY, 1);
            fromLangSpinner.setSelection(fromPos);
            toLangSpinner.setSelection(toPos);
        }
    }

    /**
     * Check the network connection
     * @param context context
     * @return is connected
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    public static String realmListToString(RealmList realmList) {
        if (realmList != null) {
            String result = "[";
            for (Object o: realmList) {
                result += o.toString() + ", ";
            }
            return result;
        }
        return "null";
    }

    public static String arrayToString(String[] arr) {
        String result = "";
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                result += arr[i] + " ";
            }
        }
        return result;
    }

}
