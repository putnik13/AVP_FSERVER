package com.atanor.fserver.facades;

public interface VideoStreamer {

	void startRedirect();

	void stopRedirect();

	void startRecordingAndRedirect();

	void stopRecordingAndRedirect();
	
	boolean isPlaying();
}
