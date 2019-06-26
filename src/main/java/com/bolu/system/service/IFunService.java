package com.bolu.system.service;


import com.bolu.system.bo.Custypefun;
import com.bolu.system.bo.Fun;
import com.bolu.system.bo.RoleFun;

import java.util.List;


public interface IFunService extends IBaseService {

	/**
	 * 添加功能菜单
	 * 
	 * @param fun
	 * @return
	 */
	public boolean addFun(Fun fun);

	/**
	 * 修改功能菜单
	 * 
	 * @param fun
	 * @return
	 */
	public boolean updateFun(Fun fun);

	/**
	 * 根据 角色id 查询用户对应的权限功能菜单 （有选中属性）
	 * 
	 * @param roleid
	 * @return
	 */
	public List<Fun> getFunTreeByRoleId(String roleid, Integer module) ;

	/**
	 * 根据 角色id 查询用户对应的权限功能菜单
	 *
	 * @param roleid
	 * @return
	 */
	public List<Fun> getFunTreeByRoleId2(String roleid);

	/***
	 * 根据 商户类型id 查询用户对应的权限功能菜单 （有选中属性）
	 * @param customid
	 * @return
	 */
	public List<Fun> getFunTreeByCustomId(String customid, Integer module);

	/***
	 * 根据 商户类型id 查询用户对应的权限功能菜单
	 * @param customid
	 * @return
	 */
	public List<Fun> getFunTreeByCustomId2(String customid);

	/**
	 * 查询 所有功能菜单
	 * @return
	 */
	public List<Fun> getFunTreeAll();

	/***
	 * 商户类型授权
	 *
	 * @param custypeid
	 *            商户类型id
	 * @param list
	 *            功能ids
	 * @return
	 * @throws Exception
	 */
	public Boolean saveCustomFun(String custypeid, List<Custypefun> list) throws Exception;

	/***
	 * 角色授权
	 * @param roleid
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Boolean saveRoleFun(String roleid, List<RoleFun> list) throws Exception;



	/**
	 *根据模块标志 获取相应的功能列表
	 *
	 * @author pc
	 * @param module
	 * @return
	 * @ctime 2018/12/25
	 */
	public List<Fun> getFunByModule(Integer module, String cusid);

}
