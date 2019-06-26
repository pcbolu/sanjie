package com.bolu.system.interceptor;

import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.Fun;
import com.bolu.system.bo.User;
import com.bolu.system.util.PubDataManager;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pc
 * @date 2019/5/17
 */
public class ViewInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(ViewInterceptor.class);

    /**
     * 前置处理
     *
     * @author pc
     * @param request
     * @param response
     * @param handler
     * @return
     * @ctime 2019/5/17
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        setBModel(request);
        //System.out.println("---------------preHandle---------------");
        return true;
    }

    /**
     * 后置处理
     *
     * @author pc
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @return
     * @ctime 2019/5/17
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /*setBModel(request);*/
        //System.out.println("---------------postHandle---------------");
    }


    /**
     * 请求竣工后再处理（发送页面跳转）
     *
     * @author pc
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     * @ctime 2019/5/17
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //setBModel(request);
        //System.out.println("---------------afterCompletion---------------");
    }


    /**
     * 设置后台显示菜单内容
     *
     * @return
     * @author pc
     * @ctime 2018/12/4
     */
    public synchronized void setBModel(HttpServletRequest req) {
        String SysName="三阶维度";
        List<Fun> li = PubDataManager.getUMenu(req);
        User currUser = PubDataManager.getCurrUser(req);
        req.setAttribute("u", currUser);
        req.setAttribute("MenuList", li);
        Map<String, String> button = new HashMap<String, String>();
        String pfid="";
        String fid="";
        String uri = req.getRequestURI();
        String[] urlArr = uri.split("/");
        String ctrl = "";
        String action = "";
        if (urlArr.length == 4) {
            ctrl = urlArr[2];
            action = urlArr[3];
        }
        if (null != li) {
            for (Fun pfun : li) {
                // 获取 二级 菜单
                List<Fun> children = pfun.getSubfun();
                if (null != children && children.size() > 0) {
                    Boolean pChecked=false;
                    for (Fun cfun : children) {
                        if (StringUtils.isMeaningFul(ctrl) && StringUtils.isMeaningFul(action) && ctrl.equals(cfun.getControl()) && action.equals(cfun.getAction())) {
                            pfid = cfun.getParentid();
                            fid = cfun.getId();
                            SysName=cfun.getName();
                            pChecked=true;
                            cfun.setChecked(1);
                            String apptype=cfun.getApptype();
                           /* logger.info("应用类型编码:"+apptype);*/
                            req.setAttribute("apptype", apptype);
                            // 获取 三级 菜单（按钮）
                            List<Fun> suns = cfun.getSubfun();
                            if (null != suns && suns.size() > 0) {
                                for (Fun sfun : suns) {
                                    if (sfun.getSubitem().trim().equals("button") && !(sfun.getFcode().equals("show")))
                                        button.put(sfun.getFcode(), sfun.getName());
                                }
                            }
                        } else {
                            cfun.setChecked(0);
                        }
                    }
                    if(pChecked){
                        pfun.setChecked(1);
                    }else {
                        pfun.setChecked(0);
                    }
                }
            }
        }
        req.setAttribute("pfid", pfid);
        req.setAttribute("fid", fid);
        req.setAttribute("button", button);
        req.setAttribute("SysName", SysName);
    }
}
