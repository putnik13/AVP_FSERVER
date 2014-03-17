package com.atanor.fserver.monitor;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.api.Warning;
import com.atanor.fserver.config.Config;
import com.atanor.fserver.events.RecordingAlarmEvent;
import com.atanor.fserver.events.StopRecordingEvent;
import com.google.common.eventbus.EventBus;

public class MonitorRecordingSize implements Monitor {

	private static final Logger LOG = LoggerFactory.getLogger(MonitorRecordingSize.class);

	@Inject
	private EventBus eventBus;

	@Inject
	private Config config;

	private String recordingPath;
	private Timer timer;
	private long currentSize;
	private int warnAttempts;

	@Override
	public void monitor() {
		Validate.notEmpty(recordingPath, "recordingPath is not set");
		currentSize = -1;
		warnAttempts = 0;

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				execute();
			}
		}, config.getMonitorRecordingSizeStartDelayMs(), config.getMonitorIntervalRecordingSizeMs());
	}

	@Override
	public void interrupt() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		recordingPath = null;
		currentSize = -1;
		warnAttempts = 0;
	}

	public void setRecordingPath(final String recordingPath) {
		this.recordingPath = recordingPath;
	}

	private void execute() {
		final File recording = new File(recordingPath);
		if (!recording.exists()) {
			LOG.error("!!! {} file not exist.", recordingPath);
			eventBus.post(new StopRecordingEvent());
			return;
		}

		if (FileUtils.sizeOf(recording) == 0) {
			LOG.warn("!!! Recording file is empty.");
			warnAttempts++;
			eventBus.post(new RecordingAlarmEvent(Warning.RECORDING_FILE_EMPTY));
		} else if (FileUtils.sizeOf(recording) == currentSize) {
			LOG.warn("!!! Recording file is not changed.");
			warnAttempts++;
			eventBus.post(new RecordingAlarmEvent(Warning.RECORDING_SIZE_NOT_CHANGED));
		} else {
			warnAttempts = 0;
		}

		if (warnAttempts != 0 && warnAttempts >= config.getRecordingSizeWarnAttempts()) {
			LOG.error("!!! All warning attempts exhausted.");
			eventBus.post(new StopRecordingEvent());
			return;
		}

		currentSize = FileUtils.sizeOf(recording);
	}

}
