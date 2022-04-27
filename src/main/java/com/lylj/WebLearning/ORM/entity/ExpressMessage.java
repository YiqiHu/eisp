/**
 * @date 2019年11月3日
 * @time 下午11:10:00
 * @author YiqiHu
 */
package com.lylj.WebLearning.ORM.entity;

public class ExpressMessage {
	private String username;
	private String role;
	private String bossname;
	private String company;
	public final String getUsername() {
		return username;
	}
	public final void setUsername(String username) {
		this.username = username;
	}
	public final String getRole() {
		return role;
	}
	public final void setRole(String role) {
		this.role = role;
	}
	public final String getBossname() {
		return bossname;
	}
	public final void setBossname(String bossname) {
		this.bossname = bossname;
	}
	public final String getCompany() {
		return company;
	}
	public final void setCompany(String company) {
		this.company = company;
	}
}
