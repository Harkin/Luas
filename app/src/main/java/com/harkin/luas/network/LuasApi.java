package com.harkin.luas.network;

import android.content.Context;

import com.google.gson.Gson;
import com.harkin.luas.R;
import com.harkin.luas.network.models.StopList;
import com.harkin.luas.network.models.Timetable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import hugo.weaving.DebugLog;
import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;
import rx.Observable;

public class LuasApi {
    private static final String BASE_URL = "http://luasforecasts.rpa.ie/xml";
    private static final LuasApi INSTANCE = new LuasApi();

    private final LuasService luasApi;

    public LuasApi() {
        luasApi = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setConverter(new SimpleXMLConverter())
                .build()
                .create(LuasService.class);
    }

    public static LuasApi getInstance() {
        return INSTANCE;
    }

    public Observable<Timetable> getTrams(String stopShortName) {
        return luasApi.getLuasTimes(stopShortName).map(Timetable.Builder::build);
    }

    public Observable<StopList> getStops(Context context) {
        return Observable.defer(() -> {
            StopList list = readStopsFromDisk(context);
            if (list == null) {
                return Observable.error(new NullPointerException());
            } else {
                return Observable.just(list);
            }
        });
    }

    @DebugLog private StopList readStopsFromDisk(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.stops);
        Reader reader = new InputStreamReader(is);
        StopList response = new Gson().fromJson(reader, StopList.Builder.class).build();

        try {
            is.close();
            reader.close();
        } catch (IOException e) {
            //todo
            e.printStackTrace();
        }
        return response;
    }
}