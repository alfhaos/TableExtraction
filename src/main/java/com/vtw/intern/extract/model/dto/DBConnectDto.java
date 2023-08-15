package com.vtw.intern.extract.model.dto;

public class DBConnectDto {
	
	private String userName;
	private String pwd;
	private String url;
	private String driver;
	
	public DBConnectDto() {
		super();
	}

	public DBConnectDto(String userName, String pwd, String url, String driver) {
		super();
		this.userName = userName;
		this.pwd = pwd;
		this.url = url;
		this.driver = driver;
	}

	public DBConnectDto(String userName, String pwd) {
		super();
		this.userName = userName;
		this.pwd = pwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	
	
	

}
