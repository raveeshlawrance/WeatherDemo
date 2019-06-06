package com.twitter.weather.appdemo.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.twitter.weather.appdemo.R;
import com.twitter.weather.appdemo.model.weatherlist.WeatherResponse;
import com.twitter.weather.appdemo.presenter.LocationInterface;
import com.twitter.weather.appdemo.presenter.WeatherApiInterface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationTrackerHelper implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private Context context;
    private int locationRequestCode = 100;

    public LocationTrackerHelper(Context context) {
        this.context = context;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void onStartTrackingLocation(Activity activity, final LocationInterface locationInterface) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, locationRequestCode);
            return;
        }
        FusedLocationProviderClient fusedLocationApi = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationApi.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null)
                    locationInterface.onTrackingLocation(location);
                else
                    locationInterface.onLocationTrackFailed();
            }
        });
        fusedLocationApi.getLastLocation().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                locationInterface.onLocationTrackFailed();
            }
        });
        fusedLocationApi.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                locationInterface.onTrackingLocation(task.getResult());
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("changed", "loc");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("changed", "loc");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("changed", "loc");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("changed", "loc");
    }

    public String getAddress(Location location) {
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses != null && addresses.get(0) != null ? addresses.get(0).getAddressLine(0) : "Location Not found";
    }
}
