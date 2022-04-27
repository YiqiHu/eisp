/**
 * @date 2019年11月13日
 * @time 下午7:39:22
 * @author YiqiHu
 */
package com.lylj.WebLearning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lylj.WebLearning.ORM.Mapper.CommentMapper;
import com.lylj.WebLearning.ORM.entity.CommentsInfor;

@Service
public class CommentService {
	@Autowired
	CommentMapper commentMapper;
	public int insert(CommentsInfor commentsInfor) {
		return commentMapper.creat(commentsInfor);
	}
	
	public Double getAvgGrade(String customer_name) {
		return commentMapper.getAvgGrade(customer_name);
	}
	
}
