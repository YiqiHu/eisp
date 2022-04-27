/**
 * @date 2019年10月20日
 * @time 下午2:25:45
 * @author YiqiHu
 */
package com.lylj.WebLearning.confuguration;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lylj.WebLearning.ORM.entity.RespBean;
import com.lylj.WebLearning.Util.CustomerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lylj.WebLearning.service.CustomerService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomerService customer;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customer);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 不拦截这些链接，可直接访问
		web.ignoring().antMatchers("/sign.html","/login.html", "/static/**","/login_must", "/favicon.ico");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		     .antMatchers("/register").permitAll()

			 .anyRequest().authenticated()
			 .and()
			 .formLogin()
			 .loginPage("/index.html").loginProcessingUrl("/login")
			 .usernameParameter("phoneNum").passwordParameter("password")
				
			 
			 .failureHandler(new AuthenticationFailureHandler() {
					@Override
					public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp,
							AuthenticationException e) throws IOException {
						resp.setContentType("application/json;charset=utf-8");
						RespBean respBean = null;
						if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
							respBean = RespBean.error("账户名或者密码输入错误!");
						} else {
							respBean = RespBean.error("登录失败!");
						}
						resp.setStatus(401);
						ObjectMapper om = new ObjectMapper();
						PrintWriter out = resp.getWriter();
						out.write(om.writeValueAsString(respBean));
						out.flush();
						out.close();
					}
				})
			 .successHandler(new AuthenticationSuccessHandler() {
					@Override
					public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp,
							Authentication auth) throws IOException, ServletException {
						String role = CustomerUtils.getCurrentUser().getRole();
						
						if (role.equals("customer")) {
							RequestDispatcher dispatcher = req.getRequestDispatcher("/page/customer");
							dispatcher.forward(req, resp);
						} else if (role.equals("boss")) {
							RequestDispatcher dispatcher = req.getRequestDispatcher("/page/boss");
							dispatcher.forward(req, resp);
						} else if (role.equals("expressMan")) {
							RequestDispatcher dispatcher = req.getRequestDispatcher("/page/expressMan");
							dispatcher.forward(req, resp);
						} else if (role.equals("way")) {
							RequestDispatcher dispatcher = req.getRequestDispatcher("/page/way");
							dispatcher.forward(req, resp);
						}
					}
				})
			 .permitAll()
			 .and()
			 .logout().logoutUrl("/logout")
			 .logoutSuccessHandler(new LogoutSuccessHandler() {
					@Override
					public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp,
							Authentication authentication) throws IOException, ServletException {
						resp.setContentType("application/json;charset=utf-8");
						RespBean respBean = RespBean.ok("注销成功!");
						ObjectMapper om = new ObjectMapper();
						PrintWriter out = resp.getWriter();
						out.write(om.writeValueAsString(respBean));
						out.flush();
						out.close();
					}
				})
			 .permitAll()
			 .and().csrf().disable();

	}

}

