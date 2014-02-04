package com.atanor.fserver.injector;

import com.atanor.fserver.facades.VideoFacade;
import com.atanor.fserver.facades.player.VideoFacadeImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class AppCoreModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(VideoFacade.class).to(VideoFacadeImpl.class).in(Scopes.SINGLETON);
	}

}
