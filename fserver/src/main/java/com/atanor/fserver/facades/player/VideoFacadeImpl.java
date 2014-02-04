package com.atanor.fserver.facades.player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.api.ERRCODE;
import com.atanor.fserver.facades.RecordingException;
import com.atanor.fserver.facades.VideoFacade;
import com.atanor.fserver.facades.VideoRecorder;
import com.google.common.collect.Maps;

public class VideoFacadeImpl implements VideoFacade {

	private static final Logger LOG = LoggerFactory.getLogger(VideoFacadeImpl.class);
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");

	@Inject
	private VideoRecorder recorder;

	private final String mediaSource;
	private final String mediaOptions;
	private final String recordingsOutput;

	@Inject
	public VideoFacadeImpl(final @Named("media.source") String mediaSource,
			final @Named("media.options") String mediaOptions, final @Named("recordings.output") String recordingsOutput) {
		this.mediaSource = mediaSource;
		this.mediaOptions = mediaOptions;
		this.recordingsOutput = recordingsOutput;
	}

	@Override
	public void startRecording() {
		if (recorder.isPlaying()) {
			throw new RecordingException(ERRCODE.ERR1);
		}

		final Date startTime = new Date();
		final String fileName = buildRecordingName(startTime);

		final Map<String, String> params = Maps.newHashMap();
		params.put("input", mediaSource);
		params.put("output", buildRecordingPath(fileName));

		recorder.startRecording(mediaOptions, params);
	}

	@Override
	public void stopRecording() {
		LOG.debug("PlayerFacade: stoptRecording()");
	}

	@Override
	public void addChapterTag() {
		LOG.debug("PlayerFacade: addChapterTag()");
	}

	private static String buildRecordingName(final Date date) {
		return "RECORDING-" + df.format(date) + ".mp4";
	}

	private String buildRecordingPath(final String recordingName) {
		return recordingsOutput + "/" + recordingName;
	}

}
