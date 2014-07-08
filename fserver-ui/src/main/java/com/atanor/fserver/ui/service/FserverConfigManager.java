package com.atanor.fserver.ui.service;

import java.util.List;

import com.atanor.fserver.ui.domain.FserverConfig;

public interface FserverConfigManager {
	public void saveConfig(FserverConfig fsrvConf);
	public List<FserverConfig> showConfig();
	public void clearConfig();
}
