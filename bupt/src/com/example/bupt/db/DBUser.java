package com.example.bupt.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.bupt.base.BaseApp;
import com.example.bupt.model.User;

public class DBUser extends BaseSqlite{

	public DBUser(Context context) {
		super(context);
	}

	@Override
	protected String tableName() {
		return DB_USER;
	}

	@Override
	protected String[] tableColumns() {
		String[] cols = {
				User.COL_UID,
				User.COL_ACCOUNT,
				User.COL_PASSWORD,
				User.COL_NAME,
				User.COL_SEX,
				User.COL_BIRTHDAY,
				User.COL_STAR,
				User.COL_LOCATION,
				User.COL_FACE,
				User.COL_DESC
		};
		return cols;
	}

	/**
	 * 获得所有用户
	 */
	public List<User> findAllUsers() {
		List<User> userList = new ArrayList<User>();
		User user = null;
		try{
			db = dbh.getReadableDatabase();
			cursor = db.query(tableName(), tableColumns(), null, null,null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					user = new User();
					user.setUid(cursor.getInt(cursor.getColumnIndex(User.COL_UID)));
					user.setAccount(cursor.getString(cursor.getColumnIndex(User.COL_ACCOUNT)));
					user.setPassword(cursor.getString(cursor.getColumnIndex(User.COL_PASSWORD)));
					user.setUname(cursor.getString(cursor.getColumnIndex(User.COL_NAME)));
					user.setUsex(cursor.getString(cursor.getColumnIndex(User.COL_SEX)));
					user.setUbirthday(cursor.getString(cursor.getColumnIndex(User.COL_BIRTHDAY)));
					user.setUstar(cursor.getString(cursor.getColumnIndex(User.COL_STAR)));
					user.setUlocation(cursor.getString(cursor.getColumnIndex(User.COL_LOCATION)));
					byte[] face = cursor.getBlob(cursor.getColumnIndex(User.COL_FACE));
					if(face != null){
					ByteArrayInputStream is = new ByteArrayInputStream(face);
					user.setUface(BitmapFactory.decodeStream(is));
					}
					user.setUdesc(cursor.getString(cursor.getColumnIndex(User.COL_DESC)));
					userList.add(user);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {  
			cursor.close();
			db.close();
		}  
		return userList;
	}
	
	/**
	 * 新增用户
	 * @param user
	 */
	public void insertUsr(User user) {
		// 参数绑定对象
		ContentValues values = new ContentValues();
		values.put(User.COL_UID, user.getUid());// 用户id
		values.put(User.COL_ACCOUNT, user.getAccount());// 用户账号
		values.put(User.COL_PASSWORD, user.getPassword());// 用户密码
		values.put(User.COL_NAME, user.getUname());// 用户昵称
		values.put(User.COL_SEX, user.getUsex());// 用户性别
		values.put(User.COL_BIRTHDAY, user.getUbirthday());// 用户生日
		values.put(User.COL_STAR, user.getUstar());// 用户星座
		values.put(User.COL_LOCATION, user.getUlocation());// 用户地址
		// 如果存在图片  将图片类型数据进行存储，先转换
		if(user.getUface() != null){
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			user.getUface().compress(CompressFormat.PNG, 100, os);
			values.put(User.COL_FACE, os.toByteArray());
		}
		values.put(User.COL_DESC, user.getUdesc());
		// 插入用户数据
		create(values);
	}
	
	/**
	 * 获取当前账户信息,本地记录的账户信息可能不多,暂时按这种方法来
	 * @return
	 */
	public User getLocalUser(){
		int userid = BaseApp.getUid();
		if(userid == 0) return null;
		List<User> list = this.findAllUsers();
		if(!list.isEmpty()){
			Iterator<User> it = list.iterator();
			while(it.hasNext()){
				User u = it.next();
				if(u.getUid() == userid) return u;
			}
		}
		return null;
	}
	
	/**
	 * 修改用户uname
	 */
	public void updateUname(String uname) {
		this.updateUserInfo(User.COL_NAME, uname);
	}

	/**
	 * 修改用户upic
	 */
	public void updateUface(Drawable uface) {
		int uid = BaseApp.getUid();
		if(uid == 0) return;
		ContentValues values = new ContentValues();  
		if(uface != null){
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			BitmapDrawable pic = (BitmapDrawable) uface;
			pic.getBitmap().compress(CompressFormat.PNG, 100, os);
			values.put(User.COL_FACE, os.toByteArray());
			this.update(values, User.COL_UID+"=?", new String[]{uid+""});
		}  
	}

	/**
	 * 修改用户uLocation
	 */
	public void updateUlocation(String ulocation) {
		this.updateUserInfo(User.COL_LOCATION, ulocation);
	}
	
	/**
	 * 修改用户生日
	 */
	public void updateUbirthday(long birthday) {
		//this.updateUserInfo(User.COL_BIRTHDAY, birthday);
	}

	/**
	 * 修改用户usex
	 */
	public void updateUsex(int usex) {
		int userid = BaseApp.getUid();
		if(userid == 0) return;
		ContentValues values = new ContentValues();
		values.put(User.COL_SEX, usex);
		this.update(values, User.COL_UID+"=?", new String[]{userid+""});
	}
	
	private void updateUserInfo(String tblCol, String newVal){
		int userid = BaseApp.getUid();
		if(userid == 0) return;
		ContentValues values = new ContentValues();
		values.put(tblCol, newVal);
		this.update(values, User.COL_UID+"=?", new String[]{userid+""});
	}

}
