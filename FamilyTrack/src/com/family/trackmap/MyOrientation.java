package com.family.trackmap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
/**
 * ������
 * @author jsjxy
 *
 */
public class MyOrientation    implements SensorEventListener{
        
	      private   SensorManager   sensorManger ;     //������������ 
	      private  Context context;                                       //������
	      private  Sensor  sensor;                                          //������
	
		private   OrientainChanged    changed ;       //�ӿڶ���
	      private   float   lastX ;                                             //X��
	      private  float  lastY;                                              //Y��
	      private  float  lastZ;                                           //Z��   ������ķ���
	      
	      
	     public     MyOrientation  (Context  context ){
	    	         this.context  = context;
	     }
	     //��������
	     public  void start(){
	    	    sensorManger  =  (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);    //���ϵͳ�Ĵ���������
              if(sensorManger != null){
            	         sensor = sensorManger .getDefaultSensor(sensor.TYPE_ORIENTATION);  //��÷������͵Ĵ�����
                    }
             if(sensor != null){
            	   sensorManger.registerListener(this, sensor, sensorManger.SENSOR_DELAY_UI);       //�������������һ����Խ�׼ȷ�ľ���
             }
	     }
	       //ֹͣ����
	     public  void stop(){
	    	     sensorManger.unregisterListener(this);     //ֹͣ��λ
	     }
	@Override
	//���������ı�ʱ
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		    if(event.sensor.getType()== sensor.TYPE_ORIENTATION){    //�����õ��Ƿ��򴫸���
		    	
		    	    float   x = event.values[SensorManager.DATA_X];
		    	  if(Math.abs(x - lastX) > 1.0 ){
		    		   //�ص���֪ͨ���������
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
	//�ص��ӿ�
     public  interface  OrientainChanged{
    	             void  Onchanged(float x); 
     }
	
}
