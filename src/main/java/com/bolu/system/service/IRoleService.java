package com.bolu.system.service;


import com.bolu.base.common.CurrentPage;
import com.bolu.system.bo.Role;

import java.util.List;

public interface IRoleService extends IBaseService<Role> {
	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 */
	public boolean addRole(Role role) throws Exception;

	/***
	 * 修改用户
	 * 
	 * @param role
	 * @return
	 */
	public boolean updateRole(Role role);

	/***
	 * 分页查询角色信息
	 * 
	 * @param nowPage
	 * @param pageSize
	 * @param role
	 * @return
	 */
	public CurrentPage<Role> getRolePage(int nowPage, int pageSize, Role role);

	/**
	 * 根据商户id 查询 角色信息
	 * 
	 * @param cusid
	 * @return
	 */
	public List<Role> getRoleListByCusId(String cusid, Integer sta);

	/**
	 * 根据状态查询决角色
	 * 
	 * @param sta
	 * @return
	 */
	public List<Role> getUserListBySta(Integer sta);
	
	/**
	 * 删除角色信息
	 * @param roleid
	 * @return
	 */
	public Boolean delRole(String roleid);

	/****
	 * 根据角色id查询，查询改角色是否是有效角色
	 * @param roleid
	 * @return
	 */
	public List<Role> getRoleById(String roleid);

	/**
	 * 查询商户的生成的默认角色
	 * @param cusid
	 * @return
	 */
	public Role getRoleMoRenByCusid(String cusid);

	/**
	 *查询商户 非默认角色
	 * @author pc
	 * @param cusid
	 * @return
	 * @ctime 2018/12/11
	 */
	public List<Role> getRoleNotMoRenByCusid(String cusid);

}
