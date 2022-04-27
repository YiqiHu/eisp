/**
 * @date 2019年10月24日
 * @time 下午9:38:22
 * @author LiangHB
 */
package com.lylj.WebLearning.controller;

import java.io.File;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lylj.WebLearning.ORM.entity.Order;
import com.lylj.WebLearning.ORM.entity.OrderInfor;
import com.lylj.WebLearning.ORM.entity.OrderMessage;
import com.lylj.WebLearning.ORM.entity.RespBean;
import com.lylj.WebLearning.ORM.entity.customerLog;
import com.lylj.WebLearning.Util.CustomerUtils;
import com.lylj.WebLearning.Util.PictureFactory;
import com.lylj.WebLearning.service.CustomerService;
import com.lylj.WebLearning.service.OrderService;

import ch.qos.logback.core.helpers.Transform;

@RestController
public class OrderController {
	@Autowired
	SimpMessagingTemplate messagingTemplate;
	@Autowired
	OrderService orderService;
	@Autowired
	CustomerService customerService;

	@MessageMapping("/xiadan")
	public RespBean order(Principal principal, OrderMessage orderMessage) {
		String from = principal.getName();
		orderMessage.setFrom(from);
		String user = orderMessage.getTo();
		Order order = orderMessage.getOrder();
		int ans = orderService.createOrder(from, order.getSendAddress(), order.getSendPhoneNum(),
				order.getSendDistrict(), order.getReceiveName(), order.getReceiveAddress(), order.getReceivePhoneNum(),
				order.getReceiveDistrict(), user);
		if (ans == 1) {
			SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
			Date date = new java.util.Date();
			String orderTime = df.format(date);
			orderMessage.getOrder().setOrderTime(orderTime);
			messagingTemplate.convertAndSendToUser(user, "/queue/try", orderMessage);
			return RespBean.ok("下单成功！");
		} else {
			return RespBean.error("下单失败！");
		}
	}

	// 返回订单图片路径，以供打印
	@PostMapping(value = "/submit")
	public String dealOrder(Order order, HttpServletRequest req, Principal principal) throws Exception {
		// 订单处理时间
		int price = order.getPrice();
		String l = order.getPictureLocation();
		String expressName = principal.getName();
		order.setExpressName(expressName);
		order = orderService.getOrderBytwo(order.getExpressName(), order.getSendName());
		order.setPictureLocation(l);
		order.setPrice(price);
		String solveTime;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new java.util.Date();
		solveTime = df.format(date);
		// 生成订单编号,时间+两位随机数
		df = new SimpleDateFormat("MMddHHmmssSSS");
		String number = df.format(date);
		Random random = new Random();
		for (int i = 0; i < 2; i++) {
			number += String.valueOf(random.nextInt(10));
		}
		// 设置
		order.setNumber(number);
		order.setSolveTime(solveTime);
		order.setState(1);
		long Id = orderService.getID(order);
		order.setID(Id);
		// 订单图片和条形码
		SimpleDateFormat sft = new SimpleDateFormat("yyyy/MM/dd/");
		String path = "/image/orderImage/";
		String realPath = req.getSession().getServletContext().getRealPath(path);
		String format = sft.format(new Date());
		File folder = new File(realPath + format);
		if (!folder.isDirectory()) {
			folder.mkdirs();
		}
		String newName = UUID.randomUUID().toString() + ".jpg";
		// 获取快递员公司信息
		String company = customerService.getCompany(order.getExpressName());
		if (company.equals("圆通快递")) {
			company = "yuantong";
		} else if (company.equals("中通快递")) {
			company = "zhongtong";
		} else if (company.equals("申通快递")) {
			company = "shentong";
		} else if (company.equals("百世汇通")) {
			company = "baishi";
		} else if (company.equals("韵达快递")) {
			company = "yunda";
		}
		// 生成图片并存储
		PictureFactory.generate(company, order, new File(folder, newName), new File(folder, number + ".jpg"));
		String orderLocation = "../.." + path + format + number + ".jpg";
		order.setOrderPicture(orderLocation);

		int a = orderService.dealOrder(order);
		OrderInfor orderInfor = new OrderInfor();
		orderInfor.setCustomer_name(order.getExpressName());
		orderInfor.setNumber(number);
		orderInfor.setSolve_time(solveTime);
		orderInfor.setState(1);
		int update = orderService.updateOrderInfor(orderInfor);
		if (a == 1 && update == 1) {
			System.out.println("提交成功！");
		} else {
			return "提交失败";
		}
		return orderLocation;
	}

