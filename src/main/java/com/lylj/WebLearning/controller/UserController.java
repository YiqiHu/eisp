/**
 * @date 2019年10月18日
 * @time 下午8:25:45
 * @author YiqiHu
 */
package com.lylj.WebLearning.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lylj.WebLearning.ORM.entity.Address;
import com.lylj.WebLearning.ORM.entity.ExpressMessage;
import com.lylj.WebLearning.ORM.entity.Order;
import com.lylj.WebLearning.ORM.entity.RespBean;
import com.lylj.WebLearning.ORM.entity.customerLog;
import com.lylj.WebLearning.Util.CustomerUtils;
import com.lylj.WebLearning.service.CommentService;
import com.lylj.WebLearning.service.CustomerService;

@RestController
public class UserController {
	@Autowired
	CustomerService userService;
	
	@Autowired
	CommentService commentService;

	@RequestMapping(value = "/login_must")
	public RespBean loginmust() {
		return RespBean.error("尚未登录，请登录!");
	}

	@RequestMapping(value = "page/{role}", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView redirect(HttpServletResponse resp, @PathVariable String role) throws IOException {
		ModelAndView mv = new ModelAndView("redirect:/" + role + ".html");
		mv.addObject(CustomerUtils.getCurrentUser());
		return mv;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public RespBean register(customerLog user, ExpressMessage EM, String District, String bossPhoNum) {
		if (user.getRole().equals("expressMan")) {
			customerLog boss = (customerLog) userService.loadUserByUsername(bossPhoNum);
			EM.setBossname(boss.getUsername());
			EM.setUsername(user.getCustomer_name()+"#"+user.getMobile());
			user.setAddresses(boss.getAddresses());
			user.setCustomer_name(user.getCustomer_name() + "#" + user.getMobile());
			EM.setCompany(userService.getCompany(boss.getUsername()));
		}
		if (user.getRole().equals("boss")) {
			user.setCustomer_name(user.getCustomer_name() + "#" + user.getMobile());
			String username=user.getCustomer_name();
			EM.setUsername(username);
			user.setAddresses(District + "_" + user.getAddresses());
		}
		if (user.getRole().equals("customer")) {
			user.setAddresses(user.getCustomer_name()+"_"+user.getMobile()+"_"+District + "_" + user.getAddresses());
			user.setCustomer_name(user.getCustomer_name() + "#" + user.getMobile());
		}
		if (user.getRole().equals("way")) {
			user.setAddresses(user.getCustomer_name()+"_"+user.getMobile()+"_"+District + "_" + user.getAddresses());
			user.setCustomer_name(user.getCustomer_name() + "#" + user.getMobile());
		}
		

		int i = userService.register(user, EM);
		if (i == 1) {
			return RespBean.ok("注册成功!请登录");
		} else if (i == -1) {
			return RespBean.error("该电话已注册，注册失败!");
		}
		return RespBean.error("注册失败!");
	}

	@RequestMapping(value = "/queryExpreMan", method = RequestMethod.POST)
	public RespBean query(String district) {
		district = district + "_";
		LinkedList<customerLog> found = userService.queryExpreMan(district);
		for (customerLog customerLog : found) {
			String company=userService.getCompany(customerLog.getCustomer_name());
			Double grade=commentService.getAvgGrade(customerLog.getCustomer_name());
			if (grade!=null) {
				customerLog.setPassword(grade+"");
			}
			customerLog.setPassword("暂无评分");
			customerLog.setRole(company);
			customerLog.setCustomer_name(customerLog.getCustomer_name().split("#")[0]);
		}
		if (found != null) {
			return RespBean.ok("查询成功", found);
		} else {
			return RespBean.error("查询失败！");
		}
	}

	@RequestMapping(value = "/getStaffinfo", method = RequestMethod.POST)
	public LinkedList<customerLog> getStaffInfo(Principal principal) {
		String bossName=principal.getName();
		LinkedList<customerLog> ans=new LinkedList<>(); 
		LinkedList<String> temp = userService.getEMname(bossName);
		for (String string : temp) {
			customerLog customerLog=userService.getcustomerlog(string);
			customerLog.setCustomer_name(customerLog.getCustomer_name().split("#")[0]);
			ans.add(customerLog);
		}
		return ans;
	}

	@RequestMapping(value = "/showAddresses", method = RequestMethod.POST)
	public LinkedList<Address> showAddresses(Principal principal) {
		String address = userService.getAddresses(principal.getName());
		LinkedList<Address> ans = new LinkedList<>();
		// 分割开不同的地址
		String[] a = address.split("%");
		for (String string : a) {
			String b[] = string.split("_");
			ans.add(new Address(b[0], b[1],b[2],b[3]));
		}
		return ans;
	}
	@RequestMapping(value = "/addToAddress", method = RequestMethod.POST)
	public String add(Principal principal,Address address) {
		String oldAddress = userService.getAddresses(principal.getName());
		String appendAddres=address.getName()+"_"+address.getPhoneNum()+"_"+address.getDistrict()+"_"+address.getCompleAddre();
		oldAddress=oldAddress+"%"+appendAddres;
		int target=userService.addToAddress(oldAddress,principal.getName());
		if (target==1) {
			return "添加成功！";
		}else {
			return "添加失败！";
		}
	}
	@RequestMapping(value = "/getCustomer", method = RequestMethod.POST)
	public customerLog getInforNow(Principal principal) {
		String name=principal.getName();
		customerLog c=userService.getcustomerlog(name);
		name=name.split("#")[0];
		c.setCustomer_name(name);
		return c;
	}
	@RequestMapping(value = "/resetPass", method = RequestMethod.POST)
	public String resetPass(Principal principal,String password) {
		String name=principal.getName();
		int ans=userService.resetPass(name,password);
		if (ans==1) {
			return "修改成功！";
		} else {
			return "修改失败！";
		}
	}
}
