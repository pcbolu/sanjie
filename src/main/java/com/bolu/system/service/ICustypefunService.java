package com.bolu.system.service;


import com.bolu.system.bo.Custypefun;

import java.util.List;

public interface ICustypefunService extends IBaseService {

	/***
	 * 添加
	 * 
	 * @param csutypefun
	 * @return
	 */
	public Boolean addCustypefun(Custypefun csutypefun) throws Exception;

	/**
	 * 根据商户类型id 查找商户类型权限
	 * 
	 * @param custypeid
	 * @return
	 */
	public List<Custypefun> getCustypefunByCustomId(String custypeid);

}
