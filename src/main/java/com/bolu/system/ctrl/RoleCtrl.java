package com.bolu.system.ctrl;


import com.bolu.base.common.JSonResponseHelper;
import com.bolu.base.common.ReqParUtil;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.Role;
import com.bolu.system.bo.User;
import com.bolu.system.service.IRoleService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/role")
public class RoleCtrl {

	private static final Logger logger = Logger.getLogger(RoleCtrl.class);

	@Autowired
	private IRoleService roleService;

	/***
	 * 保存角色
	 * @author pc
	 * @param req
	 * @param rsp
	 * @param role
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "saveRole")
	public void saveRole(HttpServletRequest req, HttpServletResponse rsp, Role role) {
		// 当前登录用户
		User user = ReqParUtil.getCurrUser(req);
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isMeaningFul(role.getId())) {
				if (roleService.updateRole(role)) {
					json.put("status", true);
					json.put("msg", "修改成功");
				} else {
					json.put("status", false);
					json.put("msg", "修改失败！");
					logger.info("修改失败！");
				}
			} else {
				// 添加角色是 设置商户id 与当前登录人id 一致
				role.setCusid(user.getCusid());
				if (roleService.addRole(role)) {
					json.put("status", true);
					json.put("msg", "添加成功");
				} else {
					json.put("status", false);
					json.put("msg", "添加失败！");
					logger.info("添加失败！");
				}
			}
		} catch (Exception e) {
			json.put("status", false);
			json.put("msg", "保存失败！" + e.getMessage());
			logger.error("保存失败！" + e.getMessage());
		}
		JSonResponseHelper.JSonResponse(rsp, json);
	}

	/**
	 * 删除角色
	 * @author pc
	 * @param id
	 * @return
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "deleteRole")
	public void deleteRole(HttpServletRequest req, HttpServletResponse rsp, String id) {
		JSONObject json = new JSONObject();
		try {
			if (roleService.delRole(id)) {
				json.put("status", true);
				json.put("msg", "删除成功");
			} else {
				json.put("status", false);
				json.put("msg", "删除失败！");
				logger.info("删除失败！");
			}
		} catch (Exception e) {
			json.put("status", false);
			json.put("msg", "删除失败！" + e.getMessage());
			logger.error("删除失败！" + e.getMessage());
		}
		JSonResponseHelper.JSonResponse(rsp, json);
	}
}
