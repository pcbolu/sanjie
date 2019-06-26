package com.bolu.system.service.impl;

import com.bolu.base.common.DateHelper;
import com.bolu.system.bo.VisitLink;
import com.bolu.system.bo.VisitRecord;
import com.bolu.system.dao.IVisitLinkDao;
import com.bolu.system.dao.impl.VisitLinkDao;
import com.bolu.system.service.IVisitLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VisitLinkService extends BaseService<VisitLink> implements IVisitLinkService {

    private IVisitLinkDao visitLinkDao;

    @Autowired
    private void setDao(VisitLinkDao visitLinkDao) {
        this.visitLinkDao = visitLinkDao;
        super.setBaseDao(visitLinkDao);
    }


    /***
     * 根据value 查询生访问链接
     * @param value
     * @return
     */
    public VisitLink getVisitLinkByValue(String value, Date endTime) {
        String sql = "select * from VisitLink v where 1=1 and v.value=? and v.endTime>? order by createTime desc";
        List<VisitLink> visitLinkList = visitLinkDao.findList(sql, new Object[]{value,endTime});
        if(visitLinkList.size()>0){
            return visitLinkList.get(0);
        }
        return null;
    }





}
