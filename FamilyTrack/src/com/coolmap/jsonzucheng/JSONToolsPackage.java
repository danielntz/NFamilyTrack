package com.coolmap.jsonzucheng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONToolsPackage {
     
	    private static final String TAG = null;

		public   JSONToolsPackage(){
	    	  
	      }
	
	     //封装单独JSON(带有key值)
	      public  String   createJson() throws JSONException{
	    	   
	    	          String   json;
	    	          JSONObject   jsonobject = new JSONObject();
	    	          JSONObject   jsontostring = new JSONObject();
	    	          jsonobject.put("name", "小明");
	    	          jsonobject.put("sex", "男");
	    	          jsonobject.put("age", 25);
	    	          jsontostring.put("Person", jsonobject);     //给整体json前加上一个key值
	    	          Log.i(TAG, jsontostring.toString());
	    	          return  jsontostring.toString();
	    	     
	      }
	
	    //封装JSON数组(带有key值)
	         public  String   createJsons() throws JSONException{
	        	     
	    	          JSONArray     jsonarray   = new JSONArray();
	    	          JSONObject   jsontostring = new JSONObject();
	    	          for(int i = 0 ; i < 5 ;i++){     //封装5个json，构成一个数组
	    	         JSONObject   jsonobject = new JSONObject();  
	    	          jsonobject.put("name", "小明" + i);
	    	          jsonobject.put("sex", "男" + i );
	    	          jsonobject.put("age", 25 + i);
	    	          jsonarray.put(jsonobject);
	    	          }
	    	          jsontostring.put("Person", jsonarray);     //给整体json前加上一个key值
	    	          Log.i(TAG, jsontostring.toString());
	    	          return  jsontostring.toString();
	         }
	
	     //封装单独JSON(不带有key值)
	         public  String   createJsonnokey() throws JSONException{
		    	   
   	          String   json;
   	          JSONObject   jsonobject = new JSONObject();
   	          JSONObject   jsontostring = new JSONObject();
   	          jsonobject.put("name", "小明");
   	          jsonobject.put("latitude", 345.454);
   	          jsonobject.put("longtitude", 25.23432);
   	          Log.i(TAG, jsonobject.toString());
   	          return jsonobject.toString();
   	     
     }
	    //封装JSON数组(不带有key值)
	         public  String   createJsonsnokey() throws JSONException{
	        	      String   json;
	    	          JSONArray     jsonarray   = new JSONArray();
	    	          JSONObject   jsontostring = new JSONObject();
	    	          for(int i = 0 ; i < 5 ;i++){     //封装5个json，构成一个数组
	    	         JSONObject   jsonobject = new JSONObject();  
	    	          jsonobject.put("name", "小明" + i);
	    	          jsonobject.put("sex", "男" + i );
	    	          jsonobject.put("age", 25 + i);
	    	          jsonarray.put(jsonobject);
	    	          }
	    	      Log.i(TAG, jsonarray.toString());
	    	      return  jsonarray.toString();
	         }
	   
}
