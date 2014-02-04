package com.atanor.fserver.injector;

import java.util.Map;

import com.atanor.fserver.api.http.CommandResource;
import com.google.common.collect.Maps;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class AppServletModule extends ServletModule {

	private static final String JERSEY_CONFIG_PROPERTY_PACKAGES = "com.sun.jersey.config.property.packages";
	//private static final String JERSEY_API_JSON_POJO_MAPPING_FEATURE = "com.sun.jersey.api.json.POJOMappingFeature";
	private static final String REST_API_PACKAGE = "com.atanor.fserver.api.http";
	
	@Override
	protected void configureServlets() {

		bind(CommandResource.class);
		
		final Map<String, String> params = Maps.newHashMap();
        params.put(JERSEY_CONFIG_PROPERTY_PACKAGES, REST_API_PACKAGE);
        //params.put(JERSEY_API_JSON_POJO_MAPPING_FEATURE, "true");
        
		serve("/api/*").with(GuiceContainer.class, params);
	}

}
