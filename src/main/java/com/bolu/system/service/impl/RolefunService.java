package com.bolu.system.service.impl;


import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.RoleFun;
import com.bolu.system.dao.IRolefunDao;
import com.bolu.system.service.IRolefunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RolefunService extends BaseService implements IRolefunService {
	private IRolefunDao rolefunDao;

	@Autowired
	private void setDao(IRolefunDao rolefunDao) {
		this.rolefunDao = rolefunDao;
		super.setBaseDao(rolefunDao);
	}

	/***
	 * 添加角色功能记录
	 * @author pc
	 * @param rolefun
	 * @return
	 * @throws Exception
	 * @ctime 2018/12/4
	 */
	public Boolean addRolefun(RoleFun rolefun) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == rolefun.getFunid()) {
			throw new RuntimeException("功能菜单不能为空！");
		}
		if (null == rolefun.getEntire()) {
			throw new RuntimeException("全选状态不能为空！");
		}
		map.put("id", StringUtils.uuid());
		map.put("roleid", rolefun.getRoleid());
		map.put("funid", rolefun.getFunid());
		map.put("entire", rolefun.getEntire());
		if (rolefunDao.add(map)) {
			return true;
		}
		return false;
	}

	/**
	 * 查询商户角色 线下权限
	 *
	 * @author pc
	 * @param cusid 商户id
	 * @return
	 * @ctime 2018/12/25
	 */
	public List<RoleFun> getUnderRoleFunByCusid(String cusid){
		String sql="select rf.* from Rolefun rf left join fun fun on rf.funid=fun.id   where rf.roleid in (" +
				"select id from role where cusid=?" +
				") and fun.module=2";
		List<RoleFun>  rolefunList=rolefunDao.findList(sql,new Object[]{cusid});
		return rolefunList;
	}
		
}
