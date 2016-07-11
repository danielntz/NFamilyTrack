package com.family.trackmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.coolmap.file.FileOperation;
import com.coolmap.info.Location;
import com.coolmap.jsonzucheng.JSONToolsAnalysis;
import com.coolmap.sqlite.LocationDao;
import com.coolmap.sqlite.LocationSQLite;
import com.family.map.R;
import com.family.trackmap.MyOrientation.OrientainChanged;

public class MainActivity extends Activity implements android.view.View.OnClickListener{

	private static final String TAG = null;
	private MapView  mapview;
	private BaiduMap  mapdu ;
	private Button   tanchu;
	private PopupWindow window ;
	private  int  flag =1;
	private Context context;
	private  TextView one;
	private  TextView two;
	private  TextView three;
	private TextView mylocate;
	private TextView   addwu;
	//覆盖物相关
	private   BitmapDescriptor     mMarker;
	private   BitmapDescriptor    moldmanMarker;
	private  LinearLayout    MarkerLinear;
	private  LayoutInflater  inflater;
	//相关定位
	private  LocationClient client;
	private Mylocation    locationlistener;
	private  boolean  firsiIn  = true;   //第一次使用地图的定位
	private  double    lontitude;    //经度
	private  double  latititude;      //纬度
	private  MyOrientation    myorientaion ;   //定义方向传感器
	//定位还需要配置service
	//自定义定位图标
	private BitmapDescriptor  mylocationbitmap;
	private float  CurrentX ; 
	//绘制路线
	private   List<LatLng>latLngPolygon ;
	private   List<LatLng> oldmanPolygon;
	//动态绘制路线
	private  List<LatLng>  dongtailujing   = new ArrayList<LatLng>();
	private int  i = 0 ;   //标记点得数
	private  int k = 0;
    private	data    dos[]    = new data[5];
    //数据库变量
	 private   LocationSQLite   locationopenhelper = null;
  	 LocationDao      dao = null;    //对数据库进行各种操作
   //隐式调用另一个程序的名字
  	  private   String action  =  "com.nihao.download.service" ;        //action
  	  //判断service的打开还是关闭
  	  private   boolean  serviceflag = true;
  	  //从文件中读取的bmob数据
  	  private String   bmobstring;
  	  private  List<Location> huoqulocation = new ArrayList<Location>();
     //老人的位置信息
  	  private  double   oldlatitude;
  	  private  double   oldlongtitude;
  	  //绘制路线的数据集合
  	  private  List<Location> huoquluxian ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		//Bmob.initialize(this,"13197bceaa649971f7f0655de655885e");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
        initdot();
		mapview = (MapView)findViewById(R.id.bmapView);
		this.context = this;
		MapStatusUpdate   chengdu = MapStatusUpdateFactory.zoomTo(15.0f);   //初始化地图的大小范围
		mapdu = mapview.getMap();
		mapdu.setMapStatus(chengdu);
	
	//	drawroad();
		initLocation();
		initMapWu();
		InitSQLite();
	//	new Thread(new dynaticwrite()).start();
		
     //  new Thread(new dongtailujing()).start();
      // startLocationBomb();
      
