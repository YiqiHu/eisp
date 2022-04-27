/**
 * @date 2019年11月5日
 * @time 下午8:17:44
 * @author LiangHB
 */
package com.lylj.WebLearning.ORM.entity;


public class Address {
	private String district;
	private String compleAddre;
	private String name;
	private String phoneNum;
	public Address() {
	}
	
	public Address(String name,String phoneNum,String distric,String compleAddre){
		this.compleAddre=compleAddre;
		this.district=distric;
		this.name=name;
		this.phoneNum=phoneNum;
	}
	
	public String getCompleAddre() {
		return compleAddre;
	}
	public void setCompleAddre(String compleAddre) {
		this.compleAddre = compleAddre;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
}
