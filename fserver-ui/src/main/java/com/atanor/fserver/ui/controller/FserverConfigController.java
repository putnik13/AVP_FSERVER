package com.atanor.fserver.ui.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atanor.fserver.ui.domain.FserverConfig;
import com.atanor.fserver.ui.service.FserverConfigManager;

@Controller
@RequestMapping("/fconfig")
public class FserverConfigController {
	private static Logger LOGGER = Logger.getLogger(FserverConfigController.class);

	@Autowired
	private FserverConfigManager fConfig;
	
	@SuppressWarnings("finally")
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String config(Model model) {
		model.addAttribute("menuItem", "fserverConfig");
		
		try{
			model.addAttribute("config", fConfig.showConfig().get(0));
		}catch(IndexOutOfBoundsException e){
			FserverConfig config = new FserverConfig();
			
			config.setDisk_space_alarm_mb(200);
			config.setDisk_space_monitor_interval_ms(5000);
			config.setDisk_space_threshold_mb(50);
			config.setMedia_container("avi");
			config.setMedia_cut("ffmpeg -i ${input} -acodec copy -vcodec copy -ss ${ch.start} -t ${ch.duration} ${output}");
			config.setMedia_record("ffmpeg -i ${input} -acodec copy -vcodec copy ${output}");
			config.setMedia_record_and_redirect("ffmpeg -i ${input} -acodec copy -vcodec copy ${output} -acodec copy -vcodec copy -f mpegts ${redirect}");
			config.setMedia_redirect("ffmpeg -i ${input} -acodec copy -vcodec copy -f mpegts ${redirect}");
			config.setMedia_source("rtsp://192.168.0.7:8554");
			config.setRecording_size_monitor_interval_ms(3000);
			config.setRecording_size_monitor_start_delay_ms(5000);
			config.setRecording_size_warn_attempts(10);
			config.setRecordings_output("/home/videosrv/fserver/recordings");
			config.setRedirect_url("udp://192.168.0.4:10000");
			config.setSocket_api_port(24001);
			
			fConfig.saveConfig(config);
			
			model.addAttribute("config", fConfig.showConfig().get(0));
			
		}finally{
			return "control";
		}
	}

	@SuppressWarnings("finally")
	@RequestMapping(params = { "disk_space_alarm_mb",
			"disk_space_monitor_interval_ms", "disk_space_threshold_mb",
			"media_container", "media_cut", "media_record",
			"media_record_and_redirect", "media_redirect", "media_source",
			"recording_size_monitor_interval_ms",
			"recording_size_monitor_start_delay_ms",
			"recording_size_warn_attempts", "recordings_output",
			"redirect_url", "socket_api_port" }, method = {RequestMethod.GET, RequestMethod.POST})
	public String changeFserverConfig(Model model,
			@RequestParam("disk_space_alarm_mb") Integer disk_space_alarm_mb,
			@RequestParam("disk_space_monitor_interval_ms") Integer disk_space_monitor_interval_ms,
			@RequestParam("disk_space_threshold_mb") Integer disk_space_threshold_mb,
			@RequestParam("media_container") String media_container,
			@RequestParam("media_cut") String media_cut,
			@RequestParam("media_record") String media_record,
			@RequestParam("media_record_and_redirect") String media_record_and_redirect,
			@RequestParam("media_redirect") String media_redirect,
			@RequestParam("media_source") String media_source,
			@RequestParam("recording_size_monitor_interval_ms") Integer recording_size_monitor_interval_ms,
			@RequestParam("recording_size_monitor_start_delay_ms") Integer recording_size_monitor_start_delay_ms,
			@RequestParam("recording_size_warn_attempts") Integer recording_size_warn_attempts,
			@RequestParam("recordings_output") String recordings_output,
			@RequestParam("redirect_url") String redirect_url,
			@RequestParam("socket_api_port") Integer socket_api_port) {
		
		FserverConfig config = new FserverConfig();
		
		config.setDisk_space_alarm_mb(disk_space_alarm_mb);
		config.setDisk_space_monitor_interval_ms(disk_space_monitor_interval_ms);
		config.setDisk_space_threshold_mb(disk_space_threshold_mb);
		config.setMedia_container(media_container);
		config.setMedia_cut(media_cut);
		config.setMedia_record(media_record);
		config.setMedia_record_and_redirect(media_record_and_redirect);
		config.setMedia_redirect(media_redirect);
		config.setMedia_source(media_source);
		config.setRecording_size_monitor_interval_ms(recording_size_monitor_interval_ms);
		config.setRecording_size_monitor_start_delay_ms(recording_size_monitor_start_delay_ms);
		config.setRecording_size_warn_attempts(recording_size_warn_attempts);
		config.setRecordings_output(recordings_output);
		config.setRedirect_url(redirect_url);
		config.setSocket_api_port(socket_api_port);
		
		try{
			fConfig.showConfig().get(0);
			fConfig.clearConfig();
			fConfig.saveConfig(config);
		}catch(IndexOutOfBoundsException e){
			fConfig.saveConfig(config);
		}finally{
			return "redirect:/fconfig";
			
		}
	}
	
	@RequestMapping("/defaults")
	public String settingsToDefaults(){
		fConfig.clearConfig();
		return "redirect:/fconfig";
	}
}
