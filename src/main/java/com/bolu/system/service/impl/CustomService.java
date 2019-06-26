package com.bolu.system.service.impl;


import com.bolu.base.common.CurrentPage;
import com.bolu.system.bo.Custom;
import com.bolu.system.dao.ICustomDao;
import com.bolu.system.service.ICustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomService extends BaseService<Custom> implements ICustomService {

    private ICustomDao customDao;

    @Autowired
    private void setDao(ICustomDao customDao) {
        this.customDao = customDao;
        super.setBaseDao(customDao);
    }

    /**
     * 分页 商戶类型 查询
     *
     * @param nowPage
     * @param pageSize
     * @param custom
     * @return
     * @author pc
     * @ctime 2018/12/4
     */
    public CurrentPage<Custom> getCustomPage(int nowPage, int pageSize, Custom custom) {
        List<Object> param = new ArrayList<Object>();
        String strQ = "select  *  from custom  where 1=1  ";
        String strC = "select count(id)  from  custom  where 1=1  ";
        CurrentPage<Custom> currentPage = customDao.getPage(strC, strQ, param.toArray(), nowPage, pageSize);
        return currentPage;
    }

}
