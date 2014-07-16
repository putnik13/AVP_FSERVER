package com.atanor.fserver.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atanor.fserver.ui.dao.RecordStatusDAO;
import com.atanor.fserver.ui.domain.RecordStatus;

@Service
public class RecordStatusManagerImpl implements RecordStatusManager{

	@Autowired
	private RecordStatusDAO recordStatusDAO;
	
	@Transactional
	public void setStatus(RecordStatus status) {
		recordStatusDAO.setStatus(status);
	}

	@Transactional
	public RecordStatus showStatus() {
		return recordStatusDAO.showStatus();
	}

	@Transactional
	public void removeStatus() {
		recordStatusDAO.removeStatus();
	}
	

}
