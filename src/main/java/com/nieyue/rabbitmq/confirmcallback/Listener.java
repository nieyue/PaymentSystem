package com.nieyue.rabbitmq.confirmcallback;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.nieyue.business.AcountBusiness;
import com.nieyue.service.PaymentService;

/**
 * 消息监听者
 * @author 聂跃
 * @date 2017年5月31日
 */
@Configuration  
public class Listener {
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private AcountBusiness acountBusiness;
	@Resource
	private PaymentService PaymentService;
	@Resource
	private Sender sender;
	@Value("${myPugin.projectName}")
	String projectName;
	
}
