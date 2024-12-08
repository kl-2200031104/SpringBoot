package com.real.estate.Bean;

public class UserBean extends BaseBean{

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public long getRoleid() {
		return roleid;
	}
	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	private String name;
	private String email;
	private String password;
	private String phoneNo;
	private long roleid;
	private String rolename;
	
	@Override
	public String getKey() {
		return id + "";
	}
	@Override
	public String getValue() {
		return name;
	}
	
	
	
	
}
