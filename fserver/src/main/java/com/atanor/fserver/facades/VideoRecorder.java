package com.atanor.fserver.facades;

public interface VideoRecorder {

	void startRecording();

	RecordingProcessInfo stopRecording();

	void addChapterTag();

	boolean isPlaying();

	void createChapters(RecordingProcessInfo info);
}
