package com.atanor.fserver.facades.player;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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

import com.atanor.fserver.facades.VideoRecorder;
import com.atanor.fserver.utils.FormatTime;
import com.google.common.collect.Lists;

public class FFmpegRecorder implements VideoRecorder {

	private static final Logger LOG = LoggerFactory.getLogger(FFmpegRecorder.class);

	private DefaultExecutor executor;
	private final List<Date> tags = Lists.newArrayList();

	private Date startTime;
	private Date endTime;

	@Override
	public void startRecording(final String line, final Map<String, String> params) {
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
				super.onProcessComplete(exitValue);
				executor = null;
				LOG.debug("Recording process completed.");
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
			startTime = new Date();
		} catch (IOException e) {
			LOG.error("Failure to start stream recording..", e);
		}
		LOG.info(">>>>>> FFmpeg recorder started recording.");
	}

	@Override
	public void stopRecording() {
		if (isPlaying()) {
			endTime = new Date();
			executor.getWatchdog().destroyProcess();
			executor = null;
			LOG.info("<<<<<< FFmpeg recorder stopped recording.");

			if (!tags.isEmpty()) {
				LOG.info("----- FFmpeg recorder creates video chapters");
				createChapters();
			}
			clean();
		}
	}

	private void clean() {
		tags.clear();
		startTime = null;
		endTime = null;
	}

	private void createChapters() {
		String startTag = "00:00:00";
		final List<Date> allTags = createAllTags();

		for (int i = 1; i < allTags.size(); i++) {
			final long duration = (allTags.get(i).getTime() - allTags.get(i - 1).getTime()) / 1000;
			createChapter(startTag, duration);
			startTag = FormatTime.format(allTags.get(i).getTime() - allTags.get(0).getTime());
		}
	}

	private void createChapter(final String startTag, final long duration) {
		LOG.debug("Chapter creating, start tag {}, duration {} sec", startTag, duration);
	}

	@Override
	public boolean isPlaying() {
		return executor != null;
	}

	@Override
	public void addChapterTag() {
		LOG.info("------ FFmpeg recorder added chapter tag.");
		if (isPlaying()) {
			tags.add(new Date());
		}
	}

	private List<Date> createAllTags() {
		final List<Date> allTags = Lists.newArrayList();
		allTags.add(startTime);
		allTags.addAll(tags);
		allTags.add(endTime);
		return allTags;
	}

}
