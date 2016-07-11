package com.coolmap.info;


public class Location  {
  
	   private   String  old_name;
	   private   double longtitude;
	   private   double latitude;
	    
	   public  Location(){
		   
	   }

	public Location(String old_name, double longtitude, double latitude) {
		super();
		this.old_name = old_name;
		this.longtitude = longtitude;
		this.latitude = latitude;
	}

	public String getOld_name() {
		return old_name;
	}

	public void setOld_name(String old_name) {
		this.old_name = old_name;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	   
	   
	
	
}
