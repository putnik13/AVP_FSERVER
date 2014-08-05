package com.atanor.fserver.facades.video;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.api.Error;
import com.atanor.fserver.facades.ProcessAware;

public class ProcessRunner {

	private static final Logger LOG = LoggerFactory.getLogger(ProcessRunner.class);

	private final ProcessAware callback;
	private DefaultExecutor executor;
	private boolean stopped;

	private boolean state = true;
	
	public ProcessRunner() {
		this(null);
	}

	public ProcessRunner(final ProcessAware callback) {
		this.callback = callback;
	}

	public void run(final String line, Map<String, String> params) {
		if (isRunning()) {
			return;
		}
		
		cleanState();
		
		final CommandLine cmdLine = CommandLine.parse(line);
		cmdLine.setSubstitutionMap(params);

		final LogOutputStream output = new LogOutputStream() {
			@Override
			protected void processLine(String line, int level) {
				LOG.debug(line);
			}
		};
		final PumpStreamHandler streamHandler = new PumpStreamHandler(output);
		final DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler() {

			@Override
			public void onProcessComplete(int exitValue) {
				LOG.debug("Process completed.");
				super.onProcessComplete(exitValue);
				cleanState();
				if (callback != null) {
					callback.onProcessComplete(exitValue);
				}
			}

			@Override
			public void onProcessFailed(ExecuteException e) {
				if(!stopped){
					LOG.debug("Process failed.", e);
					super.onProcessFailed(e);
					cleanState();
					if (callback != null) {
						callback.onProcessFailed();
					}
				}
			}
		};

		try {
			executor = new DefaultExecutor();
			executor.setStreamHandler(streamHandler);
			executor.setWatchdog(new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT));
			executor.execute(cmdLine, EnvironmentUtils.getProcEnvironment(), resultHandler);
		} catch (IOException e) {
			LOG.error("Failure to start process..", e);
		}
	}

	public void stop() {
		if (isRunning()) {
			stopped = true;
			executor.getWatchdog().destroyProcess();
			executor = null;
		}
	}

	public boolean isRunning() {
		return executor != null;
	}

	private void cleanState(){
		executor = null;
		stopped = false;
	}
}
