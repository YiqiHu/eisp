/**
 * @date 2019年10月24日
 * @time 下午9:40:31
 * @author LiangHB
 */
package com.lylj.WebLearning.ORM.entity;

import java.text.SimpleDateFormat;


public class Order {
	private long ID;
	private String sendName;
	private String sendDistrict;
	private String sendAddress;
	private String sendPhoneNum;

	private String receiveName;
	private String receiveDistrict;
	private String receiveAddress;
	private String receivePhoneNum;

	private String expressName;//包含电话号以确保区分
	private Integer price;
	private String pictureLocation;
	private String orderPicture;
	private Integer state;
	private String orderTime;
	private String solveTime;
	private String number;//时间戳给

	public Order(String sendName, String sendAddress, String sendPhoneNum, String sendDistrict, String receiveName,
			String receiveAddress, String receivePhoneNum, String receiveDistrict, String expressName) {
		this.sendName = sendName;
		this.sendAddress = sendAddress;
		this.sendDistrict = sendDistrict;
		this.sendPhoneNum = sendPhoneNum;

		this.receiveName = receiveName;
		this.receiveAddress = receiveAddress;
		this.receiveDistrict = receiveDistrict;
		this.receivePhoneNum = receivePhoneNum;

        this.expressName=expressName;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.orderTime=df.format(new java.util.Date());
	}
    public Order() {
    	
    }
	public final String getSendName() {
		return sendName;
	}

	public final void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public final String getSendDistrict() {
		return sendDistrict;
	}

	public final void setSendDistrict(String sendDistrict) {
		this.sendDistrict = sendDistrict;
	}

	public final String getSendAddress() {
		return sendAddress;
	}

	public final void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public final String getSendPhoneNum() {
		return sendPhoneNum;
	}

	public final void setSendPhoneNum(String sendPhoneNum) {
		this.sendPhoneNum = sendPhoneNum;
	}

	public final String getReceiveName() {
		return receiveName;
	}

	public final void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public final String getReceiveDistrict() {
		return receiveDistrict;
	}

	public final void setReceiveDistrict(String receiveDistrict) {
		this.receiveDistrict = receiveDistrict;
	}

	public final String getReceiveAddress() {
		return receiveAddress;
	}

	public final void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public final String getReceivePhoneNum() {
		return receivePhoneNum;
	}

	public final void setReceivePhoneNum(String receivePhoneNum) {
		this.receivePhoneNum = receivePhoneNum;
	}


	public final Integer getPrice() {
		return price;
	}

	public final void setPrice(Integer price) {
		this.price = price;
	}

	public final String getPictureLocation() {
		return pictureLocation;
	}

	public final void setPictureLocation(String pictureLocation) {
		this.pictureLocation = pictureLocation;
	}

	

	public final String  getOrderTime() {
		return orderTime;
	}

	public final void setOrderTime(String  orderTime) {
		this.orderTime = orderTime;
	}

	public final String  getSolveTime() {
		return solveTime;
	}

	public final void setSolveTime(String solveTime) {
		this.solveTime = solveTime;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getOrderPicture() {
		return orderPicture;
	}
	public void setOrderPicture(String orderPicture) {
		this.orderPicture = orderPicture;
	}

}
