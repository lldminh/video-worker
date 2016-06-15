package com.golftec.teaching.model.data;

public class Cache {
	
	private String cacheItemId;
	private String userId;
	private String deviceId;
	private long time = 0l;
	
	public void setCache(String cacheItemId, String userId, String deviceId) {
		this.cacheItemId = cacheItemId;
		this.userId = userId;
		this.deviceId = deviceId;
	}
	
	public long getCacheTime() {
		return time;
	}
	
	public void setCacheTime(long time) {
		this.time = time;
	}
	
	public String getCacheItemId() {
		return cacheItemId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
