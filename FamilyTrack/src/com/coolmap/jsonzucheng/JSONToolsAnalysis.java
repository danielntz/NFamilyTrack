package com.coolmap.jsonzucheng;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.coolmap.info.Location;

public class JSONToolsAnalysis {

	private static final String TAG = null;

	public   JSONToolsAnalysis(){

	}

	//解析单独JSON(带有key值)
	public  void    analysejsonkey(String jsonstring) throws JSONException{

		   JSONObject  jsonobject  = new JSONObject(jsonstring);  //获得整体的json字符串
		   JSONObject  json  = jsonobject.getJSONObject("Person"); //获得key所带有的value值
		   String  name  = json.getString("name");
		   String  sex  =  json.getString("sex");
		   int  age = json.getInt("age");
		   Log.i(TAG, name + sex + age);
		
	}
	//解析JSON数组(带有key值)
	public  void    analysejsonskey(String jsonstring) throws JSONException{
          JSONObject  jsonobject = new JSONObject(jsonstring);
          JSONArray   jsonarray = jsonobject.getJSONArray("Person");           //获得key所带有的value值
          for(int i = 0;i < jsonarray.length(); i++){
		    	 JSONObject   jsonobject2 =  jsonarray.getJSONObject(i);      //json数组中有多个json对象
		         String name  =   jsonobject2.getString("name");
		         String sex  = jsonobject2.getString("sex");
		          int age  =  jsonobject2.getInt("age");
		          Log.i(TAG, name + sex + age);
		     }
          
	}

	//解析单独JSON(不带有key值)
	public  void    analysejsonnokey(String jsonstring) throws JSONException{
             JSONObject  json = new JSONObject(jsonstring);
             String  name  = json.getString("name");
  		     String  sex  =  json.getString("sex");
  		     int  age = json.getInt("age");
  		     Log.i(TAG, name + sex + age);
	}

	//解析JSON数组(不带有key值)
	public  List<Location>    analysejsonsnokey(String jsonstring ) throws JSONException{
                  
		    List<Location> download = new ArrayList<Location>();
		    JSONArray  jsonarray =  new JSONArray(jsonstring);
		    for(int i = 0 ; i < jsonarray.length(); i ++){
		    	String s = String.valueOf(jsonarray.get(i));
		    	JSONObject  jsonobject = new JSONObject(s);
		    	Location  location = new Location();
		    	 String  name  = jsonobject.getString("name");
		    	 location.setOld_name(name);
	  		     double  latitude  =  jsonobject.getDouble("latitude");
	  		     location.setLatitude(latitude);
	  		     double  longtitude = jsonobject.getDouble("longtitude");
	  		     location.setLongtitude(longtitude);
	  		     download.add(location);
	  		   
		    }
		   return download;
		
	}
}
