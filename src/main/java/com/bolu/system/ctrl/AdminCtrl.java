package com.bolu.system.ctrl;


import com.bolu.base.common.JSonResponseHelper;
import com.bolu.base.common.ReqParUtil;
import com.bolu.system.bo.Fun;
import com.bolu.system.bo.User;
import com.bolu.system.service.ICustomService;
import com.bolu.system.service.IFunService;
import com.bolu.system.service.IUserService;
import com.bolu.system.util.PubDataManager;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminCtrl {
    private static final Logger logger = Logger.getLogger(AdminCtrl.class);

    @Autowired
    public IFunService funService;
    @Autowired
    public IUserService userService;
    @Autowired
    public ICustomService customService;




    /**
     * 用户登录
     *
     * @param req
     * @param rsp 2018/12/3
     * @author pc
     */
    @RequestMapping("/loginjson")
    @ResponseBody
    public void loginjson(HttpServletRequest req, HttpServletResponse rsp) {
        JSONObject json = new JSONObject();
        String username = ReqParUtil.getReqParameter(req, "userName");
        String password = ReqParUtil.getReqParameter(req, "passWord");
        // 登录验证 返回user 对象
        Map<String, Object> userMap = userService.loginUser(username, password);
        // 登录成功！
        if (userMap.get("status").toString().equals("success")) {
            User user = (User) userMap.get("user");
            // 将用户信息放入session 中
            ReqParUtil.SessionSetAttr(req, "user", user); // 用户信息放入session 中

            List<Fun> li = funService.getFunTreeByRoleId2(user.getRoleid());
            // 将用户对应的功能菜单放入session 中
            ReqParUtil.SessionSetAttr(req, "usermenu", li);

            json.put("status", true);
            json.put("code", "success");
            json.put("msg", "登录成功！");

        } else if (userMap.get("status").toString().equals("NoUserName")) {
            json.put("status", false);
            json.put("code", "NoUserName");
            json.put("msg", "用户名不存在！");
            logger.info("用户名不存在！");
        } else if (userMap.get("status").toString().equals("NoRole")) {
            json.put("status", false);
            json.put("code", "NoRole");
            json.put("msg", "用户角色无效！");
            logger.info("用户角色无效！");
        } else {
            json.put("status", false);
            json.put("code", "PasswordError");
            json.put("msg", "密码错误！");
            logger.info("密码错误！");
        }
        JSonResponseHelper.JSonResponse(rsp, json);
    }






    /***
     * 404 页面
     * @author: pc
     * @return
     */
    @RequestMapping("errorIndex")
    public String errorIndex(HttpServletRequest req, HttpServletResponse rsp, Model model) {
        model.addAttribute("SysName", PubDataManager.sysname);
        return "/base/404";
    }

}
