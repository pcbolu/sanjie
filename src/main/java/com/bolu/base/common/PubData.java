package com.bolu.base.common;


import org.apache.log4j.Logger;

public class PubData {

	private static final Logger logger = Logger.getLogger(PubData.class);

	public static String baseUrl = PropertyUtil.getProperty("pro.baseUrl");//获取set.properties 文件中的 项目访问前缀 pro.baseUrl 配置

	public static String resPath = PropertyUtil.getProperty("pro.resPath");//获取set.properties 文件中的 静态资源路劲 pro.baseUrl 配置

	public static String proName = PropertyUtil.getProperty("pro.name");//获取set.properties 文件中的 项目名称 pro.baseUrl 配置

	public static String proServerIp=PropertyUtil.getProperty("pro.serverIp");//获取set.properties 文件中的 项目名称 pro.serverIp 配置

	public static String appId = PropertyUtil.getProperty("wx.appId");// appid

	public static String appSecret = PropertyUtil.getProperty("wx.appSecret"); //appSecret

	public static String alAccessKeyID=PropertyUtil.getProperty("al.AccessKeyID");// 阿里 短信服务 AccessKeyID
	public static String alAccessKeySecret=PropertyUtil.getProperty("al.AccessKeySecret");//阿里 短信服务 alAccessKeySecret

	public static String alSmsTemplateCode=PropertyUtil.getProperty("al.smsTemplateCode");//绑定手机号模板编码
	public static String alSbTemplateCode=PropertyUtil.getProperty("al.sbTemplateCode");//绑定手机号模板编码
	public static String alSignName=PropertyUtil.getProperty("al.signName");//阿里短信服务签名名称


	public static int isinit = 0;//是否初始化








}
