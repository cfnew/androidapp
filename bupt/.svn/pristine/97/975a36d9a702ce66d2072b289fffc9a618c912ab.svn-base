package com.example.bupt.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.bupt.base.AppException;
import com.example.bupt.base.BaseApp;
import com.example.bupt.base.URLs;
import com.example.bupt.db.DBFriends;
import com.example.bupt.db.DBMsg;
import com.example.bupt.model.ChatList;
import com.example.bupt.model.DetailsSList;
import com.example.bupt.model.Friend;
import com.example.bupt.model.FriendList;
import com.example.bupt.model.Msg;
import com.example.bupt.model.MsgList;
import com.example.bupt.model.Notice;
import com.example.bupt.model.PaopaoList;
import com.example.bupt.model.Result;
import com.example.bupt.model.ResultArea;
import com.example.bupt.model.ResultChat;
import com.example.bupt.model.ResultFriend;
import com.example.bupt.model.ResultLogin;
import com.example.bupt.model.ResultUserInfo;
import com.example.bupt.model.SearchFriList;
import com.example.bupt.model.ShareList;
import com.example.bupt.model.TimelineList;
import com.example.bupt.model.User;
import com.example.bupt.utils.CacheUtil;
import com.example.bupt.utils.DateUtil;
import com.example.bupt.utils.StringUtils;

