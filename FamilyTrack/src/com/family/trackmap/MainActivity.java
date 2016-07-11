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
	//���������
	private   BitmapDescriptor     mMarker;
	private   BitmapDescriptor    moldmanMarker;
	private  LinearLayout    MarkerLinear;
	private  LayoutInflater  inflater;
	//��ض�λ
	private  LocationClient client;
	private Mylocation    locationlistener;
	private  boolean  firsiIn  = true;   //��һ��ʹ�õ�ͼ�Ķ�λ
	private  double    lontitude;    //����
	private  double  latititude;      //γ��
	private  MyOrientation    myorientaion ;   //���巽�򴫸���
	//��λ����Ҫ����service
	//�Զ��嶨λͼ��
	private BitmapDescriptor  mylocationbitmap;
	private float  CurrentX ; 
	//����·��
	private   List<LatLng>latLngPolygon ;
	private   List<LatLng> oldmanPolygon;
	//��̬����·��
	private  List<LatLng>  dongtailujing   = new ArrayList<LatLng>();
	private int  i = 0 ;   //��ǵ����
	private  int k = 0;
    private	data    dos[]    = new data[5];
    //���ݿ����
	 private   LocationSQLite   locationopenhelper = null;
  	 LocationDao      dao = null;    //�����ݿ���и��ֲ���
   //��ʽ������һ�����������
  	  private   String action  =  "com.nihao.download.service" ;        //action
  	  //�ж�service�Ĵ򿪻��ǹر�
  	  private   boolean  serviceflag = true;
  	  //���ļ��ж�ȡ��bmob����
  	  private String   bmobstring;
  	  private  List<Location> huoqulocation = new ArrayList<Location>();
     //���˵�λ����Ϣ
  	  private  double   oldlatitude;
  	  private  double   oldlongtitude;
  	  //����·�ߵ����ݼ���
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
		MapStatusUpdate   chengdu = MapStatusUpdateFactory.zoomTo(15.0f);   //��ʼ����ͼ�Ĵ�С��Χ
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
				Bundle   bundle =  marker.getExtraInfo();     //���Marker�е���Ϣ
				//	   info    dedao       =  (info) bundle.getSerializable("info");
				//	   Toast.makeText(context, dedao.getName(), 0).show();
				Intent  intent = new Intent();
				intent.setClass(context, detail_content.class);
				intent.putExtras(bundle);      //����bundle��ֵ
				startActivity(intent);
				return true;
			}
		});

	}  
	
	//������ʽIntent
	public  void   startLocationBomb(){
		      startService(new Intent(action));
		  
	}
	
	//��ʼ�����ݱ�
	public   void  InitSQLite(){
		//�������һ�����ݿ⣬���в���
    	locationopenhelper = new LocationSQLite(this);
		SQLiteDatabase  db = locationopenhelper.getWritableDatabase();
		//�Զ�д�ķ�ʽ���һ�����ݿ�ʵ����Ȼ����Զ������в������ݿ�����Ҫ�ر�*/
		writetoLocationTable("xiaoming",2323.435,345.234);
	}
	//�������ʼ��
	private void initMapWu() {
		// TODO Auto-generated method stub
		mMarker   = BitmapDescriptorFactory.fromResource(R.drawable.dingweitubiao);     //��ö�λ��ͼƬ
       moldmanMarker = BitmapDescriptorFactory.fromResource(R.drawable.oldmandot); // ��������˶�λ��Ƭ

	}
	
	//��sdcard�ж�ȡ����
		public    List<Location>  readfile(String  path ) throws JSONException{
			 //�ж��ļ��Ƿ����
			bmobstring =   new  FileOperation().ReadFile1(path);
			  //��bmob���ݽ���json����
			return   new JSONToolsAnalysis().analysejsonsnokey(bmobstring);
			  
		}

	private void initLocation() {
		// TODO Auto-generated method stub
		client = new LocationClient(this);
		locationlistener = new Mylocation();
		client.registerLocationListener(locationlistener);
		//����location��һЩ����
		LocationClientOption  option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);       //��õ�ǰ�ĵ�ַ
		option.setOpenGps(true);
		option.setScanSpan(1000);
		client.setLocOption(option);     //���������������
		//��ʼ��ͼ��
		mylocationbitmap  = BitmapDescriptorFactory.fromResource(R.drawable.arrow);
		//��ʼ�� ���򴫸���
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
		//�������򴫸���
		myorientaion.start();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mapdu.setMyLocationEnabled(false);
		client.stop();
		//ֹͣ���򴫸���
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
	    		Toast.makeText(getApplicationContext(), "�����������ڻ�ȡ.......", 0).show();
	    	}
	    	else{
	    		
	    		stopService(new Intent(action));
	    		serviceflag = true;
	    		Toast.makeText(getApplicationContext(), "ֹͣ����", 0).show();
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
			LatLng lat = new LatLng(latititude, lontitude);//��þ��Ⱥ�γ��
			MapStatusUpdate    map = MapStatusUpdateFactory.newLatLng(lat);
			mapdu.animateMapStatus(map);    //��ͼ��ǰλ���Ѷ�������ʽչ��      
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
	  
	    	    //�ж��ļ��Ƿ����
	    	  File file   = new File("sdcard/download.txt");
	    	  if(file.exists()){
	    		   //˵���ļ�����
	    			try {
						huoqulocation = readfile("sdcard/download.txt");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         // Toast.makeText(getApplicationContext(), huoqulocation.get(huoqulocation.size() - 1).getOld_name(), 0).show();
	    	          oldlatitude =  huoqulocation.get(huoqulocation.size() - 1).getLatitude();     //��ȡ��������γ��
	    	          oldlongtitude = huoqulocation.get(huoqulocation.size() - 1 ).getLongtitude();  //��ȡ�������ľ���
	    	          //���ݻ�ȡ������Ϣ���ж�λ
	    	          sureoldmanpostion(oldlatitude, oldlongtitude);
	    	   /*   	LatLng lat = new LatLng(oldlatitude, oldlongtitude);//��þ��Ⱥ�γ��
	    			MapStatusUpdate    map = MapStatusUpdateFactory.newLatLng(lat);
	    			mapdu.animateMapStatus(map);    //��ͼ��ǰλ���Զ�������ʽչ��       */
	    	  }
	    	  else{
	    		    Toast.makeText(getApplicationContext(), "���Ȼ�ȡλ����Ϣ", 0).show();
	    	  }
		
       return  true;
	    }
	    if( id == R.id.watchguiji){
	    	  File file   = new File("sdcard/download.txt");
	    	  if(file.exists()){
	    		  //˵���ļ�����,���ļ���ȡ�����ݣ����л���·��
	    			try {
						huoquluxian = readfile("sdcard/download.txt");
						drawoldmanroad(huoquluxian);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	  }
	    	  else{
	    		  Toast.makeText(getApplicationContext(), "���Ȼ�ȡλ����Ϣ", 0).show();
	    	  }
	    }
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void onClick(View v) {


	}
	/**
	 * ��Ӹ�����
	 * @param hhh
	 */
	private void AddOverly(List<Info> hhh) {
		// TODO Auto-generated method stub
		mapdu.clear();   //���ͼ���ϵĶ���
		//���徭γ��
		LatLng  latlng = null;
		Marker marker = null;
		OverlayOptions   options;
		//����һ��ѭ��
		for(Info  nihao :  hhh){
			//��γ��      
			latlng = new LatLng(nihao.getLatitude(),nihao.getLongitude());
			//ͼ��
			options = new MarkerOptions().position(latlng).icon(mMarker).zIndex(5);      //��ӵ�ͼ�����߲�
			marker = (Marker) mapdu.addOverlay(options);
			//markerЯ��һЩֵ
			Bundle bundle = new Bundle();
			bundle.putSerializable("info", nihao);       //ʵ��������
			marker.setExtraInfo(bundle);                       //marker Я����ϢҲ����info����е���Ϣ

		}
		//ÿ�������ͼ���ʱ�򣬰ѵ�ͼ�ƶ�����һ��ͼ���λ��
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
			//���¾�γ��
			latititude = location.getLatitude();     //�ѵ�ǰ�ľ��ȱ���
			lontitude = location.getLongitude();  //�ѵ�ǰ��γ�ȱ���
			Log.i(TAG, latititude +" " +lontitude);
			//05-30 14:18:11.875: I/(8365): 34.238335 108.967124

			if(firsiIn){
				LatLng lat = new LatLng(location.getLatitude(), location.getLongitude());//��þ��Ⱥ�γ��
				MapStatusUpdate    map = MapStatusUpdateFactory.newLatLng(lat);
				mapdu.animateMapStatus(map);    //��ͼ��ǰλ���Ѷ�������ʽչ��
				firsiIn = false;
				Toast.makeText(context, location.getAddrStr(), Toast.LENGTH_SHORT).show();   //�ѵ�ǰ�����ڵ�λ��show����
			}
		}
	}
	//��̬���ƾ�����·��
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
	//��̬���ƾ�����·��
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
	//��̬����·��
	
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
		  //�����ĸ����㣬��ʼ��	
			dos[0] = new data(34.2596600000,108.9433500000);
			dos[1] = new data(34.2613310000,108.9794260000);
			dos[3] = new data(34.2569150000,108.9430620000);
			dos[2] = new data(34.2533340000,108.9713770000);
			dos[4] = new data(34.2596600000,108.9433500000);
	  }
	
	//�����ݱ���д������(SQLite)
		public   void   writetoLocationTable(String name , double latitude, double longtitude){
			dao = new LocationDao(this);
			dao.addinfo(name,latitude,longtitude);	  
		}
	   //д�ļ����Զ�̬��json��ʽ
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
	   
	   //������˶�λ��ͼ��(����ڸ�����)
	   public   void   sureoldmanpostion(double latittude , double longtitude){
			mapdu.clear();   //���ͼ���ϵĶ���
			//���徭γ��
			LatLng  latlng = null;
			Marker marker = null;
			OverlayOptions   options;
				latlng = new LatLng(latittude,longtitude);
				//ͼ��
				options = new MarkerOptions().position(latlng).icon(moldmanMarker).zIndex(5);      //��ӵ�ͼ�����߲�
				marker = (Marker) mapdu.addOverlay(options);
				//markerЯ��һЩֵ
			//	Bundle bundle = new Bundle();
			//	bundle.putSerializable("info", nihao);       //ʵ��������
			//	marker.setExtraInfo(bundle);                       //marker Я����ϢҲ����info����е���Ϣ

			//ÿ�������ͼ���ʱ�򣬰ѵ�ͼ�ƶ�����һ��ͼ���λ��
			MapStatusUpdate  msu = MapStatusUpdateFactory.newLatLng(latlng);
			mapdu.animateMapStatus(msu);    //��ͼ��ǰλ���Ѷ�������ʽչ��    
			//mapdu.setMapStatus(msu);
	   }
	
	 
}

