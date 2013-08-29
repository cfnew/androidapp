package com.example.bupt.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


import com.example.bupt.utils.AppUtil;

import android.R.array;

public class BaseMessage {
	private String status;
	private String info;
	private String data;
	private String modelName;
	private String modelType;
	private Map<String, BaseModel> dataModel;
	private Map<String, ArrayList<? extends BaseModel>> dataModelList;

	public BaseMessage(String modelName,String modelType) {
		this.modelName=modelName;
		this.modelType=modelType;
		this.dataModel = new HashMap<String, BaseModel>();
		this.dataModelList = new HashMap<String, ArrayList<? extends BaseModel>>();
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus() {
		this.status = status;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo() {
		this.info = info;
	}
	
	public String getData()
	{
		return this.data;
	}
	
	public Object getDataModel(String modelName) throws Exception
	{
		Object model=this.dataModel.get(modelName);
		if (model==null)
		{
			throw new Exception("dataModel is empty");
			
		}
		return model;
	}
	
	public ArrayList<? extends BaseModel> getDataModelList(String modelName) throws Exception
	{
		ArrayList<? extends BaseModel> modelList=this.dataModelList.get(modelName);
		if(modelList==null||modelList.size()==0)
		{
			throw new Exception("dataModelList is empty");
			
			
		}
		return modelList;
	}
	
	public void setResult(String data) throws Exception
	{
		this.data=data;
		String modelClassName=C.PackageName.MODEL_PACKAGE+modelName;
		if(data.length()>0)
		{
			JSONArray jsonArray=new JSONArray(data);
			if(this.modelType==C.ModelType.MODEL)
			{
				JSONObject jsonModel=jsonArray.getJSONObject(0);
				if(jsonModel==null)
					throw new Exception("dataModel is empty");
				this.dataModel.put(this.modelName,jsonToModel(modelClassName,jsonModel));				
			}
			else
			{
				ArrayList<BaseModel> modelList = new ArrayList<BaseModel>();
				for(int i=0;i<jsonArray.length();i++)
				{
					JSONObject jsonModel=jsonArray.getJSONObject(i);
					if(jsonModel==null)
						throw new Exception("dataModel is empty");
					modelList.add(jsonToModel(modelClassName, jsonModel));		
				}
				this.dataModelList.put(this.modelName, modelList);
			}

		}
	}
	
	private BaseModel jsonToModel(String modelClassName,JSONObject jsonModel) throws Exception
	{
		BaseModel baseModel=(BaseModel) Class.forName(modelClassName).newInstance();
		Class<? extends BaseModel> modelClass=baseModel.getClass();
		Iterator<String> iterator =jsonModel.keys();
		while(iterator.hasNext())
		{
			String fieldStr=iterator.next();
			String value=null;
			if(jsonModel.optString(fieldStr)!=null)
			   value=jsonModel.optString(fieldStr);
			else
				value=Integer.toString(jsonModel.optInt(fieldStr));
			Field field=modelClass.getDeclaredField(fieldStr);
			field.setAccessible(true);
			field.set(baseModel, value);
		}
	     return baseModel;
	}
	
	private String getModelName(String str)
	{
		String[] strArr=str.split("\\W");
		if(strArr.length>0)
		{
			str=strArr[0];
			
		}
		return AppUtil.ucfirst(str);
	}
}
