package com.nieyue.pay;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.alipay.api.AlipayApiException;
import com.nieyue.bean.Payment;
import com.nieyue.service.PaymentService;
import com.nieyue.util.HttpClientUtil;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.weixin.UnifiedOrderUtil;
import com.nieyue.weixin.bean.UnifiedOrder;
import com.nieyue.weixin.business.WeiXinBusiness;

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
	 @Resource
	 WeiXinBusiness weiXinBusiness;
	 @Resource
	 UnifiedOrderUtil unifiedOrderUtil;
	 
	 
	 public String getPayment(Payment payment,HttpServletRequest request) throws Exception{
		
		 //存储payment
		boolean b = paymentService.addPayment(payment);
		if(!b){
			return "";
		}
		String openid =null;//
		String result = weiXinBusiness.WXUnifiedorder(payment, unifiedOrderUtil.getIpAddr(request), openid,"APP");
		Map<String, Object> m = MyDom4jUtil.xmlStrToMap(result);
		String prepay_id = (String) m.get("prepay_id");
		Map<String,String> map=unifiedOrderUtil.getAPPPaySignMap(prepay_id);
		String sign = unifiedOrderUtil.getAPPPaySign(map);
		map.put("sign", sign);
		JSONObject json = JSONObject.fromObject(map);
		//System.err.println(json); 
		return json.toString();
		 
	 }


	 
	 /**
	  * 微信支付回调
	  * @param request
	  * @return
	  */
	public String getNotifyUrl(HttpServletRequest request) {
		 boolean signVerified=true;
		 Payment payment= new Payment();
		//String paymentId = "100";
				try {
					InputStream inStream = request.getInputStream(); 
					ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
					byte[] buffer = new byte[1024];  
					int len = 0;  
					while ((len = inStream.read(buffer)) != -1) {  
					    outSteam.write(buffer, 0, len);  
					}  
					outSteam.close();  
					inStream.close();  
					String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息  
					Map<String, Object> map = MyDom4jUtil.xmlStrToMap(result);
					 String paymentId = (String) map.get("attach");
					 String out_trade_no = (String) map.get("out_trade_no");
					 Integer total_fee =Integer.valueOf((String)map.get("total_fee"));
					 payment = paymentService.loadPayment(Integer.valueOf(paymentId));
					//已经处理过了
					 if(payment.getStatus()!=1){
						 return "success";
					 }
					if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) { 
				   //验证签名
					// if (verifyWeixinNotify(map,payment)) {  
					//订单处理  
					 if(!out_trade_no.equals(payment.getOrderNumber())
							 ||!total_fee.equals(Integer.valueOf((int)(payment.getMoney()*100)))){
						 signVerified=false;
					 }
					 
					 if(signVerified &&payment!=null){
						    // TODO 验签成功后
						    //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
						// HttpClientUtil.doGet(bookStoreDomainUrl+"/bookOrder/paymentNotifyUrl?auth="+MyDESutil.getMD5("1000")+"&params="+URLEncoder.encode(payment.getBusinessNotifyUrl(),"UTF-8"));//异步回调
						String businessNotifyUrl=payment.getBusinessNotifyUrl();
						String fenge="&params=";//分割值
						int fengelength=fenge.length();//分割长度
						int num=businessNotifyUrl.indexOf(fenge);//分割位置
						String prefix = businessNotifyUrl.substring(0,num);//分割之前
						String pas = businessNotifyUrl.substring(num+fengelength);//分割之后
						
						String enpas = URLEncoder.encode(pas,"UTF-8");
						String newBusinessNotifyUrl=prefix+fenge+enpas;
						 String businessResult= HttpClientUtil.doGet(newBusinessNotifyUrl);//异步回调
						 if(JSONObject.fromObject(businessResult).get("code").equals(200)
								 ||JSONObject.fromObject(businessResult).get("code").equals("200")){
							//支付成功
							 payment.setStatus(2);//成功
							 paymentService.updatePayment(payment);
						//成功	
						return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
						 }
					 }  
				//	} 
					
					 }else{
						 payment.setStatus(3);//失败
						 paymentService.updatePayment(payment);
					 }
				} catch (Exception e) {
					payment.setStatus(4);//异常
					//payment.setBusinessNotifyUrl(e.toString());
					 paymentService.updatePayment(payment);
				} 
				//成功	
				return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[FAIL]]></return_msg></xml>";
	}
	/**
	 * 微信回调通知验证
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public boolean verifyWeixinNotify(Map<String, Object> map,Payment payment) throws Exception {  
        SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();  
        String sign = (String) map.get("sign");  
        for (Object keyValue : map.keySet()) {  
            if(!keyValue.toString().equals("sign")){  
                parameterMap.put(keyValue.toString(), map.get(keyValue));  
            }  
              
        }  
        //ip地址必须一样
		UnifiedOrder uo = unifiedOrderUtil.createUnifiedOrder(payment,null,null,"APP");//创建微信订单
		String createSign = unifiedOrderUtil.getSign(uo);
        if(createSign.equals(sign)){  
            return true;  
        }else{  
            return false;  
        }  
    } 
	 
	public static void main(String[] args) throws AlipayApiException {
	}
}
