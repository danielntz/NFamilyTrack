package com.family.trackmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.family.map.R;


/**
 * 覆盖物的属性
 * @author jsjxy
 *
 */
public class Info   implements Serializable {
       
	      private    double      latitude;       //经度
	      private   double      longitude;   //纬度
          private  int    readId;                //图片
          private  String name;            //名字
          private  String distance;       //距离
          private  int   zan;                  //赞的数量
           
          public     List<Info>  hhh = new ArrayList<Info>();
           
          
           public void  jianli(){
        	           hhh.add(new Info(32.3,108.5,R.drawable.nibuhao,"罗马帝国","距离100米",123));
        	           hhh.add(new Info(32.5,108.34,R.drawable.nihao,"故宫","距离300米",334));
        	           hhh.add(new Info(36.5,103.34,R.drawable.wohao,"年吗","距离500米",555));
        	           hhh.add(new Info(39.5,109.34,R.drawable.wobuhao,"加利福尼亚","距离120米",444));   
          }
       //重载构造函数
	      public Info(double latitude, double longitude, int readId, String name,
				String distance, int zan) {
			super();
			this.latitude = latitude;
			this.longitude = longitude;
			this.readId = readId;
			this.name = name;
			this.distance = distance;
			this.zan = zan;
			
		}
	    
		public Info() {
			// TODO Auto-generated constructor stub
		}
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
		public int getReadId() {
			return readId;
		}
		public void setReadId(int readId) {
			this.readId = readId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDistance() {
			return distance;
		}
		public void setDistance(String distance) {
			this.distance = distance;
		}
		public int getZan() {
			return zan;
		}
		public void setZan(int zan) {
			this.zan = zan;
		}
		 
}
