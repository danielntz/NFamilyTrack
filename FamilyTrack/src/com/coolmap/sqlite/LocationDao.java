package com.coolmap.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolmap.info.Location;

public class LocationDao {
     
	  private  LocationSQLite   locateopener = null;
	  
	
	  public LocationDao(Context context){
		        locateopener  = new LocationSQLite(context);        
	  }
	  
	  //给表中添加一条记录
	  public  long   addinfo( String old_name , double latitude ,double longtitude){
  	    SQLiteDatabase  db = locateopener.getWritableDatabase();
  	    ContentValues content = new ContentValues();
       //键值对，“”中的内容是数据库表中的属性，必须和表中的属性相同，并且类型也要相同才行
  	    //  content.put("id", id);
          content.put("name", old_name);   
          content.put("latitude",latitude);
          content.put("longtitude", longtitude);
          long id1 = db.insert("Location", null, content);
          return id1;
  }
	  //查找表中所有记录
	  /*public   List<Location> findall(){
  	    SQLiteDatabase db = locateopener.getReadableDatabase();
  	    Cursor cursor = db.query("hero", new String[]{"id","name","latitude","longtitude",},null,null, null, null, null);
         List<Location> list = new  ArrayList<Location>();
         while(cursor.moveToNext()){
      	   int id = cursor.getInt(cursor.getColumnIndex("id"));
      	   String name = cursor.getString(cursor.getColumnIndex("name"));
      	   int  level = cursor.getInt(cursor.getColumnIndex("level"));
      	   String power = cursor.getString(cursor.getColumnIndex("power"));
      	   String  intelligence = cursor.getString(cursor.getColumnIndex("intelligence"));
      	   String   life = cursor.getString(cursor.getColumnIndex("life"));
      	   Location   p = new Location(name,latitude,longtitute);
      	   list.add(p);
         }
         db.close();
			cursor.close();
			return   list;
  }*/
	  
	  
	  //删除表中的一条记录
	    public    void  delete(String name ){
	    	SQLiteDatabase db = locateopener.getReadableDatabase();
	    	String  sql  =  "delete from hero where name=?";
	    	db.execSQL(sql,new Object[]{name});
	    }
	   //删除表中的所有记录
	    public   void   delete(){
	    	SQLiteDatabase  db =  locateopener.getReadableDatabase();
	    	String sql = "delete from hero ";
	    	db.execSQL(sql);
	    }
	    
	    
	   
}
