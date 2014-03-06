package com.atanor.fserver.api;

public enum Warning implements Signal {

	LOW_DISK_SPACE("warn1", "Low disk space");

	private final String description;
	private final String code;

	private Warning(final String code, final String description) {
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
