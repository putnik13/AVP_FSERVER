package com.atanor.fserver.facades;

import com.atanor.fserver.api.ERRCODE;

@SuppressWarnings("serial")
public class RecordingException extends RuntimeException {

	private ERRCODE errCode;
	
	public RecordingException(final ERRCODE errCode){
		this.errCode = errCode;
	}

	public RecordingException(final ERRCODE errCode, final Throwable cause){
		super(cause);
		this.errCode = errCode;
	}
	
	public ERRCODE getErrCode() {
		return errCode;
	}
	
}
