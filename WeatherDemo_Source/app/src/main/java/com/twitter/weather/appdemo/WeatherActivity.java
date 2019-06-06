package com.twitter.weather.appdemo;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.twitter.weather.appdemo.model.forecastlist.ForecastResponse;
import com.twitter.weather.appdemo.model.weatherlist.WeatherResponse;
import com.twitter.weather.appdemo.presenter.APICallIntegration;
import com.twitter.weather.appdemo.presenter.APICallStatusInterface;
import com.twitter.weather.appdemo.presenter.LocationInterface;
import com.twitter.weather.appdemo.presenter.PermissionInterface;
import com.twitter.weather.appdemo.presenter.WeatherApiInterface;
import com.twitter.weather.appdemo.utility.Constants;
import com.twitter.weather.appdemo.utility.LocationTracker;
import com.twitter.weather.appdemo.utility.LocationTrackerHelper;
import com.twitter.weather.appdemo.utility.Utility;
import com.twitter.weather.appdemo.viewmodel.WeatherViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherActivity extends FragmentActivity implements LocationInterface, APICallStatusInterface {

    private WeatherViewModel weatherViewModel;

    @BindView(R.id.txtViewCurrentDate)
    TextView txtViewCurrentDate;

    @BindView(R.id.txtViewCurrentTemperature)
    TextView txtViewCurrentTemperature;

    @BindView(R.id.txtViewCurrentTemperatureUnit)
    TextView txtViewCurrentTemperatureUnit;

    @BindView(R.id.txtViewCurrentLocation)
    TextView txtViewCurrentLocation;

    @BindView(R.id.txtViewStdDeviation)
    TextView txtViewStdDeviation;

    @BindView(R.id.imgViewCloudiness)
    ImageView imgViewCloudiness;

    @BindView(R.id.txtViewWindSpeed)
    TextView txtViewWindSpeed;

    @BindView(R.id.btnStdDeviation)
    Button btnStdDeviation;

    @BindView(R.id.txtViewCurrentTemperatureFahrenheit)
    TextView txtViewCurrentTemperatureFahrenheit;

    @BindView(R.id.txtViewCurrentTemperatureUnitFahrenheit)
    TextView txtViewCurrentTemperatureUnitFahrenheit;

    LocationInterface locationInterface;
    private double latitude;
    private double longitude;
    private LocationTrackerHelper locationTrackerHelper;
    private APICallStatusInterface apiCallStatusInterface;
    private WeatherResponse weatherListResponse;
    private ForecastResponse forecastResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        locationTrackerHelper = new LocationTrackerHelper(getApplicationContext());
        apiCallStatusInterface = this;
        locationInterface = this;
        onStartTrackingLocation();
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        txtViewCurrentDate.setText(weatherViewModel.getCurrentDate());

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        btnStdDeviation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallForecastAPI();
            }
        });
    }

    private void onStartTrackingLocation() {
        locationTrackerHelper.onStartTrackingLocation(this, locationInterface);
    }

    private void onCallForecastAPI() {
        APICallIntegration apiCallIntegration = new APICallIntegration(getApplicationContext(), apiCallStatusInterface);
        apiCallIntegration.onCallForecastAPI(latitude, longitude);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onStartTrackingLocation();
                }
                return;
            }

        }
    }

    @Override
    public void onLocationChanged() {

    }

    @Override
    public void onLocationTrackFailed() {
        Toast.makeText(getApplicationContext(), "Please check your Network connection or GPS and try again later.", Toast.LENGTH_LONG).show();
        txtViewCurrentLocation.setText("Location not found");
    }

    @Override
    public void onTrackingLocation(Location location) {
        if (location == null) {
            Toast.makeText(getApplicationContext(), "Please turn on your GPS and try again.", Toast.LENGTH_LONG).show();
            txtViewCurrentLocation.setText("Location not found");
            return;
        }
        txtViewCurrentLocation.setText(locationTrackerHelper.getAddress(location));
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        APICallIntegration apiCallIntegration = new APICallIntegration(getApplicationContext(), apiCallStatusInterface);
        apiCallIntegration.onCallWeatherAPI(latitude, longitude);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstance) {
        super.onSaveInstanceState(savedInstance);
        savedInstance.putParcelable("weatherInfo", weatherListResponse);
        savedInstance.putParcelable("forecastInfo", forecastResponse);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        weatherListResponse = savedInstanceState.getParcelable("weatherInfo");
        forecastResponse = savedInstanceState.getParcelable("forecastInfo");
    }

    @Override
    public void onResponseSuccess(Response response) {
        if(response.body() instanceof ForecastResponse) {
            forecastResponse = (ForecastResponse) response.body();
            txtViewStdDeviation.setText("Standard Deviation : " + forecastResponse.getStandardDeviation(forecastResponse.getList()));
        } else if(response.body() instanceof WeatherResponse) {
            weatherListResponse = (WeatherResponse) response.body();
            txtViewCurrentTemperature.setText(weatherListResponse.getMain().getTemp() + "°");
            txtViewCurrentTemperatureUnit.setText("C");
            txtViewCurrentTemperatureFahrenheit.setText(" / " + weatherListResponse.getMain().getTempInFahrenheit() + "°");
            txtViewCurrentTemperatureUnitFahrenheit.setText("F");
            txtViewWindSpeed.setText("Wind: " + weatherListResponse.getWind().getSpeed());
            imgViewCloudiness.setVisibility(weatherListResponse.getClouds().isCloudVisible());
        }
    }

    @Override
    public void onResponseFailure(Response response, String errorMessage) {
        if(response == null) {
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            txtViewStdDeviation.setText(errorMessage);
            hideFields();
            return;
        }
        if(response.body() instanceof ForecastResponse) {
            txtViewStdDeviation.setText("Unable to fetch standard deviation.");
        } else if(response.body() instanceof WeatherResponse){
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    private void hideFields() {
        txtViewCurrentTemperature.setVisibility(View.GONE);
        txtViewCurrentTemperatureUnit.setVisibility(View.GONE);
        txtViewCurrentTemperatureFahrenheit.setVisibility(View.GONE);
        txtViewCurrentTemperatureUnitFahrenheit.setVisibility(View.GONE);
        txtViewWindSpeed.setVisibility(View.GONE);
        imgViewCloudiness.setVisibility(View.GONE);
    }
}