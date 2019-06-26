package com.bolu.system.service;


import com.bolu.system.bo.RoleFun;

import java.util.List;

public interface IRolefunService extends IBaseService {
	
	/***
	 * 添加
	 * @param rolefun
	 * @return
	 */
	public Boolean  addRolefun(RoleFun rolefun) throws Exception;

	/**
	 *查询商户线下功能的 所有角色权限
	 *
	 * @author pc
	 * @param cusid 商户id
	 * @return
	 * @ctime 2018/12/25
	 */
	public List<RoleFun> getUnderRoleFunByCusid(String cusid);

}
