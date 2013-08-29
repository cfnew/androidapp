package com.example.bupt.issue;

import com.example.bupt.R;
import com.example.bupt.R.layout;
import com.example.bupt.R.menu;
import com.example.bupt.utils.LastClickUtil;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class PaopaoIssuePublicTab extends Activity {

	private Circle myCircle = null;
	private ImageView tabView[] = null;
	private TextView tabViewText[] = null;
	private RelativeLayout tabViewRL[] = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paopao_issue_public_tab);
		init();
	}
	
	private void init(){
		drawCircle(9);
		tabView = new ImageView[9];
		tabViewText = new TextView[9];
		tabViewRL = new RelativeLayout[9];
		tabView[0] = (ImageView) findViewById(R.id.paopao_issue_pub_iv_tab0);
		tabView[1] = (ImageView) findViewById(R.id.paopao_issue_pub_iv_tab1);
		tabView[2] = (ImageView) findViewById(R.id.paopao_issue_pub_iv_tab2);
		tabView[3] = (ImageView) findViewById(R.id.paopao_issue_pub_iv_tab3);
		tabView[4] = (ImageView) findViewById(R.id.paopao_issue_pub_iv_tab4);
		tabView[5] = (ImageView) findViewById(R.id.paopao_issue_pub_iv_tab5);
		tabView[6] = (ImageView) findViewById(R.id.paopao_issue_pub_iv_tab6);
		tabView[7] = (ImageView) findViewById(R.id.paopao_issue_pub_iv_tab7);
		tabView[8] = (ImageView) findViewById(R.id.paopao_issue_pub_iv_tab8);
		tabViewText[0] = (TextView) findViewById(R.id.paopao_issue_pub_tv_tab0);
		tabViewText[1] = (TextView) findViewById(R.id.paopao_issue_pub_tv_tab1);
		tabViewText[2] = (TextView) findViewById(R.id.paopao_issue_pub_tv_tab2);
		tabViewText[3] = (TextView) findViewById(R.id.paopao_issue_pub_tv_tab3);
		tabViewText[4] = (TextView) findViewById(R.id.paopao_issue_pub_tv_tab4);
		tabViewText[5] = (TextView) findViewById(R.id.paopao_issue_pub_tv_tab5);
		tabViewText[6] = (TextView) findViewById(R.id.paopao_issue_pub_tv_tab6);
		tabViewText[7] = (TextView) findViewById(R.id.paopao_issue_pub_tv_tab7);
		tabViewText[8] = (TextView) findViewById(R.id.paopao_issue_pub_tv_tab8);
		tabViewRL[0] = (RelativeLayout) findViewById(R.id.paopao_issue_pub_rl_tab0);
		tabViewRL[1] = (RelativeLayout) findViewById(R.id.paopao_issue_pub_rl_tab1);
		tabViewRL[2] = (RelativeLayout) findViewById(R.id.paopao_issue_pub_rl_tab2);
		tabViewRL[3] = (RelativeLayout) findViewById(R.id.paopao_issue_pub_rl_tab3);
		tabViewRL[4] = (RelativeLayout) findViewById(R.id.paopao_issue_pub_rl_tab4);
		tabViewRL[5] = (RelativeLayout) findViewById(R.id.paopao_issue_pub_rl_tab5);
		tabViewRL[6] = (RelativeLayout) findViewById(R.id.paopao_issue_pub_rl_tab6);
		tabViewRL[7] = (RelativeLayout) findViewById(R.id.paopao_issue_pub_rl_tab7);
		tabViewRL[8] = (RelativeLayout) findViewById(R.id.paopao_issue_pub_rl_tab8);
		
		int index = 0;
		for(index = 0 ; index != 9 ; index++){
			XYPoint.setLayout(tabViewRL[index],(int)myCircle.getX(index)-25,(int)myCircle.getY(index)-100);
			tabViewText[index].setText(PaopaoTabDetails.getTabName(index));
			tabViewRL[index].setVisibility(View.VISIBLE);
			set_call_function(index);
		}
	}
	
	private void set_call_function(final int index){
		tabViewRL[index].setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Log.i("index",String.valueOf(index));
				call_next_page_public(index);					
			}				
		});
	}
	
	private void call_next_page_public(final int index){
		if(LastClickUtil.onClick() == false) return;
		Intent intent = new Intent(PaopaoIssuePublicTab.this,PaopaoIssuePublic.class);
		intent.putExtra("tabnumber", index);
		startActivity(intent);
		finish();
	}
	
	private void drawCircle(int munber){
		myCircle = new Circle(munber);
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		int screenWidth = wm.getDefaultDisplay().getWidth();
		int screenHeight = wm.getDefaultDisplay().getHeight();
		float PointX = screenWidth/2;
		float PointY = (int)screenHeight/2;
		float radius = (int)screenWidth/3+25;
		float angle = (int)360/munber;
		int index = 0;
		float cacheX = 0;
		float cacheY = 0;
		for(index = 0; index != munber ; index++){
			cacheX = (float) (radius*Math.sin((index*angle)*Math.PI/180));
			cacheY = (float) (radius*Math.cos((index*angle)*Math.PI/180));
			myCircle.setPoint(index, cacheX+PointX, -cacheY+PointY);
			Log.i("X  Y",String.valueOf(cacheX+PointX)+"   "+String.valueOf(cacheY+PointY));
		}
	}
	
	class Circle{
		
		float ArrayX[]=null;
		float ArrayY[]=null;
		
		Circle(int munber){
			ArrayX = new float[munber];
			ArrayY = new float[munber];
		}
		
		void setPoint(int index , float x,float y){
			ArrayX[index] = x;
			ArrayY[index] = y;
		}
		
		float getX(int index){
			return ArrayX[index];
		}
		
		float getY(int index){
			return ArrayY[index];
		}
	}
	
	public void back_to_main(View v){
		if(LastClickUtil.onClick() == false) return;
		Intent intent = new Intent(PaopaoIssuePublicTab.this,PaopaoIssueNew.class);
		startActivity(intent);
		finish();
	}

}
