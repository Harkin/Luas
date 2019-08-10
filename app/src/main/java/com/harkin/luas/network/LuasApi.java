package com.harkin.luas.network;

import android.content.Context;

import com.harkin.luas.R;
import com.harkin.luas.network.models.StopList;
import com.harkin.luas.network.models.Timetable;

import org.simpleframework.xml.core.Persister;

import java.io.InputStream;

import hugo.weaving.DebugLog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

public class LuasApi {
    private static final String BASE_URL = "https://luasforecasts.rpa.ie/xml/";
    private static LuasApi INSTANCE = new LuasApi();

    private final LuasService luasApi;

    private LuasApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        luasApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client)
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
//        return luasApi.getStops().map(StopList.Builder::build);
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
        InputStream stream = context.getResources().openRawResource(R.raw.stops);

        try {
            StopList response = new Persister().read(StopList.Builder.class, stream).build();

            stream.close();

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }
}
