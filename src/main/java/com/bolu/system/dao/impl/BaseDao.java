package com.bolu.system.dao.impl;

import com.bolu.base.common.CurrentPage;
import com.bolu.base.common.PaginationHelper;
import com.bolu.system.dao.IBaseDao;
import com.bolu.system.util.ReflectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BaseDao<T> implements IBaseDao<T> {
	private static final Logger logger = Logger.getLogger(BaseDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<T> rowMapper;

	private String  tableName;

	private Class<T> entityClass;


	public BaseDao() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) type.getActualTypeArguments()[0];
		this.tableName= ReflectUtils.getTableName(entityClass);
		this.rowMapper=BeanPropertyRowMapper.newInstance(entityClass);
	}

	/***
	 * 添加
	 *
	 * @param map
	 *            添加的map集合
	 * @return
	 */
	public Boolean add(Map<String, Object> map) {
		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer sbn = new StringBuffer();
			StringBuffer sbv = new StringBuffer();
			sb.append("insert into  ");
			sb.append(tableName);
			sbn.append("(");
			sbv.append(" values(");
			List<Object> list = new ArrayList<Object>();
			list.clear();
			int mlen = map.size();
			int i = 1;
			Iterator item = map.entrySet().iterator();
			while (item.hasNext()) {
				Map.Entry entry = (Map.Entry) item.next();
				String key = entry.getKey().toString();
				Object val = entry.getValue();
				if (i < mlen) {
					sbn.append(key);
					sbn.append(",");

					sbv.append("?,");
				} else {
					sbn.append(key);
					sbn.append(")");

					sbv.append("?)");
				}
				list.add(val);
				i++;
			}
			sb.append(sbn.toString());
			sb.append(sbv.toString());
			String str = sb.toString();
			Object[] param;
			if (list.size() > 0)
				param = list.toArray();
			else
				param = new Object[]{};
			jdbcTemplate.update(str, param);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("数据库插入操作异常:" + ex.toString());
			throw new RuntimeException("数据库插入操作异常:" + ex.getMessage());
		}
	}


	/**
	 * 通过 bean 添加
	 *
	 * @param entity
	 * @return
	 * @author pc
	 * @ctime 2019/5/17
	 */
	public Boolean add(T entity) {
		try {
			String idValue = ReflectUtils.getIdValue(entity);
			if (null == idValue || (null != idValue && (idValue.trim()).length() == 0)) {
				throw new RuntimeException("主键值不能为空！");
			}
			//获取 bean @table 的name属性值 表名
			String tableName = ReflectUtils.getTableName(entity.getClass());
			//获取对象的值，封装到map中
			Map<String, Object> map = ReflectUtils.getNameAndValue(entity);

			StringBuffer sb = new StringBuffer();
			StringBuffer sbn = new StringBuffer();
			StringBuffer sbv = new StringBuffer();
			sb.append("insert into  ");
			sb.append(tableName);
			sbn.append("(");
			sbv.append(" values(");

			List<Object> list = new ArrayList<Object>();
			list.clear();
			int mlen = map.size();
			int i = 1;
			Iterator item = map.entrySet().iterator();
			while (item.hasNext()) {
				Map.Entry entry = (Map.Entry) item.next();
				String key = entry.getKey().toString();
				Object val = entry.getValue();
				if (i < mlen) {
					sbn.append(key);
					sbn.append(",");

					sbv.append("?,");
				} else {
					sbn.append(key);
					sbn.append(")");
					sbv.append("?)");
				}
				list.add(val);

				i++;
			}
			sb.append(sbn.toString());
			sb.append(sbv.toString());

			String str = sb.toString();
			Object[] param;
			if (list.size() > 0)
				param = list.toArray();
			else
				param = new Object[]{};
			jdbcTemplate.update(str, param);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("数据库增加操作异常:" + ex.toString());
			throw new RuntimeException("数据库增加操作异常:" + ex.getMessage());
		}
	}


	/***
	 *
	 * @param map
	 *            修改的map集合
	 * @param id
	 *            修改的主键id 主键是String 类型
	 * @return
	 */
	public Boolean edit(Map<String, Object> map, String id) {
		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer sbn = new StringBuffer();
			StringBuffer sbv = new StringBuffer();
			sb.append("update ");
			sb.append(tableName);
			sb.append(" set ");
			sbn.append("");
			sbv.append(" where id=?");
			List<Object> list = new ArrayList<Object>();
			list.clear();
			int mlen = map.size();
			int i = 1;
			Iterator item = map.entrySet().iterator();
			while (item.hasNext()) {
				Map.Entry entry = (Map.Entry) item.next();
				String key = entry.getKey().toString();
				Object val = entry.getValue();

				if (i < mlen) {
					sbn.append(key);
					sbn.append("=?,");
				} else {
					sbn.append(key);
					sbn.append("=?");
				}
				list.add(val);
				i++;
			}
			list.add(id);
			sb.append(sbn.toString());
			sb.append(sbv.toString());
			String str = sb.toString();
			Object[] param;
			if (list.size() > 0)
				param = list.toArray();
			else
				param = new Object[]{};
			jdbcTemplate.update(str, param);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("数据库修改操作异常:" + ex.toString());
			throw new RuntimeException("数据库修改操作异常:" + ex.getMessage());
		}
	}


	/***
	 *
	 * @param map
	 *            修改的map集合
	 * @param id
	 *            修改的主键id 主键是int 类型
	 * @return
	 */
	public Boolean edit(Map<String, Object> map, Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer sbn = new StringBuffer();
			StringBuffer sbv = new StringBuffer();


			sb.append("update ");
			sb.append(tableName);
			sb.append(" set ");
			sbn.append("");
			sbv.append(" where id=?");
			List<Object> list = new ArrayList<Object>();
			list.clear();
			int mlen = map.size();
			int i = 1;
			Iterator item = map.entrySet().iterator();
			while (item.hasNext()) {
				Map.Entry entry = (Map.Entry) item.next();
				String key = entry.getKey().toString();
				Object val = entry.getValue();
				if (i < mlen) {
					sbn.append(key);
					sbn.append("=?,");
				} else {
					sbn.append(key);
					sbn.append("=?");
				}
				list.add(val);

				i++;
			}
			list.add(id);
			sb.append(sbn.toString());
			sb.append(sbv.toString());
			String str = sb.toString();
			Object[] param;
			if (list.size() > 0)
				param = list.toArray();
			else
				param = new Object[]{};
			jdbcTemplate.update(str, param);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("数据库修改操作异常:" + ex.toString());
			throw new RuntimeException("数据库修改操作异常:" + ex.getMessage());
		}
	}

	/***
	 *
	 * @param entity
	 *            通过 bean 修改
	 * @return
	 */
	public Boolean edit(T entity) {
		try {
			String idValue = ReflectUtils.getIdValue(entity);
			if (null == idValue || (null != idValue && (idValue.trim()).length() == 0)) {
				throw new RuntimeException("主键值不能为空！");
			}
			//获取 bean @table 的name属性值 表名
			String tableName = ReflectUtils.getTableName(entity.getClass());
			//获取对象的值，封装到map中
			Map<String, Object> map = ReflectUtils.getNameAndValue(entity);

			StringBuffer sb = new StringBuffer();
			StringBuffer sbn = new StringBuffer();
			StringBuffer sbv = new StringBuffer();
			sb.append("update ");
			sb.append(tableName);
			sb.append(" set ");
			sbn.append("");
			sbv.append(" where id=?");
			List<Object> list = new ArrayList<Object>();
			list.clear();

			int mlen = map.size();
			int i = 1;
			Iterator item = map.entrySet().iterator();
			while (item.hasNext()) {
				Map.Entry entry = (Map.Entry) item.next();
				String key = entry.getKey().toString();
				Object val = entry.getValue();

				if (i < mlen) {
					sbn.append(key);
					sbn.append("=?,");
				} else {
					sbn.append(key);
					sbn.append("=?");
				}
				list.add(val);
				i++;
			}
			list.add(idValue);
			sb.append(sbn.toString());
			sb.append(sbv.toString());
			String str = sb.toString();
			Object[] param;
			if (list.size() > 0)
				param = list.toArray();
			else
				param = new Object[]{};
			jdbcTemplate.update(str, param);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("数据库修改操作异常:" + ex.toString());
			throw new RuntimeException("数据库修改操作异常:" + ex.getMessage());
		}
	}

	/***
	 *
	 * @param entity
	 *           通过 bean 保存
	 * @return
	 */
	public Boolean save(T entity) {
		String idValue = ReflectUtils.getIdValue(entity);
		if (null == idValue || (null != idValue && (idValue.trim()).length() == 0)) {
			throw new RuntimeException("主键值不能为空！");
		}
		T oldObj = get(idValue);
		if (null != oldObj) {
			return edit(entity);
		} else {
			return add(entity);
		}
	}


	/***
	 * 根据id删除 id为int 类型
	 *
	 * @param id
	 *            主键id
	 * @return
	 */
	public Boolean del(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("delete from  ");
			sb.append(tableName);
			sb.append("  where id=?");
			String str = sb.toString();
			Object[] params = new Object[]{id};
			int re = jdbcTemplate.update(str, params);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("数据库删除操作异常:" + ex.toString());
			throw new RuntimeException("数据库删除操作异常:" + ex.getMessage());
		}
	}


	/***
	 * 根据id删除 id为String 类型
	 *
	 * @param id
	 *            主键id
	 * @return
	 */
	public Boolean del(String id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("delete from  ");
			sb.append(tableName);
			sb.append("  where id=?");
			String str = sb.toString();
			Object[] params = new Object[]{id};
			int re = jdbcTemplate.update(str, params);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("数据库删除操作异常:" + ex.toString());
			throw new RuntimeException("数据库删除操作异常:" + ex.getMessage());
		}
	}


	/***
	 * 通用删除
	 *
	 * @param str
	 *            删除的sql 语句
	 * @param params
	 *            参数
	 * @return
	 */
	public Boolean del(String str, Object[] params) {
		try {
			int re = jdbcTemplate.update(str, params);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("数据删除操作异常:" + ex.toString());
			throw new RuntimeException("数据删除操作异常:" + ex.getMessage());
		}
	}


	/***
	 * 根据id 获取对象 id为String 类型
	 *
	 * @param id
	 *            主键id
	 * @return
	 */
	public T get(String id) {
		try {
			T objs;
			StringBuffer sb = new StringBuffer();
			sb.append("select * from ");
			sb.append(tableName);
			sb.append(" where id=");
			sb.append("'" + id + "'");
			String str = sb.toString();
			List list = (List) jdbcTemplate.query(str, rowMapper);
			if (list.isEmpty())
				objs = null;
			else
				objs = (T) list.get(0);
			return objs;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("get 数据库查询异常:" + ex.toString());
			throw new RuntimeException("get 数据库查询异常:" + ex.getMessage());
		}
	}


	/**
	 * 根据id 获取对象 id类型为 int 类型
	 *
	 * @param id 主键id
	 * @return
	 */
	public T get(Integer id) {
		try {
			T objs;
			StringBuffer sb = new StringBuffer();
			sb.append("select * from ");
			sb.append(tableName);
			sb.append(" where id=");
			sb.append(id);
			String str = sb.toString();
			List list = (List) jdbcTemplate.query(str, rowMapper);
			if (list.isEmpty())
				objs = null;
			else
				objs = (T) list.get(0);
			return objs;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("get 数据库查询异常:" + ex.toString());
			throw new RuntimeException("get 数据库查询异常:" + ex.getMessage());
		}
	}


	/***
	 * 通用查询
	 *
	 * @param strSql
	 *            查询的sql 语句 查询的参数
	 * @param params
	 * @return
	 */
	public List<T> findList(String strSql, Object[] params) {
		try {
			String str = strSql;
			List list = (List) jdbcTemplate.query(str, params, rowMapper);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("查询数据出现异常" + ex.toString());
			throw new RuntimeException("查询数据出现异常" + ex.getMessage());
		}
	}


	/****
	 *
	 * @param sql 查询条件语句
	 * @param params 参数
	 * @return
	 */
	public Integer count(String sql, Object[] params) {
		try {
			int re = jdbcTemplate.queryForObject(sql, params,Integer.class);
			return re;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("count 数据库查询异常:" + ex.toString());
			throw new RuntimeException("count 数据库查询异常:" + ex.getMessage());
		}
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
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select * from ");
			sb.append(tableName);
			if (!strWhere.equals("")) {
				sb.append(" where ");
				sb.append(strWhere);
			}
			String str = sb.toString();
			List list = (List) jdbcTemplate.query(str, rowMapper);



			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("query 数据库查询异常:" + ex.toString());
			throw new RuntimeException("query 数据库查询异常:" + ex.getMessage());
		}
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
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select * from ");
			sb.append(tableName);
			if (!strWhere.equals("")) {
				sb.append(" where ");
				sb.append(strWhere);
			}
			if (!strOrder.equals("")) {
				sb.append(" ");
				sb.append(strOrder);
			}
			String str = sb.toString();
			List list = (List) jdbcTemplate.query(str, rowMapper);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("query 数据库查询异常:" + ex.toString());
			throw new RuntimeException("query 数据库查询异常:" + ex.getMessage());
		}
	}

	/***
	 * 通用分页查询
	 *
	 * @param strC
	 *            统计记录数sql
	 * @param strQ
	 *            查询sql
	 * @param param
	 *            参数
	 * @param nowPage
	 *            页码
	 * @param pageSize
	 *            每页显示条数
	 * @return
	 */
	public CurrentPage<T> getPage(String strC, String strQ, Object[] param, int nowPage, int pageSize) {
		PaginationHelper<T> ph = new PaginationHelper<T>();
		try {
			CurrentPage<T> p = ph.fetchPage(jdbcTemplate, strC, strQ, param, nowPage, pageSize, rowMapper);
			return p;
		} catch (EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			return new CurrentPage<T>();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("getPage 查询数据出现异常" + ex.toString());
			throw new RuntimeException("getPage 查询数据出现异常" + ex.getMessage());
		}
	}


	/***
	 * 执行sql 语句，修改 新增 删除都可
	 *
	 * @param sql
	 *            sql 语句
	 * @param params
	 *            参数
	 * @return
	 */
	public Boolean executeSQL(String sql, Object[] params) {
		try {
			jdbcTemplate.update(sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("执行 sql 语句异常:" + ex.toString());
			throw new RuntimeException("执行 sql 语句异常:" + ex.getMessage());
		}
		return true;
	}

	/**
	 * 更新sql  增,删，改 通用
	 *
	 * @param params
	 * @return
	 * @author pc
	 * @ctime 2019/1/30
	 */
	@Override
	public Boolean update(String str, Object[] params) {
		try {
			jdbcTemplate.update(str, params);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("update 语句出现异常" + ex.getMessage());
			throw new RuntimeException("update 语句出现异常" + ex.getMessage());
		}
	}


	/**
	 * 批量 执行 sql语句 增,删，改 通用
	 *
	 * @param sql
	 * @return
	 * @author pc
	 * @ctime 2019/1/30
	 */
	@Override
	public Boolean batchUpdate(String[] sql) {
		try {
			jdbcTemplate.batchUpdate((String[]) sql);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("batchUpdate 执行sql 语句出现异常" + ex.getMessage());
			throw new RuntimeException("batchUpdate 执行sql 语句出现异常" + ex.getMessage());
		}
	}
}
