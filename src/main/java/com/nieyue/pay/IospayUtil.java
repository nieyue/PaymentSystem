package com.nieyue.pay;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;

import com.nieyue.bean.Payment;
import com.nieyue.service.PaymentService;
import com.nieyue.util.HttpClientUtil;
import com.nieyue.weixin.UnifiedOrderUtil;
import com.nieyue.weixin.business.WeiXinBusiness;

import net.sf.json.JSONObject;

/**
 * ios支付工具
 * @author 聂跃
 * @date 2017年9月12日
 */
@Configuration
public class IospayUtil {
	 @Resource
	 PaymentService paymentService;
	 @Resource
	 WeiXinBusiness weiXinBusiness;
	 @Resource
	 UnifiedOrderUtil unifiedOrderUtil;
	 
	 /**
	  * ios app 支付
	  * @param payment
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 public String getAppPayment(Payment payment){
		 //存储payment
		boolean b = paymentService.addPayment(payment);
		if(!b){
			return null;
		}
		JSONObject paymentjson = JSONObject.fromObject(payment);
		return paymentjson.toString();
		 
	 }
	 /**
	  * iso支付回调验证
	  * @param request
	  * @return
	  */
	public String getNotifyUrl(HttpServletRequest request) {
		String body=(String) request.getAttribute("body");
	 	String surl="https://sandbox.itunes.apple.com/verifyReceipt";
		String url="https://buy.itunes.apple.com/verifyReceipt";
		
			String result; 
			try {
				result = HttpClientUtil.doPostString(url,"{\"receipt-data\":\""+body+"\"}");
				JSONObject rejson = JSONObject.fromObject(result);
				if(rejson.get("status").equals("21007")
						||rejson.get("status").equals(21007)){//沙盒
					result = HttpClientUtil.doPostString(surl,"{\"receipt-data\":\""+body+"\"}");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "{\"code\":\"40000\",\"msg\":\"异常\"}";
			}
		return result;
	}

}
