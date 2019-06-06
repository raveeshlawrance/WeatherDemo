package com.twitter.weather.appdemo.model;

import java.io.Serializable;

public class Coord implements Serializable {
	private Object lon;
	private Object lat;

	public void setLon(Object lon){
		this.lon = lon;
	}

	public Object getLon(){
		return lon;
	}

	public void setLat(Object lat){
		this.lat = lat;
	}

	public Object getLat(){
		return lat;
	}
}