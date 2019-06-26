package com.bolu.system.util;

import com.bolu.base.common.ReqParUtil;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.Fun;
import com.bolu.system.bo.User;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PubDataManager {
    private static final Logger logger = Logger.getLogger(PubDataManager.class);

    public static String sysname = "三阶维度";
    private static PubDataManager instance;


    public static PubDataManager getInstance() {
        if (instance == null) {
            instance = new PubDataManager();
        }
        return instance;
    }

    /**
     * 设置后台显示菜单内容
     *
     * @param model
     * @param pfid
     * @param req
     */
    public synchronized void setBModel(Model model, Integer pfid, HttpServletRequest req) {
        List<Fun> li = getUMenu(req);
        User currUser = getCurrUser(req);
        model.addAttribute("u", currUser);
        model.addAttribute("MenuList", li);
        model.addAttribute("pfid", pfid);
        model.addAttribute("SysName", sysname);
    }

    /**
     * 设置后台显示菜单内容
     *
     * @param model
     * @param pfid  父级菜单id
     * @param fid   子级菜单id
     * @return
     * @author pc
     * @ctime 2018/12/4
     */
    public synchronized void setBModel(Model model, String pfid, String fid, HttpServletRequest req) {
        List<Fun> li = getUMenu(req);
        User currUser = getCurrUser(req);

        model.addAttribute("u", currUser);
        model.addAttribute("MenuList", li);

        Map<String, String> button = new HashMap<String, String>();

        if (StringUtils.isMeaningFul(pfid)) {
            if (StringUtils.isBlank(fid)) {
                fid="0";
            }
            for (Fun pfun : li) {
                if (pfid.equals(pfun.getId())) {
                    pfun.setChecked(1);
                } else {
                    pfun.setChecked(0);
                }
                // 获取 二级 菜单
                List<Fun> children = pfun.getSubfun();
                if (null != children && children.size() > 0) {
                    for (Fun cfun : children) {
                        //如果  fid 为空 说明 没有三级菜单
                        if (null == fid && null != pfid) {
                            if (cfun.getSubitem().trim().equals("button"))
                                button.put(cfun.getFcode(), cfun.getName());
                        }
                        // 获取父菜单id等于pfid  和 子级菜单id等于 fid   菜单下的功能按钮
                        else if (null != fid && null != pfid && pfun.getId().equals(pfid) && cfun.getId().equals(fid)) {
                            cfun.setChecked(1);
                            // 获取 三级 菜单（按钮）
                            List<Fun> suns = cfun.getSubfun();
                            if (null != suns && suns.size() > 0) {
                                for (Fun sfun : suns) {
                                    if (sfun.getSubitem().trim().equals("button"))
                                        button.put(sfun.getFcode(), sfun.getName());
                                }
                            }
                        } else {
                            cfun.setChecked(0);
                        }
                    }
                }
            }
        } else {
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
                           if(StringUtils.isMeaningFul(ctrl)&&StringUtils.isMeaningFul(action)&&ctrl.equals(cfun.getControl())&&action.equals(cfun.getAction())){
                                pfid=cfun.getParentid();
                                fid=cfun.getId();
                                pChecked=true;
                                cfun.setChecked(1);
                                // 获取 三级 菜单（按钮）
                                List<Fun> suns = cfun.getSubfun();
                                if (null != suns && suns.size() > 0) {
                                    for (Fun sfun : suns) {
                                        if (sfun.getSubitem().trim().equals("button")&&!(sfun.getFcode().equals("show")))
                                            button.put(sfun.getFcode(), sfun.getName());
                                    }
                                }
                            }else{
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
        }
        model.addAttribute("pfid", pfid);
        model.addAttribute("fid", fid);
        //权限按钮
        model.addAttribute("button", button);
        model.addAttribute("SysName", sysname);
    }


    /**
     * 设置后台显示菜单内容
     *
     * @param model
     * @param req
     * @return
     * @author pc
     * @ctime 2018/12/4
     */
    public synchronized void setBModel(Model model, HttpServletRequest req) {
        List<Fun> li = getUMenu(req);
        //一级菜单id
        Integer pfid = StringUtils.isMeaningFul(ReqParUtil.getReqParameter(req, "pfid")) ? Integer.parseInt(ReqParUtil.getReqParameter(req, "pfid")) : null;
        //二级菜单id
        Integer fid = StringUtils.isMeaningFul(ReqParUtil.getReqParameter(req, "fid")) ? Integer.parseInt(ReqParUtil.getReqParameter(req, "fid")) : null;
        User currUser = getCurrUser(req);
        model.addAttribute("u", currUser);
        model.addAttribute("MenuList", li);
        model.addAttribute("pfid", pfid);
        model.addAttribute("fid", fid);
        model.addAttribute("SysName", sysname);
    }


    /**
     * 获取用户后台菜单
     *
     * @param req
     * @return
     * @author pc
     * @ctime 2018/12/4
     */
    public static List<Fun> getUMenu(HttpServletRequest req) {
        Object obj = ReqParUtil.SessionGetAttr(req, "usermenu");
        if (obj == null)
            return null;
        else {
            List<Fun> tmem = (List<Fun>) obj;
            return tmem;
        }
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


}
