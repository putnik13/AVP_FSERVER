package com.atanor.fserver.facades;

import com.atanor.fserver.api.Signal;

public interface VideoFacade {

	Signal startRecording();

	Signal stopRecording();

	Signal addChapterTag();
	
	Signal startStreamRedirect();
	
	Signal stopStreamRedirect();
	
	Signal startRecordingAndRedirect();
	
	Signal stopRecordingAndRedirect();
}
