package com.bolu.system.dao;


import com.bolu.base.common.CurrentPage;
import com.bolu.system.bo.Fun;

public interface IFunDao extends IBaseDao<Fun> {
	public CurrentPage<Fun> getPages(int nowPage, int pageSize, Fun fun);
}
