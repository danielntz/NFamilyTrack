package com.family.trackmap;

import android.graphics.Point;

public class data {
       //经度和纬度
	     private   double  Latitude;
	     private   double  Longtitude;
		public data(double latitude, double longtitude) {
			super();
			Latitude = latitude;
			Longtitude = longtitude;
		}
		public double getLatitude() {
			return Latitude;
		}
		public void setLatitude(double latitude) {
			Latitude = latitude;
		}
		public double getLongtitude() {
			return Longtitude;
		}
		public void setLongtitude(double longtitude) {
			Longtitude = longtitude;
		}
	
}
