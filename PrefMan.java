package com.yazdanparast.sajjad.chess;


import android.content.Context;
import android.content.SharedPreferences;

public class PrefMan {
    Context context ;
    SharedPreferences prefs ;
    private final String RPEF_NAME = "introSlider";
    private final String PREF_KEY = "start";
    public PrefMan(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(RPEF_NAME,Context.MODE_PRIVATE);
    }

    public void setSituation(boolean tanhakhore_badbakht){
        prefs.edit().putBoolean(PREF_KEY,tanhakhore_badbakht).apply();
    }

    public boolean getSituation(){
        return prefs.getBoolean(PREF_KEY,true);
    }
}
