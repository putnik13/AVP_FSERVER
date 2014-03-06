package com.atanor.fserver.injector;

import java.util.concurrent.Executors;

import com.atanor.fserver.api.socket.CommandServer;
import com.atanor.fserver.config.Config;
import com.atanor.fserver.events.DeadEventListener;
import com.atanor.fserver.facades.VideoFacade;
import com.atanor.fserver.facades.VideoRecorder;
import com.atanor.fserver.facades.VideoStreamer;
import com.atanor.fserver.facades.video.FFmpegRecorder;
import com.atanor.fserver.facades.video.FFmpegStreamer;
import com.atanor.fserver.facades.video.VideoFacadeImpl;
import com.atanor.fserver.monitor.Monitor;
import com.atanor.fserver.monitor.MonitorDiskSize;
import com.atanor.fserver.monitor.MonitorManager;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

public class AppCoreModule extends AbstractModule {

	@Override
	protected void configure() {
		final EventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
		eventBus.register(new DeadEventListener());
		bind(EventBus.class).toInstance(eventBus);
		
		bind(VideoRecorder.class).to(FFmpegRecorder.class);
		bind(VideoStreamer.class).to(FFmpegStreamer.class);
		bind(VideoFacade.class).to(VideoFacadeImpl.class).in(Scopes.SINGLETON);
		bind(Config.class).in(Scopes.SINGLETON);
		
		bind(CommandServer.class).asEagerSingleton();
		bind(Monitor.class).annotatedWith(Names.named("DiskSize")).to(MonitorDiskSize.class);
		bind(MonitorManager.class);
	}

}
