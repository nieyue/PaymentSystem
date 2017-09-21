package com.nieyue.rabbitmq.confirmcallback;

import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nieyue.bean.Payment;

/**
 * 消息生产者
 * @author 聂跃
 * @date 2017年5月31日
 */
@Component 
public class Sender  implements RabbitTemplate.ConfirmCallback{
	 private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);  
	/**
	 * 不能注入，否则没回调
	 */
	 private RabbitTemplate rabbitTemplate;
	 @Resource
	 private AmqpConfig amqpConfig;
	@Autowired  
	    public Sender(RabbitTemplate rabbitTemplate) {  
	        this.rabbitTemplate = rabbitTemplate;  
	        this.rabbitTemplate.setConfirmCallback(this); 
	    } 
	
	/**
	 * 支付回调
	 * @param orderRabbitmqDTO
	 */
	 public void sendPaymentRedirect(Payment payment) { 
	        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
	        this.rabbitTemplate.convertAndSend(amqpConfig.PAYMENTREDIRECT_DIRECT_EXCHANGE, amqpConfig.PAYMENTREDIRECT_DIRECT_ROUTINGKEY, payment, correlationData);  
	     
	 }  
	 /** 回调方法 */
	 @Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
	        if (ack) {
	        	LOGGER.info("消息发送确认成功");
	        } else {
	        	LOGGER.info("消息发送确认失败:" + cause);

	        }  
		
	}

}
