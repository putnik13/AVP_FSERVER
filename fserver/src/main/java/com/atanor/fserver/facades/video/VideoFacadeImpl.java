package com.atanor.fserver.facades.video;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.api.Error;
import com.atanor.fserver.api.Info;
import com.atanor.fserver.api.Signal;
import com.atanor.fserver.events.ProcessInterruptedEvent;
import com.atanor.fserver.events.StopRecordingEvent;
import com.atanor.fserver.facades.RecordingProcessInfo;
import com.atanor.fserver.facades.VideoFacade;
import com.atanor.fserver.facades.VideoRecorder;
import com.atanor.fserver.facades.VideoStreamer;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class VideoFacadeImpl implements VideoFacade {

	private static final Logger LOG = LoggerFactory.getLogger(VideoFacadeImpl.class);

	@Inject
	private VideoRecorder recorder;

	@Inject
	private VideoStreamer streamer;

	private EventBus eventBus;

	@Inject
	public VideoFacadeImpl(final EventBus eventBus) {
		this.eventBus = eventBus;
		eventBus.register(this);
	}

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

		Signal response = Info.SUCCESS;
		try {
			final RecordingProcessInfo info = recorder.stop();
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

		Signal response = Info.SUCCESS;
		try {
			streamer.stop();
		} catch (Exception e) {
			LOG.error("Fail to stop stream redirection", e);
			response = Error.INTERNAL_SERVER_ERROR;
		}
		return response;
	}

	@Override
	public Signal startRecordingAndRedirect() {
		if (recorder.isPlaying()) {
			return Error.OPERATION_IN_PROGRESS;
		}

		Signal response = Info.SUCCESS;
		try {
			recorder.startRecordingAndRedirect();
		} catch (Exception e) {
			LOG.error("Fail to start recording and redirect stream", e);
			response = Error.INTERNAL_SERVER_ERROR;
		}
		return response;
	}

	@Override
	public Signal stopRecordingAndRedirect() {

		Signal response = Info.SUCCESS;
		try {
			recorder.stop();
		} catch (Exception e) {
			LOG.error("Fail to stop recording and redirect stream", e);
			response = Error.INTERNAL_SERVER_ERROR;
		}
		return response;
	}

	@Subscribe
	public void onStopRecording(final StopRecordingEvent event) {
		try {
			recorder.stop();
		} catch (Exception e) {
			LOG.error("Fail to stop recording", e);
		}
		eventBus.post(new ProcessInterruptedEvent());
	}

}
