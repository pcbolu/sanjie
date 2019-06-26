package com.bolu.system.dao.impl;


import com.bolu.base.common.CurrentPage;
import com.bolu.system.bo.Fun;
import com.bolu.system.dao.IFunDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class FunDao extends BaseDao<Fun> implements IFunDao {


	public CurrentPage<Fun> getPages(int nowPage, int pageSize, Fun fun) {
		// TODO Auto-generated method stub
		String strC = "select count(*)" +
				" from fun";
		String strQ = "SELECT *" +
				" from fun";

		//处理多参数情况
		List<Object> list = new ArrayList<Object>();

		//列表转参数
		Object[] param;
		if(list.size()>0)
			param = list.toArray(new Object[1]);
		else
			param = new Object[] {};

		return getPage(strC,strQ,param,nowPage,pageSize);
	}
}
