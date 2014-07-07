package com.atanor.fserver.api.rest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.api.Signal;
import com.atanor.fserver.facades.VideoFacade;

@Singleton
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class CommandResource {

	private static final Logger LOG = LoggerFactory.getLogger(CommandResource.class);

	@Inject
	private VideoFacade videoFacade;

	@POST
	@Path("/startRecording")
	public String startVideoRecording() {
		LOG.info("--startRecording-- command received");
		final Signal response = videoFacade.startRecording();
		return response.getCode();
	}

	@POST
	@Path("/stopRecording")
	public String stopVideoRecording() {
		LOG.info("--stopRecording-- command received");
		final Signal response = videoFacade.stopRecording();
		return response.getCode();
	}

	@POST
	@Path("/addChapter")
	public String addChapterTag() {
		LOG.info("--addChapter-- command received");
		final Signal response = videoFacade.addChapterTag();
		return response.getCode();
	}
	

	@POST
	@Path("/startRedirect")
	public String startStreamRedirect() {
		LOG.info("--startRedirect-- command received");
		final Signal response = videoFacade.startStreamRedirect();
		return response.getCode();
	}
	
	@POST
	@Path("/stopRedirect")
	public String stopStreamRedirect() {
		LOG.info("--stopRedirect-- command received");
		final Signal response = videoFacade.stopStreamRedirect();
		return response.getCode();
	}
	
	@POST
	@Path("/startRecordingAndRedirect")
	public String startRecordingAndStreamRedirect() {
		LOG.info("--startRecordingAndRedirect-- command received");
		final Signal response = videoFacade.startRecordingAndRedirect();
		return response.getCode();
	}
	
	@POST
	@Path("/stopRecordingAndRedirect")
	public String stopRecordingAndStreamRedirect() {
		LOG.info("--stopRecordingAndRedirect-- command received");
		final Signal response = videoFacade.stopRecordingAndRedirect();
		return response.getCode();
	}

}
