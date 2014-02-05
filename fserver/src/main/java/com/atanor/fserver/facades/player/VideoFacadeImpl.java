package com.atanor.fserver.facades.player;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.api.ERROR;
import com.atanor.fserver.facades.RecordingException;
import com.atanor.fserver.facades.RecordingProcessInfo;
import com.atanor.fserver.facades.VideoFacade;
import com.atanor.fserver.facades.VideoRecorder;

public class VideoFacadeImpl implements VideoFacade {

	private static final Logger LOG = LoggerFactory.getLogger(VideoFacadeImpl.class);

	@Inject
	private VideoRecorder recorder;

	@Override
	public void startRecording() {
		if (recorder.isPlaying()) {
			throw new RecordingException(ERROR.RECORDING_IN_PROGRESS);
		}
		recorder.startRecording();
	}

	@Override
	public void stopRecording() {
		if (!recorder.isPlaying()) {
			throw new RecordingException(ERROR.RECORDING_NOT_IN_PROGRESS);
		}
		final RecordingProcessInfo info = recorder.stopRecording();
		if (info != null && info.hasChapterTags()) {
			recorder.createChapters(info);
		}
	}

	@Override
	public void addChapterTag() {
		if (!recorder.isPlaying()) {
			throw new RecordingException(ERROR.RECORDING_NOT_IN_PROGRESS);
		}
		recorder.addChapterTag();
	}

}
