package com.atanor.fserver.facades.video;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.config.Config;
import com.atanor.fserver.events.ProcessInterruptedEvent;
import com.atanor.fserver.facades.ProcessAware;
import com.atanor.fserver.facades.VideoStreamer;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;

public class FFmpegStreamer implements VideoStreamer, ProcessAware {

	private static final Logger LOG = LoggerFactory.getLogger(FFmpegStreamer.class);

	@Inject
	private EventBus eventBus;
	
	@Inject
	private Config config;

	private ProcessRunner player;

	public FFmpegStreamer() {
		player = new ProcessRunner(this);
	}

	@Override
	public void startRedirect() {
		if (isPlaying()) {
			return;
		}

		final Map<String, String> params = Maps.newHashMap();
		params.put(Config.INPUT_MEDIA_PARAM, config.getMediaSource());
		params.put(Config.REDIRECT_MEDIA_PARAM, config.getRedirectUrl());

		player.run(config.getMediaRedirectOptions(), params);
		LOG.info(">>>>>> FFmpeg started redirect stream to {}", config.getRedirectUrl());
	}

	@Override
	public void stop() {
		if (isPlaying()) {
			player.stop();
			LOG.info("<<<<<< FFmpeg stopped redirect stream.");
		}
	}

	@Override
	public boolean isPlaying() {
		return player.isRunning();
	}

	@Override
	public void onProcessComplete(int exitValue) {
		eventBus.post(new ProcessInterruptedEvent());
	}

	@Override
	public void onProcessFailed() {
		eventBus.post(new ProcessInterruptedEvent());
	}

}
