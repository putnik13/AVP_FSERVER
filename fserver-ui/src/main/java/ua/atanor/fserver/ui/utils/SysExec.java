package ua.atanor.fserver.ui.utils;

import java.io.IOException;

import org.apache.log4j.Logger;

public class SysExec {
	
	private static Logger LOGGER = Logger.getLogger(SysExec.class);
	
	@SuppressWarnings("finally")
	public static Boolean execute(String command){
		
		Boolean reVal = false;
		
		try {
			Process proc = Runtime.getRuntime().exec(command);
			proc.waitFor();
			
			if(proc.exitValue() != 0){
				reVal = false;
				LOGGER.error("Exit code: "+proc.exitValue()+" "+proc.getErrorStream().read());
				LOGGER.error("Command: "+command);
			}else{
				reVal = true;
				LOGGER.info("Excute succesfully!");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			reVal = false;
			LOGGER.error(e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			reVal = false;
			LOGGER.error(e);
		}finally{
			return reVal;
		}
	}
}
