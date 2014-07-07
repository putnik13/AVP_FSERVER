package com.atanor.fserver.facades;

public interface VideoStreamer {

	void startRedirect();

	void stop();

	boolean isPlaying();
}
