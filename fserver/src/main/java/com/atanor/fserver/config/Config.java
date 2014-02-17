package com.atanor.fserver.config;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Singleton;

import org.apache.commons.lang3.Validate;

import com.atanor.fserver.utils.AppUtils;

@Singleton
public class Config {

	private static final String SOCKET_API_PORT = "socket.api.port";
	private static final String MEDIA_SOURCE = "media.source";
	private static final String MEDIA_RECORD_OPTIONS = "media.record";
	private static final String MEDIA_CUT_OPTIONS = "media.cut";
	private static final String MEDIA_REDIRECT_OPTIONS = "media.redirect";
	private static final String RECORDINGS_OUTPUT = "recordings.output";
	private static final String REDIRECT_URL = "redirect.url";
	
	private static Properties properties;

	static {
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
	private final String mediaRecordOptions;
	private final String mediaCutOptions;
	private final String mediaRedirectOptions;
	private final String recordingsOutput;
	private final Integer socketApiPort;
	private final String redirectUrl;
	
	public Config() {
		validate(MEDIA_SOURCE);
		this.mediaSource = properties.getProperty(MEDIA_SOURCE);

		validate(MEDIA_RECORD_OPTIONS);
		this.mediaRecordOptions = properties.getProperty(MEDIA_RECORD_OPTIONS);

		validate(MEDIA_CUT_OPTIONS);
		this.mediaCutOptions = properties.getProperty(MEDIA_CUT_OPTIONS);

		validate(MEDIA_REDIRECT_OPTIONS);
		this.mediaRedirectOptions = properties.getProperty(MEDIA_REDIRECT_OPTIONS);
		
		validate(RECORDINGS_OUTPUT);
		this.recordingsOutput = properties.getProperty(RECORDINGS_OUTPUT);

		validate(REDIRECT_URL);
		this.redirectUrl = properties.getProperty(REDIRECT_URL);
		
		validate(SOCKET_API_PORT);
		this.socketApiPort = Integer.parseInt(properties.getProperty(SOCKET_API_PORT));
	}

	private static void validate(final String property) {
		Validate.notNull(properties.getProperty(property), "property %s is not specified", property);
	}

	public String getMediaSource() {
		return mediaSource;
	}

	public String getMediaRecordOptions() {
		return mediaRecordOptions;
	}

	public String getMediaCutOptions() {
		return mediaCutOptions;
	}

	public String getMediaRedirectOptions() {
		return mediaRedirectOptions;
	}

	public String getRecordingsOutput() {
		return recordingsOutput;
	}

	public Integer getSocketApiPort() {
		return socketApiPort;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

}
