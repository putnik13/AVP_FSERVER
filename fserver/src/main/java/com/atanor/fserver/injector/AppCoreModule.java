package com.atanor.fserver.injector;

import java.io.IOException;
import java.util.Properties;

import com.atanor.fserver.facades.VideoFacade;
import com.atanor.fserver.facades.VideoRecorder;
import com.atanor.fserver.facades.player.FFmpegRecorder;
import com.atanor.fserver.facades.player.VideoFacadeImpl;
import com.atanor.fserver.utils.AppUtils;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

public class AppCoreModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(VideoRecorder.class).to(FFmpegRecorder.class);
		bind(VideoFacade.class).to(VideoFacadeImpl.class).in(Scopes.SINGLETON);
		
		loadProperties();
	}

	private void loadProperties() {
		try {
			Properties properties = new Properties();
			properties.load(AppUtils.getServletContext().getResourceAsStream("/init.properties"));
			Names.bindProperties(binder(), properties);
		} catch (IOException e) {
			throw new IllegalStateException("Can not load init properties", e);
		}
	}
	
}
