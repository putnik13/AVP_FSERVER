package ua.api.rest.admin.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.api.rest.admin.modules.spring.Status;

public class ModuleStatus {

	private static Logger LOGGER = Logger.getLogger(ModuleStatus.class);

	private String service;
	private String result = "";

	public ModuleStatus() {
	}

	public ModuleStatus(String service) {
		this.service = service;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void execute(String serviceParam1, String serviceParam2,
			String serviceParam3) {

		URL file = getClass().getResource("/");
		File script = new File(file.getPath()+"../../scripts/status/" + getService() + ".script");
		LOGGER.debug("Executed script path: "+script);
		
		if (script.exists()) {
			LOGGER.debug(getService() + ".script - file exist");
			if (!script.canExecute()) {
				script.setExecutable(true);
			}
		} else {
			LOGGER.debug(getService() + ".script - file not found in WAR");
			
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("status.xml");
			Status staticPath = (Status) ctx.getBean("statusBean");
			ctx.close();
			
			script = new File(staticPath.getStaticScriptPath()+"/"+getService()+".script");

			if (script.exists()) {
				LOGGER.debug(getService() + ".script - file exist");
				if (!script.canExecute()) {
					script.setExecutable(true);
				}
			} else {
				LOGGER.debug(getService() + ".script - file not found in script directory");
			}
		}

		try {
			Process proc = Runtime.getRuntime().exec(
					"/bin/bash -c " + script + " " + serviceParam1 + " "
							+ serviceParam2 + " " + serviceParam3);

			BufferedReader read = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));

			int exitValue = proc.waitFor();
			LOGGER.info("Runtime Exit Code: " + exitValue);

			String line;
			while ((line = read.readLine()) != null) {
				if (read.ready()) {
					setResult(getResult() + line + "\n");
				} else {
					setResult(getResult() + line);
				}
			}

			if (getResult().equals("\n") || getResult().equals("")) {
				setResult(null);
			}

		} catch (IOException e) {
			LOGGER.fatal("Execute error: " + e.getMessage());
		} catch (InterruptedException e) {
			LOGGER.fatal("Execute error: " + e.getMessage());
		}

	}

	public void list(){
		String cmd = "";

		// Get scripts from WAR-archive
		URL file = getClass().getResource("/");
		File script = new File(file.getPath()+"../../scripts/status/");
		
		for(int i = 0; i < script.list().length; i++){
			if(i+1 == script.list().length || script.list().length == 0){
				cmd += script.list()[i].replace(".script", "")+"";
			}else{
				cmd += script.list()[i].replace(".script", "")+"; ";
			}
		}
		
		// Get scripts from monitoring folder
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("status.xml");
		Status staticPath = (Status) ctx.getBean("statusBean");
		ctx.close();
		
		LOGGER.debug("Static path: "+staticPath.getStaticScriptPath());
		
		script = new File(staticPath.getStaticScriptPath());

		String staticCmd;
		if(script.list().length > 0){
			staticCmd = "; ";
		}else{
			staticCmd = "";
		}
		
		for(int i = 0; i < script.list().length; i++){
			if(i+1 == script.list().length || script.list().length == 0){
				staticCmd += script.list()[i].replace(".script", "")+"";
			}else{
				staticCmd += script.list()[i].replace(".script", "")+"; ";
			}
		}
		
		setResult(cmd+staticCmd);
	}
}
