package com.harkin.luas.api;

import android.content.Context;

import com.google.gson.Gson;
import com.harkin.luas.BusWrapper;
import com.harkin.luas.R;
import com.harkin.luas.models.api.StopResponse;
import com.harkin.luas.models.api.StopTimetableResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LuasAPI {
    private static final String BASE_URL = "http://94.236.101.9/RTPIPublicService/service.svc/";
    private static LuasAPI instance;

    private final LuasApiInterface luasApi;

    public LuasAPI() {
        luasApi = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build()
                .create(LuasApiInterface.class);
    }

    public static synchronized LuasAPI getInstance() {
        if (instance == null) {
            instance = new LuasAPI();
        }
        return instance;
    }

    public void getLuasTimes(String stopId) {
        String endUrl = BASE_URL + "realtimebusinformation?stopid=" + stopId + "&maxresults=500&format=Json&deviceid=3638";
        Map<String, Object> params = new HashMap<>();
        params.put("stopid", stopId);
        params.put("maxresults", 500);
        params.put("format", "Json");
        params.put("deviceid", 1);
        luasApi.getLuasTimes(params, new Callback<StopTimetableResponse.Builder>() {
            @Override public void success(StopTimetableResponse.Builder builder, Response response) {
                BusWrapper.getInstance().post(builder.build());
            }

            @Override public void failure(RetrofitError error) {

            }
        });
    }

    public void updateStops() {
        Map<String, Object> params = new HashMap<>();
        params.put("operator", "luas");
        params.put("deviceid", 1);

        luasApi.getStops(params, new Callback<StopResponse.Builder>() {
            @Override public void success(StopResponse.Builder builder, Response response) {
                BusWrapper.getInstance().post(builder.build());
            }

            @Override public void failure(RetrofitError error) {

            }
        });
    }

    public void getStops(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.stops);
        Reader reader = new InputStreamReader(is);
        StopResponse response = new Gson().fromJson(reader, StopResponse.Builder.class).build();

        try {
            is.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null) {
            BusWrapper.getInstance().post(response);
        } else {
            updateStops();
        }
    }

}