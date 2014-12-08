package com.harkin.luas.api;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.harkin.luas.models.api.LuasTimeResponse;
import com.harkin.luas.models.api.StopResponse;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by henry on 14/07/2014.
 */
public class LuasAPI {
    private static LuasAPI instance;

    private final OkHttpClient client;
    private final Request.Builder builder;
    private RequestQueue mRequestQueue;

    public static synchronized LuasAPI getInstance(Context context) {
        if (instance == null) {
            instance = new LuasAPI(context);
        }
        return instance;
    }

    public LuasAPI(Context c) {
        client = new OkHttpClient();
        builder =new Request.Builder();
        mRequestQueue = Volley.newRequestQueue(c.getApplicationContext());
    }


    private static final String baseUrl = "http://94.236.101.9/RTPIPublicService/service.svc/";

    public void getLuasTimes(String stopId, Response.Listener<LuasTimeResponse> listener, Response.ErrorListener errorListener) {
        String endUrl = baseUrl + "realtimebusinformation?stopid=" + stopId + "&maxresults=500&format=Json&deviceid=3638";
        GsonRequest request = new GsonRequest<LuasTimeResponse>(Method.GET, endUrl, LuasTimeResponse.class, listener, errorListener);
        mRequestQueue.add(request);
    }

    public void getStops(Response.Listener<StopResponse> listener, Response.ErrorListener errorListener) {
        String endUrl = baseUrl + "busstopinformation?deviceid=3638&operator=luas";
        GsonRequest request = new GsonRequest<StopResponse>(Method.GET, endUrl, StopResponse.class, listener, errorListener);
        mRequestQueue.add(request);
    }

    public void getLuasTimes(String stopId, Callback callback) {
        String endUrl = baseUrl + "realtimebusinformation?stopid=" + stopId + "&maxresults=500&format=Json&deviceid=3638";
        Request request = builder.url(endUrl).build();
        client.newCall(request).enqueue(callback);
    }

    public void getStops(Callback callback) {
        String endUrl = baseUrl + "busstopinformation?deviceid=3638&operator=luas";
        Request request = builder.url(endUrl).build();
        client.newCall(request).enqueue(callback);
    }
}
