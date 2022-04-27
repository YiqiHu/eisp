/**
 * @date 2019年11月13日
 * @time 下午7:41:11
 * @author YiqiHu
 */
package com.lylj.WebLearning.ORM.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.lylj.WebLearning.ORM.entity.CommentsInfor;

@Mapper
public interface CommentMapper {
	int creat(CommentsInfor commentsInfor);

	Double getAvgGrade(String customer_name);
}
