package com.atanor.fserver.api.http;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.facades.VideoFacade;

@Singleton
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class CommandResource {

	private static final Logger LOG = LoggerFactory.getLogger(CommandResource.class);
	private static final String RESPONSE_SUCCESS = "SUCCESS";

	@Inject
	private VideoFacade videoFacade;

	@POST
	@Path("/startVideoRecording")
	public String startVideoRecording() {
		LOG.info("--startVideoRecording-- command received");
		videoFacade.startRecording();
		return RESPONSE_SUCCESS;
	}

	@POST
	@Path("/stopVideoRecording")
	public String stopVideoRecording() {
		LOG.info("--stopVideoRecording-- command received");
		videoFacade.stopRecording();
		return RESPONSE_SUCCESS;
	}

	@POST
	@Path("/addChapter")
	public String addChapterTag() {
		LOG.info("--addChapter-- command received");
		videoFacade.addChapterTag();
		return RESPONSE_SUCCESS;
	}

}
