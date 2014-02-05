package com.atanor.fserver.facades.player;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.facades.ProcessAware;

public class FFmpegPlayer {

	private static final Logger LOG = LoggerFactory.getLogger(FFmpegPlayer.class);

	private final ProcessAware callback;
	private DefaultExecutor executor;

	public FFmpegPlayer() {
		this(null);
	}

	public FFmpegPlayer(final ProcessAware callback) {
		this.callback = callback;
	}

	public void start(final String line, final Map<String, String> params) {
		if (isPlaying()) {
			return;
		}

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
				LOG.debug("FFmpeg process completed.");
				super.onProcessComplete(exitValue);
				executor = null;
				if (callback != null) {
					callback.onProcessComplete(exitValue);
				}
			}

			@Override
			public void onProcessFailed(ExecuteException e) {
				super.onProcessFailed(e);
				executor = null;
			}
		};

		try {
			executor = new DefaultExecutor();
			executor.setStreamHandler(streamHandler);
			executor.setWatchdog(new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT));
			executor.execute(cmdLine, resultHandler);
		} catch (IOException e) {
			LOG.error("Failure to start FFmpeg process..", e);
		}
	}

	public void stop() {
		if (isPlaying()) {
			executor.getWatchdog().destroyProcess();
			executor = null;
		}
	}

	public boolean isPlaying() {
		return executor != null;
	}

}
