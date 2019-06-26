package com.bolu.base.common;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JSonResponseHelper {


    /***
     * 根据标志位返回Json Response
     * @param rsp
     * @param json
     * @param flag
     * @param msg
     * @throws JSONException
     */
    public static void JSonResponseFlagMsg(HttpServletResponse rsp, JSONObject json, int flag, String msg) throws JSONException {
        if (flag == 1) {
            json.put("code", "1");
            json.put("msg", msg);
        } else {
            String re = "" + flag;
            json.put("code", re);
            json.put("msg", msg);
        }
        try {
            rsp.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            rsp.getWriter().write(json.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /***
     * 返回 json 数据
     * @param rsp
     * @param json json对象
     * @throws JSONException
     */
    public static void JSonResponse(HttpServletResponse rsp, JSONObject json) throws JSONException {
        try {
            rsp.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            rsp.getWriter().write(json.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /***
     * 根据标志位返回Jsonp Response
     * @param req
     * @param rsp
     * @param json json数据
     * @param flag  编码
     * @param msg  提示信息
     * @throws JSONException
     */
    public static void JSonPRspFlagMsg(HttpServletRequest req, HttpServletResponse rsp, JSONObject json, int flag, String msg) throws JSONException {
        if (flag == 1) {
            json.put("code", "1");
            json.put("msg", msg);
        } else {
            String re = "" + flag;
            json.put("code", re);
            json.put("msg", msg);
        }

        try {
            String callback = req.getParameter("callback");
            StringBuffer buf = new StringBuffer();
            buf.append(callback);
            buf.append("(");
            buf.append(json.toString());
            buf.append(");");
            rsp.setContentType("application/x-javascript; charset=UTF-8");
            rsp.getWriter().write(buf.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    /***
     * 根据标志位返回Jsonp Response
     * @param req
     * @param rsp
     * @param json
     * @throws JSONException
     */
    public static void JSonPRspFlagMsg(HttpServletRequest req, HttpServletResponse rsp, JSONObject json) throws JSONException {
        try {
            String callback = req.getParameter("callback");
            StringBuffer buf = new StringBuffer();
            buf.append(callback);
            buf.append("(");
            buf.append(json.toString());
            buf.append(");");
            rsp.setContentType("application/x-javascript; charset=UTF-8");
            rsp.getWriter().write(buf.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
