package com.atanor.fserver.utils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.atanor.fserver.config.Config;

public class VideoSocketChecker {
	
	public Boolean check(){
		Boolean state = false;
		
		Config config = new Config();
		String src = config.getMediaSource().split("rtsp://")[1];
		
		String host = src.split(":")[0];
		Integer port = Integer.parseInt(src.split(":")[1]);
		
		try {
			Socket socket = new Socket(host, port);
			socket.close();
			state = true;
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
		return state;
	}
}
