package com.nieyue.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.nieyue.bean.Payment;
import com.nieyue.service.PaymentService;
import com.nieyue.util.HttpClientUtil;

import net.sf.json.JSONObject;

/**
 * 微信支付工具
 * @author 聂跃
 * @date 2017年9月12日
 */
@Configuration
public class WechatpayUtil {
	 @Value("${myPugin.bookStoreDomainUrl}")
	 String bookStoreDomainUrl;
	 @Resource
	 PaymentService paymentService;
	 public String getPayment(Payment payment) throws UnsupportedEncodingException{
		
		 //存储payment
		boolean b = paymentService.addPayment(payment);
		if(!b){
			return "";
		}
/*		 model.setBody(payment.getBody());
		 model.setSubject(payment.getSubject());//订单信息
		 model.setOutTradeNo(payment.getOrderNumber());
		 model.setTimeoutExpress("30m");
		 model.setTotalAmount(String.valueOf(payment.getMoney()));
		 model.setProductCode("QUICK_MSECURITY_PAY");
		 model.setPassbackParams(payment.getPaymentId().toString());
		 request.setBizModel(model);
		 if(payment.getNotifyUrl()!=null && !payment.getNotifyUrl().equals("")){
			 request.setNotifyUrl(payment.getNotifyUrl());
		 }
		 */
			 return "";
		 
	 }


	 public static void main(String[] args) throws AlipayApiException {
	}
	 
}
