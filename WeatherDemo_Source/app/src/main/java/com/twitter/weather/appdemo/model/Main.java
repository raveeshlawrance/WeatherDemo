package com.twitter.weather.appdemo.model;

import java.io.Serializable;

public class Main implements Serializable {
	private double temp;
	private double pressure;
	private int humidity;
	private double temp_min;
	private double temp_max;

	public void setTemp(int temp){
		this.temp = temp;
	}

	public int getTemp(){
		return (int) Math.ceil(temp);
	}

	public int getTempInFahrenheit(){
		double fahrenheit = (temp * 9/5) + 32;
		return (int) Math.ceil(fahrenheit);
	}

	public void setPressure(int pressure){
		this.pressure = pressure;
	}

	public double getPressure(){
		return pressure;
	}

	public void setHumidity(int humidity){
		this.humidity = humidity;
	}

	public int getHumidity(){
		return humidity;
	}

	public void setTempMin(int tempMin){
		this.temp_min = tempMin;
	}

	public double getTempMin(){
		return temp_min;
	}

	public void setTempMax(int tempMax){
		this.temp_max = tempMax;
	}

	public double getTempMax(){
		return temp_max;
	}
}