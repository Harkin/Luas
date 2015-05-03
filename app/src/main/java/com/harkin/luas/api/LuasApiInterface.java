package com.harkin.luas.api;

import com.harkin.luas.models.api.StopTimetableResponse;
import com.harkin.luas.models.api.StopResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

public interface LuasApiInterface {

    @GET("/realtimebusinformation")
    void getLuasTimes(
            @QueryMap Map<String, Object> options,
            Callback<StopTimetableResponse.Builder> result
    );

    @GET("/busstopinformation")
    void getStops(
            @QueryMap Map<String, Object> options,
            Callback<StopResponse.Builder> result
    );

}
