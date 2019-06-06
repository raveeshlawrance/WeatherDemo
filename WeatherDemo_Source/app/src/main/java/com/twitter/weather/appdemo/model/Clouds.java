package com.twitter.weather.appdemo.model;

import android.view.View;

import java.io.Serializable;

public class Clouds implements Serializable {
	private int all;

	public void setAll(int all){
		this.all = all;
	}

	public int getAll(){
		return all;
	}

	public int isCloudVisible(){
		return getAll() > 50 ? View.VISIBLE : View.GONE;
	}
}