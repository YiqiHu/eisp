/**
 * @date 2019年10月16日
 * @time 下午9:18:32
 * @author YiqiHu
 */
package com.lylj.WebLearning.ORM.entity;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class customerLog implements UserDetails {

	private String customer_name;

	private String role;

	private String password;

	private String addresses;

	private String mobile;


	public final String getRole() {
		return role;
	}

	public final void setRole(String role) {
		this.role = role;
	}

	public final String getAddresses() {
		return addresses;
	}

	public final void setAddresses(String addresses) {
		this.addresses = addresses;
	}

	public final String getMobile() {
		return mobile;
	}

	public final void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
	    java.util.List<SimpleGrantedAuthority> authority=new ArrayList<SimpleGrantedAuthority>();
		authority.add(new SimpleGrantedAuthority(role));
		return authority;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return customer_name;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
}