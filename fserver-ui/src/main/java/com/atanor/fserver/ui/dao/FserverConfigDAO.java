package com.atanor.fserver.ui.dao;

import java.util.List;

import com.atanor.fserver.ui.domain.FserverConfig;

public interface FserverConfigDAO {
	public void saveConfig(FserverConfig fsrvConf);
	public List<FserverConfig> showConfig();
	public void clearConfig();
}
