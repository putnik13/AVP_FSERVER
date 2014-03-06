package com.atanor.fserver.monitor;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.eventbus.EventBus;

public class MonitorManager {

	@Inject
	private EventBus eventBus;
	
	@Named("DiskSize")
	@Inject
	private Monitor diskSize;
	
	private Monitor recordingSize;

	public void startMonitoring(final String recordingPath) {
		recordingSize = new MonitorRecordingSize(eventBus, recordingPath);

		diskSize.monitor();
		recordingSize.monitor();
	}

	public void stopMonitoring() {
		if (diskSize != null) {
			diskSize.interrupt();
		}
		if (recordingSize != null) {
			recordingSize.interrupt();
		}
	}
}
