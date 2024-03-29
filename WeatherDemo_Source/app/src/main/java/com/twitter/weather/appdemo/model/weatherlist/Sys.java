package com.twitter.weather.appdemo.model.weatherlist;

import java.io.Serializable;

public class Sys implements Serializable {
	private int type;
	private int id;
	private Object message;
	private String country;
	private int sunrise;
	private int sunset;

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setMessage(Object message){
		this.message = message;
	}

	public Object getMessage(){
		return message;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setSunrise(int sunrise){
		this.sunrise = sunrise;
	}

	public int getSunrise(){
		return sunrise;
	}

	public void setSunset(int sunset){
		this.sunset = sunset;
	}

	public int getSunset(){
		return sunset;
	}
}