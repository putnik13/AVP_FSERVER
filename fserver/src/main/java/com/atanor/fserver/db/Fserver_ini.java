package com.atanor.fserver.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Fserver_ini implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	private String media_container;
	private String media_source;
	private String redirect_url;
	private String recordings_output;
	private int socket_api_port;
	private String media_record;
	private String media_cut;
	private String media_redirect;
	private String media_record_and_redirect;
	private int disk_space_alarm_mb;
	private int disk_space_threshold_mb;
	private int disk_space_monitor_interval_ms;
	private int recording_size_monitor_start_delay_ms;
	private int recording_size_monitor_interval_ms;
	private int recording_size_warn_attempts;

	public Fserver_ini() {
	}

	public Fserver_ini(Long id, String media_container, String media_source,
			String redirect_url, String recordings_output, int socket_api_port,
			String media_record, String media_cut, String media_redirect,
			String media_record_and_redirect, int disk_space_alarm_mb,
			int disk_space_threshold_mb, int disk_space_monitor_interval_ms,
			int recording_size_monitor_start_delay_ms,
			int recording_size_monitor_interval_ms,
			int recording_size_warn_attempts) {
		this.id = id;
		this.media_container = media_container;
		this.media_source = media_source;
		this.redirect_url = redirect_url;
		this.recordings_output = recordings_output;
		this.socket_api_port = socket_api_port;
		this.media_record = media_record;
		this.media_cut = media_cut;
		this.media_redirect = media_redirect;
		this.media_record_and_redirect = media_record_and_redirect;
		this.disk_space_alarm_mb = disk_space_alarm_mb;
		this.disk_space_threshold_mb = disk_space_threshold_mb;
		this.disk_space_monitor_interval_ms = disk_space_monitor_interval_ms;
		this.recording_size_monitor_start_delay_ms = recording_size_monitor_start_delay_ms;
		this.recording_size_monitor_interval_ms = recording_size_monitor_interval_ms;
		this.recording_size_warn_attempts = recording_size_warn_attempts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMedia_container() {
		return media_container;
	}

	public void setMedia_container(String media_container) {
		this.media_container = media_container;
	}

	public String getMedia_source() {
		return media_source;
	}

	public void setMedia_source(String media_source) {
		this.media_source = media_source;
	}

	public String getRedirect_url() {
		return redirect_url;
	}

	public void setRedirect_url(String redirect_url) {
		this.redirect_url = redirect_url;
	}

	public String getRecordings_output() {
		return recordings_output;
	}

	public void setRecordings_output(String recordings_output) {
		this.recordings_output = recordings_output;
	}

	public int getSocket_api_port() {
		return socket_api_port;
	}

	public void setSocket_api_port(int socket_api_port) {
		this.socket_api_port = socket_api_port;
	}

	public String getMedia_record() {
		return media_record;
	}

	public void setMedia_record(String media_record) {
		this.media_record = media_record;
	}

	public String getMedia_cut() {
		return media_cut;
	}

	public void setMedia_cut(String media_cut) {
		this.media_cut = media_cut;
	}

	public String getMedia_redirect() {
		return media_redirect;
	}

	public void setMedia_redirect(String media_redirect) {
		this.media_redirect = media_redirect;
	}

	public String getMedia_record_and_redirect() {
		return media_record_and_redirect;
	}

	public void setMedia_record_and_redirect(String media_record_and_redirect) {
		this.media_record_and_redirect = media_record_and_redirect;
	}

	public int getDisk_space_alarm_mb() {
		return disk_space_alarm_mb;
	}

	public void setDisk_space_alarm_mb(int disk_space_alarm_mb) {
		this.disk_space_alarm_mb = disk_space_alarm_mb;
	}

	public int getDisk_space_threshold_mb() {
		return disk_space_threshold_mb;
	}

	public void setDisk_space_threshold_mb(int disk_space_threshold_mb) {
		this.disk_space_threshold_mb = disk_space_threshold_mb;
	}

	public int getDisk_space_monitor_interval_ms() {
		return disk_space_monitor_interval_ms;
	}

	public void setDisk_space_monitor_interval_ms(int disk_space_monitor_interval_ms) {
		this.disk_space_monitor_interval_ms = disk_space_monitor_interval_ms;
	}

	public int getRecording_size_monitor_start_delay_ms() {
		return recording_size_monitor_start_delay_ms;
	}

	public void setRecording_size_monitor_start_delay_ms(
			int recording_size_monitor_start_delay_ms) {
		this.recording_size_monitor_start_delay_ms = recording_size_monitor_start_delay_ms;
	}

	public int getRecording_size_monitor_interval_ms() {
		return recording_size_monitor_interval_ms;
	}

	public void setRecording_size_monitor_interval_ms(
			int recording_size_monitor_interval_ms) {
		this.recording_size_monitor_interval_ms = recording_size_monitor_interval_ms;
	}

	public int getRecording_size_warn_attempts() {
		return recording_size_warn_attempts;
	}

	public void setRecording_size_warn_attempts(int recording_size_warn_attempts) {
		this.recording_size_warn_attempts = recording_size_warn_attempts;
	}
	
}
