/**
 * @date 2019年10月16日
 * @time 下午9:27:23
 * @author LiangHB
 */
package com.lylj.WebLearning.ORM.Mapper;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lylj.WebLearning.ORM.entity.ExpressMessage;
import com.lylj.WebLearning.ORM.entity.customerLog;

@Mapper
public interface customerLogMapper {

	int insert(customerLog user);

	customerLog loadByPhoneNum(@Param("mobile") String phoneNum);

	int insertEM(ExpressMessage EM);

	LinkedList<customerLog> selectExpreMan(String district);

	String selecCompany(String name);

	LinkedList<String> getEMName(String bossName);

	String getAddresses(String name);

	int updateAddress(String oldAddress,String name);

	customerLog getCustomer(String name);

	int resetPass(String name, String encode);
}