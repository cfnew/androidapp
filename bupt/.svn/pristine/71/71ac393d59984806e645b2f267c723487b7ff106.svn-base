package com.example.bupt.model;


/**
 * 数据操作结果实体类
 */
public class Result extends Base {

	private int status;
	private String info;
	
	public boolean OK() {
		return status == 1;
	}

	@Override
	public String toString(){
		return String.format("RESULT: CODE:%d,MSG:%s", status, info);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
