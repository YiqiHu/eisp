/**
 * @date 2019年10月26日
 * @time 上午11:10:52
 * @author LiangHB
 */
package com.lylj.WebLearning.ORM.entity;

//下单消息传输，区别于订单物流信息
public class OrderMessage {
     String from;
     String to;
     Order order;
	public final String getFrom() {
		return from;
	}
	public final void setFrom(String from) {
		this.from = from;
	}
	public final String getTo() {
		return to;
	}
	public final void setTo(String to) {
		this.to = to;
	}
	public final Order getOrder() {
		return order;
	}
	public final void setOrder(Order order) {
		this.order = order;
	}
}
