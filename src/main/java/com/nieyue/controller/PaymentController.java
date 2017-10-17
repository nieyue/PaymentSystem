package com.nieyue.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.nieyue.bean.Payment;
import com.nieyue.ipa.IPABusiness;
import com.nieyue.pay.AlipayUtil;
import com.nieyue.pay.WechatpayUtil;
import com.nieyue.rabbitmq.confirmcallback.Sender;
import com.nieyue.service.PaymentService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;
import com.nieyue.weixin.UnifiedOrderUtil;
import com.nieyue.weixin.business.WeiXinBusiness;


/**
 * 支付控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Resource
	private PaymentService paymentService;
	@Resource
	private Sender sender;
	@Resource
	private AlipayUtil alipayUtil;
	@Resource
	private WechatpayUtil wechatpayUtil;
	@Resource
	private WeiXinBusiness weiXinBusiness;
	@Resource
	private IPABusiness iPABusiness;
	@Resource
	private UnifiedOrderUtil unifiedOrderUtil;
	
	
	/**
	 * 支付分页浏览
	 * @param orderName 支付排序数据库字段
	 * @param orderWay 支付排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingPayment(
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="businessId",required=false)Integer businessId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="payment_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<Payment> list = new ArrayList<Payment>();
			list= paymentService.browsePagingPayment(type,businessId,acountId,createDate,updateDate,status,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * appStorePay
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/appStorePay", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList appStorePay(
			//@RequestBody String body,
			HttpServletRequest request,
			HttpSession session) throws IOException  {
			List list=new ArrayList();
//			
	 	String surl="https://sandbox.itunes.apple.com/verifyReceipt";
		String url="https://buy.itunes.apple.com/verifyReceipt";
//		System.err.println("--------------------------------------");
//		ServletInputStream ris =  request.getInputStream();
//		    StringBuilder content = new StringBuilder();  
//		    byte[] b = new byte[64];  
//		    int lens = -1;  
//		    while ((lens = ris.read(b)) > 0) {  
//		        content.append(new String(b, 0, lens));  
//		    }  
//		    String strcont = content.toString();// 内容
//		strcont=java.util.Base64.getEncoder().encodeToString(strcont.getBytes());
//		System.err.println(strcont);
//			String result; 
//			try {
//				result = HttpClientUtil.doPostString(surl,"{\"receipt-data\":"+strcont+"}");
//				list.add(result);
//				System.err.println(result);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			ServletInputStream ris =  request.getInputStream();
			System.err.println(232);
			 InputStreamReader isr=new InputStreamReader(ris);
             BufferedReader br=new BufferedReader(isr);
             StringBuffer sb=new StringBuffer();
             String rl=null;
             while((rl=br.readLine())!=null){
                 sb.append(rl);    
                 //buf=new byte[1024];//重新生成，避免和上次读取的数据重复
             }
             br.close();
             isr.close();
             ris.close();
		    String strcont = sb.toString();// 内容
		    strcont=java.util.Base64.getEncoder().encodeToString(strcont.getBytes());
		    System.err.println(strcont);
			String s = iPABusiness.setIapCertificate("1000", strcont, false);
			System.err.println(s);
			list.add(s);
		/*String result="";
		System.err.println(body);
			try {
				 result = HttpClientUtil.doPostString(surl,body);
				list.add(result);
				System.err.println(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 支付修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updatePayment(@RequestBody Payment payment,HttpSession session)  {
		boolean um = paymentService.updatePayment(payment);
		return ResultUtil.getSR(um);
	}
	/**
	 * 阿里云APP支付
	 * @return
	 * @throws UnsupportedEncodingException 
	 * http://192.168.7.111:8888/payment/alipay?orderNumber=6&money=0.01&subject=aliyun&body=aliyun&notifyUrl=http://www.baidu.com&businessNotifyUrl=http://www.baidu.com
	 */
	@RequestMapping(value = "/alipay", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String alipay(
			@ModelAttribute Payment payment,
			HttpSession session) throws UnsupportedEncodingException  {
		String b = alipayUtil.getPayment(payment);
		return b;
		/*List<String> ls=new ArrayList<String>();
		if(!b.equals("")&&b!=null){
			ls.add(b);
			return ResultUtil.getSlefSRSuccessList(ls);
		}
		return ResultUtil.getSlefSRFailList(ls);*/
	}
	/**
	 * 微信APP支付 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/wechatpay",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String WeiXinAppUnifiedOrder(
			@ModelAttribute Payment payment,
			HttpServletRequest request) throws Exception {
		String b=wechatpayUtil.getPayment(payment,request);
		return b;
		
	}
/*	*//**
	 * 阿里云支付
	 * @return
	 * @throws UnsupportedEncodingException 
	 * http://192.168.7.111:8888/payment/alipay?orderNumber=6&money=0.01&subject=aliyun&body=aliyun&notifyUrl=http://www.baidu.com&businessNotifyUrl=http://www.baidu.com
	 *//*
	@RequestMapping(value = "/alipay", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String alipay(
			@RequestParam(value="acountId") Integer acountId,
			@RequestParam(value="type") Integer type,
			@RequestParam(value="businessType") Integer businessType,
			@RequestParam(value="status") Integer status,
			@RequestParam(value="orderNumber") String orderNumber,
			@RequestParam(value="money") String money,
			@RequestParam(value="subject") String subject,
			@RequestParam(value="body") String body,
			@RequestParam(value="notifyUrl") String notifyUrl,
			@RequestParam(value="businessNotifyUrl") String businessNotifyUrl,
			HttpSession session) throws UnsupportedEncodingException  {
		System.err.println(businessNotifyUrl);
		String b = alipayUtil.getPayment(orderNumber,money,subject,body,notifyUrl,businessNotifyUrl);
		return b;
		List<String> ls=new ArrayList<String>();
		if(!b.equals("")&&b!=null){
			ls.add(b);
			return ResultUtil.getSlefSRSuccessList(ls);
		}
		return ResultUtil.getSlefSRFailList(ls);
	}
*/	/**
	 * 阿里云支付查询
	 * @return
	 * @throws AlipayApiException 
	 */
	@RequestMapping(value = "/alipayTradeQuery", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList alipayAlipayTradeQuery(
			@RequestParam(value="orderNumber") String orderNumber,
			HttpSession session) throws AlipayApiException  {
		String body = alipayUtil.getAlipayTradeQuery(orderNumber);
		List<String> ls=new ArrayList<String>();
		if(!body.equals("")&&body!=null){
			ls.add(body);
			return ResultUtil.getSlefSRSuccessList(ls);
		}
		return ResultUtil.getSlefSRFailList(ls);
	}
	/**
	 * 阿里云支付回调
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/alipayNotifyUrl", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String alipayNotifyUrl(HttpServletRequest request,HttpSession session) {
		 String pm = alipayUtil.getNotifyUrl(request);
		return pm;
	}
	/**
	 * 阿里云支付回调
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/wechatpayNotifyUrl", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String wechatpayNotifyUrl(HttpServletRequest request,HttpSession session) {
		String pm = wechatpayUtil.getNotifyUrl(request);
		return pm;
	}
	/**
	 * 支付增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addPayment(@RequestBody Payment payment, HttpSession session) {
		boolean am = paymentService.addPayment(payment);
		return ResultUtil.getSR(am);
	}
	/**
	 * 支付删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delPayment(@RequestParam("paymentId") Integer paymentId,HttpSession session)  {
		boolean dm = paymentService.delPayment(paymentId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 支付浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="businessId",required=false)Integer businessId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="status",required=false)Integer status,
			HttpSession session)  {
		int count = paymentService.countAll(type,businessId,acountId,createDate,updateDate,status);
		return count;
	}
	/**
	 * 支付单个加载
	 * @return
	 */
	@RequestMapping(value = "/{paymentId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadPayment(@PathVariable("paymentId") Integer paymentId,HttpSession session)  {
		List<Payment> list = new ArrayList<Payment>();
		Payment payment = paymentService.loadPayment(paymentId);
			if(payment!=null &&!payment.equals("")){
				list.add(payment);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	
}
