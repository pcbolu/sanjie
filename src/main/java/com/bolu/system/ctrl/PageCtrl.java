package com.bolu.system.ctrl;

import com.bolu.base.common.JSonResponseHelper;
import com.bolu.base.common.PubData;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.CusInfoSet;
import com.bolu.system.bo.VisitLink;
import com.bolu.system.bo.VisitRecord;
import com.bolu.system.service.ICusInfoSetService;
import com.bolu.system.service.IVisitLinkService;
import com.bolu.system.service.IVisitRecordService;
import com.bolu.system.util.QRCodeUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class PageCtrl {

    @Autowired
    private ICusInfoSetService cusInfoSetService;
    @Autowired
    private IVisitRecordService visitRecordService;
    @Autowired
    private IVisitLinkService visitLinkService;

    private static final Logger logger = Logger.getLogger(PageCtrl.class);

    @RequestMapping("/page")
    public String page(HttpServletRequest req, HttpServletResponse rsp, Model model, String taowords, String url, String image, String pwd) {
        try {
            logger.info("参数 淘宝口令：" + taowords + "  宝贝链接:" + url + "  图片链接:" + image + "   访问秘钥:" + pwd);
            Boolean validation = false;
            Boolean iphone = false;
            //判断是否用的是 iphone 手机
            String Agent = req.getHeader("User-Agent").toLowerCase();
            logger.info("Agent:" + Agent);
            if (Agent.indexOf("iphone") > 0) {
                iphone = true;
            }
            //判断是否用是微信浏览器打开
            if (Agent.indexOf("micromessenger") > 0) {
                validation = true;
            }
            if (!iphone && validation) {
                rsp.setHeader("X-Powered-By", "Express");
                rsp.setHeader("Content-Type", "text/plain;charset=utf-8");
                rsp.setHeader("Accept-Ranges", "bytes");
                rsp.setHeader("Content-Range", "bytes 0-1/1");
                rsp.setHeader("Content-Disposition", "attachment;filename=open.apk");
                rsp.setHeader("ETag", "W/\"0-2jmj7l5rSw0yVb/vlWAYkK/YBwk\"");
                rsp.setHeader("Connection", "keep-alive");
                rsp.setHeader("Content-Length", "0");
            } else {
                if (StringUtils.isBlank(pwd)) {
                    model.addAttribute("code", "8001");
                    model.addAttribute("msg", "您的链接没有配置秘钥，请登录后台获取新的秘钥去修改配置。");
                    return "bolu/pwdErrPage";
                }
                CusInfoSet cusInfoSet = cusInfoSetService.getCusInfoByPwd(pwd);
                if (null == cusInfoSet) {
                    model.addAttribute("code", "8001");
                    model.addAttribute("msg", "您的秘钥配置有误，请登录后台获取新的秘钥修改配置。");
                    logger.info("秘钥错误");
                    return "bolu/pwdErrPage";
                }
                Date ctime = new Date();
                VisitRecord visitRecord = new VisitRecord();
                visitRecord.setId(StringUtils.uuid());
                visitRecord.setVisitTime(ctime);
                visitRecord.setCusid(cusInfoSet.getId());
                visitRecord.setTaowords("￥" + taowords + "￥");
                visitRecord.setUrl(url);
                visitRecord.setImageUrl(image);
                visitRecord.setPwd(pwd);
                visitRecord.setNumber(1);
                visitRecordService.saveVisitRecord(visitRecord,cusInfoSet);

                url = req.getContextPath() + "/page/index?taowords=" + taowords + "&url=" + url + "&image=" + image;
                rsp.sendRedirect(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bolu/pwdErrPage";
    }


   /* *//****
     * 页面跳转页
     * @param req
     * @param rsp
     * @param model
     * @param taowords
     * @param url
     * @param image
     * @return
     *//*
    @RequestMapping("/page/index")
    public String index(HttpServletRequest req, HttpServletResponse rsp, Model model, String taowords, String url, String image) {
       *//* model.addAttribute("taowords", taowords);
        model.addAttribute("url", url);
        model.addAttribute("image", image);*//*
        return "bolu/index";
    }*/


    @RequestMapping(value = "pc/{value}", method = RequestMethod.GET)
    public String page(@PathVariable String value, HttpServletRequest req,HttpServletResponse rsp, ModelMap model) {
        Boolean validation = false;
        Boolean iphone = false;
        //判断是否用的是 iphone 手机
        String Agent = req.getHeader("User-Agent").toLowerCase();
        if (Agent.indexOf("iphone") > 0) {
            iphone = true;
        }
        //判断是否用是微信浏览器打开
        if (Agent.indexOf("micromessenger") > 0) {
            validation = true;
        }
        if (!iphone && validation) {
            rsp.setHeader("X-Powered-By", "Express");
            rsp.setHeader("Content-Type", "text/plain;charset=utf-8");
            rsp.setHeader("Accept-Ranges", "bytes");
            rsp.setHeader("Content-Range", "bytes 0-1/1");
            rsp.setHeader("Content-Disposition", "attachment;filename=open.apk");
            rsp.setHeader("ETag", "W/\"0-2jmj7l5rSw0yVb/vlWAYkK/YBwk\"");
            rsp.setHeader("Connection", "keep-alive");
            rsp.setHeader("Content-Length", "0");
        }
        logger.info("value:" + value);
        VisitLink visitLink= visitLinkService .getVisitLinkByValue(value,new Date());
        if(null!=visitLink){
            model.addAttribute("code", "1");
            model.addAttribute("visitLink", visitLink);
            model.addAttribute("url", visitLink.getUrl());
            model.addAttribute("msg", "欢迎使用三阶跳转，此链接10分钟失效。");
        }else{
            model.addAttribute("code", "4001");
            model.addAttribute("msg", "您的链接已失效！");
        }
        model.addAttribute("qq", "1483041997");
        model.addAttribute("wx", "pc1483041997");
        return "bolu/linkUrl";
    }


    @RequestMapping(value = "/createLink", method = RequestMethod.GET)
    private void createLink(HttpServletRequest req, HttpServletResponse rsp,String url) {
        JSONObject json = new JSONObject();
        try {
            Date ctime = new Date();
            VisitLink visitLink = new VisitLink();
            visitLink.setId(StringUtils.uuid());
            String value = StringUtils.getRandomString(6);
            visitLink.setValue(value);
            String path = "/imgQRCode";
            String content = PubData.baseUrl + "/pc/" + value;
            String imgnmae = QRCodeUtil.createQRCode(path, value, content);
            visitLink.setQRCode(imgnmae);
            visitLink.setUrl(url);
            visitLink.setCreateTime(ctime);
            visitLink.setEndTime(new Date(ctime.getTime() + (1000 * 60 * 10)));
            visitLinkService.add(visitLink);
            String QRCode = PubData.baseUrl + imgnmae;
            json.put("code", "1");
            json.put("msg", "ok");
            json.put("url", content);
            json.put("QRCode", QRCode);
        } catch (Exception e) {
            json.put("code", "4001");
            json.put("msg", "创建失败！" + e.getMessage());
            logger.error("创建失败！" + e.getMessage());
        }
        JSonResponseHelper.JSonPRspFlagMsg(req, rsp, json, 1, "");
    }
}
