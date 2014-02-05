package com.atanor.fserver.facades.player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.api.ERROR;
import com.atanor.fserver.config.Config;
import com.atanor.fserver.facades.RecordingException;
import com.atanor.fserver.facades.VideoFacade;
import com.atanor.fserver.facades.VideoRecorder;
import com.google.common.collect.Maps;

public class VideoFacadeImpl implements VideoFacade {

	private static final Logger LOG = LoggerFactory.getLogger(VideoFacadeImpl.class);
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");

	@Inject
	private VideoRecorder recorder;

	@Inject
	private Config config;

	@Override
	public void startRecording() {
		if (recorder.isPlaying()) {
			throw new RecordingException(ERROR.RECORDING_IN_PROGRESS);
		}

		final Date startTime = new Date();
		final String fileName = buildRecordingName(startTime);

		final Map<String, String> params = Maps.newHashMap();
		params.put("input", config.getMediaSource());
		params.put("output", buildRecordingPath(fileName));

		recorder.startRecording(config.getMediaOptions(), params);
	}

	@Override
	public void stopRecording() {
		if (!recorder.isPlaying()) {
			throw new RecordingException(ERROR.RECORDING_NOT_IN_PROGRESS);
		}
		recorder.stopRecording();
	}

	@Override
	public void addChapterTag() {
		if (!recorder.isPlaying()) {
			throw new RecordingException(ERROR.RECORDING_NOT_IN_PROGRESS);
		}
		recorder.addChapterTag();
	}

	private static String buildRecordingName(final Date date) {
		return "RECORDING-" + df.format(date) + ".mp4";
	}

	private String buildRecordingPath(final String recordingName) {
		return config.getRecordingsOutput() + "/" + recordingName;
	}

}
