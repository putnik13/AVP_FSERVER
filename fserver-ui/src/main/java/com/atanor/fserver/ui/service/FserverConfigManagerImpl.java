package com.atanor.fserver.ui.service;

import java.util.List;

import org.hibernate.metamodel.source.annotations.entity.ConfiguredClassType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atanor.fserver.ui.dao.FserverConfigDAO;
import com.atanor.fserver.ui.domain.FserverConfig;

@Service
public class FserverConfigManagerImpl implements FserverConfigManager{

	@Autowired
	private FserverConfigDAO config;
	
	@Transactional
	public void saveConfig(FserverConfig fsrvConf) {
		config.saveConfig(fsrvConf);
	}

	@Transactional
	public List<FserverConfig> showConfig() {
		return config.showConfig();
	}

	@Transactional
	public void clearConfig() {
		config.clearConfig();
	}

}
