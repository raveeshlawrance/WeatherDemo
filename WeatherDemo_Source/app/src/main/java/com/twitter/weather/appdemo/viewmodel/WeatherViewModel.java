package com.twitter.weather.appdemo.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.twitter.weather.appdemo.utility.Constants;
import com.twitter.weather.appdemo.utility.Utility;

public class WeatherViewModel extends ViewModel {

    private String currentLocation;

    public String getCurrentDate() {
        return Utility.getDate(System.currentTimeMillis(), Constants.DATE_FORMAT);
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
}
