package com.atanor.fserver.api;

public enum ERRCODE {

	ERR1("Recording has already started."), 
	ERR2("Recording has already stopped.");

	private final String description;

	private ERRCODE(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
