package com.twitter.weather.appdemo.presenter;

import android.location.Location;

public interface LocationInterface {
    void onLocationChanged();
    void onLocationTrackFailed();
    void onTrackingLocation(Location location);
}
