package com.bolu.system.service.impl;


import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.Custypefun;
import com.bolu.system.dao.ICustypefunDao;
import com.bolu.system.service.ICustypefunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustypefunService extends BaseService implements ICustypefunService {

	private ICustypefunDao custypefunDao;

	@Autowired
	private void setDao(ICustypefunDao custypefunDao) {
		this.custypefunDao = custypefunDao;
		super.setBaseDao(custypefunDao);
	}


	/**
	 * 添加商户类别功能
	 * @author pc
	 * @param custypefun
	 * @return
	 * @throws Exception
	 */
	public Boolean addCustypefun(Custypefun custypefun) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(custypefun.getId())) {
			custypefun.setId(StringUtils.uuid());
		}
		if (StringUtils.isBlank(custypefun.getCustypeid())) {
			throw new RuntimeException("商户类型id不能为空！");
		}
		if (null == custypefun.getFunid()) {
			throw new RuntimeException("功能菜单id 不能为空！");
		}
		if (null == custypefun.getEntire()) {
			throw new RuntimeException("全选状态不能为空！");
		}

		map.put("id", custypefun.getId());
		map.put("custypeid", custypefun.getCustypeid());
		map.put("funid", custypefun.getFunid());
		map.put("entire", custypefun.getEntire());
		if (custypefunDao.add(map)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据商户类型id查询 商户类型权限
	 * @author pc
	 * @param custypeid
	 * @return
	 * @ctime 2018/12/4
	 */
	public List<Custypefun> getCustypefunByCustomId(String custypeid) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from custypefun where 1=1 ";
		if (null != custypeid) {
			sql += " and custypeid = ? ";
			params.add(custypeid);
			List<Custypefun> list = custypefunDao.findList(sql, params.toArray());
			return list;
		} else {
			return null;
		}
	}
}
