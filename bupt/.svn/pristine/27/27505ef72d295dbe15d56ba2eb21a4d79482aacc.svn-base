package com.example.bupt.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.base.BaseApp;
import com.example.bupt.db.DBUser;
import com.example.bupt.http.Api;
import com.example.bupt.model.ResultArea;
import com.example.bupt.model.ResultUserInfo;
import com.example.bupt.model.User;
import com.example.bupt.utils.BitmapUtil;
import com.example.bupt.utils.DateUtil;
import com.example.bupt.utils.FileUtils;
import com.example.bupt.utils.ToastUtil;

/*
 * 个人设置主界面
 */

public class SettingMain extends BaseActivity {
	
	//1表示图片上传成功，0表示图片上传失败
	private static final int IMAGE_UPLOAD_SUCESS=1;
	private static final int IMAGE_UPLOAD_FAILER=0;
	
	// 0本地图片请求 1相机请求 2完成请求
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	public static final int SETTING_NAME = 3;
	public static final int SETTING_SEX = 4;
	public static final int SETTING_SIGNATURE = 5;
	public static final int SETTING_AREA = 6;
	public static final int SETTING_BIRTHDAY = 7;
	private static final int START_INIT = 100;
	
	private Dialog loading;
	// 修改头像组件
	private RelativeLayout rl_setting_face;
	private ImageView user_face;
	// 修改名字组件
	private RelativeLayout rl_setting_name;
	private TextView user_name;
	//修改生日
	private TextView user_birthday;
	private SettingDate wheelMain;
	// 修改性别
	private TextView user_sex;
	//修改area
	private TextView user_area;
	//用户id
	private int uid;
	//数据库操作
	private DBUser userdao;
	private User user_to_init;
	private User user;
	private BitmapUtil btm = null;

