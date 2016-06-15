package com.golftec.teaching.server.networking.connection;

import com.golftec.teaching.server.networking.request.IRequest;
import com.golftec.teaching.server.networking.response.IResponse;

import java.io.Serializable;

public interface IConnection extends Serializable{

	public String getSessionId();

	public void setSessionId(String token);

	public String getIpAddress();
	public int getPort();
	public long getConnectionStarted();
	public long getTotalConnectionTime();
	public void sendRequest(IRequest request);
	public void sendResponse(IResponse response);
	public void setResponseHandler(IResponse handler);
	
}
