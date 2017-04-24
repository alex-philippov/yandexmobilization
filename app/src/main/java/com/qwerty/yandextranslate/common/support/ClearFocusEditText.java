package com.qwerty.yandextranslate.common.support;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by alex on 10.04.17.
 */

public class ClearFocusEditText extends EditText {
    public ClearFocusEditText(Context context) {
        super(context);
    }

    public ClearFocusEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClearFocusEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            this.clearFocus();
        }
        return super.dispatchKeyEvent(event);
    }
}