	// way的转运
	@PostMapping(value = "/transfor")
	public RespBean Transform(String number, Principal principal) {
		LinkedList<OrderInfor> aInfors = orderService.getOrderByNum(number);
		Order order = orderService.getbyNum(number);
		if (aInfors.size() == 3 && order != null) {
			order.setReceiveName(order.getReceiveName().split("#")[0]);
			return RespBean.ok("查询成功", order);
		} else {
			return RespBean.error("无此订单，或越级操作，请重新输入！");
		}
	}

	// expressMan的派件
	@PostMapping(value = "/final")
	public RespBean end(String number, Principal principal) {
		// 该货物不应当出现在你手上或单号错误，总之，报错
		LinkedList<OrderInfor> aInfors = orderService.getOrderByNum(number);
		Order order = orderService.getbyNum(number);
		if (aInfors.size() == 2 && order != null) {
			order.setReceiveName(order.getReceiveName().split("#")[0]);
			return RespBean.ok("查询成功", order);
		} else {
			return RespBean.error("无此订单，或越级操作，请重新输入！");
		}
	}
	// 更新物流信息
	@PostMapping(value = "/update")
	public String update(String number, Principal principal, String role) {

		OrderInfor orderInfor = new OrderInfor();
		String solveTime;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new java.util.Date();
		solveTime = df.format(date);
		orderInfor.setCustomer_name(principal.getName());
		orderInfor.setNumber(number);
		orderInfor.setSolve_time(solveTime);
		if (role.equals("expressManpai")) {
			orderInfor.setState(3);
		} else if (role.equals("way")) {
			orderInfor.setState(2);
		} else if (role.equals("expressManqian")) {
			orderInfor.setState(4);
		}
		int update = orderService.updateOrderInfor(orderInfor);
		if (update == 1) {
			return "更新物流成功！";
		} else {
			return "更新失败！";
		}

	}

	// 三方查询订单,通过name
	@PostMapping(value = "/getOrder")
	public LinkedList<Order> getOrderInfo(Principal principal, Integer state) {
		String role = CustomerUtils.getCurrentUser().getRole();
		String expreName = null;
		String sendName = null;
		String bossName = null;
		if (role.equals("boss")) {
			bossName = principal.getName();
		} else if (role.equals("expressMan")) {
			expreName = principal.getName();
		} else if (role.equals("customer")) {
			sendName = principal.getName();
		}
		LinkedList<Order> ans = orderService.getOrder(bossName, expreName, sendName, state);
		for (Order order : ans) {
			order.setExpressName(order.getExpressName().split("#")[0] + " " + order.getExpressName().split("#")[1]);
			order.setSendName(order.getSendName().split("#")[0]);
		}
		return ans;
	}

	// 用户通过name
	@PostMapping(value = "/getOrderBySend")
	public LinkedList<Order> getOrderInfo(Principal principal, int state) {
		String sendName = principal.getName();
		return orderService.SenderGetOrder(sendName, state);
	}

	// 查询订单，通过单号,订单物流信息
	@PostMapping(value = "/getOrderInfor")
	public LinkedList<OrderInfor> getOrder(String number) {
		return orderService.getOrderByNum(number);
	}

	// 通过姓名查待收货
	@PostMapping(value = "/getToReceive")
	public LinkedList<Order> getMyOrder(Principal principal, Integer state) {
		String name = principal.getName();
		String phoneNum = name.split("#")[1];
		LinkedList<Order> ans = orderService.getOrderByPhoneNum(phoneNum, state);
		for (Order order : ans) {
			order.setSendName(order.getSendName().split("#")[0]);
		}
		return ans;
	}

}
