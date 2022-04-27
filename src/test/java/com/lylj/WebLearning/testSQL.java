/**
 * @date 2019年10月27日
 * @time 上午8:18:04
 * @author LiangHB
 */
package com.lylj.WebLearning;

import com.lylj.WebLearning.service.OrderService;

/**
 * 
 */
public class testSQL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        OrderService orderService=new OrderService();
        orderService.createOrder("林一",
        		"15245678945",
        		"河北省秦皇岛市开发区",
        		"东北大学秦皇岛分校140号",
        		"李三",
        		"15647859632",
        		"山西省太原市万柏林区",
        		"千峰南路西岸小区","zhangsan");
	}

}
