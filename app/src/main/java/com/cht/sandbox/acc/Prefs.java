package com.cht.sandbox.acc;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface Prefs {

    @DefaultInt(0)
    int min();

    @DefaultInt(100)
    int max();

    @DefaultInt(80)
    int current();
}
