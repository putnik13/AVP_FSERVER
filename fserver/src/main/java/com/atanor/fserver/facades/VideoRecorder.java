package com.atanor.fserver.facades;

public interface VideoRecorder {

	void startRecording();

	void startRecordingAndRedirect();
	
	RecordingProcessInfo stop();

	void addChapterTag();

	boolean isPlaying();

	void createChapters(RecordingProcessInfo info);
}
