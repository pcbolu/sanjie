package com.bolu.system.ctrl;


import com.bolu.base.common.JSonResponseHelper;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.Custypefun;
import com.bolu.system.bo.RoleFun;
import com.bolu.system.service.IFunService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/fun")
public class FunCtrl {

	private static final Logger logger = Logger.getLogger(FunCtrl.class);

	@Autowired
	public IFunService funService;

	/***
	 * 保存 授权
	 * @author: pc
	 * @param rsp
	 * @param type
	 * @param id
	 * @param jsonstr
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "saveAuthority")
	public void saveAuthority(HttpServletResponse rsp, String type, String id, String jsonstr) {
		ObjectMapper mapper = new ObjectMapper();
		JSONObject json = new JSONObject();
		try {
			// 角色授权
			if (StringUtils.isMeaningFul(type) && type.trim().equals("role")) {
				JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, RoleFun.class);
				List<RoleFun> list = mapper.readValue(jsonstr, javaType);
				Boolean isSave = funService.saveRoleFun(id, list);
				if (isSave) {
					json.put("status", true);
					json.put("msg", "授权成功！");

				} else {
					json.put("status", false);
					json.put("msg", "授权失败！");
					logger.error("授权失败！");
				}
				System.out.println(list.toString());
			} else {
				// 商户类型授权
				JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Custypefun.class);
				List<Custypefun> list = mapper.readValue(jsonstr, javaType);
				System.out.println(list.toString());
				Boolean isSave = funService.saveCustomFun(id, list);
				if (isSave) {
					json.put("status", true);
					json.put("msg", "授权成功！");
				} else {
					json.put("status", false);
					json.put("msg", "授权失败！");
					logger.error("授权失败！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", false);
			json.put("msg", "授权失败！"+e.getMessage());
			logger.error("授权失败！");
		}
		JSonResponseHelper.JSonResponse(rsp, json);
	}
}
