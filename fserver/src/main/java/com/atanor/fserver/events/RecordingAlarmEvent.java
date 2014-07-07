package com.atanor.fserver.events;

import com.atanor.fserver.api.Signal;

public class RecordingAlarmEvent {

	private Signal alarm;

	public RecordingAlarmEvent(final Signal alarm) {
		this.alarm = alarm;
	}

	public Signal getAlarm() {
		return alarm;
	}
}
