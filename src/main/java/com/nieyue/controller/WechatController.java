package com.nieyue.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.util.MyDom4jUtil;
import com.nieyue.weixin.UnifiedOrderUtil;
import com.nieyue.weixin.business.Order;
import com.nieyue.weixin.business.WeiXinBusiness;

import net.sf.json.JSONObject;
/**
 * 微信接口
 * @author yy
 *
 */
@RestController("wechatController")
@RequestMapping("/wechat")
public class WechatController {
	@Resource
	WeiXinBusiness weiXinBusiness;
	@Resource
	UnifiedOrderUtil unifiedOrderUtil;
	 
	/**
	 * 微信支付 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/payApp",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String WeiXinAppUnifiedOrder(
			HttpServletRequest request) throws Exception {
		Order o=new Order();
		o.setOrderId(234);
		String openid =null;//
		String result = weiXinBusiness.WXUnifiedorder(o,"测试", unifiedOrderUtil.getIpAddr(request), openid,"APP","http://nieyue.tea18.cn/weixin/notifyUrl");
		Map<String, Object> m = MyDom4jUtil.xmlStrToMap(result);
		String prepay_id = (String) m.get("prepay_id");
		Map<String,String> map=unifiedOrderUtil.getAPPPaySignMap(prepay_id);
		String sign = unifiedOrderUtil.getAPPPaySign(map);
		map.put("sign", sign);
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}
	/**
	 * 微信支付 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/paywap/{orderId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String WeiXinCommentUnifiedOrder(@PathVariable("orderId")String orderId,HttpServletRequest request) throws Exception {
		Order o=new Order();
		o.setOrderId(234);
		String openid =null;//
		String result = weiXinBusiness.WXUnifiedorder(o,"测试", unifiedOrderUtil.getIpAddr(request), openid,"NATIVE","http://nieyue.tea18.cn/weixin/notifyUrl");
		Map<String, Object> m = MyDom4jUtil.xmlStrToMap(result);
		String prepay_id = (String) m.get("prepay_id");
		Map<String,String> map=unifiedOrderUtil.getPaySignMap(prepay_id);
		map.put("pay_sign",unifiedOrderUtil.getPaySign(map));
		String userAgent=request.getHeader("user-agent");
		char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger")+15);
		map.put("agent",new String(new char[]{agent}) );
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}
	/**
	 * 微信支付  统一下单 jsapi
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/test/{orderId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String WeiXiPayWap(@PathVariable("orderId")String orderId,HttpServletRequest request) throws Exception {
		Order o=new Order();
		o.setOrderId(234);
		//String openid = (String)request.getSession().getAttribute("openid");
		//String openid = "orFtEwbV4pZCgYpU09JrfZavbAjE";//放肆约
		String openid = "oDvosuIH0Lmn9eDN1nTTTQGVww74";//雅耀本真
		String result = weiXinBusiness.WXUnifiedorder(o,"测试", unifiedOrderUtil.getIpAddr(request), openid,"JSAPI","http://nieyue.tea18.cn/weixin/notifyUrl");
		System.out.println(result);
		Map<String, Object> m = MyDom4jUtil.xmlStrToMap(result);
		String prepay_id = (String) m.get("prepay_id");
		Map<String,String> map=unifiedOrderUtil.getPaySignMap(prepay_id);
		map.put("pay_sign",unifiedOrderUtil.getPaySign(map));
		map.put("pay_sign",unifiedOrderUtil.getPaySign(map));
		String userAgent=request.getHeader("user-agent");
		char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger")+15);
		map.put("agent",new String(new char[]{agent}) );
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}
	/**
	 * 微信统一下单notify_url
	 */
	@RequestMapping(value="/notifyUrl",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String WeiXinNotifyUrl(@RequestBody String rxml, HttpServletRequest request) throws Exception {
		return weiXinBusiness.WXNotifyUrl(rxml);
	}
	/**
	 * 微信订单查询
	 */
	@RequestMapping(value="/order/query",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String WeiXinOrderQuery(HttpServletRequest request) throws Exception {
        	String out_trade_no="56390728112700";
			//String out_trade_no=(String) request.getSession().getAttribute("out_trade_no");
        	//String out_trade_no="4009762001201607269799315041";
        	String order = weiXinBusiness.WXOrderQuery(out_trade_no);
         return order;
	}
	/**
	 * 关闭微信订单
	 */
	@RequestMapping(value="/order/close",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String WeiXinOrderClose(HttpServletRequest request) throws Exception {
        	//String out_trade_no="23134A0726100835";
        	String out_trade_no="56390728112700";
			//String out_trade_no=(String) request.getSession().getAttribute("out_trade_no");
        	//String out_trade_no="4009762001201607269799315041";
        	String order = weiXinBusiness.WXOrderClose(out_trade_no);
         return order;
	}
	/**
	 * 微信申请退款
	 */
	@RequestMapping(value="/refund",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String WeiXinRefund(HttpServletRequest request) throws Exception {
		//String out_trade_no="23134A0726100835";
		String out_trade_no="56390728112700";
		//String out_trade_no=(String) request.getSession().getAttribute("out_trade_no");
		String refund = weiXinBusiness.WXRefund(out_trade_no, 1, 1);
		return refund;
	}
	/**
	 *查询微信退款订单
	 */
	@RequestMapping(value="/refund/query",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String WeiXinRefundQuery(HttpServletRequest request) throws Exception {
        	//String out_trade_no="23134A0726100835";
        	String out_trade_no="56390728112700";
			//String out_trade_no=(String) request.getSession().getAttribute("out_trade_no");
        	//String out_trade_no="4009762001201607269799315041";
        	String refundquery = weiXinBusiness.WXRefundQuery(out_trade_no);
         return refundquery;
	}
}
