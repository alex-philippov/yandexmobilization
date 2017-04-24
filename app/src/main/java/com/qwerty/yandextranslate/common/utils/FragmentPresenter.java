package com.qwerty.yandextranslate.common.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentPresenter {
    private FragmentManager fragmentManager;

    public FragmentPresenter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(int container, Fragment fragment) {
        Fragment findFragment = fragmentManager.findFragmentById(container);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (findFragment != null) {
            if (!findFragment.isAdded()) {
                transaction
                        .add(container, findFragment);
            }
        } else {
            transaction.add(container, fragment);
        }
        transaction.commit();
    }
}
