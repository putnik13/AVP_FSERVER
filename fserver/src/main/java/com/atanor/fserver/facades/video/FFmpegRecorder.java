package com.atanor.fserver.facades.video;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.config.Config;
import com.atanor.fserver.events.ProcessInterruptedEvent;
import com.atanor.fserver.facades.ProcessAware;
import com.atanor.fserver.facades.RecordingProcessInfo;
import com.atanor.fserver.facades.VideoRecorder;
import com.atanor.fserver.monitor.MonitorManager;
import com.atanor.fserver.utils.FormatTime;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;

public class FFmpegRecorder implements VideoRecorder, ProcessAware {

	private static final Logger LOG = LoggerFactory.getLogger(FFmpegRecorder.class);
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");

	private static final String CHAPTER_SUFFIX = "-CH-";

	@Inject
	private EventBus eventBus;
	
	@Inject
	private MonitorManager monitor;

	private final List<Date> tags = Lists.newArrayList();

	private Config config;
	
	private ProcessRunner player;
	private Date startTime;
	private Date endTime;
	private String recordingPath;

	public FFmpegRecorder() {
		player = new ProcessRunner(this);
		addShutdownHook();
	}

	@Override
	public void startRecording() {
		if (isPlaying()) {
			return;
		}
		
		config = new Config();
		LOG.info("Media Source: "+config.getMediaSource());
		
		final String fileName = buildRecordingName(new Date());
		recordingPath = buildRecordingPath(fileName);

		Map<String, String> params = Maps.newHashMap();
		params.put(Config.INPUT_MEDIA_PARAM, config.getMediaSource());
		params.put(Config.OUTPUT_MEDIA_PARAM, recordingPath);

		player.run(config.getMediaRecordOptions(), params);
		monitor.startMonitoring(recordingPath);
		startTime = new Date();
		LOG.info(">>>>>> FFmpeg started recording");
	}

	@Override
	public void startRecordingAndRedirect() {
		if (isPlaying()) {
			return;
		}

		config = new Config();
		
		final String fileName = buildRecordingName(new Date());
		recordingPath = buildRecordingPath(fileName);

		Map<String, String> params = Maps.newHashMap();
		params.put(Config.INPUT_MEDIA_PARAM, config.getMediaSource());
		params.put(Config.OUTPUT_MEDIA_PARAM, recordingPath);
		params.put(Config.REDIRECT_MEDIA_PARAM, config.getRedirectUrl());

		player.run(config.getMediaRecordAndRedirectOptions(), params);
		monitor.startMonitoring(recordingPath);
		startTime = new Date();
		LOG.info(">>>>>> FFmpeg started record and redirect stream to {}", config.getRedirectUrl());
	}

	@Override
	public RecordingProcessInfo stop() {
		RecordingProcessInfo info = null;
		monitor.stopMonitoring();
		if (isPlaying()) {
			endTime = new Date();
			player.stop();
			LOG.info("<<<<<< FFmpeg stopped.");

			info = buildRecordingProcessInfo();
			clean();
		}
		return info;
	}

	private RecordingProcessInfo buildRecordingProcessInfo() {
		final RecordingProcessInfo info = new RecordingProcessInfo();
		info.setRecordingPath(recordingPath);
		info.setStartTime(startTime);
		info.setEndTime(endTime);
		info.setTags(Lists.newArrayList(tags));
		return info;
	}

	private void clean() {
		tags.clear();
		recordingPath = null;
		startTime = null;
		endTime = null;
	}

	@Override
	public boolean isPlaying() {
		return player.isRunning();
	}

	@Override
	public void addChapterTag() {
		LOG.info("------ FFmpeg added chapter tag.");
		if (isPlaying()) {
			tags.add(new Date());
		}
	}

	private String buildRecordingName(final Date date) {
		return "RECORDING-" + df.format(date) + mediaContainer();
	}

	private String buildChapterName(final String recordingPath, final String suffix) {
		return recordingPath.replaceFirst(mediaContainer(), suffix + mediaContainer());
	}

	private String buildRecordingPath(final String recordingName) {
		config = new Config();
		return config.getRecordingsOutput() + "/" + recordingName;
	}

	@Override
	public void onProcessComplete(int exitValue) {
		if (endTime == null) {
			endTime = new Date();
		}
		monitor.stopMonitoring();
		eventBus.post(new ProcessInterruptedEvent());
	}

	@Override
	public Boolean onProcessFailed() {
		if (endTime == null) {
			endTime = new Date();
		}
		monitor.stopMonitoring();
		eventBus.post(new ProcessInterruptedEvent());
		return false;
	}

	@Override
	public void createChapters(final RecordingProcessInfo info) {
		LOG.info("----- FFmpeg recorder creates video chapters");

		String startTag = "00:00:00";
		final List<Date> allTags = createAllTags(info);
		Map<String, String> params = Maps.newHashMap();
		params.put(Config.INPUT_MEDIA_PARAM, info.getRecordingPath());

		for (int i = 1; i < allTags.size(); i++) {
			final String duration = FormatTime.format(allTags.get(i).getTime() - allTags.get(i - 1).getTime());
			final String chapter = buildChapterName(info.getRecordingPath(), CHAPTER_SUFFIX + i);
			createChapter(chapter, startTag, duration, params);
			startTag = FormatTime.format(allTags.get(i).getTime() - allTags.get(0).getTime());
		}
	}

	private List<Date> createAllTags(final RecordingProcessInfo info) {
		final List<Date> allTags = Lists.newArrayList();
		allTags.add(info.getStartTime());
		allTags.addAll(info.getTags());
		allTags.add(info.getEndTime());
		return allTags;
	}

	private void createChapter(final String chapter, final String startTag, final String duration,
			Map<String, String> params) {
		LOG.debug("Chapter creating, start tag {}, duration {} sec", startTag, duration);

		config = new Config();
		
		params.put(Config.CHAPTER_START_MEDIA_PARAM, startTag);
		params.put(Config.CHAPTER_DURATION_MEDIA_PARAM, duration);
		params.put(Config.OUTPUT_MEDIA_PARAM, chapter);

		new ProcessRunner().run(config.getMediaCutOptions(), params);
	}

	private String mediaContainer() {
		config = new Config();
		return "." + config.getMediaContainer();
	}

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				LOG.warn("!!! Shutdown hook to gracefully complete recording");
				try {
					stop();
				} catch (Exception e) {
					LOG.error("Unexpected exception while shutting down", e);
				}
			}
		}));
	}

}
