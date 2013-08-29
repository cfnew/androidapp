package com.example.bupt.model;

/**
 * 实体类
 */
public abstract class Entity extends Base {

	protected int id;
	protected String cacheKey;

	public int getId() {
		return id;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
}
