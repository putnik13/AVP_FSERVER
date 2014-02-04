package com.atanor.fserver.facades;

public interface VideoFacade {

	void startRecording() throws RecordingException;

	void stopRecording() throws RecordingException;

	void addChapterTag() throws RecordingException;
}