public class Api {
	/**
	 * 登陆
	 * @param context
	 * @param params
	 * @return
	 */
	public static ResultLogin login(Context context,Map<String,String> params){
		JSONObject jobj = HttpUtils.postByHttpClient(context, URLs.PASSPORT_LOGIN, params);
		if(jobj != null){
			ResultLogin res = new ResultLogin();
			try {
				res.setStatus(jobj.getInt("status"));
				res.setInfo(jobj.getString("info"));
				JSONObject d = jobj.getJSONObject("data");
				res.setUid(d.getInt("uid"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return res;
		}
		return null;
	}

	/**
	 * 获取当前用户的信息
	 * @param userid
	 * @return
	 */
	public static ResultUserInfo downloadUserInfo(Context context, int userid){
		if(userid > 0){
			Map<String,String> params = new HashMap<String,String>();
			params.put("uid", userid+"");
			JSONObject jobj = HttpUtils.getByHttpClient(context, URLs.GET_USERINFO, params);
			if(jobj != null){
				ResultUserInfo res = new ResultUserInfo();
				User u = new User();
				try {
					res.setStatus(jobj.getInt("status"));
					res.setInfo(jobj.getString("info"));
					JSONObject d = jobj.getJSONObject("data");
					u.setUid(d.getInt("uid"));
					u.setUname(d.getString("uname"));
					u.setUsex(d.getString("sex"));
					u.setUbirthday(d.getString("birthday"));
					u.setUstar(d.getString("star"));
					u.setUlocation(d.getString("location"));
					u.setUdesc(d.getString("intro"));
					String faceUrl = d.getString("avatar_middle");
					if(StringUtils.isEmpty(faceUrl)){
						Bitmap bm = HttpUtils.getNetBitmap(context,faceUrl);
						if(null != bm){
							u.setUface(bm);
						}
					}
					res.setUser(u);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return res;
			}
		}
		return null;
	}
	
	/**
	 * 获取要查看的好友信息
	 * @param context
	 * @param userid
	 * @return
	 */
	public static ResultFriend getUserInfo(Context context, int userid){
		if(userid > 0){
			Map<String,String> params = new HashMap<String,String>();
			params.put("uid", userid+"");
			JSONObject jobj = HttpUtils.getByHttpClient(context, URLs.GET_USERINFO, params);
			Log.i("user_info_detail",jobj.toString());
			if(jobj != null){
				ResultFriend res = new ResultFriend();
				Friend u = new Friend();
				try {
					res.setStatus(jobj.getInt("status"));
					res.setInfo(jobj.getString("info"));
					JSONObject d = jobj.getJSONObject("data");
					u.setFriendId(d.getInt("uid"));
					u.setFriendName(d.getString("uname"));
					u.setSex(d.getInt("sex"));
					if(!StringUtils.isEmpty(d.getString("birthday"))){
						u.setAge(DateUtil.genAge(d.getInt("birthday")));
					}
					u.setStar(d.getString("star"));
					u.setLocation(d.getString("location"));
					u.setDesc(d.getString("intro"));
					u.setFace(d.getString("avatar_middle"));
					u.setFaceBig(d.getString("avatar_original"));
					u.setIsFriend(d.getInt("is_friend"));
					res.setF(u);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return res;
			}
		}
		return null;
	}
	
	/**
	 * 发出添加好友请求
	 * @param uid
	 * @param targetUid
	 * @return
	 */
	public static Result addNewFriend(Context context, int uid, int targetUid){
		Map<String,String> params = new HashMap<String,String>();
		params.put("uid", uid+"");
		params.put("fid", targetUid+"");
		JSONObject jobj = HttpUtils.postByHttpClient(context, URLs.ADD_NEW_FRIEND, params);
		if(jobj != null){
			Result res = new Result();
			try {
				res.setStatus(jobj.getInt("status"));
				res.setInfo(jobj.getString("info"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return res;
		}
		return null;
	}
	
	/**
	 * 接受或拒绝好友邀请
	 * @param context
	 * @param targetUid
	 * @param option
	 * @return
	 */
	public static Result optFriendRequest(Context context,int targetUid, String action){
		Map<String,String> params = new HashMap<String,String>();
		params.put("uid", targetUid+"");
		params.put("action", action.trim());
		JSONObject jobj = HttpUtils.postByHttpClient(context, URLs.ACCEPT_NEW_FRIEND, params);
		if(jobj != null){
			Result res = new Result();
			try {
				res.setStatus(jobj.getInt("status"));
				res.setInfo(jobj.getString("info"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return res;
		}
		return null;
	}
	
	/**
	 * 获取所有的省份
	 * 
	 * @return
	 */
	public static ResultArea getProvinces(Context context) {
		JSONObject jobj = HttpUtils.getByHttpClient(context, URLs.GET_PROVINCE,null);
		if (jobj != null) {
			ResultArea res = new ResultArea();
			try {
				res.setStatus(jobj.getInt("status"));
				res.setInfo(jobj.getString("info"));
				JSONArray jarr = jobj.getJSONArray("data");
				ArrayList<ArrayList<String>> pros = new ArrayList<ArrayList<String>>();
				ArrayList<String> tmp = null;
				for (int i = 0; i < jarr.length(); i++) {
					tmp = new ArrayList<String>();
					tmp.add(jarr.getJSONObject(i).getString("area_id"));
					tmp.add(jarr.getJSONObject(i).getString("title"));
					pros.add(tmp);
				}
				res.setAreas(pros);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return res;
		}
		return null;
	}

	/**
	 * 加载活动数据
	 * @param context
	 * @param cat
	 * @param pageIndex
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 */
	public static PaopaoList getPaopaoList(Context context, int cat, int pageIndex, boolean isRefresh)
			throws AppException {
		PaopaoList list = null;
		String key = "paopao_list_" + cat + "_" + pageIndex + "_" + BaseApp.PAGE_SIZE;
		if (HttpUtils.isNetworkConnected(context) && (!CacheUtil.isCacheReadable(context,key) || isRefresh)) {
			try {
				Map<String,String> params = new HashMap<String,String>();
				params.put("limit", BaseApp.PAGE_SIZE+"");
				params.put("page", pageIndex+"");
				JSONObject jobj = HttpUtils.getByHttpClient(context, URLs.HOME_PUBLIC_TIMELINE, params);
				list = PaopaoList.parse(jobj);
				if (list != null && pageIndex == 1) {
					Notice notice = list.getNotice();
					list.setNotice(null);
					list.setCacheKey(key);
					CacheUtil.saveObject(context,list, key);
					list.setNotice(notice);
				}
			} catch (Exception e) {
				list = (PaopaoList) CacheUtil.readObject(context,key);
				if (list == null)
					list = new PaopaoList();
			}
		} else {
			list = (PaopaoList) CacheUtil.readObject(context,key);
			if (list == null)
				list = new PaopaoList();
		}
		return list;
	}
	
	/**
	 * 加载个人时间轴数据
	 * @param context
	 * @param cat
	 * @param pageIndex
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 */
	public static TimelineList getTimelineList(Context context, int uid, int pageIndex, boolean isRefresh)
			throws AppException {
		TimelineList list = null;
		String key = "timeline_list_" + pageIndex + "_" + BaseApp.PAGE_SIZE;
		if (HttpUtils.isNetworkConnected(context) && (!CacheUtil.isCacheReadable(context,key) || isRefresh)) {
			try {
				Map<String,String> params = new HashMap<String,String>();
				params.put("uid", uid+"");
				params.put("limit", BaseApp.PAGE_SIZE+"");
				params.put("page", pageIndex+"");
				JSONObject jobj = HttpUtils.getByHttpClient(context, URLs.HOME_USER_TIMELINE, params);
				list = TimelineList.parse(jobj);
				if (list != null && pageIndex == 1) {
					Notice notice = list.getNotice();
					list.setNotice(null);
					list.setCacheKey(key);
					CacheUtil.saveObject(context,list, key);
					list.setNotice(notice);
				}
			} catch (Exception e) {
				list = (TimelineList) CacheUtil.readObject(context,key);
				if (list == null)
					list = new TimelineList();
			}
		} else {
			list = (TimelineList) CacheUtil.readObject(context,key);
			if (list == null)
				list = new TimelineList();
		}
		return list;
	}
	
	/**
	 * 加载分享数据
	 * @param context
	 * @param cat
	 * @param pageIndex
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 */
	public static ShareList getShareList(Context context, int cat, int pageIndex, boolean isRefresh)
			throws AppException {
		ShareList list = null;
		String key = "share_list_" + cat + "_" + pageIndex + "_" + BaseApp.PAGE_SIZE;
		if (HttpUtils.isNetworkConnected(context) && (!CacheUtil.isCacheReadable(context,key) || isRefresh)) {
			try {
				Map<String,String> params = new HashMap<String,String>();
				params.put("limit", BaseApp.PAGE_SIZE+"");
				params.put("page", pageIndex+"");
				JSONObject jobj = HttpUtils.getByHttpClient(context, URLs.SHARE_PUBLIC_TIMELINE, params);
				list = ShareList.parse(jobj);
				if (list != null && pageIndex == 1) {
					Notice notice = list.getNotice();
					list.setNotice(null);
					list.setCacheKey(key);
					CacheUtil.saveObject(context,list, key);
					list.setNotice(notice);
				}
			} catch (Exception e) {
				list = (ShareList) CacheUtil.readObject(context,key);
				if (list == null)
					list = new ShareList();
			}
		} else {
			list = (ShareList) CacheUtil.readObject(context,key);
			if (list == null)
				list = new ShareList();
		}
		return list;
	}
	
	/**
	 * 加载消息数据
	 * @param context
	 * @param cat
	 * @param pageIndex
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 */
	public static MsgList getMessageList(Context context, boolean isRefresh)
			throws AppException {
		MsgList list = null;
		DBMsg db = new DBMsg(context);
		List<Msg> dbList = db.findAllMsgs();
		if(dbList.size() > 0){
			list = new MsgList();
			list.setMsgList(dbList);
		}else{
			if (HttpUtils.isNetworkConnected(context)) {
				try {
					JSONObject jobj = HttpUtils.getByHttpClient(context, URLs.GET_UNREAD_MSG, null);
					list = MsgList.parse(jobj);
					if (list != null) {
						//存入数据库
						db.insertMsg(list.getMsgList());
					}
				} catch (Exception e) {
					list = new MsgList();
				}
			} else {
				list = new MsgList();
			}
		}
		return list;
	}
	
	/**
	 * 加载好友列表数据
	 * @param context
	 * @param cat
	 * @param pageIndex
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 */
	public static FriendList getFriendList(Context context, boolean isRefresh)
			throws AppException {
		FriendList list = null;
		DBFriends db = new DBFriends(context);
		List<Friend> dbList = db.findAllFriends();
		if(dbList.size() > 0){
			list = new FriendList();
			list.setFriendList(dbList);
		}else{
			if (HttpUtils.isNetworkConnected(context)) {
				try {
					JSONObject jobj = HttpUtils.getByHttpClient(context, URLs.FRIEND_GET_FRIENDS, null);
					list = FriendList.parse(jobj);
					if (list != null) {
						//存入数据库
						db.addUsers(list.getFriendList());
					}
				} catch (Exception e) {
					list = new FriendList();
				}
			} else {
				list = new FriendList();
			}
		}
		return list;
	}

	/**
	 * 加载会话数据
	 * @param context
	 * @param cat
	 * @param pageIndex
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 */
	public static ChatList getChatList(Context context,int chat_id)
			throws AppException {
		ChatList list = null;
		String key = "chat_list_"+chat_id;
		if (HttpUtils.isNetworkConnected(context) && (!CacheUtil.isCacheReadable(context,key))) {
			try {
				Map<String,String> params = new HashMap<String,String>();
				params.put("chat_id", chat_id+"");
				JSONObject jobj = HttpUtils.getByHttpClient(context, URLs.GET_CHAT_LIST, params);
				list = ChatList.parse(jobj);
				if (list != null) {
					Notice notice = list.getNotice();
					list.setNotice(null);
					list.setCacheKey(key);
					CacheUtil.saveObject(context,list, key);
					list.setNotice(notice);
				}
			} catch (Exception e) {
				list = (ChatList) CacheUtil.readObject(context,key);
				if (list == null)
					list = new ChatList();
			}
		} else {
			list = (ChatList) CacheUtil.readObject(context,key);
			if (list == null)
				list = new ChatList();
		}
		return list;
	}
	
	/**
	 * 会话回复
	 * @return
	 */
	public static ResultChat replay(Context context, int chatId, String content)
		throws AppException{
		Map<String,String> params = new HashMap<String,String>();
		params.put("chat_id", chatId+"");
		params.put("content", content.trim());
		JSONObject jobj = HttpUtils.postByHttpClient(context, URLs.CHAT_REPLY, params);
		if(jobj != null){
			ResultChat res = new ResultChat();
			try {
				res.setStatus(jobj.getInt("status"));
				res.setInfo(jobj.getString("info"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return res;
		}
		return null;
	}
	
	/**
	 * 加载好友搜索数据
	 * @param context
	 * @param cat
	 * @param pageIndex
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 */
	public static SearchFriList getSearchFriList(Context context,String content,int pageIndex, int pageSize)
			throws AppException {
		SearchFriList list = null;
		String key = "search_fri_list_"+content;
		if (HttpUtils.isNetworkConnected(context) && (!CacheUtil.isCacheReadable(context,key))) {
			try {
				Map<String,String> params = new HashMap<String,String>();
				params.put("name", content);
				params.put("page", pageIndex+"");
				params.put("limit", pageSize+"");
				JSONObject jobj = HttpUtils.getByHttpClient(context, URLs.SEARCH_FRIEND, params);
				Log.i("chat_res",jobj.toString());
				list = SearchFriList.parse(jobj);
				if (list != null) {
					Notice notice = list.getNotice();
					list.setNotice(null);
					list.setCacheKey(key);
					CacheUtil.saveObject(context,list, key);
					list.setNotice(notice);
				}
			} catch (Exception e) {
				list = (SearchFriList) CacheUtil.readObject(context,key);
				if (list == null)
					list = new SearchFriList();
			}
		} else {
			list = (SearchFriList) CacheUtil.readObject(context,key);
			if (list == null)
				list = new SearchFriList();
		}
		return list;
	}
	
	/**
	 * 加载活动数据
	 * @param context
	 * @param cat
	 * @param pageIndex
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 */
	public static DetailsSList getDetailsSList(Context context,int fid)
			throws AppException {
		DetailsSList list = null;
		String key = "pp_detail_"+fid;
		if (HttpUtils.isNetworkConnected(context) && (!CacheUtil.isCacheReadable(context,key))) {
			try {
				Map<String,String> params = new HashMap<String,String>();
				params.put("fid", fid+"");
				JSONObject jobj = HttpUtils.getByHttpClient(context, URLs.GET_DETAILS_DATA, params);
				list = DetailsSList.parse(jobj);
				if (list != null) {
					Notice notice = list.getNotice();
					list.setNotice(null);
					list.setCacheKey(key);
					CacheUtil.saveObject(context,list, key);
					list.setNotice(notice);
				}
			} catch (Exception e) {
				list = (DetailsSList) CacheUtil.readObject(context,key);
				if (list == null)
					list = new DetailsSList();
			}
		} else {
			list = (DetailsSList) CacheUtil.readObject(context,key);
			if (list == null)
				list = new DetailsSList();
		}
		return list;
	}
	
	/**
	 * 会话回复
	 * @return
	 */
	public static Result joinPaopao(Context context, int ppid)
		throws AppException{
		Map<String,String> params = new HashMap<String,String>();
		params.put("uid", BaseApp.getUid()+"");
		params.put("fid", ppid+"");
		JSONObject jobj = HttpUtils.postByHttpClient(context, URLs.HOME_JOIN_FEED, params);
		if(jobj != null){
			Result res = new Result();
			try {
				res.setStatus(jobj.getInt("status"));
				res.setInfo(jobj.getString("info"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return res;
		}
		return null;
	}
	
	/**
	 * 删除一条消息
	 * @return
	 */
	public static Result delMessage(Context context, int msgid)
		throws AppException{
		Map<String,String> params = new HashMap<String,String>();
		params.put("uid", BaseApp.getUid()+"");
		params.put("nid", msgid+"");
		Log.i("uid+nid",BaseApp.getUid()+"====="+msgid);
		JSONObject jobj = HttpUtils.postByHttpClient(context, URLs.DEL_ONE_MSG, params);
		if(jobj != null){
			Result res = new Result();
			try {
				res.setStatus(jobj.getInt("status"));
				res.setInfo(jobj.getString("info"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return res;
		}
		return null;
	}
	
}
