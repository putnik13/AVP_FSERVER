package com.atanor.fserver.ui.service;

import com.atanor.fserver.ui.domain.RecordStatus;

public interface RecordStatusManager {
	public void setStatus(RecordStatus status);
	public RecordStatus showStatus();
	public void removeStatus();
}
