package ua.api.rest.admin.api;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import ua.api.rest.admin.modules.ModuleNetConfig;

@Path("/network")
public class NetConfig {
	private static Logger LOGGER = Logger.getLogger(NetConfig.class);
	
	@GET
	@Path("/{ipaddress}/{netmask}")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleNetConfig ipConfig(@PathParam("ipaddress") String ipaddress,
			@PathParam("netmask") String netmask){
		
		ModuleNetConfig netCfg = new ModuleNetConfig();
		netCfg.netConfig(ipaddress, netmask, "");
		return netCfg;
	}
	
	@GET
	@Path("/{ipaddress}/{netmask}/{gateway}")
	@Produces(MediaType.APPLICATION_JSON)
	public ModuleNetConfig ipGatewayConfig(@PathParam("ipaddress") String ipaddress,
			@PathParam("netmask") String netmask,
			@PathParam("gateway") String gateway){
		
		ModuleNetConfig netCfg = new ModuleNetConfig();
		netCfg.netConfig(ipaddress, netmask, gateway);
		return netCfg;
	}
	
	@GET
	@Path("/commit")
	public ModuleNetConfig ipConfigCommit(){
		ModuleNetConfig netCfg = new ModuleNetConfig();
		netCfg.commit();
		return null;
	}
	
	@GET
	@Path("/show")
	@Produces(MediaType.APPLICATION_JSON)
	public String showIpConfig() throws IOException{
		ModuleNetConfig netCfg = new ModuleNetConfig();
		String ret = netCfg.readInterface();
		return "{\"value\":\""+ret+"\"}";
	}
}
