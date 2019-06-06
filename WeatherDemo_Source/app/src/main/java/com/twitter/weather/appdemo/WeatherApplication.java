package com.twitter.weather.appdemo;

import android.app.Application;

import com.twitter.weather.appdemo.utility.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApplication extends Application {

    private static Retrofit retrofitInstance;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            synchronized (WeatherApplication.class) {
                if (retrofitInstance == null)
                    retrofitInstance = getRetrofit();
            }
        }

        return retrofitInstance;
    }

    private static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HTTP.CLIENTS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return  retrofit;
    }
}
