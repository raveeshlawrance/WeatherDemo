package com.twitter.weather.appdemo.presenter;

import com.twitter.weather.appdemo.model.forecastlist.ForecastResponse;

import retrofit2.Response;

public interface APICallStatusInterface<T> {
    void onResponseSuccess(Response<T> response);
    void onResponseFailure(Response<T> response, String errorMessage);
}
