package com.harkin.luas.api;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.harkin.luas.models.LuasTimeResponse;
import com.harkin.luas.models.StopResponse;

/**
 * Created by henry on 14/07/2014.
 */
public class LuasAPI {
    private RequestQueue mRequestQueue;
    private static LuasAPI instance;

    public static synchronized LuasAPI getInstance(Context context) {
        if (instance == null) {
            instance = new LuasAPI(context);
        }
        return instance;
    }

    public LuasAPI(Context c) {
        mRequestQueue = Volley.newRequestQueue(c.getApplicationContext());
    }


    private static final String baseUrl = "http://94.236.101.9/RTPIPublicService/service.svc/";

    public GsonRequest<LuasTimeResponse> getLuasTimes(int stopId, Response.Listener<LuasTimeResponse> listener, Response.ErrorListener errorListener) {
        String endUrl = baseUrl + "realtimebusinformation?stopid=" + stopId + "&maxresults=5000&format=Json&deviceid=3638";
        return new GsonRequest<LuasTimeResponse>(Method.GET, endUrl, LuasTimeResponse.class, listener, errorListener);
    }

    public GsonRequest<StopResponse> getStops(Response.Listener<StopResponse> listener, Response.ErrorListener errorListener) {
        String endUrl = baseUrl + "busstopinformation?deviceid=3638&operator=bac ";
        return new GsonRequest<StopResponse>(Method.GET, endUrl, StopResponse.class, listener, errorListener);
    }
}
