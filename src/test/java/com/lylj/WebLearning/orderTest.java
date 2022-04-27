/**
 * @date 2019年11月2日
 * @time 上午8:57:39
 * @author LiangHB
 */
package com.lylj.WebLearning;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lylj.WebLearning.ORM.entity.Order;
import com.lylj.WebLearning.service.OrderService;

/**
 * 
 */
public class orderTest {

	@Test
	public void test() {
		OrderService orderService=new OrderService();
		Order order=new Order();
		orderService.dealOrder(order);
	}

}
