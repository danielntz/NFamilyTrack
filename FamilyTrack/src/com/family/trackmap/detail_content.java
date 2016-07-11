package com.family.trackmap;



import com.family.map.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 显示覆盖物内容
 * @author jsjxy
 *
 */
public class detail_content  extends Activity implements OnClickListener{
                private static final String TAG = null;
				private   ImageView view1;
                private   TextView   view2;
                private  TextView   view3;
                private  TextView   view4;
                private LinearLayout     view5;
	         
	         @Override
	        protected void onCreate(Bundle savedInstanceState) {
	        	// TODO Auto-generated method stub
	        	super.onCreate(savedInstanceState);
	        	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        	setContentView(R.layout.detail_layout);
	        	
	           Intent inent = getIntent();
	           Bundle bundle =   inent.getExtras();   //获得传递过来的值
	            Info    dedao       =  (Info) bundle.getSerializable("info");
	           view1= (ImageView)findViewById(R.id.showphoto);
	           view2=(TextView)findViewById(R.id.name);
	           view3 = (TextView)findViewById(R.id.count);
	           view4= (TextView)findViewById(R.id.distance);
	           view5 = (LinearLayout)findViewById(R.id.buju);
	           view1.setImageResource(dedao.getReadId());
	           view2.setText(dedao.getName());
	           view3.setText(dedao.getDistance());
	           view4.setText(dedao.getZan()+"");
	           view5.setOnClickListener(this);
	 
	        }

			@Override
			public void onClick(View arg0) {
				int id = arg0.getId();
				if (id == R.id.buju) {
					finish();
				} else {
				}
			}

			
}
