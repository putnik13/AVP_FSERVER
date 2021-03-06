package com.atanor.fserver.api;

public enum Error implements Signal {

	INTERNAL_SERVER_ERROR("err0", "Internal server error"), 
	OPERATION_IN_PROGRESS("err1", "Operation in progress"), 
	OPERATION_NOT_IN_PROGRESS("err2", "Operation not in progress"), 
	OPERATION_INTERRUPTED("err3", "Operation interrupted");
	
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
