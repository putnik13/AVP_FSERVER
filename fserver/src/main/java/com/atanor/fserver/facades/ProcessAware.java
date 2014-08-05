package com.atanor.fserver.facades;

public interface ProcessAware {

	void onProcessComplete(int exitValue);
	
	Boolean onProcessFailed();
}
