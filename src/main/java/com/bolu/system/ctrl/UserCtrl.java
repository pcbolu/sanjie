package com.bolu.system.ctrl;


import com.bolu.base.common.JSonResponseHelper;
import com.bolu.base.common.ReqParUtil;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.User;
import com.bolu.system.service.IRoleService;
import com.bolu.system.service.IUserService;
import com.bolu.system.util.PubDataManager;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserCtrl {

	private static final Logger logger = Logger.getLogger(RoleCtrl.class);

	@Autowired
	public IUserService userService;
	@Autowired
	public IRoleService roleService;

	/***
	 * 进入登录界面
	 * @author: pc
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest req) {
		model.addAttribute("SysName", PubDataManager.sysname);
		return "system/login";
	}

	/**
	 * 保存用户信息
	 * @author pc
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "saveUser")
	public void saveUser(HttpServletRequest req, HttpServletResponse rsp, User user) {
		User currUser = ReqParUtil.getCurrUser(req);
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isMeaningFul(user.getId())) {
				if (userService.updateUser(user, req)) {
					json.put("status", true);
					json.put("msg", "修改成功");
				} else {
					json.put("status", false);
					json.put("msg", "修改失败！");
				}
			} else {
				// 添加用户 默认添加 与 当前登录人所在商户 保持一致
				//添加的用户的商户id 应该与 所选 角色 的商户id  保持一致
				user.setCusid(currUser.getCusid());
				// 设置默认密码 123
				user.setPassword("123");
				if (userService.addUser(user)) {
					json.put("status", true);
					json.put("msg", "添加成功");
				} else {
					json.put("status", false);
					json.put("msg", "添加失败！");
					logger.info("添加失败");
				}
			}
		} catch (Exception e) {
			json.put("status", false);
			json.put("msg", "保存失败！" + e.getMessage());
			logger.error("保存失败" + e.getMessage());
		}
		JSonResponseHelper.JSonResponse(rsp, json);
	}


	/**
	 *删除用户信息
	 * @author pc
	 * @param req
	 * @param rsp
	 * @param id
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "deleteUser")
	public void deleteUser(HttpServletRequest req, HttpServletResponse rsp, String id) {
		JSONObject json = new JSONObject();
		try {
			if (userService.del(id)) {
				json.put("status", true);
				json.put("msg", "删除成功");
			} else {
				json.put("status", false);
				json.put("msg", "删除失败！");
				logger.info("删除失败");
			}
		} catch (Exception e) {
			json.put("status", false);
			json.put("msg", "删除失败！" + e.getMessage());
			logger.error("删除失败" + e.getMessage());
		}
		JSonResponseHelper.JSonResponse(rsp, json);
	}



	/**
	 *修改密码
	 * @author pc
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "updatePassword")
	public void updatePassword(HttpServletRequest req, HttpServletResponse rsp, User user) {
		JSONObject json = new JSONObject();
		try {
			User u = userService.getUserByuserNameAndPassword(user);
			if (null == u) {
				json.put("status", false);
				json.put("msg", "修改失败！密码错误!");
				logger.info("修改失败！密码错误!");
				JSonResponseHelper.JSonResponse(rsp, json);
				return;
			}
			if (!(StringUtils.isMeaningFul(user.getPone()) && StringUtils.isMeaningFul(user.getPtwo())
					&& user.getPone().trim().equals(user.getPtwo().trim()))) {
				json.put("status", false);
				json.put("msg", "密码输入不一致，请重新输入！");
				logger.info("密码输入不一致，请重新输入！");
				JSonResponseHelper.JSonResponse(rsp, json);
				return;
			}
			user.setPassword(user.getPone());
			if (userService.updateUser(user, req)) {
				json.put("status", true);
				json.put("msg", "修改成功");
			} else {
				json.put("status", false);
				json.put("msg", "修改失败！");
				logger.info("密码修改失败！");
			}
		} catch (Exception e) {
			json.put("status", false);
			json.put("msg", "密码修改失败！" + e.getMessage());
			logger.error("密码修改失败！" + e.getMessage());
		}
		JSonResponseHelper.JSonResponse(rsp, json);
	}


	/**
	 * 检查 用户名唯一 验证接口
	 * @author pc
	 * @param req
	 * @param rsp
	 * @param username
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "checkUserName")
	public void checkUserName(HttpServletRequest req, HttpServletResponse rsp, String username) {
		JSONObject json = new JSONObject();

		String userid = ReqParUtil.getReqParameter(req, "userid");

		try {
			// List<user> list= userService.getUserByUserName(username);
			List<User> list = userService.checkUserNameOnly(username, userid);
			if (null != list && list.size() > 0) {
				json.put("valid", false);
				json.put("msg", "用户名已存在！");
			} else {
				json.put("valid", true);
				json.put("msg", "用户名通过！");
			}
		} catch (Exception e) {
			json.put("valid", false);
			json.put("msg", "检验失败！" + e.getMessage());
			logger.error("检验失败！" + e.getMessage());
		}
		JSonResponseHelper.JSonResponse(rsp, json);
	}

	/**
	 * @param req
	 * @param rsp
	 * @param model
	 * @return
	 * @author: pc
	 * @description 注销/退出
	 * @date: 2018/11/30 15:51
	 */
	@RequestMapping("/loginout")
	public String loginout(HttpServletRequest req, HttpServletResponse rsp, Model model) {
		User user = ReqParUtil.getCurrUser(req);
		// 清除 session 中的所有数据
		Enumeration<?> em = req.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			req.getSession().removeAttribute(em.nextElement().toString());
		}
		model.addAttribute("SysName", PubDataManager.sysname);
		return "system/login";
	}


}
