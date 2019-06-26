package com.bolu.system.service;


import com.bolu.base.common.CurrentPage;
import com.bolu.system.bo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface IBaseService<T> {


	/***
	 * 添加
	 *
	 * @param map
	 *            添加的map集合
	 * @return
	 */
	public Boolean add(Map<String, Object> map);

	/**
	 * 通过 bean 添加
	 *
	 * @param obj
	 * @return
	 * @author pc
	 * @ctime 2019/5/17
	 */
	public Boolean add(T obj);

	/***
	 *
	 * @param map
	 *            修改的map集合
	 * @param id
	 *            修改的主键id 主键是String 类型
	 * @return
	 */
	public Boolean edit(Map<String, Object> map, Integer id);

	/***
	 *
	 * @param map
	 *            修改的map集合
	 * @param id
	 *            修改的主键id 主键是int 类型
	 * @return
	 */
	public Boolean edit(Map<String, Object> map, String id);


	/***
	 *
	 * @param obj
	 *            通过 bean 修改
	 * @return
	 */
	public Boolean edit(T obj);


	/***
	 *
	 * @param obj
	 *           通过 bean 保存
	 * @return
	 */
	public Boolean save(T obj);

	/***
	 * 根据id删除 id为int 类型
	 *
	 * @param id
	 *            主键id
	 * @return
	 */
	public Boolean del(Integer id);

	/***
	 * 根据id删除 id为String 类型
	 *
	 * @param id
	 *            主键id
	 * @return
	 */
	public Boolean del(String id);

	/***
	 * 通用删除
	 *
	 * @param str
	 *            删除的sql 语句
	 * @param params
	 *            参数
	 * @return
	 */
	public Boolean del(String str, Object[] params);


	/***
	 * 根据id 获取的对象 主键是int 类型
	 *
	 * @param id
	 * @return
	 */
	public T get(Integer id);

	/***
	 * 根据 id 获取对象 主键是 String
	 *
	 * @param id
	 * @return
	 */
	public T get(String id);


	/***
	 * 通用查询
	 *
	 * @param strSql
	 *            查询的sql 语句 查询的参数
	 * @param params
	 * @return
	 */
	public List findList(String strSql, Object[] params);

	/****
	 *
	 * @param sql
	 *            查询条件语句
	 * @param params
	 *            参数
	 * @return
	 */
	public Integer count(String sql, Object[] params);


	/****
	 *
	 * @param strWhere
	 *            查询条件语句
	 * @param params
	 *            参数
	 * @return
	 */
	public List query(String strWhere, Object[] params);

	/***
	 * @param strWhere
	 *            查询条件语句
	 * @param params
	 *            参数
	 * @param strOrder
	 *            排序语句
	 * @return
	 */
	public List query(String strWhere, Object[] params, String strOrder);



	/***
	 * 通用分页查询
	 * @param strC 统计记录数sql
	 * @param strQ 查询sql
	 * @param param 参数
	 * @param nowPage 页码
	 * @param pageSize  每页显示条数
	 * @return
	 */
	public CurrentPage<T> getPage(String strC, String strQ, Object[] param, int nowPage, int pageSize);


	/***
	 * 执行sql 语句 ,通用 修改 新增 删除
	 *
	 * @param sql
	 *            sql 语句
	 * @param params
	 *            参数
	 * @return
	 */
	public Boolean executeSQL(String sql, Object[] params);


	/**
	 * 更新sql  增,删，改 通用
	 *
	 * @param params
	 * @return
	 * @author pc
	 * @ctime 2019/1/30
	 */
	public Boolean update(String str, Object[] params);


	/**
	 * 批量 执行 sql语句 增,删，改 通用
	 *
	 * @param sql
	 * @return
	 * @author pc
	 * @ctime 2019/1/30
	 */
	public Boolean batchUpdate(String[] sql);


	/**
	 *获取当前 请求对象
	 * @author pc
	 * @return
	 * @ctime 2018/12/4
	 */
	public HttpServletRequest getcurrRequest();

	/**
	 * 获取当前 session 对象
	 *
	 * @return
	 */
	public HttpSession getcurrSession();

	/**
	 * 获取当前登录人
	 *
	 * @return
	 */
	public User getcurrUser();


	/**
	 *获取当前登陆用户的商户id
	 *
	 * @author pc
	 * @param
	 * @return
	 * @ctime 2019/1/28
	 */
	public String getCurrUserCusid();

	/**
	 *获取当前登陆用户的id
	 *
	 * @author pc
	 * @param
	 * @return
	 * @ctime 2019/1/28
	 */
	public String getCurrUserUserId();

	/***
	 * 获取项目根路径
	 * @return
	 */
	public String getBasePath();
}
