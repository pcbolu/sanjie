package com.bolu.system.service;

import com.bolu.system.bo.VisitLink;

import java.util.Date;

public interface IVisitLinkService   extends IBaseService<VisitLink> {

    /***
     * 根据value 查询生访问链接
     * @param value
     * @return
     */
    public VisitLink getVisitLinkByValue(String value, Date enddate);


}
