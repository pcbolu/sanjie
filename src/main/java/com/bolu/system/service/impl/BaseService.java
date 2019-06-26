package com.bolu.system.service.impl;


import com.bolu.base.common.CurrentPage;
import com.bolu.system.bo.User;
import com.bolu.system.dao.IBaseDao;
import com.bolu.system.service.IBaseService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class BaseService<T> implements IBaseService<T> {

	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Boolean add(Map<String, Object> map) {
		return baseDao.add(map);
	}

	@Override
	public Boolean add(T obj) {
		return baseDao.add(obj);
	}

	@Override
	public Boolean edit(Map<String, Object> map, Integer id) {
		return baseDao.edit(map,id);
	}

	@Override
	public Boolean edit(Map<String, Object> map, String id) {
		return baseDao.edit(map,id);
	}

	@Override
	public Boolean edit(T obj) {
		return baseDao.edit(obj);
	}

	@Override
	public Boolean save(T obj) {
		return baseDao.save(obj);
	}

	@Override
	public Boolean del(Integer id) {
		return baseDao.del(id);
	}

	@Override
	public Boolean del(String id) {
		return baseDao.del(id);
	}

	@Override
	public Boolean del(String str, Object[] params) {
		return baseDao.del(str,params);
	}

	@Override
	public T get(Integer id) {
		return (T)baseDao.get(id);
	}

	@Override
	public T get(String id) {
		return (T)baseDao.get(id);
	}

	/***
	 * 通用查询
	 *
	 * @param strSql
	 *            查询的sql 语句 查询的参数
	 * @param params
	 * @return
	 */


	public List findList(String strSql, Object[] params) {
		return baseDao.findList(strSql, params);
	}

	/****
	 *
	 * @param sql
	 *            查询条件语句
	 * @param params
	 *            参数
	 * @return
	 */
	public Integer count(String sql, Object[] params) {
		return baseDao.count(sql, params);
	}

	/****
	 *
	 * @param strWhere
	 *            查询条件语句
	 * @param params
	 *            参数
	 * @return
	 */
	public List query(String strWhere, Object[] params) {
		return baseDao.query(strWhere, params);
	}

	/***
	 * @param strWhere
	 *            查询条件语句
	 * @param params
	 *            参数
	 * @param strOrder
	 *            排序语句
	 * @return
	 */
	public List query(String strWhere, Object[] params, String strOrder) {
		return baseDao.query(strWhere, params, strOrder);
	}


	/**
	 * 分页查询 strC 统计记录数的查询语句
	 * <p>
	 * strQ 查询语句
	 * <p>
	 * params参数列表
	 * <p>
	 * nowpage 当前页码
	 * <p>
	 * pageSize 每页显示条数
	 */
	public CurrentPage getPage(String strC, String strQ, Object[] param, int nowPage, int pageSize) {
		return baseDao.getPage(strC, strQ, param, nowPage, pageSize);
	}




	/***
	 * 执行sql 语句 ,通用 修改 新增 删除
	 *
	 * @param sql
	 *            sql 语句
	 * @param params
	 *            参数
	 * @return
	 */
	public Boolean executeSQL(String sql, Object[] params) {
		return baseDao.executeSQL(sql, params);
	}


	/**
	 * 更新sql  增,删，改 通用
	 *
	 * @param params
	 * @return
	 * @author pc
	 * @ctime 2019/1/30
	 */
	public Boolean update(String str, Object[] params){
		return baseDao.update(str,params);
	}


	/**
	 * 批量 执行 sql语句 增,删，改 通用
	 *
	 * @param sql
	 * @return
	 * @author pc
	 * @ctime 2019/1/30
	 */
	public Boolean batchUpdate(String[] sql){
		return baseDao.batchUpdate(sql);
	}
	/**
	 * 获取当前 请求对象
	 *
	 * @return
	 */
	public HttpServletRequest getcurrRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		return request;
	}

	/**
	 * 获取当前 session 对象
	 *
	 * @return
	 */
	public HttpSession getcurrSession() {
		HttpSession session = getcurrRequest().getSession();
		return session;
	}

	/**
	 * 获取当前登录人
	 *
	 * @return
	 */
	public User getcurrUser() {
		User user = (User) getcurrSession().getAttribute("user");
		return user;
	}

	/**
	 * 获取当前登陆用户的商户id
	 *
	 * @param
	 * @return
	 * @author pc
	 * @ctime 2019/1/28
	 */
	public String getCurrUserCusid() {
		User user = getcurrUser();
		return user.getCusid();
	}

	/**
	 * 获取当前登陆用户的id
	 *
	 * @param
	 * @return
	 * @author pc
	 * @ctime 2019/1/28
	 */
	public String getCurrUserUserId() {
		User user = getcurrUser();
		return user.getId();
	}

	/***
	 * 获取项目根路径
	 * @return
	 */
	public String getBasePath() {
		String path = getcurrRequest().getContextPath();
		String basePath = getcurrRequest().getScheme() + "://" + getcurrRequest().getServerName() + ":" + getcurrRequest().getServerPort() + path + "/";
		return basePath;
	}
	
}
