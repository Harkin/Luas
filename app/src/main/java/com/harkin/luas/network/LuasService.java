package com.harkin.luas.network;

import com.harkin.luas.network.models.Timetable;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface LuasService {
    @GET("get.ashx?action=forecast&encrypt=false")
    Observable<Timetable.Builder> getLuasTimes(@Query("stop") String stop);
}
