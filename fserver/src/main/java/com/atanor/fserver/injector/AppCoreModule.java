package com.atanor.fserver.injector;

import com.atanor.fserver.api.socket.CommandServer;
import com.atanor.fserver.facades.VideoFacade;
import com.atanor.fserver.facades.VideoRecorder;
import com.atanor.fserver.facades.VideoStreamer;
import com.atanor.fserver.facades.video.FFmpegRecorder;
import com.atanor.fserver.facades.video.FFmpegStreamer;
import com.atanor.fserver.facades.video.VideoFacadeImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class AppCoreModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(VideoRecorder.class).to(FFmpegRecorder.class);
		bind(VideoStreamer.class).to(FFmpegStreamer.class);
		bind(VideoFacade.class).to(VideoFacadeImpl.class).in(Scopes.SINGLETON);

		bind(CommandServer.class).asEagerSingleton();
	}

}
