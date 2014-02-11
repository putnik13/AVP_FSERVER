package com.atanor.fserver.api.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.atanor.fserver.facades.RecordingException;

@Provider
public class RecordingExceptionMapper implements ExceptionMapper<RecordingException> {

	@Override
	public Response toResponse(RecordingException ex) {
		return Response.status(Response.Status.BAD_REQUEST).entity(ex.getError().getCode())
				.type(MediaType.TEXT_PLAIN).build();
	}

}
