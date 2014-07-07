package com.atanor.fserver.api;

public enum Info implements Signal {

	SUCCESS("OK", "Operation executed successfully");

	private final String description;
	private final String code;

	private Info(final String code, final String description) {
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
