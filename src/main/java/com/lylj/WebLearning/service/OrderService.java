/**
 * @date 2019年10月26日
 * @time 上午9:38:27
 * @author YiqiHu
 */
package com.lylj.WebLearning.service;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lylj.WebLearning.ORM.Mapper.OrderMapper;
import com.lylj.WebLearning.ORM.entity.Order;
import com.lylj.WebLearning.ORM.entity.OrderInfor;

@Service
public class OrderService {
	@Autowired
	OrderMapper orderMapper;

	public int createOrder(String sendName, String sendAddress, String sendPhoneNum, String sendDistrict,
			String receiveName, String receiveAddress, String receivePhoneNum, String receiveDistrict,
			String expressName) {
		Order order = new Order(sendName, sendAddress, sendPhoneNum, sendDistrict, receiveName, receiveAddress,
				receivePhoneNum, receiveDistrict, expressName);
		return orderMapper.create(order);
	}

	public int dealOrder(Order order) {
		return orderMapper.set(order);
	}
	public long getID(Order order) {
		return orderMapper.getID(order);
	}
	public LinkedList<Order> getOrder(String bossName,String expreName,String sendName, Integer state){
		if (bossName!=null) {
			return orderMapper.bossGetOrder(bossName);
		}else if (expreName!=null) {
			if (state==0) {
				return orderMapper.expreGetOrder0(expreName);
			}else {
				return orderMapper.expreGetOrder1(expreName);
			}
		}else {
			return orderMapper.sendGetOrder(sendName.split("#")[1],state);
		}
	}
	public LinkedList<Order> SenderGetOrder(String sendName,int state){
			return orderMapper.sendGetOrder(sendName,state);
	}

	
	public LinkedList<OrderInfor> getOrderByNum(String number) {
		return orderMapper.getOrderByNum(number);
	}
	
	public int updateOrderInfor(OrderInfor orderInfor) {
		return orderMapper.updateInfor(orderInfor);
	}

	public LinkedList<Order> getOrderByPhoneNum(String phoneNum,Integer state) {
		if (state==3) {
			return orderMapper.getOrderByPhoneNUm(phoneNum);
		}else {
			LinkedList<Order> orders=orderMapper.getOrderByPhoneNUm0(phoneNum);
			for (Order order : orders) {
				OrderInfor o=orderMapper.getOrderByNum(order.getNumber()).getFirst();
				String expressName=o.getCustomer_name();
				order.setSolveTime(o.getSolve_time());
				order.setExpressName(expressName.split("#")[0]);
				//拿收件电话来存快递员电话
				order.setReceivePhoneNum(expressName.split("#")[1]);
			}
			return orders;
		}
	}

	
	public Order getOrderBytwo(String expressName, String sendName) {
		return orderMapper.getOrderbyTwo(expressName,sendName);
	}

	
	public Order getbyNum(String number) {
		return orderMapper.getBynum(number);
	}
	
	
}
