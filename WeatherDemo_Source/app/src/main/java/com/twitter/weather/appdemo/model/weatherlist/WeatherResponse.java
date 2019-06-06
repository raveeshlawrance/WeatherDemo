package com.twitter.weather.appdemo.model.weatherlist;

import android.os.Parcel;
import android.os.Parcelable;

import com.twitter.weather.appdemo.model.Clouds;
import com.twitter.weather.appdemo.model.Coord;
import com.twitter.weather.appdemo.model.Main;
import com.twitter.weather.appdemo.model.WeatherItem;
import com.twitter.weather.appdemo.model.Wind;

import java.util.List;

public class WeatherResponse implements Parcelable {
	private Coord coord;
	private List<WeatherItem> weather;
	private String base;
	private Main main;
	private int visibility;
	private Wind wind;
	private Clouds clouds;
	private int dt;
	private Sys sys;
	private int id;
	private String name;
	private int cod;

	protected WeatherResponse(Parcel in) {
		base = in.readString();
		visibility = in.readInt();
		dt = in.readInt();
		id = in.readInt();
		name = in.readString();
		cod = in.readInt();
	}

	public static final Creator<WeatherResponse> CREATOR = new Creator<WeatherResponse>() {
		@Override
		public WeatherResponse createFromParcel(Parcel in) {
			return new WeatherResponse(in);
		}

		@Override
		public WeatherResponse[] newArray(int size) {
			return new WeatherResponse[size];
		}
	};

	public void setCoord(Coord coord){
		this.coord = coord;
	}

	public Coord getCoord(){
		return coord;
	}

	public void setWeather(List<WeatherItem> weather){
		this.weather = weather;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public void setBase(String base){
		this.base = base;
	}

	public String getBase(){
		return base;
	}

	public void setMain(Main main){
		this.main = main;
	}

	public Main getMain(){
		return main != null ? main : new Main();
	}

	public void setVisibility(int visibility){
		this.visibility = visibility;
	}

	public int getVisibility(){
		return visibility;
	}

	public void setWind(Wind wind){
		this.wind = wind;
	}

	public Wind getWind() {
		return wind != null ? wind : new Wind();
	}

	public void setClouds(Clouds clouds){
		this.clouds = clouds;
	}

	public Clouds getClouds(){
		return clouds != null ? clouds : new Clouds();
	}

	public void setDt(int dt){
		this.dt = dt;
	}

	public int getDt(){
		return dt;
	}

	public void setSys(Sys sys){
		this.sys = sys;
	}

	public Sys getSys(){
		return sys;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCod(int cod){
		this.cod = cod;
	}

	public int getCod(){
		return cod;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(base);
		dest.writeInt(visibility);
		dest.writeInt(dt);
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeInt(cod);
	}
}