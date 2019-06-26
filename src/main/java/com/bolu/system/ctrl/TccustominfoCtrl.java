package com.bolu.system.ctrl;


import com.bolu.base.common.JSonResponseHelper;
import com.bolu.system.bo.CusInfo;
import com.bolu.system.service.ICustomService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/tccustominfo")
public class TccustominfoCtrl {


	private static final Logger logger = Logger.getLogger(RoleCtrl.class);


	@Autowired
	public ICustomService customService;

	/****
	 *
	 * 保存账户信息
	 * @author: pc
	 * @param rsp
	 * @param
	 * @param
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "/saveAccount")
	public void saveAccount(HttpServletResponse rsp, CusInfo cusInfo) {
		JSONObject json = new JSONObject();
		try {
			// 修改商户信息
			//Boolean iscb = custombaseService.updateCusstombase(custombase);
			Boolean isci = false;
			if (isci) {
				json.put("status", true);
				json.put("msg", "保存成功！");
			} else {
				json.put("status", true);
				json.put("msg", "保存失败！");
				logger.info("保存失败");
			}
		} catch (Exception e) {
			json.put("status", true);
			json.put("msg", "保存失败" + e.getMessage());
			logger.error("保存失败" + e.getMessage());
		}

		JSonResponseHelper.JSonResponse(rsp, json);
	}
}
