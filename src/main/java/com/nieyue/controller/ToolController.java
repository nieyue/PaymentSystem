package com.nieyue.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.comments.RequestToMethdoItemUtils;
import com.nieyue.comments.RequestToMethodItem;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResultList;



/**
 * 控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/tool")
public class ToolController {
	
	@Resource
	StringRedisTemplate stringRedisTemplate;
	@Resource
	RequestToMethdoItemUtils requestToMethdoItemUtils;
	/**
	 * 获取API接口文档
	 * @return
	 */
	@RequestMapping(value={"/getAPI"}, method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getAPI(
			HttpServletRequest request
			){
		List<RequestToMethodItem> requestToMethdoItemUtilsresult = requestToMethdoItemUtils.getRequestToMethodItemList(request);
		return ResultUtil.getSlefSRSuccessList(requestToMethdoItemUtilsresult);
	
	}
	/**
	 * 获取Session
	 * @return
	 */
	@RequestMapping(value={"/getSession"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String getSession(
			HttpSession	 session
			){
		System.err.println(session.getAttribute("acount"));
		System.err.println(session.getAttribute("role"));
		System.err.println(session.getAttribute("finance"));
		return session.getId();
		
	}
	
	/**
	 * 获取Map
	 * @return
	 */
	@RequestMapping(value={"/getMap"}, method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String,String> getMap(
			@RequestParam Map<String,String> map,
			HttpSession	 session
			){
		System.err.println(map.get("nieyue"));
		System.err.println(map.get("a"));
		return map;
		
	}
	
}
