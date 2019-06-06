package com.twitter.weather.appdemo.presenter;

import com.twitter.weather.appdemo.model.forecastlist.ForecastResponse;
import com.twitter.weather.appdemo.model.weatherlist.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiInterface {

    @GET("2.5/weather")
    Call<WeatherResponse> getWeather(@Query("lat") double lat,
                                     @Query("lon") double lon,
                                     @Query("units") String units,
                                     @Query("appid") String appid);

    @GET("2.5/forecast")
    Call<ForecastResponse> get5DaysWeather(@Query("lat") double lat,
                                           @Query("lon") double lon,
                                           @Query("cnt") int cnt,
                                           @Query("appid") String appid);
}
