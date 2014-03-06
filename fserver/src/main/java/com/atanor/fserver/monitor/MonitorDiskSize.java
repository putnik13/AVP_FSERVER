package com.atanor.fserver.monitor;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import org.apache.commons.io.FileSystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.api.Warning;
import com.atanor.fserver.config.Config;
import com.atanor.fserver.events.RecordingAlarmEvent;
import com.atanor.fserver.events.StopRecordingEvent;
import com.google.common.eventbus.EventBus;

public class MonitorDiskSize implements Monitor {

	private static final Logger LOG = LoggerFactory.getLogger(MonitorDiskSize.class);
	private static final int DELAY_TIME = 0;

	@Inject
	private EventBus eventBus;

	@Inject
	private Config config;

	private Timer timer;

	@Override
	public void monitor() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				execute();
			}
		}, DELAY_TIME, config.getMonitorIntervalDiskSpaceMs());

	}

	@Override
	public void interrupt() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private void execute() {
		try {

			final double freeDiskSpaceKb = FileSystemUtils.freeSpaceKb(config.getRecordingsOutput());
			final Long freeDiskSpaceMb = Math.round(freeDiskSpaceKb / 1024);

			if (freeDiskSpaceMb.longValue() <= config.getThresholdDiskSpaceMb()) {
				LOG.error("!!! Low disk space threshold reached.");
				eventBus.post(new StopRecordingEvent());
				return;
			}
			
			if (freeDiskSpaceMb.longValue() <= config.getAlarmDiskSpaceMb()) {
				LOG.warn("!!! Low disk space.");
				eventBus.post(new RecordingAlarmEvent(Warning.LOW_DISK_SPACE));
			}

		} catch (IOException e) {
			LOG.error("Error during getting disk space size", e);
		}
	}
}
