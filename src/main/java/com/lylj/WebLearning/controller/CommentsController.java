/**
 * @date 2019年11月13日
 * @time 下午7:37:49
 * @author YiqiHu
 */
package com.lylj.WebLearning.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lylj.WebLearning.ORM.entity.CommentsInfor;
import com.lylj.WebLearning.service.CommentService;

@RestController
public class CommentsController {
	@Autowired
	CommentService commentService;

	@PostMapping(value = "/submitComments")
	public String comment(Principal principal, CommentsInfor commentsInfor) {
		commentsInfor.setCustomer_name(principal.getName());
		int ans=commentService.insert(commentsInfor);
		if (ans==1) {
			return "提交成功!";
		}else {
			return "提交失败！";
		}
	}
}
