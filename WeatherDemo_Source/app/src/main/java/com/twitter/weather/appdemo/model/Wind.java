package com.twitter.weather.appdemo.model;

import java.io.Serializable;

public class Wind implements Serializable {
	private double speed;
	private double deg;

	public void setSpeed(double speed){
		this.speed = speed;
	}

	public Object getSpeed(){
		return speed;
	}

	public void setDeg(int deg){
		this.deg = deg;
	}

	public double getDeg(){
		return deg;
	}
}