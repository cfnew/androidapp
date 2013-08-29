package com.example.bupt.issue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.base.BaseViewPager;
import com.example.bupt.issue.calendar.MyCalendar;
import com.example.bupt.utils.FileUtils;
import com.example.bupt.utils.ToastUtil;

@SuppressLint("SimpleDateFormat")
public class PaopaoIssue extends BaseActivity {
	//ViewPager 总页卡
	public ViewPager viewpager ;
	//三个View
	private View view1,view2,view3;
	//tab页面列表
	private List<View> views;
	private TextView nextPage = null;
	private static final int INIT_VIEW_PAGER = 100;
	private static final int INIT_VIEW_PAGER_0 = 0;
	private static final int INIT_VIEW_PAGER_1 = 1;
	private static final int INIT_VIEW_PAGER_2 = 2;

	//传送的object
	private IssueToSend issue = new IssueToSend();
	private String uid = "17";
	private Dialog loading = null;
	//Loca表示
	private static final int FOR_LOCATION =7;
	//year
	private static final int CALL_CALENDAR = 8;
	//Tab
	private static final int CALL_TAB = 9;
	//Sex
	private static final int CALL_SEX = 10;
	//camera
	private static final int IMAGE_REQUEST_CODE = 11;
	//time
	private static final int CALL_TIME = 12;
	//寻找好友
	private static final int FOR_LOCATION_FRIEND =13;
	//添加好友
	private static final int CALL_ADD_FRIEND =14;


	//title1中的控件
	//用户头像和名字信息
	private EditText getTitle = null;
	private TextView getYear = null;
	private TextView getTime = null;
	private TextView getLoca = null;
	private Calendar dat = Calendar.getInstance();
	private int titleLimit = 20;
	private int BodyLimit = 140;
	private int TabFlag = 0;
	private TextView tabView;


	/*
	 * title2中的控件
	 */
	private EditText getNum = null;
	private TextView getSex = null;
	private EditText getYoung = null;
	private EditText getOld = null;
	private EditText getBody = null;
	//用int值代表性别 0 随意 1男 2女 初始为0
	private int issue_temp_sex = 0;
	private String[] StringSex = new String[]{"随意","男","女"};

	/*
	 * tittle3控件
	 */
	private ToggleButton simihuodong = null;
	private ToggleButton yaoqinghaoyou = null;
	private boolean simiFlag = false;
	private boolean yaoqingFlag = false;
	private TextView p_location;
	private String my_p_lication = null;
	
