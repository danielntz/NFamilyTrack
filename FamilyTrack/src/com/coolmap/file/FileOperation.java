package com.coolmap.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import com.coolmap.jsonzucheng.JSONToolsPackage;

import android.util.Log;

public class FileOperation {
   
	   private static final String TAG = null;
       private   static   JSONArray  array = new JSONArray();     //静态变量
	 public  FileOperation(){
		   
	   }
	
		 //写文件在(Sdcard)上采用FileOutputStream方法 ,注意加权限(已json的方式)
		 public void WriteFile1(String path) throws IOException, JSONException{
			 
			 FileOutputStream  fout = new FileOutputStream(path);
			 //构造json字符串(名字，经度，纬度)
			  String json =  new JSONToolsPackage().createJsonnokey();
			  array.put(json);
			  String changarray = array.toString();
			 byte[]  buffer = changarray.getBytes();
			 int len = buffer.length;
			 fout.write(buffer, 0, len);
			 fout.close();
			 Log.i(TAG, "成功");
			 
		 }
		//读文件在(Sdcard)上采用FileInputStream方法，注意加权限注意加权限(已json的方式)
		 public String ReadFile1(String path){
			     String   contentsd = "";
			  try {
			      FileInputStream  input = new FileInputStream(path);
				  byte[] buffer = new byte[input.available()];
				  input.read(buffer);
				  contentsd = new String(buffer);
				//  Log.i(TAG, contentsd);
		          input.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  	 return contentsd;
		 }
	
}
