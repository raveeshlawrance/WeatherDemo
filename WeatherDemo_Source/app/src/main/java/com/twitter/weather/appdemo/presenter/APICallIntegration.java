package com.twitter.weather.appdemo.presenter;

import android.content.Context;
import com.twitter.weather.appdemo.R;
import com.twitter.weather.appdemo.WeatherApplication;
import com.twitter.weather.appdemo.model.forecastlist.ForecastResponse;
import com.twitter.weather.appdemo.model.weatherlist.WeatherResponse;
import com.twitter.weather.appdemo.utility.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class APICallIntegration {

    private final String API_KEY;
    private final APICallStatusInterface apiCallStatusInterface;
    private WeatherApiInterface weatherService;
    private Retrofit retrofitInstance;

    public APICallIntegration(Context context, APICallStatusInterface apiCallStatusInterface) {
        WeatherApplication weatherApplication = (WeatherApplication) context;
        retrofitInstance = weatherApplication.getRetrofitInstance();
        this.apiCallStatusInterface = apiCallStatusInterface;
        API_KEY = context.getString(R.string.weather_api_key);
        weatherService = retrofitInstance.create(WeatherApiInterface.class);
    }

    public void onCallForecastAPI(double latitude, double longitude) {
        Call<ForecastResponse> listCall = weatherService.get5DaysWeather(latitude, longitude, Constants.HTTP.NO_OF_DAYS, API_KEY);
        listCall.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if(response.isSuccessful()) {
                    apiCallStatusInterface.onResponseSuccess(response);
                } else {
                    apiCallStatusInterface.onResponseFailure(response, response.message());
                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                apiCallStatusInterface.onResponseFailure(null, t.getMessage());
            }
        });
    }

    public void onCallWeatherAPI(double latitude, double longitude) {
        WeatherApiInterface service = retrofitInstance.create(WeatherApiInterface.class);
        Call<WeatherResponse> listCall = service.getWeather(latitude, longitude,
                "metric", API_KEY);
        listCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    apiCallStatusInterface.onResponseSuccess(response);
                } else {
                    apiCallStatusInterface.onResponseFailure(response, response.message());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                apiCallStatusInterface.onResponseFailure(null, t.getMessage());
            }
        });
    }
}
