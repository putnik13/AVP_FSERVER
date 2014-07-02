package ua.api.rest.admin.api;

import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ua.api.rest.admin.modules.ModuleStatus;

@Path("/status")
public class Status {
	
	private static Logger LOGGER = Logger.getLogger(Status.class);

	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleStatus produceName( @PathParam("name") String name ) {
		LOGGER.debug("produceName working");
		ModuleStatus st = new ModuleStatus(name);
		
		if(name.equals("list")){
			st.list();
		}else{
			st.execute(null, null, null);
		}
		
		return st;
	}
	
	@GET
	@Path("/{name}/{param1}")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleStatus produceNameWithParam1( @PathParam("name") String name, 
			@PathParam("param1") String param1 ) {
		LOGGER.debug("produceNameWithParam1 working");
		ModuleStatus st = new ModuleStatus(name);
		st.execute(param1, null, null);
		
		return st;
	}
	
	@GET
	@Path("/{name}/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleStatus produceNameWithParam2( @PathParam("name") String name, 
			@PathParam("param1") String param1,
			@PathParam("param2") String param2) {
		LOGGER.debug("produceNameWithParam2 working");
		ModuleStatus st = new ModuleStatus(name);
		st.execute( param1, param2, null);
		
		return st;
	}

	@GET
	@Path("/{name}/{param1}/{param2}/{param3}")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleStatus produceNameWithParam3( @PathParam("name") String name, 
			@PathParam("param1") String param1,
			@PathParam("param2") String param2,
			@PathParam("param3") String param3 ) {
		LOGGER.debug("produceNameWithParam3 working");
		ModuleStatus st = new ModuleStatus(name);
		st.execute(param1, param2, param3);
		
		return st;
	}
	
}
