package com.bolu.base.common;

import com.bolu.system.bo.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ReqParUtil {
	private static final Logger logger = Logger.getLogger(ReqParUtil.class);

	/***
	 * 获取编码字符串
	 * @param req
	 * @param pname
	 * @return
	 */
	public static String getReqParameterDecode(HttpServletRequest req, String pname) {
		String name = "";
		try {
			if(null==req.getParameter(pname)){
				return "";
			}
			name = URLDecoder.decode(req.getParameter(pname).trim(), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			name = "";
		}

		return name;
	}

	/**
	 * 获取字符串
	 * @param req
	 * @param pname
	 * @return
	 */
	public static String getReqParameter(HttpServletRequest req, String pname) {
		String name = "";
		try {
			name = req.getParameter(pname).trim();
		} catch (Exception e1) {
			name = "";
		}

		return name;
	}

	/**
	 * 获取数字
	 * @param req
	 * @param pname
	 * @return
	 */
	public static int getReqParameterInt(HttpServletRequest req, String pname) {
		String name = "";
		int re = 0;
		try {
			name = req.getParameter(pname).trim();
			re = Integer.parseInt(name);
		} catch (Exception e1) {
			re = 0;
		}

		return re;
	}

	/**
	 * 获取默认值数字
	 * @param req
	 * @param pname
	 * @param def
	 * @return
	 */
	public static int getReqParameterIntDef(HttpServletRequest req, String pname, int def) {
		String name = "";
		int re = def;
		try {
			name = req.getParameter(pname).trim();
			re = Integer.parseInt(name);
		} catch (Exception e1) {
			re = def;
		}

		return re;
	}

	/**
	 * 获取浮点
	 * @param req
	 * @param pname
	 * @return
	 */
	public static double getReqParameterDouble(HttpServletRequest req, String pname) {
		String name = "";
		double re = 0;
		try {
			name = req.getParameter(pname).trim();
			re = Double.parseDouble(name);
		} catch (Exception e1) {
			re = 0;
		}

		return re;
	}

	/**
	 * 获取所有参数
	 * @param req
	 * @param pname
	 * @return
	 */
	public static HashMap<String, Object> getReqAllParam(HttpServletRequest req, String pname) {
		HashMap<String, Object> re = new HashMap<String, Object>();

		String[] tm = pname.split(",");
		for (String d : tm) {
			String pre = d.substring(0, 2);
			String pam = d.substring(3, d.length());

			if (pre.equals("iz"))
				re.put(d, getReqParameterInt(req, pam));
			else if (pre.equals("sn"))
				re.put(d, getReqParameter(req, pam));
			else if (pre.equals("sb"))
				re.put(d, getReqParameterDecode(req, pam));
			else if (pre.equals("fz"))
				re.put(d, getReqParameterDouble(req, pam));
		}

		return re;
	}

	/**
	 *  设置session参数值
	 * @param req
	 * @param name
	 * @param value
	 */
	public static void SessionSetAttr(HttpServletRequest req, String name, Object value) {
		HttpSession session = req.getSession();
		session.setAttribute(name.toLowerCase(), value);
	}

	/**
	 * 获取session参数值
	 * @param req
	 * @param name
	 * @return
	 */
	public static Object SessionGetAttr(HttpServletRequest req, String name) {
		HttpSession session = req.getSession();
		Object obj = session.getAttribute(name.toLowerCase());
		return obj;
	}

	/**
	 * 获取 ServletContext application 对象
	 * @param req
	 * @return
	 */
	 public static ServletContext getApplication(HttpServletRequest req){
		 ServletContext application = req.getSession().getServletContext();
		 return application;
	 }


	/**
	 * 获取当前登录的用户信息
	 *
	 * @return
	 */
	public static User getCurrUser(HttpServletRequest req) {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		return user;
	}



	/**
	 * 获取session参数字符串
	 * @param req
	 * @param name
	 * @return
	 */
	public static String SessionGetStr(HttpServletRequest req, String name) {
		HttpSession session = req.getSession();
		Object obj = session.getAttribute(name.toLowerCase());

		if (obj == null)
			return "";
		else
			return obj.toString();
	}

	/***
	 * 清空Session对象参数
	 * @param req
	 * @param param
	 */
	public static void SessionclearAttr(HttpServletRequest req, String param) {
		HttpSession session = req.getSession();
		session.setAttribute(param, null);
	}
	/***
	 * 获取请求中的所有参数 并将 参数的 URLDecoder 编码 转  UTF-8
	 * @param req
	 * @return
	 */
	public static   Map<String, String>  getReqParameterMapToURLDecoder(HttpServletRequest req){
		   Map< String, String>  paramsMap= new HashMap<String, String>();
		   String	querystr=req.getQueryString();
		   String[] querys=querystr.split("&"); 
		  for (String query : querys) {
			  if(StringUtils.isMeaningFul(query)) {
				  	String[]   ps=query.split("=");
				  	if(ps.length==2&&StringUtils.isMeaningFul(ps[0])&&StringUtils.isMeaningFul(ps[1])) {
				  		try {
							ps[1]=	URLDecoder.decode(ps[1], "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
				  		paramsMap.put(ps[0].trim(), ps[1].trim());
				  	}
			  }
		  }
		return paramsMap;
	}


	/***
	 * 从字符输入流中读取文本，缓冲各个字符，从而实现字符
	 * @param req
	 * @param charsetName 需要转换的编码
	 * @return
	 */
	public static String getReqInputStreamData(HttpServletRequest req, String charsetName){
		try{
			InputStreamReader isr = new InputStreamReader(req.getInputStream(), "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String str, TextMessage = "";
			while ((str = br.readLine()) != null) {
				TextMessage += str;
			}
			if(StringUtils.isMeaningFul(charsetName)){
				// URLDecoder.decode 如果TextMessage 中出现 特殊字符，这转码后数据会出现不一致 列如：‘+’ 会变成空格
				String ids = URLDecoder.decode(TextMessage, charsetName);
				return ids;
			}else {
				return TextMessage;
			}
		}catch (Exception e){
			logger.info("getReqInputStreamData出现异常"+e.getMessage());
			return "";
		}
	}


	public static String getIpAddr(HttpServletRequest request){
		String ipAddress = request.getHeader("x-forwarded-for");
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
				//根据网卡取本机配置的IP
				InetAddress inet=null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress= inet.getHostAddress();
			}
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
			if(ipAddress.indexOf(",")>0){
				ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/***
	 * 获取请求的ip地址
	 * @param request
	 * @return
	 */
	public static String getClientIpAddress(HttpServletRequest request) {
		String clientIp = request.getHeader("x-forwarded-for");
		if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getHeader("Proxy-Client-IP");
		}
		if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getRemoteAddr();
		}
		return clientIp;
	}


}
