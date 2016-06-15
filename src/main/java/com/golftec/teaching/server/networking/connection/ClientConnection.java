package com.golftec.teaching.server.networking.connection;

import com.golftec.teaching.server.networking.request.IRequest;
import com.golftec.teaching.server.networking.response.IResponse;
import com.golftec.teaching.server.networking.util.ByteObjectCommon;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * This class used by serversockets to send requests 
 * and responses to the client and to get requests from clients.
 * Use ServerConnection if you're  a clientsocket... (hint, that 
 * object has not been written yet).
 * 
 * This object is a Runnable. When started by a Thread, the 
 * object will check to see if a request or a response is 
 * null or not. If not null, it sends the request or the response.
 * 
 * It sends responses before it sends requests... The ServerConnection 
 * version the client socket will use should do exactly the opposite. 
 * 
 * This object will not close a clientsocket connection. It's the 
 * client's job to know when it's done and to close itself.
 * 
 * @author Al Wells
 *
 */
public class ClientConnection implements IConnection, Runnable {
	
	private String token;
	private String ipAddress;
	private int port;
	private long connectionStartTime = 0l;
	private IRequest request;
	private IResponse response;
	private InputStream inStream;
	private OutputStream outStream;
	private boolean listening = true;
	
	public ClientConnection(Socket clientSocket) {
		try {
			inStream = clientSocket.getInputStream();
			outStream = clientSocket.getOutputStream();
			this.ipAddress = clientSocket.getRemoteSocketAddress().toString();
			this.port = clientSocket.getPort();
			this.connectionStartTime = System.currentTimeMillis();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add support for streaming.
	 */
	public void run() {
		try {
			if(response != null) {
				int bytesToSend = response.getResponseSize();
				ObjectOutputStream out = new ObjectOutputStream(outStream);
				out.write(bytesToSend);
				out.write(ByteObjectCommon.getObjectBytes(response));
				out.flush();
				out.close();
			}
			if(request != null) {
				int bytesToSend = request.getRequestSize();
				ObjectOutputStream out = new ObjectOutputStream(outStream);
				out.write(bytesToSend);
				out.write(ByteObjectCommon.getObjectBytes(request));
				out.flush();
				out.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getSessionId() {
		return token;
	}

	public void setSessionId(String token) {
		this.token = token;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getPort() {
		return port;
	}

	public long getConnectionStarted() {
		return connectionStartTime;
	}

	public long getTotalConnectionTime() {
		return System.currentTimeMillis() - connectionStartTime;
	}

	public void sendRequest(IRequest request) {
		this.request = request;
	}
	
	public IRequest getRequest() {
		return request;
	}
	

	public void sendResponse(IResponse response) {
		this.response = response;
	}
	
	public IResponse getResponse() {
		return response;
	}

	/**
	 * Need to put in a handler protocol so that when 
	 * items come over a connected socket, we'll hear about them 
	 * and the rest of the code can react.
	 * 
	 * Change the interface and this method to take a request/response handler.
	 * 
	 * Makes no sense the way it is setup now.
	 */
	public void setResponseHandler(IResponse handler) {
		try {
			ObjectInputStream in = new ObjectInputStream(inStream);
			while(listening) {
				int streamSending = in.read();
				if(streamSending != -1) {
					//can use this for create a chunk streaming system.
					int objectSize = in.readInt();				
					Object inObject = in.readObject();
					if(inObject instanceof IRequest) {
						this.request = (IRequest) inObject;
					}
					if(inObject instanceof IResponse) {
						this.response = (IResponse) inObject;
					}
					in.reset();
					//tell the handler about it...
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * We don't actually close connections. Let the client do that so that the client 
	 * does not get stream interrupt exceptions. This method will 
	 * simply make any listening that is happening stop 
	 */
	public void closeConnection() {
		listening = false;
	}

}