		mapdu.setOnMarkerClickListener(new OnMarkerClickListener() {

			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				Bundle   bundle =  marker.getExtraInfo();     //获得Marker中的信息
				//	   info    dedao       =  (info) bundle.getSerializable("info");
				//	   Toast.makeText(context, dedao.getName(), 0).show();
				Intent  intent = new Intent();
				intent.setClass(context, detail_content.class);
				intent.putExtras(bundle);      //传递bundle的值
				startActivity(intent);
				return true;
			}
		});

	}  
	
	//启动隐式Intent
	public  void   startLocationBomb(){
		      startService(new Intent(action));
		  
	}
	
	//初始化数据表
	public   void  InitSQLite(){
		//创建或打开一个数据库，进行操作
    	locationopenhelper = new LocationSQLite(this);
		SQLiteDatabase  db = locationopenhelper.getWritableDatabase();
		//以读写的方式获得一个数据库实例，然后可以对它进行操作数据库用完要关闭*/
		writetoLocationTable("xiaoming",2323.435,345.234);
	}
	//覆盖物初始化
	private void initMapWu() {
		// TODO Auto-generated method stub
		mMarker   = BitmapDescriptorFactory.fromResource(R.drawable.dingweitubiao);     //获得定位的图片
       moldmanMarker = BitmapDescriptorFactory.fromResource(R.drawable.oldmandot); // 获得老年人定位照片

	}
	
	//从sdcard中读取数据
		public    List<Location>  readfile(String  path ) throws JSONException{
			 //判断文件是否存在
			bmobstring =   new  FileOperation().ReadFile1(path);
			  //对bmob数据进行json解析
			return   new JSONToolsAnalysis().analysejsonsnokey(bmobstring);
			  
		}

	private void initLocation() {
		// TODO Auto-generated method stub
		client = new LocationClient(this);
		locationlistener = new Mylocation();
		client.registerLocationListener(locationlistener);
		//设置location的一些参数
		LocationClientOption  option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);       //获得当前的地址
		option.setOpenGps(true);
		option.setScanSpan(1000);
		client.setLocOption(option);     //设置完成上述功能
		//初始化图标
		mylocationbitmap  = BitmapDescriptorFactory.fromResource(R.drawable.arrow);
		//初始化 方向传感器
		myorientaion = new MyOrientation(this);
		myorientaion.setChanged(new OrientainChanged() {

			@Override
			public void Onchanged(float x) {
				// TODO Auto-generated method stub
				CurrentX = x;
			}
		});
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapview.onPause();
	}  
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mapdu.setMyLocationEnabled(true);
		if(!client.isStarted())
			client.start();
		//开启方向传感器
		myorientaion.start();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mapdu.setMyLocationEnabled(false);
		client.stop();
		//停止方向传感器
		myorientaion.stop();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mapview.destroyDrawingCache();
		File file = new File("sdcard/download.txt");
		if(file.exists()){
			file.delete();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapview.onResume();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			if(serviceflag)
	    	{
	    		startService(new Intent(action));
	    		serviceflag = false;
	    		Toast.makeText(getApplicationContext(), "开启服务正在获取.......", 0).show();
	    	}
	    	else{
	    		
	    		stopService(new Intent(action));
	    		serviceflag = true;
	    		Toast.makeText(getApplicationContext(), "停止服务", 0).show();
	    	}
			return true;
		}
		if(id == R.id.common_map){
			mapdu.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			return true;
		}
		if(id == R.id.star_map){
			mapdu.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			return true;
		}
		if(id == R.id.traffic_map){
			if( mapdu.isTrafficEnabled()){
				mapdu.setTrafficEnabled(false);
			}
			else{
				mapdu.setTrafficEnabled(true);
			}
			return true;
		}
		if(id == R.id.locate){
			LatLng lat = new LatLng(latititude, lontitude);//获得经度和纬度
			MapStatusUpdate    map = MapStatusUpdateFactory.newLatLng(lat);
			mapdu.animateMapStatus(map);    //地图当前位置已动画的形式展现      
			return true;
		}
		if(id == R.id.addthing){
			Info ii = new Info();
			ii.jianli();
			AddOverly(ii.hhh);
			return true;
		}
	    if(id == R .id.locateoldman){
	    	// startService(new Intent(action));
			 // Intent intent = new Intent("com.view.action");
			//  startActivity(intent);
	  
	    	    //判断文件是否存在
	    	  File file   = new File("sdcard/download.txt");
	    	  if(file.exists()){
	    		   //说明文件存在
	    			try {
						huoqulocation = readfile("sdcard/download.txt");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         // Toast.makeText(getApplicationContext(), huoqulocation.get(huoqulocation.size() - 1).getOld_name(), 0).show();
	    	          oldlatitude =  huoqulocation.get(huoqulocation.size() - 1).getLatitude();     //获取老人最后的纬度
	    	          oldlongtitude = huoqulocation.get(huoqulocation.size() - 1 ).getLongtitude();  //获取老人最后的经度
	    	          //根据获取到的信息进行定位
	    	          sureoldmanpostion(oldlatitude, oldlongtitude);
	    	   /*   	LatLng lat = new LatLng(oldlatitude, oldlongtitude);//获得经度和纬度
	    			MapStatusUpdate    map = MapStatusUpdateFactory.newLatLng(lat);
	    			mapdu.animateMapStatus(map);    //地图当前位置以动画的形式展现       */
	    	  }
	    	  else{
	    		    Toast.makeText(getApplicationContext(), "请先获取位置信息", 0).show();
	    	  }
		
       return  true;
	    }
	    if( id == R.id.watchguiji){
	    	  File file   = new File("sdcard/download.txt");
	    	  if(file.exists()){
	    		  //说明文件存在,从文件中取出数据，进行绘制路线
	    			try {
						huoquluxian = readfile("sdcard/download.txt");
						drawoldmanroad(huoquluxian);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	  }
	    	  else{
	    		  Toast.makeText(getApplicationContext(), "请先获取位置信息", 0).show();
	    	  }
	    }
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void onClick(View v) {


	}
	/**
	 * 添加覆盖物
	 * @param hhh
	 */
	private void AddOverly(List<Info> hhh) {
		// TODO Auto-generated method stub
		mapdu.clear();   //清除图层上的东西
		//定义经纬度
		LatLng  latlng = null;
		Marker marker = null;
		OverlayOptions   options;
		//进行一个循环
		for(Info  nihao :  hhh){
			//经纬度      
			latlng = new LatLng(nihao.getLatitude(),nihao.getLongitude());
			//图标
			options = new MarkerOptions().position(latlng).icon(mMarker).zIndex(5);      //添加到图层的最高层
			marker = (Marker) mapdu.addOverlay(options);
			//marker携带一些值
			Bundle bundle = new Bundle();
			bundle.putSerializable("info", nihao);       //实例化对象
			marker.setExtraInfo(bundle);                       //marker 携带信息也就是info类表中的信息

		}
		//每次添加完图层的时候，把地图移动到第一个图层的位置
		MapStatusUpdate  msu = MapStatusUpdateFactory.newLatLng(latlng);
		mapdu.setMapStatus(msu);

	}
	private  class Mylocation implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			MyLocationData   data = new MyLocationData.Builder()//
			.direction(CurrentX)//
			.accuracy(location.getRadius())//
			.latitude(location.getLatitude())//
			.longitude(location.getLongitude())//
			.build();
			MyLocationConfiguration  config =  new MyLocationConfiguration(com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.NORMAL, true, mylocationbitmap);
			mapdu.setMyLocationConfigeration(config);
			mapdu.setMyLocationData(data);
			//更新经纬度
			latititude = location.getLatitude();     //把当前的经度保存
			lontitude = location.getLongitude();  //把当前的纬度保存
			Log.i(TAG, latititude +" " +lontitude);
			//05-30 14:18:11.875: I/(8365): 34.238335 108.967124

			if(firsiIn){
				LatLng lat = new LatLng(location.getLatitude(), location.getLongitude());//获得经度和纬度
				MapStatusUpdate    map = MapStatusUpdateFactory.newLatLng(lat);
				mapdu.animateMapStatus(map);    //地图当前位置已动画的形式展现
				firsiIn = false;
				Toast.makeText(context, location.getAddrStr(), Toast.LENGTH_SHORT).show();   //把当前你所在的位置show出来
			}
		}
	}
	//静态绘制经过的路线
	public   void   drawroad(){
		LatLng latlng1 = new LatLng(34.238335 ,108.967124);
		LatLng latlng2 = new LatLng(34.2418010000,108.9750420000);
		LatLng latlng3 = new LatLng(34.2410550000,108.9788870000);
		LatLng latlng4 = new LatLng(34.2500070000,108.9857860000);
		LatLng  latlng5 = new LatLng(34.2244460000,108.9959550000);
		LatLng  latlng6 = new LatLng(34.2292210000,108.9360200000);
		latLngPolygon = new ArrayList<LatLng>();
		latLngPolygon.add(latlng1);
		latLngPolygon.add(latlng2);
		latLngPolygon.add(latlng3);
		latLngPolygon.add(latlng4);
		latLngPolygon.add(latlng5);
		latLngPolygon.add(latlng6);
		PolylineOptions po = new PolylineOptions().color(getResources().getColor(android.R.color.holo_orange_light)).width(12).points(latLngPolygon);
		mapdu.addOverlay(po);
		Log.i(TAG, "sdfsdf");
	}
	//静态绘制经过的路线
		public   void   drawoldmanroad( List<Location> oldmantrack){
		   LatLng  latlng [ ] = new LatLng[oldmantrack.size()];
		    oldmanPolygon = new ArrayList<LatLng>();
		   
		       for( int i  = 0;  i < oldmantrack.size() ; i ++){
		    	   if(oldmantrack.get(i).getLatitude() == 0 ||oldmantrack.get(i).getLongtitude() == 0)
		    		    continue;
		    	   else{
		    	      latlng[i] = new LatLng(oldmantrack.get(i).getLatitude(), oldmantrack.get(i).getLongtitude());
		    	      oldmanPolygon.add(latlng[i]);   
		    	   }
		       }
		   	PolylineOptions po = new PolylineOptions().color(getResources().getColor(android.R.color.holo_orange_light)).width(12).points(oldmanPolygon);
		   	mapdu.addOverlay(po);
		/*	LatLng latlng1 = new LatLng(34.238335 ,108.967124);
			LatLng latlng2 = new LatLng(34.2418010000,108.9750420000);
			LatLng latlng3 = new LatLng(34.2410550000,108.9788870000);
			LatLng latlng4 = new LatLng(34.2500070000,108.9857860000);
			LatLng  latlng5 = new LatLng(34.2244460000,108.9959550000);
			LatLng  latlng6 = new LatLng(34.2292210000,108.9360200000);
			latLngPolygon = new ArrayList<LatLng>();
			latLngPolygon.add(latlng1);
			latLngPolygon.add(latlng2);
			latLngPolygon.add(latlng3);
			latLngPolygon.add(latlng4);
			latLngPolygon.add(latlng5);
			latLngPolygon.add(latlng6);
			PolylineOptions po = new PolylineOptions().color(getResources().getColor(android.R.color.holo_orange_light)).width(12).points(latLngPolygon);
			mapdu.addOverlay(po);*/
			Log.i(TAG, "sdfsdf");
		}
	//动态绘制路径
	
	  public  class   dongtailujing implements Runnable{
          
		@Override
		public void run() {
			// TODO Auto-generated method stub
			        while(i < 5){
			        	if( i != 4)
			        	         k = i + 1;
			        	else
			        		   k = i;
			        	    while( k >= 0){
			        	      LatLng  data = new LatLng(dos[k].getLatitude(), dos[k].getLongtitude());  
			        	     
			        	      dongtailujing.add(data);
			        	      k-- ;
			        	    }
			        	      runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
								    PolylineOptions po = new PolylineOptions().color(getResources().getColor(android.R.color.holo_orange_light)).width(12).points(dongtailujing);
				        			mapdu.addOverlay(po);
				        			dongtailujing.clear();
								}
							});
			        	      try {
								Thread.sleep(1000);
								i++;
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        	  
			        }
			        Thread.interrupted();
		}
		  
	  }
	
	  public  void initdot(){
		  //测试四个定点，初始化	
			dos[0] = new data(34.2596600000,108.9433500000);
			dos[1] = new data(34.2613310000,108.9794260000);
			dos[3] = new data(34.2569150000,108.9430620000);
			dos[2] = new data(34.2533340000,108.9713770000);
			dos[4] = new data(34.2596600000,108.9433500000);
	  }
	
	//向数据表中写入数据(SQLite)
		public   void   writetoLocationTable(String name , double latitude, double longtitude){
			dao = new LocationDao(this);
			dao.addinfo(name,latitude,longtitude);	  
		}
	   //写文件，以动态的json形式
	   public  void   writeFile(String path) throws IOException, JSONException{
		       FileOperation   write = new FileOperation();
		       write.WriteFile1(path);
		       
	   }
	   
	   public   class   dynaticwrite implements  Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			     while(true){
			    	   try {
						writeFile("sdcard/hahah.txt");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	   try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			     }
		}
		   
	   }
	   
	   //添加老人定位的图标(相对于覆盖物)
	   public   void   sureoldmanpostion(double latittude , double longtitude){
			mapdu.clear();   //清除图层上的东西
			//定义经纬度
			LatLng  latlng = null;
			Marker marker = null;
			OverlayOptions   options;
				latlng = new LatLng(latittude,longtitude);
				//图标
				options = new MarkerOptions().position(latlng).icon(moldmanMarker).zIndex(5);      //添加到图层的最高层
				marker = (Marker) mapdu.addOverlay(options);
				//marker携带一些值
			//	Bundle bundle = new Bundle();
			//	bundle.putSerializable("info", nihao);       //实例化对象
			//	marker.setExtraInfo(bundle);                       //marker 携带信息也就是info类表中的信息

			//每次添加完图层的时候，把地图移动到第一个图层的位置
			MapStatusUpdate  msu = MapStatusUpdateFactory.newLatLng(latlng);
			mapdu.animateMapStatus(msu);    //地图当前位置已动画的形式展现    
			//mapdu.setMapStatus(msu);
	   }
	
	 
}

