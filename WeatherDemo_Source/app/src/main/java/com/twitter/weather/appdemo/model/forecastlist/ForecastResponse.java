package com.twitter.weather.appdemo.model.forecastlist;

import android.os.Parcel;
import android.os.Parcelable;

import com.twitter.weather.appdemo.model.weatherlist.WeatherResponse;

import java.util.List;
import java.io.Serializable;

public class ForecastResponse implements Parcelable {
	private String cod;
	private String message;
	private int cnt;
	private List<WeatherResponse> list;
	private City city;

	protected ForecastResponse(Parcel in) {
		cod = in.readString();
		message = in.readString();
		cnt = in.readInt();
		list = in.createTypedArrayList(WeatherResponse.CREATOR);
	}

	public static double getStandardDeviation(List<WeatherResponse> list) {
		if(list == null)
			return 0.00;
		int numberOfDays = list.size();
		double averageTemp = 0;
		double sumOfDeviation = 0;
		for(WeatherResponse weatherResponse : list) {
			averageTemp = averageTemp + weatherResponse.getMain().getTemp();
		}
		averageTemp = averageTemp/numberOfDays;
		for(WeatherResponse weatherResponse : list) {
			sumOfDeviation = sumOfDeviation + Math.pow(weatherResponse.getMain().getTemp() - averageTemp, 2);
		}
		double standardDeviation = Math.sqrt((sumOfDeviation / (numberOfDays - 1)));

		return Double.parseDouble(String.format("%.2f", standardDeviation));
	}

	public static final Creator<ForecastResponse> CREATOR = new Creator<ForecastResponse>() {
		@Override
		public ForecastResponse createFromParcel(Parcel in) {
			return new ForecastResponse(in);
		}

		@Override
		public ForecastResponse[] newArray(int size) {
			return new ForecastResponse[size];
		}
	};

	public void setCod(String cod){
		this.cod = cod;
	}

	public String getCod(){
		return cod;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public Object getMessage(){
		return message;
	}

	public void setCnt(int cnt){
		this.cnt = cnt;
	}

	public int getCnt(){
		return cnt;
	}

	public void setList(List<WeatherResponse> list){
		this.list = list;
	}

	public List<WeatherResponse> getList(){
		return list;
	}

	public void setCity(City city){
		this.city = city;
	}

	public City getCity(){
		return city;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(cod);
		dest.writeString(message);
		dest.writeInt(cnt);
		dest.writeTypedList(list);
	}
}