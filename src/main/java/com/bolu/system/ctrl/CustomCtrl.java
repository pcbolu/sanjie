package com.bolu.system.ctrl;


import com.bolu.base.common.JSonResponseHelper;
import com.bolu.system.bo.Custom;
import com.bolu.system.bo.Fun;
import com.bolu.system.service.IFunService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/custom")
public class CustomCtrl {

	@Autowired
	public IFunService funService;

	/**
	 *商户类型 对应的功能菜单 树形结构
	 * @author pc
	 * @param req
	 * @param rsp
	 * @param custom
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "/funTreeCustom")
	public void funTreeCustom(HttpServletRequest req, HttpServletResponse rsp, Custom custom) {
		List<Fun> funTree = funService.getFunTreeByCustomId(custom.getId().toString(),1);
		JSONObject json = new JSONObject();
		json.put("tree", funTree);
		JSonResponseHelper.JSonResponse(rsp, json);
	}
}
