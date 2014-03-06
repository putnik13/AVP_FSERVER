package com.atanor.fserver.monitor;

import com.google.common.eventbus.EventBus;

public class MonitorRecordingSize implements Monitor {

	private final EventBus eventBus;
	private final String recordingPath;

	public MonitorRecordingSize(final EventBus eventBus, final String recordingPath) {
		this.eventBus = eventBus;
		this.recordingPath = recordingPath;
	}

	@Override
	public void monitor() {
		// TODO Auto-generated method stub

	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		
	}

}
