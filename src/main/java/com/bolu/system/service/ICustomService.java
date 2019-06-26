package com.bolu.system.service;


import com.bolu.base.common.CurrentPage;
import com.bolu.system.bo.Custom;

public interface ICustomService extends IBaseService<Custom> {

	public CurrentPage<Custom> getCustomPage(int nowPage, int pageSize, Custom custom);

}