	private static final int INIT_FROM_DATABASE = 90;
	private static final int INIT_FROM_NETWORK = 91;
	private static final int INIT_DOWNINFO = 92;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what == START_INIT){
				start_init();
			}
			if(msg.what == INIT_FROM_DATABASE){
				init_from_database();
			}
			if(msg.what == INIT_FROM_NETWORK){
				init_from_net();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main);
		// 数据库
		userdao = new DBUser(this);
		btm = new BitmapUtil(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_mini));
		start_init();
	}

	private void start_init(){
		//获取用户id
		uid = BaseApp.getUid();
		// 组件face
		rl_setting_face = (RelativeLayout) findViewById(R.id.my_setting_rl_face);
		user_face = (ImageView) findViewById(R.id.my_setting_iv_face);
		rl_setting_face.setOnClickListener(listener_face);
		// 组件name
		rl_setting_name = (RelativeLayout) findViewById(R.id.my_setting_rl_name);
		user_name = (TextView) findViewById(R.id.my_setting_name_text);
		rl_setting_name.setOnClickListener(listener_name);
		//组件brithday
		user_birthday = (TextView) findViewById(R.id.my_setting_tv_brithday);
		// 组件sex
		user_sex = (TextView) findViewById(R.id.my_setting_tv_sex);
		// 组件area
		user_area = (TextView) findViewById(R.id.my_setting_tv_area);
		if( BaseApp.getUid() > 0) init();
	}

	private void init(){
		Message msg = new Message();
		msg.what = INIT_FROM_DATABASE;
		mHandler.sendMessage(msg);
	}

	/**
	 * 利用本地数据库信息初始化用户
	 */
	private void init_from_database(){
		user_to_init = userdao.getLocalUser();
		if(user_to_init == null){
			Message msg = new Message();
			msg.what = INIT_FROM_NETWORK;
			mHandler.sendMessage(msg);
			return;
		}
		if(user_to_init.getUface() != null) user_face.setImageBitmap(user_to_init.getUface());
		if(user_to_init.getUname() != null) user_name.setText(user_to_init.getUname());
		if(user_to_init.getUsex() != null) user_sex.setText(Integer.parseInt(user_to_init.getUsex())==1?"男":"女");
		if(user_to_init.getUlocation() != null) user_area.setText(user_to_init.getUlocation());
		if(user_to_init.getUbirthday() != null) user_birthday.setText(user_to_init.getUbirthday());
	}

	/**
	 * 下载用户信息
	 */
	private void init_from_net() {
		//没有网络
		if(!baseApp.isNetworkConnected()){
			ToastUtil.showMsg(SettingMain.this, R.string.network_not_connected);
			return;
		}
		ResultUserInfo rui = Api.downloadUserInfo(SettingMain.this,uid);
		if(rui != null && rui.OK()){
			user = rui.getUser();
			//更新数据库
			userdao.delete(User.COL_UID+"=?", new String[]{uid+""});
			userdao.insertUsr(user);
			Message msg = new Message();
			msg.what = INIT_FROM_DATABASE;
			mHandler.sendMessage(msg);
		}else{
			ToastUtil.showMsg(SettingMain.this, rui.getInfo());
		}
	}



	public void area(View v){
		ResultArea res = Api.getProvinces(this);
		if(res.OK()){
			Bundle bundle = new Bundle();
			Intent intent = new Intent(SettingMain.this,SettingProvince.class);
			intent.putExtras(bundle);
			startActivityForResult(intent,SETTING_AREA);
		}else{
			ToastUtil.showMsg(SettingMain.this, res.getInfo());
		}

	}

	public void signature(View v) {
		Intent intent = new Intent(SettingMain.this, SettingSignature.class);
		startActivityForResult(intent, SETTING_SIGNATURE);
	}

	public void male_female(View v) {
		Intent intent = new Intent(SettingMain.this, SettingSex.class);
		intent.putExtra("sex", user_to_init.getUsex());
		startActivityForResult(intent, SETTING_SEX);
	}

	// 修改姓名
	private View.OnClickListener listener_name = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SettingMain.this, SettingName.class);
			startActivityForResult(intent, SETTING_NAME);

		}
	};

	protected void onDestroy() {          
		super.onDestroy();          
	}  

	public void back_to_main(View v) {
		finish();
	}

	// 用户头像操作 begin
	private View.OnClickListener listener_face = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			showDialogPicChoose();
		}
	};
	
	private void showDialogPicChoose() {
		String[] items = new String[] { "选择本地图片", "拍照" };

		new AlertDialog.Builder(this).setTitle("设置头像")
		.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					final Intent intentFromGallery = new Intent();
					intentFromGallery.setType("image/*"); // 设置文件类型
					intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
					new Handler().post(new Runnable() {
						@Override
						public void run() {
							startActivityForResult(intentFromGallery,IMAGE_REQUEST_CODE);
						}
					});
					break;
				case 1:
					Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);				
					startActivityForResult(intentFromCapture,CAMERA_REQUEST_CODE);
					break;
				}
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create().show();
	}

	public void call_birthday(View v){
		LayoutInflater inflater=LayoutInflater.from(SettingMain.this);
		final View timepickerview=inflater.inflate(R.layout.setting_birthday, null);
		ScreenInfo screenInfo = new ScreenInfo(SettingMain.this);
		wheelMain = new SettingDate(timepickerview);
		wheelMain.screenheight = screenInfo.getHeight();
		String time =(user_birthday.toString() == null) ? "1990-02-15":user_birthday.toString();
		Calendar calendar = Calendar.getInstance();
		if(DateUtil.isDate(time, "yyyy-MM-dd")){
			calendar.setTime(DateUtil.parse(time, DateUtil.FORMAT_SHORT));
		}
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		wheelMain.initDateTimePicker(year,month,day);
		new AlertDialog.Builder(SettingMain.this)
		.setTitle("选择您的生日")
		.setView(timepickerview)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				user_birthday.setText(wheelMain.getTime());
				update_birthday();
				userdao.updateUbirthday(get_timestamp_l(wheelMain.getTime()));
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	}
	//上传生日
	private void update_birthday(){
		String birthday_stamp = null;
		birthday_stamp = get_timestamp_s(wheelMain.getTime());
		Log.i("生日",wheelMain.getTime());
		Log.i("生日",birthday_stamp);
//		RequestParams params=new RequestParams();
//		params.put("uid", uid+"");
//		params.put("birthday",birthday_stamp);
//		HttpUtil.post(URLs.SET_BIRTHDAY, params, new JsonHttpResponseHandler()
//		{
//
//			@Override
//			public void onFailure(Throwable arg0, JSONObject arg1) {
//				// TODO Auto-generated method stub
//				super.onFailure(arg0, arg1);
//				ToastUtil.showMsg(SettingMain.this, "生日更新失败");
//			}
//
//			@Override
//			public void onSuccess(JSONObject arg0) {
//				// TODO Auto-generated method stub
//				super.onSuccess(arg0);
//				int status=0;
//				String info = null;
//				try {
//					info = arg0.getString("info");
//					status=arg0.getInt("status");
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				Log.i("birthday_info",info);
//				if(status == 1) ToastUtil.showMsg(SettingMain.this, "生日更新成功");
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
//
//		}
//				);
	}

	// 裁剪
	public void startPhotoZoom(Uri uri) {

		final Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				startActivityForResult(intent, 2);
			}
		});
	}

	// 保存图片
	@SuppressWarnings("deprecation")
	private void getImageToView(Intent data) {
//		Bundle extras = data.getExtras();
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
//			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			if(baseApp.isNetworkConnected()){
				user_face.setImageDrawable(drawable);
				//把返回的图片存到用户的临时目录下，供上传
				File imageFile=cacheNewImage(photo);
				uploadNewImage(imageFile);
				userdao.updateUface(drawable);
			}
		}
	}

	/**
	 * 根据Bitmap，缓存新产生的头像文件
	 * @param drawable
	 * @return 
	 */
	public File cacheNewImage(Bitmap bitmap){
		if(uid==0){
			return null;
		}
		String imagePath = Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ "/WoWilling/cache/"+uid+"/"+uid+"touxiang.jpg";
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
		boolean sucess=bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
		try {
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(sucess==true)
			return imageFile;
		else 
			return null;
	}

	/**
	 * 把新头像同步到服务器
	 * @param imageFile
	 * @return
	 */
	public void uploadNewImage(File imageFile)
	{
		System.out.println(imageFile.getAbsolutePath());
//		RequestParams params=new RequestParams();
//		params.put("uid", uid+"");
//		try {
//			params.put("avatar",imageFile);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		HttpUtil.post(URLs.UPDATE_NEW_AVATAR, params, new JsonHttpResponseHandler()
//		{
//
//			@Override
//			public void onFailure(Throwable arg0, JSONObject arg1) {
//				// TODO Auto-generated method stub
//				super.onFailure(arg0, arg1);
//				ToastUtil.showMsg(SettingMain.this, "头像上传失败");
//			}
//
//			@Override
//			public void onSuccess(JSONObject arg0) {
//				// TODO Auto-generated method stub
//				super.onSuccess(arg0);
//				int status=0;
//				String info = "";
//				try {
//					status=arg0.getInt("status");
//					info=arg0.getString("info");
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				if(status == 1) ToastUtil.showMsg(SettingMain.this, "头像上传成功");
//				else ToastUtil.showMsg(SettingMain.this, info);
//				Log.i("aa",info);
//
//			}
//
//			@Override
//			public void onFinish() {
//				// TODO Auto-generated method stub
//				super.onFinish();
//			}
//
//			@Override
//			public void onStart() {
//				// TODO Auto-generated method stub
//				super.onStart();
//
//			}
//
//		}
//				);

	}
	/*
	 * 修改用户的地区
	 */
	public void uploadArea(String area_get_id,String area_get_name)
	{

//		RequestParams params=new RequestParams();
//		params.put("uid", uid+"");
//		params.put("location", area_get_name);
//		params.put("locids", area_get_id);
//		HttpUtil.post(URLs.SET_AREA, params, new JsonHttpResponseHandler()
//		{
//
//			@Override
//			public void onFailure(Throwable arg0, JSONObject arg1) {
//				// TODO Auto-generated method stub
//				super.onFailure(arg0, arg1);
//				ToastUtil.showMsg(SettingMain.this, "地区更新失败");
//			}
//
//			@Override
//			public void onSuccess(JSONObject arg0) {
//				// TODO Auto-generated method stub
//				super.onSuccess(arg0);
//				int status=0;
//				String info = null;
//				try {
//					info = arg0.getString("info");
//					status=arg0.getInt("status");
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				Log.i("loca_info",info);
//				if(status == 1) ToastUtil.showMsg(SettingMain.this, "地区更新成功");
//
//			}
//
//			@Override
//			public void onFinish() {
//				// TODO Auto-generated method stub
//				super.onFinish();
//			}
//
//			@Override
//			public void onStart() {
//				// TODO Auto-generated method stub
//				super.onStart();
//
//			}
//
//		}
//				);

	}


	//
	// 用户头像操作 end
	// 回调函数
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				Bitmap camerabmp = (Bitmap) data.getExtras().get("data");
				startPhotoZoom(Uri.parse(MediaStore.Images.Media.insertImage(
						getContentResolver(), camerabmp, null, null)));
				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			case SETTING_NAME:
				String name_get = data.getStringExtra("name");
				user_name.setText(name_get);
				userdao.updateUname(name_get);
				break;

			case SETTING_SEX:
				int sex_get = data.getIntExtra("sex", 0);
				if(sex_get == 1) user_sex.setText("男"); 
				else user_sex.setText("女");
				userdao.updateUsex(sex_get);
				user_to_init.setUsex(sex_get+"");
				break;

			case SETTING_SIGNATURE:
				String signature_get = data.getStringExtra("signature");
				System.out.println(signature_get);
				break;

			case SETTING_AREA:
				String area_get_id = data.getStringExtra("select_p_id");
				String area_get_name = data.getStringExtra("select_p_name");
				user_area.setText(area_get_name);
				userdao.updateUlocation(area_get_name);
				uploadArea(area_get_id,area_get_name);
				break;
			}
		}
	}

	/**
	 * 获得timestamp
	 * String型
	 */
	private static String get_timestamp_s(String birthday){
		Date date = DateUtil.parse(birthday,DateUtil.FORMAT_SHORT);
		long timestamp = date.getTime();
		return Long.toString(timestamp);
	}
	/**
	 * 获得timestamp
	 * long型
	 */
	private static long get_timestamp_l(String birthday){
		Date date = DateUtil.parse(birthday,DateUtil.FORMAT_SHORT);
		return date.getTime();
	}

	private static String TimeStamp2Date(long timestamp){    
		String date = DateUtil.format(new Date(timestamp), DateUtil.FORMAT_SHORT);   
		return date;
	}   

}
