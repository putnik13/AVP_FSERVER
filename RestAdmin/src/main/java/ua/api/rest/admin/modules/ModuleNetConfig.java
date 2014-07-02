package ua.api.rest.admin.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class ModuleNetConfig {
	private String status;
	
	private static Logger LOGGER = Logger.getLogger(ModuleNetConfig.class);

	private static final String IPPATTERN = "^([1]?[^0]\\d?|2[0-4]\\d|25[0-4])\\."
			+ "([1]?\\d\\d?|2[0-4]\\d|25[0-4])\\."
			+ "([1]?\\d\\d?|2[0-4]\\d|25[0-4])\\."
			+ "([1]?[^0]\\d?|2[0-4]\\d|25[0-4])$";

	private static final String NETMASKPATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String netConfig(String ipaddress, String netmask, String gateway) {
		// IP address
		Pattern ipaddr = Pattern.compile(IPPATTERN);
		Matcher ipMatcher = ipaddr.matcher(ipaddress);
		
		// Netmask
		Pattern netaddr = Pattern.compile(NETMASKPATTERN);
		Matcher netMatcher = netaddr.matcher(netmask);
		
		if(!gateway.isEmpty()){
			Pattern gateaddr = Pattern.compile(IPPATTERN);
			Matcher gateMatcher = gateaddr.matcher(gateway);

			if (ipMatcher.matches() && netMatcher.matches() && gateMatcher.matches()) {
				writeInterfaces(ipaddress, netmask, gateway);
				setStatus("true");
			} else {
				setStatus("false");
			}
		}else{
			if (ipMatcher.matches() && netMatcher.matches()) {
				writeInterfaces(ipaddress, netmask, "");
				setStatus("true");
			} else {
				setStatus("false");
			}
		}
		
		
		return getStatus();
	}
	
	private void writeInterfaces(String ipaddress, String netmask, String gateway){
		File interfaces = new File("/etc/network/interfaces");
		try {
			FileWriter fw = new FileWriter(interfaces);
			if(gateway.isEmpty()){
				fw.write("auto lo\n"
						+ "iface lo inet loopback\n\n"
						+ "auto p1p1\n"
						+ "iface p1p1 inet static\n"
						+ "address "+ipaddress+"\n"
						+ "netmask "+netmask+"\n");
			}else{
				fw.write("auto lo\n"
						+ "iface lo inet loopback\n\n"
						+ "auto p1p1\n"
						+ "iface p1p1 inet static\n"
						+ "address "+ipaddress+"\n"
						+ "netmask "+netmask+"\n"
						+ "gateway "+gateway+"\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e);
		}
	}
	
	public void commit(){
		try {
			Process proc = Runtime.getRuntime().exec("sudo /etc/init.d/jetty_restart_p1p1 restart");
			proc.waitFor();
			
			if(proc.exitValue() != 0){
				LOGGER.error("Network restart error! Exit code: "+proc.exitValue());
			}else{
				LOGGER.info("Network restarted succesfully!");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e);
		}
	}
	
	public String readInterface() throws IOException{
		BufferedReader fr = new BufferedReader(new FileReader(new File("/etc/network/interfaces")));
		
		String line;
		String ip = "";
		String netmask = "";
		String gateway = "";
		while((line = fr.readLine()) != null){
			if(line.contains("address")){
				ip = line.split(" ")[1];
			}else if(line.contains("netmask")){
				netmask = line.split(" ")[1];
			}else if(line.contains("gateway")){
				gateway = line.split(" ")[1];
			}
		}
		fr.close();
		
		String returnStr = "";
		if(gateway.equals("")){
			returnStr = ip+";"+netmask;
		}else{
			returnStr = ip+";"+netmask+";"+gateway;
		}
		
		return returnStr;
	}
}
