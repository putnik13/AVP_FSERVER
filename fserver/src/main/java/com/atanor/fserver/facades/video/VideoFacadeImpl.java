package com.atanor.fserver.facades.video;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.api.Error;
import com.atanor.fserver.api.Info;
import com.atanor.fserver.api.Signal;
import com.atanor.fserver.facades.RecordingProcessInfo;
import com.atanor.fserver.facades.VideoFacade;
import com.atanor.fserver.facades.VideoRecorder;
import com.atanor.fserver.facades.VideoStreamer;

public class VideoFacadeImpl implements VideoFacade {

	private static final Logger LOG = LoggerFactory.getLogger(VideoFacadeImpl.class);

	@Inject
	private VideoRecorder recorder;

	@Inject
	private VideoStreamer streamer;

	@Override
	public Signal startRecording() {
		if (recorder.isPlaying()) {
			return Error.OPERATION_IN_PROGRESS;
		}

		Signal response = Info.SUCCESS;
		try {
			recorder.startRecording();
		} catch (Exception e) {
			LOG.error("Fail to start recording", e);
			response = Error.INTERNAL_SERVER_ERROR;
		}
		return response;
	}

	@Override
	public Signal stopRecording() {
		if (!recorder.isPlaying()) {
			return Error.OPERATION_NOT_IN_PROGRESS;
		}

		Signal response = Info.SUCCESS;
		try {
			final RecordingProcessInfo info = recorder.stopRecording();
			if (info != null && info.hasChapterTags()) {
				recorder.createChapters(info);
			}
		} catch (Exception e) {
			LOG.error("Fail to stop recording", e);
			response = Error.INTERNAL_SERVER_ERROR;
		}
		return response;
	}

	@Override
	public Signal addChapterTag() {
		if (!recorder.isPlaying()) {
			return Error.OPERATION_NOT_IN_PROGRESS;
		}

		Signal response = Info.SUCCESS;
		try {
			recorder.addChapterTag();
		} catch (Exception e) {
			LOG.error("Fail to add chapter", e);
			response = Error.INTERNAL_SERVER_ERROR;
		}
		return response;
	}

	@Override
	public Signal startStreamRedirect() {
		if (streamer.isPlaying()) {
			return Error.OPERATION_IN_PROGRESS;
		}

		Signal response = Info.SUCCESS;
		try {
			streamer.startRedirect();
		} catch (Exception e) {
			LOG.error("Fail to start stream redirection", e);
			response = Error.INTERNAL_SERVER_ERROR;
		}
		return response;
	}

	@Override
	public Signal stopStreamRedirect() {
		if (!streamer.isPlaying()) {
			return Error.OPERATION_NOT_IN_PROGRESS;
		}

		Signal response = Info.SUCCESS;
		try {
			streamer.stopRedirect();
		} catch (Exception e) {
			LOG.error("Fail to stop stream redirection", e);
			response = Error.INTERNAL_SERVER_ERROR;
		}
		return response;
	}

	@Override
	public Signal startRecordingAndRedirect() {
		if (streamer.isPlaying()) {
			return Error.OPERATION_IN_PROGRESS;
		}

		Signal response = Info.SUCCESS;
		try {
			streamer.startRecordingAndRedirect();
		} catch (Exception e) {
			LOG.error("Fail to start recording + redirect stream operation", e);
			response = Error.INTERNAL_SERVER_ERROR;
		}
		return response;
	}

	@Override
	public Signal stopRecordingAndRedirect() {
		if (!streamer.isPlaying()) {
			return Error.OPERATION_NOT_IN_PROGRESS;
		}

		Signal response = Info.SUCCESS;
		try {
			streamer.stopRecordingAndRedirect();
		} catch (Exception e) {
			LOG.error("Fail to stop recording + redirect stream operation", e);
			response = Error.INTERNAL_SERVER_ERROR;
		}
		return response;
	}

}
