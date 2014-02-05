package com.atanor.fserver.facades;

import java.util.Map;

public interface VideoRecorder {

	void startRecording(String media, Map<String, String> params);

	void stopRecording();
	
	void addChapterTag();

	boolean isPlaying();
}
