package com.harkin.luas;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

public class BusWrapper extends Bus {
    private static BusWrapper instance;
    private final Handler mainThread = new Handler(Looper.getMainLooper());

    public static synchronized BusWrapper getInstance() {
        if (instance == null) {
            instance = new BusWrapper();
        }
        return instance;
    }

    @Override public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mainThread.post(new Runnable() {
                @Override public void run() {
                    BusWrapper.super.post(event);
                }
            });
        }
    }
}
