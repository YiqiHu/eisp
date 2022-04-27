/**
 * @date 2019年10月16日
 * @time 下午10:31:15
 * @author LiangHB
 */
package com.lylj.WebLearning.service;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lylj.WebLearning.ORM.Mapper.customerLogMapper;
import com.lylj.WebLearning.ORM.entity.ExpressMessage;
import com.lylj.WebLearning.ORM.entity.customerLog;

@Service
public class CustomerService implements UserDetailsService {
	@Autowired
	customerLogMapper customerMapper;

	@Override
	public UserDetails loadUserByUsername(String phoneNum) throws UsernameNotFoundException {
		customerLog customerLog = customerMapper.loadByPhoneNum(phoneNum);
		if (customerLog == null) {
			throw new UsernameNotFoundException("账户不存在！");
		}
		return customerLog;
	}

	public int register(customerLog user,ExpressMessage EM) {
		customerLog c=customerMapper.loadByPhoneNum(user.getMobile());
		if (c!=null) {
			return -1;
		}
		if (user.getRole().equals("expressMan")||user.getRole().equals("boss")) {
			customerMapper.insertEM(EM);
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encode = encoder.encode(user.getPassword());
		user.setPassword(encode);
		return customerMapper.insert(user);
	}
	public LinkedList<customerLog> queryExpreMan(String district) {
		return customerMapper.selectExpreMan(district);
	}
	public String getCompany(String name) {
		return customerMapper.selecCompany(name);
	}
	
    public LinkedList<String> getEMname(String bossName) {
		return customerMapper.getEMName(bossName);
	}

	public String getAddresses(String name) {
		return customerMapper.getAddresses(name);
	}

	
	public int addToAddress(String oldAddress,String name) {
		return customerMapper.updateAddress(oldAddress,name);
	}
	
	public customerLog getcustomerlog(String name) {
		return customerMapper.getCustomer(name);
	}

	public int resetPass(String name,String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encode = encoder.encode(password);
		return customerMapper.resetPass(name,encode);
	}

}
