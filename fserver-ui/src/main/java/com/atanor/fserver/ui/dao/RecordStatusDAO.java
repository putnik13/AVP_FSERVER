package com.atanor.fserver.ui.dao;

import com.atanor.fserver.ui.domain.RecordStatus;

public interface RecordStatusDAO {
	public void setStatus(RecordStatus status);
	public RecordStatus showStatus();
	public void removeStatus();
}
