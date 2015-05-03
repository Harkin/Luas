package com.harkin.luas;

import android.util.Log;

public class Logger {
    private static final String TAG = "LUAS";

    public static void DEBUG(String message) {
        DEBUG(TAG, message);
    }

    public static void DEBUG(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }
}