	//屏幕宽度
	int screenWidth = 0;
	Button index11 = null;
	Button index12 = null;
	Button index13 = null;
	Button index21 = null;
	Button index22 = null;
	Button index23 = null;
	Button index31 = null;
	Button index32 = null;
	Button index33 = null;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paopao_issue);
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();//屏幕宽度
		Intent get_data = this.getIntent();
		Bundle bundle = get_data.getExtras(); 
		TabFlag = bundle.getInt("cat_ids");
		nextPage = (TextView) findViewById(R.id.paopao_issue_next);
		Message msg = new Message();
		msg.what = INIT_VIEW_PAGER;
		mHandler.sendMessage(msg);
	}

	/*
	 * 初始化ViewPager
	 */
	private void InitViewPager() {  
		viewpager=(BaseViewPager) findViewById(R.id.issue_vp_pager);  
		views=new ArrayList<View>();  
		LayoutInflater inflater=getLayoutInflater();  
		view1=inflater.inflate(R.layout.paopao_issue_title1, null);  
		view2=inflater.inflate(R.layout.paopao_issue_title2, null);  
		view3=inflater.inflate(R.layout.paopao_issue_title3, null);
		views.add(view1);  
		views.add(view2);  
		views.add(view3);
		viewpager.setAdapter(new MyViewPagerAdapter(views,mHandler));  
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(new MyOnPageChangeListener());
		FixedSpeedScroller mScroller; 
		try {               
			Field mField = ViewPager.class.getDeclaredField("mScroller");               
			mField.setAccessible(true);        
			mScroller = new FixedSpeedScroller(viewpager.getContext(), new AccelerateInterpolator());          
			mField.set(viewpager, mScroller);           
		} catch (Exception e) {           
			e.printStackTrace();  
		}   


	}

	public class MyOnPageChangeListener implements OnPageChangeListener{  

		public void onPageScrollStateChanged(int arg0) {

		}  

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}  

		public void onPageSelected(int arg0) {
			if(arg0 == 2){
				nextPage.setClickable(false);
				nextPage.setVisibility(4);
			}else{
				nextPage.setClickable(true);
				nextPage.setVisibility(0);
			}
		}
	}

	/*
	 * hander加载每一个页面的控件
	 */

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			if(msg.what == INIT_VIEW_PAGER){
				InitViewPager();
			}
			if (msg.what == INIT_VIEW_PAGER_0) {
				index11 = (Button) findViewById(R.id.paopao_issue_btn_11);
				index12 = (Button) findViewById(R.id.paopao_issue_btn_12);
				index13 = (Button) findViewById(R.id.paopao_issue_btn_13);
				index11.setWidth((int)(screenWidth/3));
				index12.setWidth((int)(screenWidth/3));
				index13.setWidth((int)(screenWidth/3));
				getTitle = (EditText) findViewById(R.id.paopao_issue_1_et_title);
				getYear = (TextView) findViewById(R.id.paopao_issue_1_et_year);
				getLoca = (TextView) findViewById(R.id.paopao_issue_1_et_loca);
				getTime = (TextView) findViewById(R.id.paopao_issue_1_et_time);
				tabView = (TextView) findViewById(R.id.paopao_issue_1_et_biaoqian);
				tabView.setText(String.valueOf(TabFlag));

				getTitle.addTextChangedListener(TW_getTitle);

//				getLoca.setOnFocusChangeListener(new View.OnFocusChangeListener() {    
//					@Override  
//					public void onFocusChange(View v, boolean hasFocus) {  
//						if(hasFocus){//获得焦点  
//							Intent intent = new Intent(PaopaoIssue.this,PaopaoIssueLo.class);
//							startActivityForResult(intent,FOR_LOCATION);
//						}
//					}             
//				}); 

			}
			if(msg.what == INIT_VIEW_PAGER_1){
				index21 = (Button) findViewById(R.id.paopao_issue_btn_21);
				index22 = (Button) findViewById(R.id.paopao_issue_btn_22);
				index23 = (Button) findViewById(R.id.paopao_issue_btn_23);
				index21.setWidth((int)(screenWidth/3));
				index22.setWidth((int)(screenWidth/3));
				index23.setWidth((int)(screenWidth/3));
				
				getNum = (EditText)findViewById(R.id.paopao_issue_2_et_renshu);
				getSex = (TextView)findViewById(R.id.paopao_issue_2_tv_sex);
				getYoung = (EditText)findViewById(R.id.paopao_issue_2_et_young);
				getOld = (EditText)findViewById(R.id.paopao_issue_2_et_old);
				getBody = (EditText)findViewById(R.id.paopao_issue_2_et_body);
				getNum.addTextChangedListener(TW_getNumber);
				getSex.setText(StringSex[issue_temp_sex]);
				getYoung.addTextChangedListener(TW_getYoung);
				getOld.addTextChangedListener(TW_getOld);
				getBody.addTextChangedListener(TW_getBody);
				getBody.setOnFocusChangeListener(new View.OnFocusChangeListener() {    
					@Override  
					public void onFocusChange(View v, boolean hasFocus) {  
						if(hasFocus){//获得焦点  
							getBody.setSelection(getBody.length());
						}
					}             
				});  

			}
			if(msg.what == INIT_VIEW_PAGER_2){
				index31 = (Button) findViewById(R.id.paopao_issue_btn_31);
				index32 = (Button) findViewById(R.id.paopao_issue_btn_32);
				index33 = (Button) findViewById(R.id.paopao_issue_btn_33);
				index31.setWidth((int)(screenWidth/3));
				index32.setWidth((int)(screenWidth/3));
				index33.setWidth((int)(screenWidth/3));
				
				simihuodong = (ToggleButton) findViewById(R.id.paopao_issue_3_tb_simi);
				yaoqinghaoyou = (ToggleButton) findViewById(R.id.paopao_issue_3_tb_yaoqing);
				p_location = (TextView) findViewById(R.id.paopao_issue_3_tv_floca);
				if(simiFlag == true) simihuodong.setChecked(true);
				if(yaoqingFlag == true) yaoqinghaoyou.setChecked(true);
				if(my_p_lication != null) p_location.setText(my_p_lication);

				simihuodong.setOnCheckedChangeListener(new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if(isChecked == true) {
							simiFlag=true;
							yaoqinghaoyou.setChecked(true);
						}
						if(isChecked == false) simiFlag=false;						
					}

				});
				yaoqinghaoyou.setOnCheckedChangeListener(new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if(isChecked == true) {
							yaoqingFlag=true;
							Intent intent = new Intent(PaopaoIssue.this,PaopaoIssueAddFriend.class);
							startActivityForResult(intent,CALL_ADD_FRIEND);

						}
						if(isChecked == false) yaoqingFlag=false;

					}

				});


			}

			if(msg.what == CALL_TAB){
				Intent intent = (Intent) msg.obj;
				int a = intent.getIntExtra("tab_back", -1);
				if(-1 != a){
					TabFlag = a;
					tabView.setText(String.valueOf(TabFlag));
				}

			}
		}
	};

	private void getTab(Intent data) {
		Message msg = new Message();
		msg.what = CALL_TAB;
		msg.obj = data;
		mHandler.sendMessage(msg);
	}

	/*
	 * title1中的三个EditText的监控输入
	 * title的输入监听 以及加入issue类
	 */
	TextWatcher TW_getTitle = new TextWatcher() {
		String temp_title = null;
		@Override
		public void beforeTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			temp_title = s.toString();
			if(temp_title.length() < titleLimit){
				issue.setTitle(temp_title);
			}else{
				Toast.makeText(PaopaoIssue.this,"题目最多支持20字",Toast.LENGTH_SHORT).show();
			}
		}
	};
	/*
	 * tab页面
	 */
	public void call_for_tab(View v){
		Intent intent = new Intent(PaopaoIssue.this,PaopaoIssueTab.class);
		intent.putExtra("TabNameFlag", TabFlag);
		startActivityForResult(intent,CALL_TAB);
		overridePendingTransition(R.anim.fade_in,
				R.anim.fade_out);
	}
	/*
	 * 通过calendar获得年份
	 */
	private void getYearFromCalendar(Intent data){
		int year = data.getIntExtra("year", 0);
		int month = data.getIntExtra("month", 0);
		int day = data.getIntExtra("day", 0);
		dat.set(Calendar.YEAR, year);
		dat.set(Calendar.MONTH, month);
		dat.set(Calendar.DAY_OF_MONTH, day);
		SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd");
		getYear.setText(format.format(dat.getTime()));
		issue.setYear(format.format(dat.getTime()));

	}


	private void getLocationFromMap(Intent data){
		issue.setLocation(data.getStringExtra("location"));
		issue.setLatitude(data.getStringExtra("latitude"));
		issue.setLongitude(data.getStringExtra("longitude"));
		getLoca.setText(issue.getLocation());
	}

	/*
	 * title2中的四个EditText的输入监控
	 * number的输入监控 以及加入issue类
	 */
	TextWatcher TW_getNumber = new TextWatcher() {
		String temp_Num = null;
		@Override
		public void beforeTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			temp_Num = s.toString();
			issue.setNeed_num(temp_Num);
		}
	};


	/*
	 * title2中的四个EditText的输入监控
	 * Young的输入监控 以及加入issue类
	 */
	TextWatcher TW_getYoung = new TextWatcher() {
		String temp_Young = null;
		@Override
		public void beforeTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			temp_Young = s.toString();
		}
	};

	/*
	 * sex
	 */
	public void paopao_issue_set_sex(View v){
		Intent intent = new Intent(PaopaoIssue.this,PaopaoIssueSetSex.class);
		intent.putExtra("Sex", issue_temp_sex);
		startActivityForResult(intent,CALL_SEX);
		overridePendingTransition(R.anim.fade_in,
				R.anim.fade_out);
	}
	//获得Sex
	private void getSex(Intent data) {
		issue_temp_sex = data.getIntExtra("Sex", 0);
		getSex.setText(StringSex[issue_temp_sex]);

	}

	/*
	 * title2中的四个EditText的输入监控
	 * Old的输入监控 以及加入issue类
	 */
	TextWatcher TW_getOld = new TextWatcher() {
		String temp_Old = null;
		@Override
		public void beforeTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			temp_Old = s.toString();
		}
	};

	/*
	 * title2中的两个EditText的监控输入
	 * body的输入监听 以及加入issue类
	 */
	TextWatcher TW_getBody = new TextWatcher() {
		String temp_body = null;
		@Override
		public void beforeTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			temp_body = s.toString();
			if(temp_body.length() < BodyLimit){
				issue.setBody(temp_body);
			}else{
				Toast.makeText(PaopaoIssue.this,"内容最多支持140字",Toast.LENGTH_SHORT).show();
			}
		}
	};
	/*
	 * tittle 3
	 */
	private void getLocaFriend(Intent data) {
		issue.setP_location(data.getStringExtra("location"));
		issue.setP_latitude(data.getStringExtra("latitude"));
		issue.setP_longitude(data.getStringExtra("longitude"));
		p_location.setText(issue.getP_location());
		my_p_lication = issue.getP_location();

	}



	/*
	 * list初始化
	 * 这里会初始化很多次因为当前页面是A的时候只会初始化B 若当前为C只会初始化B
	 */
	public class MyViewPagerAdapter extends PagerAdapter{  
		private List<View> mListViews = null;;
		private Handler mHandler = null;

		public MyViewPagerAdapter(List<View> mListViews) {  
			this.mListViews = mListViews;  
		}  

		public MyViewPagerAdapter(List<View> views, Handler mHandler) {
			this.mListViews = views;
			this.mHandler = mHandler;

		}

		@Override  
		public void destroyItem(ViewGroup container, int position, Object object)   {     
			container.removeView(mListViews.get(position));  
		}  


		@Override  
		public Object instantiateItem(ViewGroup container, int position) {            
			container.addView(mListViews.get(position), 0);



			//第一章页卡的初始化
			if(position == 0) {
				Message message = Message.obtain();
				message.what = INIT_VIEW_PAGER_0;
				mHandler.sendMessage(message);
			}


			if(position == 1){
				Message message = Message.obtain();
				message.what = INIT_VIEW_PAGER_1;
				mHandler.sendMessage(message);
			}



			if(position == 2){
				Message message = Message.obtain();
				message.what = INIT_VIEW_PAGER_2;
				mHandler.sendMessage(message);

			}


			return mListViews.get(position);  
		}  

		@Override  
		public int getCount() {           
			return  mListViews.size();  
		}  

		@Override  
		public boolean isViewFromObject(View arg0, Object arg1) {             
			return arg0==arg1;  
		}  
	}

	//用户图片获得
	//并显示
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {


		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				if(data != null) getImageToView(data);
				break;

			case FOR_LOCATION:
				if(data != null) getLocationFromMap(data);
				break;

			case CALL_CALENDAR:
				if(data != null) getYearFromCalendar(data);
				break;
			case CALL_TAB:
				if(data != null) getTab(data);
				break;
			case CALL_SEX:
				if(data != null) getSex(data);
				break;
			case CALL_TIME:
				if(data != null) getTimeFromTimer(data);
				break;
			case FOR_LOCATION_FRIEND:
				if(data != null) getLocaFriend(data);
				break;
			}
		}
	}


	private void getTimeFromTimer(Intent data) {
		int hour = data.getIntExtra("hours", 0);
		int min = data.getIntExtra("mins", 0);
		Log.i("getTimeFromTimer", String.valueOf(hour)+":"+String.valueOf(min));
		getTime.setText(String.valueOf(hour)+":"+String.valueOf(min));
		issue.setTime(String.valueOf(hour)+":"+String.valueOf(min));
		Log.i("getTime", issue.getTime());
		isTime();
	}

	// 保存图片
	private void getImageToView(Intent data) {
		//		Bitmap photo = null;
		//		Uri originalUri = data.getData();
		//		String picPath = printPath(originalUri);
		//		photo = getDiskBitmap(picPath) ; 
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			if(baseApp.isNetworkConnected())
			{
				//把返回的图片存到用户的临时目录下，供上传
				File imageFile = cacheNewImage(photo);
				uploadNewImage(imageFile);
			}
		}
	}

	private String printPath(Uri uri){
		Uri uri2 = Uri.parse("content://media"+uri.getPath());
		ContentResolver cr = this.getContentResolver();
		Cursor cursor = cr.query(uri2, null, null, null, null);
		cursor.moveToFirst();
		return cursor.getString(1);
	}

	private Bitmap getDiskBitmap(String pathString)  
	{  
		Bitmap bitmap = null;  
		try  
		{  
			File file = new File(pathString);  
			if(file.exists())  
			{  
				bitmap = BitmapFactory.decodeFile(pathString);  
			}  
		} catch (Exception e)  
		{   
		}   
		return bitmap;  
	}  


	/**
	 * 根据Bitmap，缓存新图片
	 * @param drawable
	 * @return 
	 */
	public File cacheNewImage(Bitmap bitmap){
		if(uid==null)
		{
			return null;
		}
		String imagePath = Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ "/WoWilling/cache/"+uid+"/"+uid+"temp_issue.jpg";
		System.out.println(imagePath);
		FileUtils.createPath(Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/WoWilling/cache/"+uid);
		File imageFile=new File(imagePath);
		if(!imageFile.exists())
			try {
				imageFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		FileOutputStream fos=null;
		try {
			fos=new FileOutputStream(imageFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		boolean sucess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
		try {
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(sucess);
		if(sucess==true)
			return imageFile;
		else 
			return null;
	}

	/**
	 * 上传图片并获得id号
	 * 
	 */
	public void uploadNewImage(File imageFile)
	{
		System.out.println(imageFile.getAbsolutePath());
//		RequestParams params=new RequestParams();
//		params.put("uid", uid);
//		try {
//			params.put("File",imageFile);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		Log.i("UpLoad","pic");
//		HttpUtil.post(URLs.ISSUE_PIC, params, new JsonHttpResponseHandler()
//		{
//
//			@Override
//			public void onFailure(Throwable arg0, JSONObject arg1) {
//				super.onFailure(arg0, arg1);
//				ToastUtil.showMsg(PaopaoIssue.this, "图片上传失败，请稍后再试");
//			}
//
//			@Override
//			public void onSuccess(JSONObject arg0) {
//				super.onSuccess(arg0);
//				int status=0;
//				String info=null;
//				String attachId=null;
//				JSONObject data=null;
//				try {
//					status=arg0.getInt("status");
//					info=arg0.getString("info");
//					Log.i("status", String.valueOf(status));
//					Log.i("info", info);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				if(status == 1) {
//					try {
//						data=arg0.getJSONObject("data");
//						attachId=data.getString("attachid");
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//					Log.i("attachid", attachId);
//					issue.setAttach_id(attachId);
//					ToastUtil.showMsg(PaopaoIssue.this, "图片上传成功");
//				}
//
//			}
//
//			@Override
//			public void onFinish() {
//				super.onFinish();
//			}
//
//			@Override
//			public void onStart() {
//				super.onStart();
//
//			}
//		}
//				);

	}

	//重载destory函数 销毁内存
	protected void onDestroy() {
		super.onDestroy();          

	} 

	//上端两个按钮 返回和发布
	public void back_to_main(View v){
		this.finish();
		overridePendingTransition(R.anim.fade_in,
				R.anim.fade_out);
	}

	//判断数据源的合法性
	public boolean checkInfo(){
		if(issue.getTitle() == null){
			ToastUtil.showMsg(PaopaoIssue.this, "题目不能为空，请输入题目");
			viewpager.setCurrentItem(0);
			return false;
		}
		if(issue.getTime() == null){
			ToastUtil.showMsg(PaopaoIssue.this, "请输入一个合适的时间");
			viewpager.setCurrentItem(0);
			return false;
		}
		if(isDate() == false){
			ToastUtil.showMsg(PaopaoIssue.this, "请输入一个合法的日期");
			viewpager.setCurrentItem(0);
			getYear.setText("");
			getYear.setHint("如 2013 02 15");
			return false;
		}
		if(isTime() == false){
			ToastUtil.showMsg(PaopaoIssue.this, "请输入一个合法的时间");
			viewpager.setCurrentItem(0);
			getTime.setText("");
			getTime.setHint("如 05:30");
			return false;
		}
		if(issue.getLocation() == null){
			ToastUtil.showMsg(PaopaoIssue.this, "选择一个你想要的地点，点击我想图标");
			viewpager.setCurrentItem(0);
			return false;
		}
		if(issue.getNeed_num() == null){
			ToastUtil.showMsg(PaopaoIssue.this, "请输入一个你想要的人数");
			viewpager.setCurrentItem(1);
			return false;
		}
		if(issue.getBody() == null){
			ToastUtil.showMsg(PaopaoIssue.this, "内容不能为空，请输入内容");
			viewpager.setCurrentItem(1);
			return false;
		}

		issue.setTimestamp(setTimeStamp());
		Log.i("timeStamp", issue.getTimestamp());
		return true;
	}

	private String setTimeStamp(){
		SimpleDateFormat format =   new SimpleDateFormat( "yyyy MM dd HH:mm:ss" );
		Date date = null;
		try {
			date = format.parse(issue.getYear()+" "+issue.getTime() + ":00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long timestamp = date.getTime();
		timestamp = timestamp/1000;
		return Long.toString(timestamp);
	}

	@SuppressLint("SimpleDateFormat")
	private boolean isDate(){
		SimpleDateFormat dateFormat = null;
		dateFormat = new SimpleDateFormat("yyyy MM dd");
		dateFormat.setLenient(false);

		try
		{
			dateFormat.parse(issue.getYear());
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	@SuppressLint("SimpleDateFormat")
	private boolean isTime(){
		SimpleDateFormat dateFormat = null;
		dateFormat = new SimpleDateFormat("HH:mm:ss");
		dateFormat.setLenient(false);

		try
		{
			dateFormat.parse(issue.getTime() + ":00");
			Log.i("正确获取时间","正确获取时间");
			return true;
		}
		catch (Exception e)
		{
			Log.i("错误获取时间","错误获取时间");
			return false;
		}
	}
	//发布按钮
	public void issue_out(View v){
		if(viewpager.getCurrentItem() != 2){
			viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
			return;
		}
		//发布控制

		if(checkInfo() == false) return;

		// 创建请求参数
//		RequestParams params = new RequestParams();
//		params.put("title", issue.getTitle());
//		Log.i("title", issue.getTitle());
//		params.put("content", issue.getBody());
//		Log.i("content", issue.getBody());
//		params.put("need_num", issue.getNeed_num());
//		Log.i("need_num", issue.getNeed_num());
//		params.put("start_time", issue.getTimestamp());
//		Log.i("start_time", issue.getTimestamp());
//		params.put("e_location",issue.getLocation());
//		Log.i("e_location", issue.getLocation());
//		params.put("e_longitude",issue.getLongitude());
//		Log.i("e_longitude", issue.getLongitude());
//		params.put("e_latitude",issue.getLatitude());
//		Log.i("e_latitude", issue.getLatitude());
//		params.put("p_location",issue.getLocation());
//		params.put("p_longitude",issue.getLongitude());
//		params.put("p_latitude",issue.getLatitude());		
//		params.put("is_follow",issue.getIs_follow());
//		params.put("cat_ids","1");
//		params.put("attach_id",issue.getAttach_id());
//		params.put("submit","1");
//		// 发送post请求
//		HttpUtil.post(URLs.HOME_POST_FEED, params, new JsonHttpResponseHandler() {
//			@Override
//			public void onStart() {
//				// 显示loading对话框
//				loading = DialogUtil.getLoadingDialog(PaopaoIssue.this,
//						"正在发布 请稍后...");
//				loading.show();
//			}
//
//			@Override
//			public void onFinish() {
//				super.onFinish();
//				loading.dismiss();
//			}
//
//			@Override
//			public void onSuccess(JSONObject result) {
//
//				int status = 0;
//				String info = null;
//				try {
//					status = result.getInt("status");
//					info = result.getString("info");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//
//				if (status == 1) {// 发布成功
//					ToastUtil.showMsg(PaopaoIssue.this, "发布成功");
//					ToastUtil.showMsg(PaopaoIssue.this, info);
//					finish();
//					overridePendingTransition(R.anim.fade_in,
//							R.anim.fade_out);
//				}
//			}
//
//			@Override
//			public void onFailure(Throwable e, JSONObject data) {
//				if (!baseApp.isNetworkConnected()) {
//					ToastUtil.showMsg(PaopaoIssue.this, R.string.network_none);
//				} else {
//					ToastUtil.showMsg(PaopaoIssue.this,
//							R.string.network_error);
//				}
//				e.printStackTrace();
//			}
//
//		});

	}

	public void paopao_issue_next_page(View v){
		int cuurent_index = viewpager.getCurrentItem();
		viewpager.setCurrentItem(cuurent_index+1);		
	}
	public void call_on_location_first(View v){
		Intent intent = new Intent(PaopaoIssue.this,PaopaoIssueLo.class);
		startActivityForResult(intent,FOR_LOCATION);
		
	}

	public void call_on_calendar(View v){
		Intent intent = new Intent(PaopaoIssue.this,MyCalendar.class);
		startActivityForResult(intent,CALL_CALENDAR);
		overridePendingTransition(R.anim.fade_in,
				R.anim.fade_out);
	}

	public void call_on_timer(View v){
//		Intent intent = new Intent(PaopaoIssue.this,PaopaoIssueTime.class);
//		startActivityForResult(intent,CALL_TIME);
//		overridePendingTransition(R.anim.fade_in,
//				R.anim.fade_out);
	}

	//btn 添加附加数据 图片
	public void paopao_issue_get_pic(View v){
		final Intent intentFromGallery = new Intent();
		intentFromGallery.setType("image/*"); // 设置文件类型
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery,
				IMAGE_REQUEST_CODE);
	}

	//添加寻找伙伴地址
	public void paopao_issue_find_f(View v){
		Intent intent = new Intent(PaopaoIssue.this,PaopaoIssueLo.class);
		startActivityForResult(intent,FOR_LOCATION_FRIEND);
	}
	
	public void set_current_index_1(View v){
		viewpager.setCurrentItem(0);
	}
	
	public void set_current_index_2(View v){
		viewpager.setCurrentItem(1);
	}
	
	public void set_current_index_3(View v){
		viewpager.setCurrentItem(2);
	}

}
