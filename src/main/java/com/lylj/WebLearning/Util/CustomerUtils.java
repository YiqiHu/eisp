/**
 * @date 2019年10月21日
 * @time 上午9:13:55
 * @author YiqiHu
 */
package com.lylj.WebLearning.Util;


import org.springframework.security.core.context.SecurityContextHolder;

import com.lylj.WebLearning.ORM.entity.customerLog;


public class CustomerUtils {
    public static customerLog getCurrentUser() {
        return (customerLog) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
