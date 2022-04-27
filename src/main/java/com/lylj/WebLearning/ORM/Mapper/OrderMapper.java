/**
 * @date 2019年10月26日
 * @time 上午9:13:29
 * @author LiangHB
 */
package com.lylj.WebLearning.ORM.Mapper;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Mapper;

import com.lylj.WebLearning.ORM.entity.Order;
import com.lylj.WebLearning.ORM.entity.OrderInfor;

@Mapper
public interface OrderMapper {
	// 创建
	int create(Order order);

	// 处理订单
	int set(Order order);

	// 获取ID
	Long getID(Order order);
	
	//老板查询员工订单
	LinkedList<Order> bossGetOrder(String bossName);
	
	//
	LinkedList<Order> expreGetOrder0(String expreName);
	LinkedList<Order> expreGetOrder1(String expreName);
	
	//
	LinkedList<Order> sendGetOrder(String send_phone_num,Integer state);

	
	LinkedList<OrderInfor> getOrderByNum(String number);
	
	

	int updateInfor(OrderInfor orderInfor);

	LinkedList<Order> getOrderByPhoneNUm(String phoneNum);

	
	LinkedList<Order> getOrderByPhoneNUm0(String phoneNum);

	Order getOrderbyTwo(String expressName, String sendName);

	Order getBynum(String number);
}
