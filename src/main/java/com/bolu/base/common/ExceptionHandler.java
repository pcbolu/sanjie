package com.bolu.base.common;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DataTruncation;

/**
 * spring mvc 统一错误处理
 * 
 * @author: pc
 *
 */
public class ExceptionHandler implements HandlerExceptionResolver {
	private static final Logger logger = Logger.getLogger(ExceptionHandler.class);


	/***
	 * 统一异步处理监控方法
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @return
	 */
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
		logger.error(ex.getMessage(), ex);
		String msg = "你访问的页面出错!";
		request.setAttribute("errormsg", ex.toString());
		// 有该注解的返回json,java.sql.DataTruncation
		request.setAttribute("msg", "你访问的页面出错了!");
		Throwable t = ExceptionTool.getSpecialException(ex, DataTruncation.class);
		if (t != null && t instanceof DataTruncation) {
			msg = "你输入的字符过多!";
		}
		ResponseBody rb = null;
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			rb = hm.getMethodAnnotation(ResponseBody.class);
		}

		// 判断是否是异步请求
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) || rb != null) {
			try {
				JSONObject json = new JSONObject();
				json.put("success", false);
				json.put("msg", msg);
				json.put("rows", "[]");
				json.put("total", 0);
				json.put("error", true);
				JSonResponseHelper.JSonResponse(response, json);
			} catch (Exception e) {

			}
			return new ModelAndView();
		}
		ModelAndView mv = new ModelAndView("base/404");
		mv.addObject(ex);
		return mv;
	}

}
