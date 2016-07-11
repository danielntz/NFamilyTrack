package com.family.trackmap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
/**
 * 传感器
 * @author jsjxy
 *
 */
public class MyOrientation    implements SensorEventListener{
        
	      private   SensorManager   sensorManger ;     //传感器管理者 
	      private  Context context;                                       //上下文
	      private  Sensor  sensor;                                          //传感器
	
		private   OrientainChanged    changed ;       //接口对象
	      private   float   lastX ;                                             //X轴
	      private  float  lastY;                                              //Y轴
	      private  float  lastZ;                                           //Z轴   朝向你的方向
	      
	      
	     public     MyOrientation  (Context  context ){
	    	         this.context  = context;
	     }
	     //开启监听
	     public  void start(){
	    	    sensorManger  =  (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);    //获得系统的传感器服务
              if(sensorManger != null){
            	         sensor = sensorManger .getDefaultSensor(sensor.TYPE_ORIENTATION);  //获得方向类型的传感器
                    }
             if(sensor != null){
            	   sensorManger.registerListener(this, sensor, sensorManger.SENSOR_DELAY_UI);       //第三个参数获得一个相对较准确的经度
             }
	     }
	       //停止监听
	     public  void stop(){
	    	     sensorManger.unregisterListener(this);     //停止定位
	     }
	@Override
	//当方向发生改变时
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		    if(event.sensor.getType()== sensor.TYPE_ORIENTATION){    //如果获得的是方向传感器
		    	
		    	    float   x = event.values[SensorManager.DATA_X];
		    	  if(Math.abs(x - lastX) > 1.0 ){
		    		   //回调，通知主界面更新
		    		  if(changed != null){
		    			     changed.Onchanged(x);
		    		  }
		    	  }
		    	  lastX = x ;
		    }
	}
     public void setChanged(OrientainChanged changed) {
			this.changed = changed;
		}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	//回调接口
     public  interface  OrientainChanged{
    	             void  Onchanged(float x); 
     }
	
}
