package com.bolu.system.ctrl;


import com.bolu.base.common.CurrentPage;
import com.bolu.base.common.ReqParUtil;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.*;
import com.bolu.system.service.*;
import com.bolu.system.util.PubDataManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/****
 * 视图层 Ctrl 专门用于页面跳转
 *
 * @author lenovo
 *
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/view")
public class ViewCtrl {
    private static final Logger logger = Logger.getLogger(ViewCtrl.class);

    @Autowired
    public IRoleService roleService;
    @Autowired
    public IFunService funService;
    @Autowired
    public IUserService userService;
    @Autowired
    public ICustomService customService;
    @Autowired
    public IIntentionService iIntentionService;
    @Autowired
    public INewsService newsService;
    @Autowired
    private ICusInfoService cusInfoService;
    @Autowired
    private ICusInfoSetService cusInfoSetService;
    @Autowired
    private IVisitRecordService visitRecordService;


    /**
     * 首页
     *
     * @param req
     * @param rsp
     * @param model
     * @return
     * @author pc
     * @ctime 2018/12/3
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest req, HttpServletResponse rsp, Model model) {
        PubDataManager.getInstance().setBModel(model, "1", null, req);
        // 当前登录人用户信息
        User curruser = ReqParUtil.getCurrUser(req);
        CusInfo cusInfo = cusInfoService.get(curruser.getCusid());
        CusInfoSet cusInfoSet = cusInfoSetService.get(curruser.getCusid());
        Integer num=cusInfoSet.getDayNum()-cusInfoSet.getUsedNum();
        cusInfoSet.setNum(num);
        model.addAttribute("cusInfoSet", cusInfoSet);
        model.addAttribute("cusInfo", cusInfo);
        return "system/index";

    }


    /****
     * 商户管理主页
     * @author: pc
     * @param req
     * @param model
     * @param cusInfo
     * @param page
     * @ctime 2018/12/4
     */
    @RequestMapping(value = "cusInfoIndex")
    public String cusInfoIndex(HttpServletRequest req, Model model, CusInfo cusInfo, CurrentPage<CusInfo> page) {
        page.setPageSize(10);
        CurrentPage<CusInfo> currentPage = cusInfoService.getCusInfoPage(page.getPageNumber(), page.getPageSize(), cusInfo);
        // 查找商户类别
        String strSql = "select * from custom where sta=?";
        List<Custom> listCustom = customService.findList(strSql, new Object[]{1});
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("cusInfo", cusInfo);
        model.addAttribute("listCustom", listCustom);
        return "system/cusInfoIndex";
    }


    /****
     * 商户类型管理主页
     * @author: pc
     * @param req
     * @param
     * @param model
     * @param custom
     * @param page
     * @ctime 2018/12/4
     */
    @RequestMapping(value = "customIndex")
    public String customIndex(HttpServletRequest req, Model model, Custom custom, CurrentPage<Custom> page) {
        CurrentPage<Custom> currentPage = customService.getCustomPage(page.getPageNumber(), page.getPageSize(),
                custom);
        model.addAttribute("currentPage", currentPage);
        return "pub/admin/customIndex";
    }

    /***
     * 角色管理主页
     *
     * @param req
     * @param
     * @param model
     * @param
     * @ctime 2018/12/4
     */
    @RequestMapping(value = "roleIndex")
    public String roleIndex(HttpServletRequest req, Model model, Role role, CurrentPage<Role> page) {
        // 当前登录人用户信息
        User curruser = ReqParUtil.getCurrUser(req);
        if (StringUtils.isBlank(role.getCusid())) {
            // 如果当前登录 人的商户id为0 ，说明登录用户是系统用户
            if (StringUtils.isMeaningFul(curruser.getCusid()) && curruser.getCusid().equals("0")) {
                role.setCusid("");
            } else {
                role.setCusid(curruser.getCusid());
            }
        }
        CurrentPage<Role> currentPage = roleService.getRolePage(page.getPageNumber(), page.getPageSize(), role);
        String cusid = "";
        if (!(StringUtils.isMeaningFul(curruser.getCusid()) && curruser.getCusid().equals("0"))) {
            cusid = curruser.getCusid();
        }
        CusInfo cusInfo = cusInfoService.get(cusid);

        model.addAttribute("role", role);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("cusInfo", cusInfo);
        return "pub/admin/roleIndex";
    }


    /***
     * 用户管理主页
     * @author: pc
     * @param req
     * @param model
     * @param user
     * @param page
     * @ctime 2018/12/4
     */
    @RequestMapping(value = "userIndex")
    public String userIndex(HttpServletRequest req, Model model, User user, CurrentPage<User> page) {
        // 当前登录人用户信息
        User curruser = ReqParUtil.getCurrUser(req);
        if (StringUtils.isBlank(user.getCusid())) {
            // 如果当前登录 人的商户id为0 ，说明登录用户是 超级管理员
            if (StringUtils.isMeaningFul(curruser.getCusid()) && curruser.getCusid().equals("0")) {
                user.setCusid("");
            } else {
                user.setCusid(curruser.getCusid());
            }
        }
        CurrentPage<User> currentPage = userService.getUserPage(page.getPageNumber(), page.getPageSize(), user);
        String cusid = "";
        if (!(StringUtils.isMeaningFul(curruser.getCusid()) && curruser.getCusid().equals("0"))) {
            cusid = curruser.getCusid();
        }
        List<Role> roleList = roleService.getRoleListByCusId(cusid, 1);
        CusInfo cusInfo = cusInfoService.get(cusid);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("cusInfo", cusInfo);
        model.addAttribute("roleList", roleList);
        model.addAttribute("cu", user);

        return "pub/admin/userIndex";
    }


    /***
     * 功能授权页面
     * @author: pc
     * @param req
     * @param model
     * @param type
     * @ctime 2018/12/4
     */
    @RequestMapping("funAuthority")
    public String funAuthority(HttpServletRequest req, Model model, String type, String id) {
        List<Fun> funMenu = null;
        List<Fun> funMenuTwo = null;
        if (StringUtils.isMeaningFul(type) && type.trim().equals("role")) {
            Role role = (Role) roleService.get(id);
            funMenu = funService.getFunTreeByRoleId(id, 1);
            funMenuTwo = funService.getFunTreeByRoleId(id, 2);
            model.addAttribute("title", "角色名称：" + role.getRolename());
            PubDataManager.getInstance().setBModel(model, "6", "601", req);
        }
        if (StringUtils.isMeaningFul(type) && type.trim().equals("custom")) {
            Custom custom = (Custom) customService.get(id);
            funMenu = funService.getFunTreeByCustomId(id, 1);
            funMenuTwo = funService.getFunTreeByCustomId(id, 2);
            model.addAttribute("title", "商户类型名称：" + custom.getTypename());
            PubDataManager.getInstance().setBModel(model, "2", "201", req);
        }
        model.addAttribute("type", type);
        model.addAttribute("id", id);
        model.addAttribute("funMenu", funMenu);
        model.addAttribute("funMenuTwo", funMenuTwo);
        return "pub/admin/authority";
    }


    /***
     * 账户基本信息 主页面
     * @author: pc
     * @ctime 2018/12/4
     */
    @RequestMapping(value = "accountIndex")
    public String accountIndex(HttpServletRequest req, Model model) {
        // 当前登录账户信息
        User currUser = ReqParUtil.getCurrUser(req);
        // 商户信息
        CusInfo cusInfo = cusInfoService.get(currUser.getCusid());
        CusInfoSet cusInfoSet = cusInfoSetService.get(currUser.getCusid());
        // 商户套餐信息
        model.addAttribute("cusInfo", cusInfo);
        model.addAttribute("cusInfoSet", cusInfoSet);
        return "system/accountIndex";
    }


    /***
     * 我的访问记录
     * @author: pc
     * @ctime 2018/12/4
     */
    @RequestMapping(value = "visitIndex")
    public String visitIndex(HttpServletRequest req, CurrentPage<VisitRecord> page, Model model, VisitRecord visitRecord) {
        // 当前登录人用户信息
        User curruser = ReqParUtil.getCurrUser(req);
        visitRecord.setCusid(curruser.getCusid());
        CurrentPage<VisitRecord> currentPage = visitRecordService.getVisitRecordPage(page.getPageNumber(), page.getPageSize(), visitRecord);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("visitRecord", visitRecord);
        return "system/visitIndex";
    }


    /***
     * 我的访问记录
     * @author: pc
     * @ctime 2018/12/4
     */
    @RequestMapping(value = "checkUrlTool")
    public String checkUrlTool(HttpServletRequest req, CurrentPage<VisitRecord> page, Model model, VisitRecord visitRecord) {

        return "system/checkUrlTool";
    }

}
