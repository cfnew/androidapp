package com.example.bupt.model;

import java.util.ArrayList;
import java.util.List;

public class UserShareList extends Entity{
	
	public static final int LIST_TYPE_LATEST = 0;
	
	private int pageSize;
	private int itemCount;
	private List<Share> shareList = new ArrayList<Share>();
	
	public int getPageSize(){
		return this.pageSize;
	}
	public int getItemCount(){
		return this.itemCount;
	}
	public List<Share> getShareList(){
		return shareList;
	}
	
	
}
