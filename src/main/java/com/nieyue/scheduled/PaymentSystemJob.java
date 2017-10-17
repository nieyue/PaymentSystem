package com.nieyue.scheduled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nieyue.service.PaymentService;
import com.nieyue.util.HttpClientUtil;
import com.nieyue.util.NumberUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Component
public class PaymentSystemJob {
	@Resource
	StringRedisTemplate stringRedisTemplate;
	@Value("${myPugin.projectName}")
	String projectName;
	@Resource
	PaymentService paymentService;
	
	 /**
	  * 删除无用支付订单
	 * @throws Exception 
	  */
	    //@Scheduled(cron="0/5 * * * * ?")
	    @Scheduled(cron="0 0 4 * * ?")
	    public void cronDeleteUselessPaymentJob() throws Exception{
	    	//paymentService.browsePagingPayment(null	, null, acountId, createDate, updateDate, status, pageNum, pageSize, orderName, orderWay)
	    	//Integer[] booIdArray = getYueChengShuBaPaymentSystemIdArray();
			//for (int i = 0; i < booIdArray.length; i++) {
			//System.out.println(getYueChengShuBaPaymentSystem(booIdArray[i]));
			//	getYueChengShuBaPaymentSystem(booIdArray[i]);
			//}
	        //System.out.println(new Date().toLocaleString()+" >>cron执行....");
	    }
	    /**
	     * 任务完后执行
	     * @throws InterruptedException
	     */
	    //@Scheduled(fixedDelay=ONE_Minute)
	    public void fixedDelayJob() throws InterruptedException{
	    	
	    	System.out.println(new Date().toLocaleString()+" >>fixedDelay执行....");
	    }
	    /**
	     * 没有延时
	     * @throws InterruptedException
	     */
	   // @Scheduled(fixedRate=ONE_Minute)
	    public void fixedRateJob() throws InterruptedException{
	    	Thread.sleep(1000);
	        System.out.println(new Date().toLocaleString()+" >>fixedRate执行....");
	    }

	   // @Scheduled(cron="0/1 * * * * ?")
	    public void cronJob() throws InterruptedException{
	    	
	        System.out.println(new Date().toLocaleString()+" >>cron执行....");
	    }
	    public static void main(String[] args) throws Exception {
			
		}
}