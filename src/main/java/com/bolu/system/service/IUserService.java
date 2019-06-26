package com.bolu.system.service;


import com.bolu.base.common.CurrentPage;
import com.bolu.system.bo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IUserService extends IBaseService {

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	public boolean addUser(User user) throws Exception;

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user, HttpServletRequest req);

	/***
	 * 用户登录 查询
	 *
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public Map<String, Object> loginUser(String username, String password);

	/**
	 * 根据商户ID查询用户信息列表
	 *
	 * @param cusId
	 * @return
	 */
	public List<User> getUserByCusId(String cusId);

	/**
	 * 分页查询用户信息列表
	 *
	 * @param nowPage
	 * @param pageSize
	 * @param user
	 * @return
	 */
	public CurrentPage<User> getUserPage(int nowPage, int pageSize, User user);

	/***
	 * 根据用户名称和密码查询
	 * @return
	 */
	public User getUserByuserNameAndPassword(User user);

		/****
	 * 通过用户名称 查询
	 * @param username
	 * @return
	 */
	public List<User> getUserByUserName(String username);

	/****
	 * 判断用户用户名称的唯一性
	 * @param username
	 * @return
	 */
	public List<User> checkUserNameOnly(String username, String userid);

	/***
	 * 根据角色查询用户
	 * @param roleid
	 * @return
	 */
	public List<User> getUserByRoleId(String roleid);

}
