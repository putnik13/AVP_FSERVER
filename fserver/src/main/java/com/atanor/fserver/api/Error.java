package com.atanor.fserver.api;

public enum Error implements Signal {

	INTERNAL_SERVER_ERROR("err0", "Internal server error"), 
	RECORDING_IN_PROGRESS("err1", "Recording in progress"), 
	RECORDING_NOT_IN_PROGRESS("err2", "Recording not in progress");

	private final String description;
	private final String code;

	private Error(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getCode() {
		return code;
	}

}
