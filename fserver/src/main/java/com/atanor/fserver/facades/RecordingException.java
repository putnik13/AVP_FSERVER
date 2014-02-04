package com.atanor.fserver.facades;

import com.atanor.fserver.api.ERROR;

@SuppressWarnings("serial")
public class RecordingException extends RuntimeException {

	private ERROR error;
	
	public RecordingException(final ERROR error){
		this.error = error;
	}

	public RecordingException(final ERROR error, final Throwable cause){
		super(cause);
		this.error = error;
	}
	
	public ERROR getError() {
		return error;
	}
	
}
