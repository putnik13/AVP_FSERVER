package com.atanor.fserver.config;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Singleton;

import org.apache.commons.lang3.Validate;

import com.atanor.fserver.utils.AppUtils;

@Singleton
public class Config {

	private static final String MEDIA_SOURCE = "media.source";
	private static final String MEDIA_OPTIONS = "media.options";
	private static final String RECORDINGS_OUTPUT = "recordings.output";
	
	private static Properties properties;
	
	static{
		init();
	}

	private static void init() {
		try {
			properties = new Properties();
			properties.load(AppUtils.getServletContext().getResourceAsStream("/init.properties"));
		} catch (IOException e) {
			throw new IllegalStateException("Can not load init properties", e);
		}
	}
	
	private final String mediaSource;
	private final String mediaOptions;
	private final String recordingsOutput;
	
	public Config(){
		validate(MEDIA_SOURCE);
		this.mediaSource = properties.getProperty(MEDIA_SOURCE);
		
		validate(MEDIA_OPTIONS);
		this.mediaOptions = properties.getProperty(MEDIA_OPTIONS);
		
		validate(RECORDINGS_OUTPUT);
		this.recordingsOutput = properties.getProperty(RECORDINGS_OUTPUT);
	}
	
	private static void validate(final String property){
		Validate.notNull(properties.getProperty(property), "property {} is not specified", property);
	}

	public String getMediaSource() {
		return mediaSource;
	}

	public String getMediaOptions() {
		return mediaOptions;
	}

	public String getRecordingsOutput() {
		return recordingsOutput;
	}
	
}
