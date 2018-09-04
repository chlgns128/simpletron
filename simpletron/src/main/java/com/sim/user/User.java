package com.sim.user;

public class User {
	private String id;
	private String passwd;
	private String email;
	private String nick;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public User() {
	}

	public User(String id, String passwd, String email, String nick) {
		this.id = id;
		this.passwd = passwd;
		this.email = email;
		this.nick = nick;

	}

}