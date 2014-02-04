package com.atanor.fserver.facades.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.facades.VideoFacade;

public class VideoFacadeImpl implements VideoFacade {

	private static final Logger LOG = LoggerFactory.getLogger(VideoFacadeImpl.class);
	
	@Override
	public void startRecording() {
		LOG.debug("PlayerFacade: startRecording()");
	}

	@Override
	public void stopRecording() {
		LOG.debug("PlayerFacade: stoptRecording()");
	}

	@Override
	public void addChapterTag() {
		LOG.debug("PlayerFacade: addChapterTag()");
	}

}
