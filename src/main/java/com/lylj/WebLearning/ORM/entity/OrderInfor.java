/**
 * @date 2019年11月9日
 * @time 上午8:42:43
 * @author LiangHB
 */
package com.lylj.WebLearning.ORM.entity;

//物流信息
public class OrderInfor {
	private String customer_name;
	private String solve_time;
	private Integer state;
	private String number;
	public final String getCustomer_name() {
		return customer_name;
	}
	public final void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public final String getSolve_time() {
		return solve_time;
	}
	public final void setSolve_time(String solve_time) {
		this.solve_time = solve_time;
	}
	public final Integer getState() {
		return state;
	}
	public final void setState(Integer state) {
		this.state = state;
	}
	public final String getNumber() {
		return number;
	}
	public final void setNumber(String number) {
		this.number = number;
	}
	
	
}
