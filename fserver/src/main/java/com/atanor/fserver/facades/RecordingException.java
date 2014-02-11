package com.atanor.fserver.facades;

import com.atanor.fserver.api.Error;

@SuppressWarnings("serial")
public class RecordingException extends RuntimeException {

	private Error error;
	
	public RecordingException(final Error error){
		this.error = error;
	}

	public RecordingException(final Error error, final Throwable cause){
		super(cause);
		this.error = error;
	}
	
	public Error getError() {
		return error;
	}
	
}
