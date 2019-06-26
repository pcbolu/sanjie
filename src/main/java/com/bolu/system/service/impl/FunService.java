package com.bolu.system.service.impl;


import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.*;
import com.bolu.system.dao.IFunDao;
import com.bolu.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FunService extends BaseService implements IFunService {


	private IFunDao funDao;
	@Autowired
	private ICustypefunService custypefunService;
	@Autowired
	private IRolefunService rolefunService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private ICusInfoService cusInfoService;

	@Autowired
	private void setDao(IFunDao funDao) {
		this.funDao = funDao;
		super.setBaseDao(funDao);
	}

	/**
	 * 添加功能菜单
	 * @author pc
	 * @param fun
	 * @return
	 * @ctime 2018/12/4
	 */
	public boolean addFun(Fun fun) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", fun.getName());
		map.put("control", fun.getControl());
		map.put("logo", fun.getLogo());
		map.put("num", fun.getNum());
		map.put("level", fun.getLevel());
		if (funDao.add(map)) {
			return true;
		}
		return false;
	}

	/**
	 * 修改功能菜单
	 * @author pc
	 * @param fun
	 * @return
	 * @ctime 2018/12/4
	 */
	public boolean updateFun(Fun fun) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", fun.getName());
		map.put("control", fun.getControl());
		map.put("logo", fun.getLogo());
		map.put("num", fun.getNum());
		map.put("level", fun.getLevel());
		if (funDao.edit(map, fun.getId())) {
			return true;
		}
		return false;
	}

	/**
	 * 根据 角色id 查询用户对应的权限功能菜单 （有选中属性）
	 * @author pc
	 * @param roleid
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Fun> getFunTreeByRoleId(String  roleid, Integer module) {
		Role role=roleService.get(roleid);
		String wheresql="";
		if(!(role.getCusid().equals("0"))) {			CusInfo cusInfo= cusInfoService.get(role.getCusid());
			wheresql=" and id in (select funid from custypefun where custypeid='"+cusInfo.getCusType()+"') ";
		}
		String treesql = "select * from ( "
				//查有权限的 并且子项全选中的
				+ " select id,name,num,1 checked from fun where id in (select funid  from Rolefun  where roleid=?  and entire=1 ) "+wheresql+" and  level=1   "
				//查有权限的 并且子项 未全选中的
				+ " union "
				+ " select id,name,num,2 checked from fun where id in (select funid  from Rolefun  where roleid=? and entire=2) "+wheresql+" and  level=1   "
				+ " union "
				//查没有权限的
				+ "select id,name,num,0 checked from fun where id not in ( select funid  from Rolefun  where roleid=? )  "+wheresql+"  and  level=1 "
				+ " ) a order by num ";

		List<Fun> treeone = funDao.findList(treesql, new Object[]{roleid,roleid,roleid});
		List<Fun> list = funTreeRole(treeone,roleid,wheresql);
		return list;
	}

	/**
	 * 根据 角色id 查询用户对应的权限功能菜单
	 * @author pc
	 * @param roleid
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Fun> getFunTreeByRoleId2(String  roleid) {
		String sql = "select * from fun where id in ("
				+ "    select  funid from Rolefun where roleid in ('" +roleid + "')"
				+ ")  and level =1 order by num";
		List<Fun> list = funTreeRole2(funDao.findList(sql, null), roleid);
		return list;
	}

	/**
	 * 根据 商户类型id 查询该类的商户的功能菜单 并构造成树形 （有 选中属性）
	 * @author pc
	 * @param customid
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Fun> getFunTreeByCustomId(String customid, Integer module) {
		String treesql = "select * from ( "
				//查有权限的 并且子项全选中的
				+ " select id,name,num,1 checked from fun where id in (select funid  from custypefun  where custypeid=?  and entire=1) and  level=1   "
				//查有权限的 并且子项 未全选中的
				+ " union "
				+ " select id,name,num,2 checked from fun where id in (select funid  from custypefun  where custypeid=? and entire=2) and  level=1   "
				+ " union "
				// 查没有权限的
				+ "select id,name,num,0 checked from fun where id not in ( select funid  from custypefun  where custypeid=? )  and  level=1  "
				+ " ) a order by num ";
		List<Fun> treeone = funDao.findList(treesql, new Object[]{customid,customid,customid});
		List<Fun> list = funTreeCustom2(treeone, customid);
		return list;
	}

	/**
	 * 根据 商户类型id 查询该类的商户对应类型 的功能菜单 并构造成树形
	 * @author pc
	 * @param customid
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Fun> getFunTreeByCustomId2(String customid) {
		List<Fun> list = null;
		if (StringUtils.isBlank(customid) || customid.equals("0")) {
			list = this.getFunTreeAll();
		} else {
			String sql = "select * from fun where  id in  "
					+ "( select funid from custypefun where custypeid=  (select custype from  CusInfo where id='"
					+ customid + "') )  and level =1 order by num ";
			List<Fun> listone = funDao.findList(sql, null);
			list = funTreeCustom(listone, customid);
		}

		return list;
	}

	/**
	 *
	 * @author pc
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Fun> getFunTreeAll() {
		String sql = "select * from fun where  level =1  order by num";
		List<Fun> list = funTreeAll(funDao.findList(sql, null));
		return list;

	}

	/**
	 * 根据 角色id 构造树形成结构
	 * @param list
	 * @param roleid
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Fun> funTreeRole2(List<Fun> list, String  roleid) {
		if (null != list && list.size() > 0) {
			for (Fun fun : list) {
				String sql = "select * from fun where id in ("
						+ "  select  funid from Rolefun where roleid in ('" +roleid + "')"
						+ ")  and parentid = ?  order by num";
				List<Fun> childList = funDao.findList(sql,  new Object[]{fun.getId()});
				if (null != childList && childList.size() > 0) {
					fun.setSubfun(childList);
					funTreeRole2(childList, roleid);
				}
			}
		}
		return list;
	}


	/***
	 * 根据 角色id 构造树形成结构（有选中属性）
	 * @author pc
	 * @param treeList
	 * @param roleid
	 * @param wheresql
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Fun> funTreeRole(List<Fun> treeList, String roleid, String wheresql) {
		if (null != treeList && treeList.size() > 0) {
			for (Fun tree : treeList) {
				String treesql = "select * from ( "
						//查 有权限的 并且子项全部选中
						+ "select id,name,num,1 as checked from fun where id in (select funid  from Rolefun  where roleid=? and entire=1 ) "+wheresql+" and   parentid=? "
						+" union "
						//查 有权限的 并且子项未全部选中
						+ "select id,name,num,2 as checked from fun where id in (select funid  from Rolefun  where roleid=? and entire=2 ) "+wheresql+" and   parentid=? "
						+ " union "
						//查没有权限的
						+ "select id,name,num,0 as checked from fun where id not in ( select funid  from Rolefun  where roleid=? )  "+wheresql+"  and parentid=? "
						+ ") a order by num";
				List<Fun> children = funDao.findList(treesql, new Object[]{roleid,tree.getId(),roleid,tree.getId(),roleid,tree.getId()});
				if (null != children && children.size() > 0) {
					tree.setSubfun(children);
					funTreeRole(children, roleid,wheresql);
				}
			}
		}
		return treeList;
	}


	/**
	 * 根据 商户类型id 构造树形成结构
	 * @author pc
	 * @param list
	 * @param customid
	 *            商户id
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Fun> funTreeCustom(List<Fun> list, String customid) {
		if (null != list && list.size() > 0) {
			for (Fun fun : list) {
				String sql = "select * from fun where id in ("
						+ "select funid from custypefun where custypeid=(select custype from  CusInfo where id='"
						+ customid + "') )  and parentid = " + fun.getId() + " order by num";
				List<Fun> childList = funDao.findList(sql, null);
				if (null != childList && childList.size() > 0) {
					fun.setSubfun(childList);
				}
			}
		}
		return list;
	}

	/**
	 * 构造树形结构
	 * @author pc
	 * @param list
	 * @return
	 */
	public List<Fun> funTreeAll(List<Fun> list) {
		if (null != list && list.size() > 0) {
			for (Fun fun : list) {
				String sql = "select * from fun where  parentid = " + fun.getId() + "  order by num";
				List<Fun> childList = funDao.findList(sql, null);
				if (null != childList && childList.size() > 0) {
					fun.setSubfun(childList);
					funTreeAll(childList);
				}
			}
		}
		return list;
	}

	/***
	 * 根据 商户类型id 构造树形成结构（有选中属性）
	 * @author pc
	 * @param treeList
	 * @param customid
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Fun> funTreeCustom2(List<Fun> treeList, String customid) {
		if (null != treeList && treeList.size() > 0) {
			for (Fun tree : treeList) {
				String treesql = "select * from ( "
						//查 有权限的 并且子项全部选中
						+ "select id,name,num,1 as checked from fun where id in (select funid  from custypefun  where custypeid='"+ customid + "' and entire=1 ) and   parentid=" + tree.getId()
						+" union "
						+ "select id,name,num,2 as checked from fun where id in (select funid  from custypefun  where custypeid='"+ customid + "' and entire=2) and   parentid=" + tree.getId()
						+ " union "
						//查没有权限的
						+ "select id,name,num,0 as checked from fun where id not in ( select funid  from custypefun  where custypeid='"+ customid + "')  and parentid=" + tree.getId() + ""
						+ ") a order by num";
				List<Fun> children = funDao.findList(treesql, null);
				if (null != children && children.size() > 0) {
					tree.setSubfun(children);
					funTreeCustom2(children, customid);
				}
			}
		}
		return treeList;
	}

	/***
	 * 商户类型授权
	 * @author pc
	 * @param custypeid
	 * @param list
	 * @return
	 * @throws Exception
	 * @ctime 2018/12/4
	 */
	@Transactional
	public Boolean saveCustomFun(String custypeid, List<Custypefun> list) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String delSql = "";
		if(StringUtils.isBlank(custypeid)){
			throw new RuntimeException("没有找到商户类型id");
		}
		delSql = "delete from custypefun where custypeid=? ";
		// 删除原有的权限记录
		Boolean isdel = funDao.del(delSql, new Object[]{custypeid});
		if (isdel) {
			List<RoleFun> rolefunlist= new ArrayList<RoleFun>();
			//重新权限添加记录
			for(Custypefun custypefun:list) {
				custypefun.setCustypeid(custypeid);
				Boolean idadd = custypefunService.addCustypefun(custypefun);
				if (!idadd) {
					throw new RuntimeException("添加数据错误!");
				}
				RoleFun rolefun = new RoleFun();
				rolefun.setEntire(custypefun.getEntire());
				rolefun.setFunid(custypefun.getFunid());
				rolefunlist.add(rolefun);
			}
			//该类型商户下默认角色的权限也要相应的改变，其他角色 删除角色中有，但商户类型中没有的的功能
			//查询 该类型下 所有商户信息
			List<CusInfo> listcb = cusInfoService.getCusinfoByCustype(custypeid);



			if(null!=listcb&&listcb.size()>0) {
				for(CusInfo cb : listcb) {
					//查询商户默认的角色，为默认角色重新授权
					Role role=roleService.getRoleMoRenByCusid(cb.getId());
					this.saveRoleFun(role.getId(),rolefunlist);

					//查询商户非默认的角色
					List<Role> roleList=roleService.getRoleNotMoRenByCusid(cb.getId());
					//删除角色中有，但商户类型中没有的的功能
					for (Role slRole : roleList) {
						String delfunsql="delete from Rolefun where roleid=? and funid not in " +
								"(select funid from custypefun where custypeid=?)";
						boolean del = roleService.del(delfunsql, new Object[]{slRole.getId(),cb.getCusType()});
					}
				}
			}
			return true;
		} else {
			throw new RuntimeException(" 删除原有权限错误！");
		}
	}

	/***
	 * 角色授权
	 * @author pc
	 * @param roleid
	 * @param list
	 * @return
	 * @throws Exception
	 * @ctime 2018/12/4
	 */
	@Transactional
	public Boolean saveRoleFun(String roleid, List<RoleFun> list) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String delSql = "";
		if (null != roleid) {
			delSql = "delete from Rolefun where roleid=?";
			params.add(roleid);
		} else {
			throw new RuntimeException("没有找到角色id");
		}
		// 删除原有的权限记录
		Boolean isdel = funDao.del(delSql, params.toArray());
		if (isdel) {

			for(RoleFun rolefun:list) {
				rolefun.setRoleid(roleid);
				// 重新权限添加记录
				Boolean idadd = rolefunService.addRolefun(rolefun);
				if (!idadd) {
					throw new RuntimeException("添加数据错误!");
				}
			}
			return true;
		} else {
			throw new RuntimeException("删除原有权限错误！");
		}
	}

	/**
	 *根据模块标志 获取相应的功能列表
	 *
	 * @author pc
	 * @param module
	 * @return
	 * @ctime 2018/12/25
	 */
	public List<Fun> getFunByModule(Integer module, String cusid){
		String sql=" select * from fun  where 1=1 and id in "
				+" (select funid from custypefun where custypeid in (select custype from CusInfo where id=?) "
				+")";
		List<Fun> funList=funDao.findList(sql,new Object[]{cusid});
		return funList;
	}

}
