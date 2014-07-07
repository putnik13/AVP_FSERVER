package com.atanor.fserver.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	private String nickName;
	private String passWord;
	private String role;
	
	public User(Long id, String nickName, String passWord, String role) {
		this.id = id;
		this.nickName = nickName;
		this.passWord = passWord;
		this.role = role;
	}
	public User(String nickName, String passWord, String role) {
		this.nickName = nickName;
		this.passWord = passWord;
		this.role = role;
	}
	public User() {
		}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
