package com.bolu.base.core;

import com.bolu.base.common.ReqParUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author pc
 * @date 2019/4/1
 */
public class SecurityFilter implements Filter {

    private static final Logger logger = Logger.getLogger(SecurityFilter.class);

    public void destroy() {
        // TODO Auto-generated method stub

    }

    //处理所有链接
    public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpReq = (HttpServletRequest) req;
            HttpServletResponse httpRsp = (HttpServletResponse) rsp;
            String uri = "";
            String que = "";

            try {
                uri = httpReq.getRequestURI();
                que = httpReq.getQueryString();
                logger.info("URI:" + uri);
            } catch (Exception ex) {
                logger.info("获取 UR异常:" + ex.getMessage());
                uri = "";
                que = "";
            }

            if (que != null) {
                uri += "?" + que;
                logger.info("URI:" + uri);
            }
            String[] pastr = uri.split("/");

            if (pastr.length == 4) {
                /* 安全检查 */
                if ("wap".equals(pastr[2]) || "api".equals(pastr[2]) || "news".equals(pastr[2]) || "web".equals(pastr[2])
                        || "pc".equals(pastr[2]) || "stoorder".equals(pastr[2]) || "hotelapi".equals(pastr[2])
                        || "page".equals(pastr[2]) || "card".equals(pastr[2]) || "underApi".equals(pastr[2])
                        || "skapi".equals(pastr[2]) || "collapi".equals(pastr[2]) || "ReceiveMsgApi".equals(pastr[2])
                        || "inspection".equals(pastr[2]) || "qrcode".equals(pastr[2])||"carBook".equals(pastr[2])
                        ||"carBookData".equals(pastr[2])||"fpMessage".equals(pastr[2])||"sanjie".equals(pastr[2])
                        ) {
                    //暂时不实现
                } else {
                    if (!pastr[3].contains("json") && !pastr[3].equals("login"))    //非登陆页
                    {
                        //暂时不实现
                        if (!checkLogin(httpReq, httpRsp)) {
                            String path = httpReq.getContextPath();
                            String basePath = httpReq.getScheme() + "://" + httpReq.getServerName() + ":" + httpReq.getServerPort() + path + "/";
                            req.getRequestDispatcher("/user/login").forward(req, rsp);
                            return;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.info("Security 异常:" + ex.getMessage());
        }

        chain.doFilter(req, rsp);
    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        logger.info("-----------init-----------");
    }

    //------------------------------------------------------------------
    //创建cookie，并将新cookie添加到“响应对象”response中。
    public void addCookie(HttpServletResponse rsp, String name, String value) {
        Cookie cookie = new Cookie(name, value);//创建新cookie
        cookie.setMaxAge(5 * 60);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域
        rsp.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
    }

    private boolean checkPermit(HttpServletRequest httpReq,
                                HttpServletResponse httpRsp) {

        return true;
    }

    //微信用户登录检测
    private boolean checkWxLogin(HttpServletRequest httpReq, HttpServletResponse httpRsp) {
        // 检查是否登录
        try {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    //用户登录检测
    private boolean checkLogin(HttpServletRequest httpReq, HttpServletResponse httpRsp) {
        // 检查是否登录
        try {
            // 检查权限
            /*Cookie[] cookies = httpReq.getCookies();//根据请求数据，找到cookie数组
            if (null == cookies) {//如果没有cookie数组
                System.out.println("没有cookie");
                String sessionId = StringUtils.uuid();
                String userName="pengchao148";
                MemcacheUtil.set(sessionId, userName);
                Cookie cookie = new Cookie("sessionid", sessionId);
                httpRsp.addCookie(cookie);
            } else {
                for (Cookie cookie : cookies) {
                    System.out.println("cookieName:" + cookie.getName() + ",cookieValue:" + cookie.getValue());
                    if (cookie.getName().equals("sessionid")) {
                        String sessionId = cookie.getValue();
                        Object obj = MemcacheUtil.get(sessionId);
                        if (obj == null) {
                            String userName="pengchao148";
                            *//*th_cookinfo mod = new th_cookinfo();
                            mod.setName("admin");
                            mod.setRoleid(1);*//*
                            MemcacheUtil.set(sessionId, userName);
                            System.out.println("To Add");
                        } else {
                            *//*th_cookinfo mod = (th_cookinfo) obj;*//*
                            System.out.println("Name:" +obj.toString());
                        }
                    }

                }
            }*/

            Object obj = ReqParUtil.SessionGetAttr(httpReq, "user");
            if (obj == null) {
                return false;
            }
            return true;

        } catch (Exception ex) {
            return false;
        }
    }





}
