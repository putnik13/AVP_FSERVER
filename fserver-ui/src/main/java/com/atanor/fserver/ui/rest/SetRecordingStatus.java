package com.atanor.fserver.ui.rest;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atanor.fserver.ui.dao.RecordStatusDAO;
import com.atanor.fserver.ui.domain.RecordStatus;
import com.atanor.fserver.ui.service.RecordStatusManager;
import com.atanor.fserver.ui.service.RecordStatusManagerImpl;

@Component
@Path("/recordingStatus")
public class SetRecordingStatus {
	private static Logger LOGGER = Logger.getLogger(SetRecordingStatus.class);

	@Autowired
	private RecordStatusManager recordStatus;

	@GET
	@Path("/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response setStatusStart(@PathParam("status") String status) {
		RecordStatus recSt = new RecordStatus();
		recSt.setStatus(status);

		String statusResult = recSt.getStatus();

		if(status.trim().equals("READY")){
			recordStatus.removeStatus();
		}else{
	
			recordStatus.removeStatus();
			recordStatus.setStatus(recSt);
			
			
			LOGGER.info("STATUS: "+status);
		}
		return Response.status(200).entity("{\"status\": \""+statusResult+"\"}").build();
	}
}
