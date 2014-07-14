package com.atanor.fserver.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.Validate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.atanor.fserver.db.Fserver_ini;
import com.atanor.fserver.db.HibernateUtil;
import com.atanor.fserver.utils.AppUtils;


public class Config {

	public static final String INPUT_MEDIA_PARAM = "input";
	public static final String OUTPUT_MEDIA_PARAM = "output";
	public static final String REDIRECT_MEDIA_PARAM = "redirect";
	public static final String CHAPTER_DURATION_MEDIA_PARAM = "ch.duration";
	public static final String CHAPTER_START_MEDIA_PARAM = "ch.start";
	
	private static final String SOCKET_API_PORT = "socket.api.port";
	private static final String MEDIA_CONTAINER = "media.container";
	private static final String MEDIA_SOURCE = "media.source";
	private static final String MEDIA_RECORD_OPTIONS = "media.record";
	private static final String MEDIA_CUT_OPTIONS = "media.cut";
	private static final String MEDIA_REDIRECT_OPTIONS = "media.redirect";
	private static final String MEDIA_RECORD_AND_REDIRECT_OPTIONS = "media.record-and-redirect";
	private static final String RECORDINGS_OUTPUT = "recordings.output";
	private static final String REDIRECT_URL = "redirect.url";
	private static final String DISK_SPACE_ALARM = "disk.space.alarm_mb";
	private static final String DISK_SPACE_THRESHOLD = "disk.space.threshold_mb";
	private static final String DISK_SPACE_MONITOR_INTERVAL = "disk.space.monitor.interval_ms";
	private static final String RECORDING_SIZE_MONITOR_INTERVAL = "recording.size.monitor.interval_ms";
	private static final String RECORDING_SIZE_MONITOR_START_DELAY = "recording.size.monitor.start.delay_ms";
	private static final String RECORDING_SIZE_WARN_ATTEMPTS = "recording.size.warn.attempts";
	
//	private Properties properties;
	private Fserver_ini properties;

	private final String mediaContainer;
	private final String mediaSource;
	private final String mediaRecordOptions;
	private final String mediaCutOptions;
	private final String mediaRedirectOptions;
	private final String mediaRecordAndRedirectOptions;
	private final String recordingsOutput;
	private final Integer socketApiPort;
	private final String redirectUrl;
	private final Integer alarmDiskSpaceMb;
	private final Integer thresholdDiskSpaceMb;
	private final Integer monitorIntervalDiskSpaceMs;
	private final Integer monitorIntervalRecordingSizeMs;
	private final Integer monitorRecordingSizeStartDelayMs;
	private final Integer recordingSizeWarnAttempts;

	public Config() {
		init();

//		validate(MEDIA_CONTAINER);
		this.mediaContainer = properties.getMedia_container();

//		validate(MEDIA_SOURCE);
		this.mediaSource = properties.getMedia_source();

//		validate(MEDIA_RECORD_OPTIONS);
		this.mediaRecordOptions = properties.getMedia_record();

//		validate(MEDIA_CUT_OPTIONS);
		this.mediaCutOptions = properties.getMedia_cut();

//		validate(MEDIA_REDIRECT_OPTIONS);
		this.mediaRedirectOptions = properties.getMedia_redirect();

//		validate(MEDIA_RECORD_AND_REDIRECT_OPTIONS);
		this.mediaRecordAndRedirectOptions = properties.getMedia_record_and_redirect();

//		validate(RECORDINGS_OUTPUT);
		this.recordingsOutput = properties.getRecordings_output();

//		validate(REDIRECT_URL);
		this.redirectUrl = properties.getRedirect_url();

//		validate(SOCKET_API_PORT);
		this.socketApiPort = properties.getSocket_api_port();

//		validate(DISK_SPACE_ALARM);
		this.alarmDiskSpaceMb = properties.getDisk_space_alarm_mb();

//		validate(DISK_SPACE_THRESHOLD);
		this.thresholdDiskSpaceMb = properties.getDisk_space_threshold_mb();

//		validate(DISK_SPACE_MONITOR_INTERVAL);
		this.monitorIntervalDiskSpaceMs = properties.getDisk_space_monitor_interval_ms();

//		validate(RECORDING_SIZE_MONITOR_INTERVAL);
		this.monitorIntervalRecordingSizeMs = properties.getRecording_size_monitor_interval_ms();

//		validate(RECORDING_SIZE_MONITOR_START_DELAY);
		this.monitorRecordingSizeStartDelayMs = properties
				.getRecording_size_monitor_start_delay_ms();

//		validate(RECORDING_SIZE_WARN_ATTEMPTS);
		this.recordingSizeWarnAttempts = properties.getRecording_size_warn_attempts();

	}

	private void init() {
		Session session = null;
		Transaction transaction= null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			properties = (Fserver_ini) session.get(Fserver_ini.class, new Long(4));
			transaction.commit();
		} catch (HibernateException e) {
			throw new HibernateException("HibernateException " + e);
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		
//		try {
//			properties = new Properties();
//			properties.load(AppUtils.getServletContext().getResourceAsStream("/init.properties"));
//
//		} catch (IOException e) {
//			throw new IllegalStateException("Can not load init properties", e);
//		}
	}


	public String getMediaContainer() {
		return mediaContainer;
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

	public String getMediaRecordAndRedirectOptions() {
		return mediaRecordAndRedirectOptions;
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

	public Integer getAlarmDiskSpaceMb() {
		return alarmDiskSpaceMb;
	}

	public Integer getThresholdDiskSpaceMb() {
		return thresholdDiskSpaceMb;
	}

	public Integer getMonitorIntervalDiskSpaceMs() {
		return monitorIntervalDiskSpaceMs;
	}

	public Integer getMonitorIntervalRecordingSizeMs() {
		return monitorIntervalRecordingSizeMs;
	}

	public Integer getMonitorRecordingSizeStartDelayMs() {
		return monitorRecordingSizeStartDelayMs;
	}

	public Integer getRecordingSizeWarnAttempts() {
		return recordingSizeWarnAttempts;
	}

}
