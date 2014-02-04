package com.atanor.fserver.api;

public enum ERROR {

	RECORDING_IN_PROGRESS("err1", "Recording is in progress."), 
	RECORDING_NOT_IN_PROGRESS("err2", "No in progress recordings.");

	private final String description;
	private final String errCode;
	
	private ERROR(final String errCode, final String description) {
		this.errCode = errCode;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getErrCode() {
		return errCode;
	}
	
}
