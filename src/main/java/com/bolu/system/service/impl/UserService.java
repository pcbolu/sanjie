package com.bolu.system.service.impl;


import com.bolu.base.common.CurrentPage;
import com.bolu.base.common.MD5Util;
import com.bolu.base.common.ReqParUtil;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.CusInfo;
import com.bolu.system.bo.Role;
import com.bolu.system.bo.User;
import com.bolu.system.dao.IUserDao;
import com.bolu.system.service.ICusInfoService;
import com.bolu.system.service.IRoleService;
import com.bolu.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class UserService extends BaseService implements IUserService {

	private IUserDao userDao;

	@Autowired
	public IRoleService roleService;
	@Autowired
	public ICusInfoService cusInfoService;

	@Autowired
	private void setDao(IUserDao userDao) {
		this.userDao = userDao;
		super.setBaseDao(userDao);
	}

	/***
	 * 添加用户
	 * @author pc
	 * @throws Exception
	 */
	public boolean addUser(User user) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(user.getId())) {
			user.setId(StringUtils.uuid());
		}
		if (StringUtils.isBlank(user.getRoleid())) {
			throw new RuntimeException("角色不能为空！");
		}
		if (StringUtils.isBlank(user.getCusid())) {
			throw new RuntimeException("商户不能为空！");
		}
		if (StringUtils.isBlank(user.getUsername())) {
			throw new RuntimeException("用户名称不能为空！");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			throw new RuntimeException("密码不能为空！");
		}
		if (null == user.getSta()) {
			user.setSta(1);
		}
		if(null==user.getIsdefault()) {
			user.setIsdefault(0);
		}
		//角色信息
		Role role=roleService.get(user.getRoleid());
		map.put("id", user.getId());
		map.put("roleid", user.getRoleid());
		map.put("cusid", role.getCusid());
		map.put("username", user.getUsername());
		map.put("password", MD5Util.MD5Encode(user.getPassword(), ""));
		if (StringUtils.isMeaningFul(user.getPhone())) {
			map.put("phone", user.getPhone());
		}
		map.put("sta", user.getSta());
		map.put("isdefault", user.getIsdefault());
		map.put("createTime", new Date());
		if (userDao.add(map)) {
			return true;
		}
		return false;
	}

	/**
	 * 修改用户
	 * @author pc
	 * @param user
	 * @param req
	 * @return
	 * @ctime 2018/12/4
	 */
	public boolean updateUser(User user, HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();
		User curruser= getcurrUser();
		if(StringUtils.isBlank(user.getId())) {
			throw new RuntimeException("修改失败，没有用户id");
		}
		if (StringUtils.isMeaningFul(user.getRoleid())) {
			map.put("roleid", user.getRoleid());
		}
		if (StringUtils.isMeaningFul(user.getCusid())) {
			map.put("cusid", user.getCusid());
		}
		if (StringUtils.isMeaningFul(user.getUsername())) {
			map.put("username", user.getUsername());
		}
		if (StringUtils.isMeaningFul(user.getPassword())) {
			// 加密之后 再插入
			map.put("password", MD5Util.MD5Encode(user.getPassword(), ""));
		}
		if (StringUtils.isMeaningFul(user.getPhone())) {
			map.put("phone", user.getPhone());
		}
		if (null != user.getSta()) {
			map.put("sta", user.getSta());
		}
		map.put("updateTime", new Date());
		if (userDao.edit(map, user.getId())) {
			//如果修改的是当前登陆人 的用户信息 ，需要更新 session 中的user 对象
			if(curruser.getId().trim().equals(user.getId().trim())) {
				User newuser =userDao.get(user.getId());
				//先清除之前 session 里 user
				req.getSession().removeAttribute("user");
				//添加新的 user 信息
				ReqParUtil.SessionSetAttr(req, "user", newuser); // 用户信息
			}
			return true;
		}
		return false;
	}


	/**
	 * 用户登录查询
	 * @author pc
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 * @ctime 2018/12/4
	 */
	public Map<String, Object> loginUser(String username, String password) {

		Map<String, Object> map = new HashMap<String, Object>();
		String sqlName = "select * from user where username=? and sta=1";
		// 首先验证改用户名称是否存在
		List<User> listUserName = userDao.findList(sqlName, new Object[] { username });
		if (null != listUserName && listUserName.size() > 0) {
			// 验证密码是否正确
			String sql = "select u.*,cs.cusName from user u left join cusinfo cs on u.cusid=cs.id where username=? and password=? and sta=1";
			password=MD5Util.MD5Encode(password, "");
			List<User> listUser = userDao.findList(sql, new Object[] { username, password });
			if (null != listUser && listUser.size() > 0) {
				User user = listUser.get(0);
				List<Role> roleList=roleService.getRoleById(user.getRoleid());
				if(null==roleList||roleList.size()==0){
					map.put("user", null);
					map.put("status", "NoRole");
					return map;
				}
				//获取上级parid
				try {
					CusInfo CusInfo =cusInfoService.get(user.getCusid());
					user.setParid(CusInfo.getPid());
					user.setCustype(CusInfo.getCusType());
				}
				catch (Exception e) {
					user.setParid("");
					user.setCustype("0");
				}
				map.put("user", user);
				map.put("status", "success");
			} else {
				map.put("user", null);
				map.put("status", "PasswordError");
			}
		} else {
			map.put("user", null);
			map.put("status", "NoUserName");
		}
		return map;
	}

	/**
	 * 根据用户名称 和密码查询
	 * @param user
	 * @author pc
	 * @return
	 * @ctime 2018/12/4
	 */
	public User getUserByuserNameAndPassword(User user) {
		String sql = "select * from user where 1=1 and username=? and password=?";
		if(StringUtils.isBlank(user.getUsername())) {
			throw new RuntimeException("用户名称不能为空！");
		}
		if(StringUtils.isBlank(user.getPassword())) {
			throw new RuntimeException("密码不能为空！");
		}
		List<Object> params= new ArrayList<Object>();
		params.add(user.getUsername());
		params.add(MD5Util.MD5Encode(user.getPassword(), ""));
		List<User> listUser = userDao.findList(sql, params.toArray());
		if (null != listUser && listUser.size() > 0) {
			return  listUser.get(0);
		}
		return null;
	}


	/***
	 * 根据商户id 查询用户
	 * @author pc
	 * @param cusId
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<User> getUserByCusId(String cusId) {
		String sql = "select * from user where cusid=?";
		List<User> userList = userDao.findList(sql, new Object[] { cusId });
		return userList;
	};

	/**
	 * 根据用户状态查询用户 信息列表
	 * @author pc
	 * @param sta
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<User> getUserBySta(Integer sta) {
		String sql = "select * from user where 1=1";
		List<Object> params = new ArrayList<Object>();
		if (null != sta) {
			sql += " and sta=?";
			params.add(sta);
		}
		List<User> userList = userDao.findList(sql, params.toArray());
		return userList;
	}

	/***
	 * 分页查询用户信息列表
	 * @author pc
	 * @param nowPage
	 *            当前页
	 * @param pageSize
	 *            每页显示条数
	 * @param user
	 *            参数对应，用户接受搜索的参数
	 * @return
	 * @ctime 2018/12/4
	 */
	public CurrentPage<User> getUserPage(int nowPage, int pageSize, User user) {
		List<Object> params = new ArrayList<Object>();
		String strQ = "select u.id,u.cusid,u.roleid,u.isdefault, u.username,ifnull(u.phone,'') phone ,u.createTime,  DATE_FORMAT(u.createTime,'%Y-%m-%d %H:%i') ctime,DATE_FORMAT(u.updateTime,'%Y-%m-%d %H:%i') utime,  u.sta,ifnull(cb.cusname,'系统账户') cusname,role.rolename "
				+ " from user u " + " left join CusInfo cb " + " on u.cusid=cb.id " + " left join Role role"
				+ " on u.roleid=role.id" + " where 1=1";
		String strC = "select count(u.id) " + " from user u " + " left join CusInfo cb" + " on u.cusid=cb.id"
				+ " left join Role role" + " on u.roleid=role.id" + " where 1=1";

		// 查询条件
		if (StringUtils.isMeaningFul(user.getUsername())) {
			strC += " and u.username like '%" + user.getUsername() + "%'";
			strQ += " and u.username like '%" + user.getUsername() + "%'";
		}
		if (StringUtils.isMeaningFul(user.getRoleid())) {
			strC += " and u.roleid =? ";
			strQ += " and u.roleid =? ";
			params.add(user.getRoleid());
		}
		if (StringUtils.isMeaningFul(user.getCusName())) {
			strC += " and cb.cusname like '%" + user.getCusName() + "%'";
			strQ += " and cb.cusname like '%" + user.getCusName() + "%'";
		}
		if (StringUtils.isMeaningFul(user.getCusid())) {
			strC += " and u.cusid =?";
			strQ += " and u.cusid =?";
			params.add(user.getCusid());
		}
		strQ+=" order by u.cusid, u.createTime desc";
		CurrentPage<User> currentPage = userDao.getPage(strC, strQ, params.toArray(), nowPage, pageSize);
		return currentPage;
	}


	/**
	 *用户登录 将用户信息放入session 中
	 * @author pc
	 * @param user
	 * @ctime 2018/12/4
	 */
	public void synLoginInfo(User user) {
		//获取当前 session对象
		HttpSession session= getcurrSession();
		session.setAttribute( "user", user);//将用户信息 放入 session 中
		session.setAttribute(  "isLogin", true);// 设置登录状态
		String sessionId= session.getId();

		Map<String,HttpSession> USER_SESSION= new HashMap<String, HttpSession>();
		USER_SESSION.put(user.getId(), session);
		session.setAttribute(  "USER_SESSION", USER_SESSION);

		Map<String,String> SESSIONID_USERID= new HashMap<String, String>();
		SESSIONID_USERID.put(session.getId(), user.getId());

	}

	/****
	 * 通过用户名称 查询
	 * @author pc
	 * @param username
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<User> getUserByUserName(String username){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select * from user where username=?";
		// 首先验证改用户名称是否存在
		List<User> listUserName = userDao.findList(sql, new Object[] { username });
		return listUserName;
	}

	/****
	 * 判断用户用户名称的唯一性
	 * @author pc
	 * @param username
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<User> checkUserNameOnly(String username, String userid){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select * from user where username=? and id <> ?";
		// 首先验证改用户名称是否存在(不包含本身)
		List<User> listUserName = userDao.findList(sql, new Object[] { username,userid });
		return listUserName;
	}

	/***
	 * 根据角色查询用户
	 * @author pc
	 * @param roleid
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<User> getUserByRoleId(String roleid) {
		String sql = "select * from user where roleid=?";
		List<User>  userList= userDao.findList(sql, new Object[] { roleid });
		return  userList;
	}

}
