package com.example.bupt.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.example.bupt.update.DownloadInfo;

public class DBDownload extends BaseSqlite{
	
	public DBDownload(Context context) {
		super(context);
	}

	@Override
	protected String tableName() {
		return DB_DOWNLOAD;
	}

	@Override
	protected String[] tableColumns() {
		String[] cols = {
				DownloadInfo.COL_ID,
				DownloadInfo.COL_THREAD,
				DownloadInfo.COL_START,
				DownloadInfo.COL_END,
				DownloadInfo.COL_COMPLETE,
				DownloadInfo.COL_URL
		};
		return cols;
	}


	/**
     * 查看数据库中是否有数据
     */
    public boolean isHasInfors(String urlstr) {
    	try{
    		db = dbh.getReadableDatabase();
    		cursor = db.query(tableName(),tableColumns(),DownloadInfo.COL_URL+"=?", new String[]{urlstr},null,null,null);
    		if(cursor != null && cursor.getCount()>0){
    			return true;
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		cursor.close();
    		db.close();
    	}
    	return false;
    }

    /**
     * 保存下载的具体信息
     */
    public void saveInfos(List<DownloadInfo> infos) {
    	ContentValues values = new ContentValues();
        for (DownloadInfo info : infos) {
        	values.clear();
        	values.put(DownloadInfo.COL_THREAD, info.getThreadId());
        	values.put(DownloadInfo.COL_START, info.getStartPos());
        	values.put(DownloadInfo.COL_END, info.getEndPos());
        	values.put(DownloadInfo.COL_COMPLETE, info.getCompeleteSize());
        	values.put(DownloadInfo.COL_URL, info.getUrl());
        	this.create(values);
        }
    }

    /**
     * 得到下载具体信息
     */
    public List<DownloadInfo> getInfos(String urlstr) {
        List<DownloadInfo> list = new ArrayList<DownloadInfo>();
        DownloadInfo down = null;
		try{
			db = dbh.getReadableDatabase();
			cursor = db.query(tableName(), tableColumns(), DownloadInfo.COL_URL+"=?", new String[]{urlstr},null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					down = new DownloadInfo();
					down.setThreadId(cursor.getInt(cursor.getColumnIndex(DownloadInfo.COL_THREAD)));
					down.setStartPos(cursor.getInt(cursor.getColumnIndex(DownloadInfo.COL_START)));
					down.setEndPos(cursor.getInt(cursor.getColumnIndex(DownloadInfo.COL_END)));
					down.setCompeleteSize(cursor.getInt(cursor.getColumnIndex(DownloadInfo.COL_COMPLETE)));
					down.setUrl(cursor.getString(cursor.getColumnIndex(DownloadInfo.COL_URL)));
					list.add(down);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {  
			cursor.close();
			db.close();
		}
		return list;
    }

    /**
     * 更新数据库中的下载信息
     */
    public void updataInfos( int completeSize,int threadId, String urlstr) {
    	ContentValues vals = new ContentValues();
    	vals.put(DownloadInfo.COL_COMPLETE, completeSize);
    	String[] bindArgs = { completeSize+"", threadId+"", urlstr };
    	this.update(vals, DownloadInfo.COL_THREAD+"=? AND "+DownloadInfo.COL_URL+"=?", bindArgs);
    }

    /**
     * 下载完成后删除数据库中的数据
     */
    public void delete_downlaod(String url) {
    	this.delete(DownloadInfo.COL_URL+"=?", new String[]{ url });
    }

}
