package com.atanor.fserver.facades.video;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.config.Config;
import com.atanor.fserver.facades.VideoStreamer;
import com.google.common.collect.Maps;

public class FFmpegStreamer implements VideoStreamer {

	private static final Logger LOG = LoggerFactory.getLogger(FFmpegStreamer.class);

	private static final String INPUT_MEDIA_PARAM = "input";
	private static final String REDIRECT_MEDIA_PARAM = "redirect";

	@Inject
	private Config config;

	private ProcessRunner player;

	public FFmpegStreamer() {
		player = new ProcessRunner();
	}

	@Override
	public void startRedirect() {
		if (isPlaying()) {
			return;
		}

		final Map<String, String> params = Maps.newHashMap();
		params.put(INPUT_MEDIA_PARAM, config.getMediaSource());
		params.put(REDIRECT_MEDIA_PARAM, config.getRedirectUrl());

		player.run(config.getMediaRedirectOptions(), params);
		LOG.info(">>>>>> FFmpeg started redirect stream.");
	}

	@Override
	public void stopRedirect() {
		if (isPlaying()) {
			player.stop();
			LOG.info("<<<<<< FFmpeg stopped redirect stream.");
		}
	}

	@Override
	public void startRecordingAndRedirect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopRecordingAndRedirect() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isPlaying() {
		return player.isRunning();
	}

}
