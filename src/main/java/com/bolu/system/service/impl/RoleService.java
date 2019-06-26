package com.bolu.system.service.impl;


import com.bolu.base.common.CurrentPage;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.Role;
import com.bolu.system.bo.RoleFun;
import com.bolu.system.bo.User;
import com.bolu.system.dao.IRoleDao;
import com.bolu.system.service.IRoleService;
import com.bolu.system.service.IRolefunService;
import com.bolu.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RoleService extends BaseService<Role> implements IRoleService {
	private IRoleDao roleDao;
	
	@Autowired
	public IRolefunService rolefunService;
	
	@Autowired 
	public IUserService userService;

	@Autowired
	private void setDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
		super.setBaseDao(roleDao);
	}

	/***
	 * 添加角色
	 * @author pc
	 * @param role
	 * @return
	 * @ctime 2018/12/4
	 */
	public boolean addRole(Role role) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(role.getId())) {
			role.setId(StringUtils.uuid());
		}
		if (StringUtils.isBlank(role.getRolename())) {
			throw new RuntimeException("角色名称不能为空！");
		}
		if (StringUtils.isBlank(role.getCusid())) {
			throw new RuntimeException("商户不能为空！");
		}
		if (null == role.getSta()) {
			role.setSta(1);
		}
		if(null==role.getIsdefault()) {
			role.setIsdefault(0);
		}
		map.put("id", role.getId());
		map.put("rolename", role.getRolename());
		map.put("cusid", role.getCusid());
		map.put("sta", role.getSta());
		map.put("isdefault", role.getIsdefault());
		if (roleDao.add(map)) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * 修改角色信息
	 * @author pc
	 * @param role
	 * @return
	 * @ctime 2018/12/4
	 */
	public boolean updateRole(Role role) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isMeaningFul(role.getRolename())) {
			map.put("rolename", role.getRolename());
		}
		if (StringUtils.isMeaningFul(role.getCusid())) {
			map.put("cusid", role.getCusid());
		}
		if (null != role.getSta()) {
			map.put("sta", role.getSta());
		}
		if (roleDao.edit(map, role.getId())) {
			return true;
		}
		return false;
	}

	/***
	 * 根据状态查询 角色信列表
	 * @author pc
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Role> getUserListBySta(Integer sta) {
		String sql = "select * from Role where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if (null != sta) {
			sql += " and sta=?";
			params.add(sta);
		}
		List<Role> roleList = roleDao.findList(sql, params.toArray());
		return roleList;
	}

	/***
	 * 根据商户id查询 角色信列表
	 * @author pc
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Role> getRoleListByCusId(String cusid, Integer sta) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from Role where 1=1 ";
		if (StringUtils.isMeaningFul(cusid)) {
			sql += "and cusid=? ";
			params.add(cusid);
		}
		if(null!=sta) {
			sql += "and sta=? ";
			params.add(sta);
		}
		List<Role> roleList = roleDao.findList(sql, params.toArray());
		return roleList;
	}

	/***
	 * 分页查询
	 * @author pc
	 * @return
	 * @ctime 2018/12/4
	 */
	public CurrentPage<Role> getRolePage(int nowPage, int pageSize, Role role) {
		List<Object> param = new ArrayList<Object>();
		String strQ = "select role.* ,(select GROUP_CONCAT(funid) from Rolefun where roleid=role.id) funids, ifnull(cb.cusname,'系统角色') cusname"
				+ " from Role role " + " left join CusInfo cb" + " on role.cusid=cb.id" + " where 1=1 ";
		String strC = "select count(role.id) from Role role" + " left join CusInfo cb" + " on role.cusid=cb.id"
				+ " where 1=1 ";
		if (StringUtils.isMeaningFul(role.getCusid())) {
			strC += " and role.cusid =? ";
			strQ += " and role.cusid =? ";
			param.add(role.getCusid());
		}
		// 查询条件
		if (StringUtils.isMeaningFul(role.getRolename())) {
			strC += " and role.rolename like '%" + role.getRolename().trim() + "%'";
			strQ += " and role.rolename like '%" + role.getRolename().trim() + "%'";
		}
		strQ+="order by role.cusid, role.rolename desc ";
		CurrentPage<Role> currentPage = roleDao.getPage(strC, strQ, param.toArray(), nowPage, pageSize);
		return currentPage;
	}
	
	/***
	 * 根据id删除角色
	 * @author pc*
	 * @param roleid
	 * @return
	 * @ctime 2018/12/4
	 */
	@Transactional
	public Boolean delRole(String roleid) {
		List<User>    userList=userService.getUserByRoleId(roleid);
		if(null!=userList&&userList.size()>0) {
			 throw new RuntimeException("角色下关联的其它账户！");
		}
		String sql=" select * from Rolefun where roleid=? ";
		List<Object> params= new ArrayList<Object>();
		params.add(roleid);
	    List<RoleFun>	 list=rolefunService.findList(sql, params.toArray());
	    //删除角色 也要删除角色下的权限记录
	    for(RoleFun rolefun:list) {
	    	if(!rolefunService.del(rolefun.getId())) {
	    		throw new RuntimeException("删除权限失败！");
	    	}
	    }
	   if(! roleDao.del(roleid)) {
		   throw new RuntimeException("删除角色失败！");
	   }
		return true;
	}

	/****
	 * 根据角色id查询，查询改角色是否是有效角色
	 * @author pc
	 * @param roleid
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Role> getRoleById(String roleid){
		String sql="select * from Role where sta=1 and id=?";
		return roleDao.findList(sql,new Object[]{roleid});
	}


	/**
	 *查询商户的生成的默认角色
	 *
	 * @author pc
	 * @param cusid
	 * @return
	 * @ctime 2018/12/11
	 */
	public Role getRoleMoRenByCusid(String cusid){
		String sql="select * from Role where isdefault=1 and cusid=?";
		List<Role> roleList =roleDao.findList(sql, new Object[]{cusid});
		if(null!=roleList&&roleList.size()>0){
			return  roleList.get(0);
		}
		return null;
	}

	/**
	 *查询商户 非默认角色
	 * @author pc
	 * @param cusid
	 * @return
	 * @ctime 2018/12/11
	 */
	public List<Role> getRoleNotMoRenByCusid(String cusid){
		String sql="select * from Role where isdefault!=1 and cusid=?";
		List<Role> roleList =roleDao.findList(sql, new Object[]{cusid});
		return  roleList;
	}
}
