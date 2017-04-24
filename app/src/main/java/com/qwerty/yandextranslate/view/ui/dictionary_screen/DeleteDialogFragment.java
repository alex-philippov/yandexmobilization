package com.qwerty.yandextranslate.view.ui.dictionary_screen;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.qwerty.yandextranslate.R;

public class DeleteDialogFragment extends DialogFragment {
    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_MESSAGE = "arg_message";

    public DeleteDialogFragment() {}

    public static DeleteDialogFragment newInstance(int title, int message) {
        DeleteDialogFragment frag = new DeleteDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, title);
        args.putInt(ARG_MESSAGE, message);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt(ARG_TITLE);
        int message = getArguments().getInt(ARG_MESSAGE);

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    ((DictionaryFragment) getParentFragment()).cleanDictionary();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dismiss();
                })
                .create();
    }
}
